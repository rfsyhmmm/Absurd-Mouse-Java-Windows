package com.funny;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;
import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    // Penggunaan LOGGER yang konsisten untuk menghilangkan peringatan "unused"
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public interface CustomUser32 extends StdCallLibrary {
        CustomUser32 INSTANCE = Native.load("user32", CustomUser32.class);

        boolean SystemParametersInfoA(int uiAction, int uiParam, Pointer pvParam, int fWinIni);

        // Fungsi untuk menyuntikkan kursor baru ke sistem
        boolean SetSystemCursor(Pointer hcur, int id);

        // Fungsi untuk membuat kursor kosong di memori
        Pointer CreateCursor(Pointer hInst, int x, int y, int w, int h, byte[] andMask, byte[] xorMask);
    }

    public static final int SPI_SETCURSORS = 0x0057;
    public static final int OCR_NORMAL = 32512;

    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");

        // Shutdown Hook: Menggunakan method reference untuk kebersihan kode
        Runtime.getRuntime().addShutdownHook(new Thread(Main::resetSystemCursor));

        SwingUtilities.invokeLater(() -> {
            CursorOverlay overlay = new CursorOverlay();
            overlay.setVisible(true);
            GlobalMouse.init(overlay);

            JFrame settings = new JFrame("Absurd Mouse - Pro Calibration");
            settings.setSize(400, 550);
            settings.setLayout(new GridLayout(11, 1, 5, 5));
            settings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Ukuran Utama
            JSlider sizeS = new JSlider(5, 1000, 200);
            sizeS.addChangeListener(e -> overlay.updateSize(sizeS.getValue()));

            // Kalibrasi Offset (Menangani Scaling Error)
            JSlider offXS = new JSlider(-200, 200, 0);
            JSlider offYS = new JSlider(-200, 200, 0);
            offXS.addChangeListener(e -> overlay.setManualOffsets(offXS.getValue(), offYS.getValue()));
            offYS.addChangeListener(e -> overlay.setManualOffsets(offXS.getValue(), offYS.getValue()));

            // Fitur Hide Mouse
            JCheckBox chkHide = new JCheckBox("🙈 Hide Native Mouse (System Global)");
            chkHide.addActionListener(e -> toggleNativeCursor(!chkHide.isSelected()));

            // Reset Darurat
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
            settings.add(btnPick);
            settings.add(chkHide);
            settings.add(btnReset);

            settings.setLocationRelativeTo(null);
            settings.setVisible(true);
        });
    }

    /**
     * Menangani peringatan "Return value of method is never used" dengan mengecek status sukses.
     */
    private static void toggleNativeCursor(boolean visible) {
        if (visible) {
            resetSystemCursor();
        } else {
            byte[] andMask = new byte[128];
            java.util.Arrays.fill(andMask, (byte) 0xFF);
            byte[] xorMask = new byte[128];

            // Menangkap return value dari CreateCursor
            Pointer blank = CustomUser32.INSTANCE.CreateCursor(null, 0, 0, 32, 32, andMask, xorMask);
            if (blank == null) {
                LOGGER.log(Level.SEVERE, "Gagal membuat kursor kosong di memori.");
                return;
            }

            // Menangkap return value dari SetSystemCursor untuk menghilangkan peringatan
            boolean success = CustomUser32.INSTANCE.SetSystemCursor(blank, OCR_NORMAL);
            if (!success) {
                LOGGER.warning("Sistem menolak perintah penyembunyian kursor (SetSystemCursor).");
            } else {
                LOGGER.info("Kursor asli berhasil disembunyikan secara global.");
            }
        }
    }

    private static void resetSystemCursor() {
        // Menangkap return value dari SystemParametersInfoA
        boolean success = CustomUser32.INSTANCE.SystemParametersInfoA(SPI_SETCURSORS, 0, null, 0);
        if (!success) {
            LOGGER.warning("Gagal mereset kursor sistem via Win32 API.");
        } else {
            LOGGER.info("Kursor sistem berhasil dikembalikan ke pengaturan default.");
        }
    }
}