package com.funny;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;
import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public interface CustomUser32 extends StdCallLibrary {
        CustomUser32 INSTANCE = Native.load("user32", CustomUser32.class);

        // Menangkap return value boolean untuk validasi sukses/gagal
        boolean SystemParametersInfoA(int uiAction, int uiParam, Pointer pvParam, int fWinIni);
        boolean SetSystemCursor(Pointer hcur, int id);
        Pointer CreateCursor(Pointer hInst, int x, int y, int w, int h, byte[] andMask, byte[] xorMask);
    }

    public static final int SPI_SETCURSORS = 0x0057;

    // Daftar ID kursor sistem agar semua state (Text, Hand, dll) ikut tersembunyi
    private static final int[] ALL_CURSOR_IDS = {
            32512, 32513, 32514, 32515, 32516, 32642, 32643, 32644, 32645, 32646, 32648, 32649, 32650, 32651
    };

    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");

        // FIX: Method reference untuk kebersihan kode
        Runtime.getRuntime().addShutdownHook(new Thread(Main::resetSystemCursor));

        SwingUtilities.invokeLater(() -> {
            CursorOverlay overlay = new CursorOverlay();
            overlay.setVisible(true);
            GlobalMouse.init(overlay);

            JFrame settings = new JFrame("Absurd Mouse - Ultimate Clean");
            settings.setSize(400, 550);
            settings.setLayout(new GridLayout(11, 1, 5, 5));
            settings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Slider Kontrol Utama
            JSlider sizeS = new JSlider(5, 1000, 200);
            sizeS.addChangeListener(e -> overlay.updateSize(sizeS.getValue()));

            JSlider offXS = new JSlider(-200, 200, 0);
            JSlider offYS = new JSlider(-200, 200, 0);
            offXS.addChangeListener(e -> overlay.setManualOffsets(offXS.getValue(), offYS.getValue()));
            offYS.addChangeListener(e -> overlay.setManualOffsets(offXS.getValue(), offYS.getValue()));

            JCheckBox chkHide = new JCheckBox("🙈 Hide ALL System Cursors");
            chkHide.addActionListener(e -> toggleAllNativeCursors(!chkHide.isSelected()));

            JButton btnReset = new JButton("🔄 Force System Cursor Reset");
            btnReset.addActionListener(e -> {
                resetSystemCursor();
                chkHide.setSelected(false);
            });

            JButton btnPick = new JButton("📁 Load Custom Icon");
            btnPick.addActionListener(e -> {
                JFileChooser ch = new JFileChooser();
                if (ch.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    overlay.setIcon(ch.getSelectedFile());
                }
            });

            settings.add(new JLabel("  Cursor Size:")); settings.add(sizeS);
            settings.add(new JLabel("  Calibration Offset X:")); settings.add(offXS);
            settings.add(new JLabel("  Calibration Offset Y:")); settings.add(offYS);
            settings.add(btnPick); settings.add(chkHide); settings.add(btnReset);

            settings.setLocationRelativeTo(null);
            settings.setVisible(true);
        });
    }

    private static void toggleAllNativeCursors(boolean visible) {
        if (visible) {
            resetSystemCursor();
        } else {
            byte[] andMask = new byte[128];
            java.util.Arrays.fill(andMask, (byte) 0xFF);
            byte[] xorMask = new byte[128];

            for (int id : ALL_CURSOR_IDS) {
                Pointer blank = CustomUser32.INSTANCE.CreateCursor(null, 0, 0, 32, 32, andMask, xorMask);
                if (blank != null) {
                    // FIX: Menangani return value dari SetSystemCursor
                    boolean ok = CustomUser32.INSTANCE.SetSystemCursor(blank, id);
                    if (!ok) LOGGER.warning("Gagal menyembunyikan kursor ID: " + id);
                }
            }
            LOGGER.info("Seluruh state kursor berhasil disembunyikan.");
        }
    }

    private static void resetSystemCursor() {
        // FIX: Menangani return value dari SystemParametersInfoA
        boolean success = CustomUser32.INSTANCE.SystemParametersInfoA(SPI_SETCURSORS, 0, null, 0);
        if (!success) {
            LOGGER.warning("Gagal mereset kursor sistem via Win32 API.");
        } else {
            LOGGER.info("Kursor sistem berhasil dipulihkan.");
        }
    }
}