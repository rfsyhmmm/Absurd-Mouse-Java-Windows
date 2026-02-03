package com.funny;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;
import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

/**
 * Main entry point for the Absurd Mouse application.
 * Manages the UI controller and Win32 API interactions for cursor manipulation.
 */
public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public interface CustomUser32 extends StdCallLibrary {
        CustomUser32 INSTANCE = Native.load("user32", CustomUser32.class);

        boolean SystemParametersInfoA(int uiAction, int uiParam, Pointer pvParam, int fWinIni);
        boolean SetSystemCursor(Pointer hcur, int id);
        Pointer CreateCursor(Pointer hInst, int x, int y, int w, int h, byte[] andMask, byte[] xorMask);
    }

    public static final int SPI_SETCURSORS = 0x0057;

    // List of Windows System Cursor IDs to be hidden across all states (Arrow, Text, Hand, etc.)
    private static final int[] ALL_CURSOR_IDS = {
            32512, 32513, 32514, 32515, 32516, 32642, 32643, 32644, 32645, 32646, 32648, 32649, 32650, 32651
    };

    public static void main(String[] args) {
        // Disable automatic DPI scaling for precise 1:1 pixel positioning
        System.setProperty("sun.java2d.uiScale", "1.0");

        // Safety Hook: Ensure system pointers are restored when the app is closed
        Runtime.getRuntime().addShutdownHook(new Thread(Main::restoreSystemPointers));

        SwingUtilities.invokeLater(() -> {
            CursorOverlay overlay = new CursorOverlay();
            overlay.setVisible(true);
            GlobalMouse.init(overlay);

            JFrame settings = new JFrame("Absurd Mouse - Configuration v1.0.0");
            settings.setSize(400, 550);
            settings.setLayout(new GridLayout(11, 1, 5, 5));
            settings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Size Control
            JSlider sizeS = new JSlider(5, 1000, 200);
            sizeS.addChangeListener(e -> overlay.updateSize(sizeS.getValue()));

            // Manual Calibration Sliders (Offset X & Y)
            JSlider offXS = new JSlider(-200, 200, 0);
            JSlider offYS = new JSlider(-200, 200, 0);
            offXS.addChangeListener(e -> overlay.setManualOffsets(offXS.getValue(), offYS.getValue()));
            offYS.addChangeListener(e -> overlay.setManualOffsets(offXS.getValue(), offYS.getValue()));

            // Pointer Visibility Control
            JCheckBox chkHide = new JCheckBox("🙈 Hide All System Pointers (Global)");
            chkHide.addActionListener(e -> toggleAllPointersVisibility(!chkHide.isSelected()));

            // Emergency Recovery Button
            JButton btnReset = new JButton("🔄 Restore Original Pointers");
            btnReset.addActionListener(e -> {
                restoreSystemPointers();
                chkHide.setSelected(false);
            });

            // Custom Icon Loader
            JButton btnPick = new JButton("📁 Load Custom Cursor Image");
            btnPick.addActionListener(e -> {
                JFileChooser ch = new JFileChooser();
                if (ch.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    overlay.setIcon(ch.getSelectedFile());
                }
            });

            settings.add(new JLabel("  Current Cursor Size:")); settings.add(sizeS);
            settings.add(new JLabel("  Manual Offset X:")); settings.add(offXS);
            settings.add(new JLabel("  Manual Offset Y:")); settings.add(offYS);
            settings.add(btnPick); settings.add(chkHide); settings.add(btnReset);

            settings.setLocationRelativeTo(null);
            settings.setVisible(true);
        });
    }

    private static void toggleAllPointersVisibility(boolean visible) {
        if (visible) {
            restoreSystemPointers();
        } else {
            byte[] andMask = new byte[128];
            java.util.Arrays.fill(andMask, (byte) 0xFF); // Full transparency mask
            byte[] xorMask = new byte[128];

            for (int id : ALL_CURSOR_IDS) {
                Pointer blank = CustomUser32.INSTANCE.CreateCursor(null, 0, 0, 32, 32, andMask, xorMask);
                if (blank != null) {
                    boolean ok = CustomUser32.INSTANCE.SetSystemCursor(blank, id);
                    if (!ok) LOGGER.warning("Failed to hide pointer state ID: " + id);
                }
            }
            LOGGER.info("All system pointers successfully hidden.");
        }
    }

    private static void restoreSystemPointers() {
        // Restore all pointers to default Windows theme from registry
        boolean success = CustomUser32.INSTANCE.SystemParametersInfoA(SPI_SETCURSORS, 0, null, 0);
        if (!success) {
            LOGGER.warning("Win32 API: Failed to restore system pointers.");
        } else {
            LOGGER.info("System pointers successfully restored to default.");
        }
    }
}