package com.funny;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalMouse {
    // Inisialisasi Logger untuk mencatat aktivitas hook
    private static final Logger LOGGER = Logger.getLogger(GlobalMouse.class.getName());

    public static void init(CursorOverlay overlay) {
        // Matikan log internal JNativeHook agar tidak memenuhi konsol
        Logger jnhLogger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        jnhLogger.setLevel(Level.OFF);
        jnhLogger.setUseParentHandlers(false);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            // Mencatat error jika gagal mendaftarkan hook sistem
            LOGGER.log(Level.SEVERE, "Gagal mendaftarkan Global Native Hook. Tracking mouse tidak akan berfungsi.", e);
            return; // Hentikan proses jika hook gagal agar tidak terjadi error berantai
        }

        GlobalScreen.addNativeMouseMotionListener(new NativeMouseMotionListener() {
            @Override
            public void nativeMouseMoved(NativeMouseEvent e) {
                overlay.setLocation(e.getX(), e.getY());
            }

            @Override
            public void nativeMouseDragged(NativeMouseEvent e) {
                overlay.setLocation(e.getX(), e.getY());
            }
        });

        // Konfigurasi JNA agar jendela overlay tidak menangkap klik (click-through)
        WinDef.HWND hwnd = new WinDef.HWND(Native.getWindowPointer(overlay));
        int wl = User32.INSTANCE.GetWindowLong(hwnd, WinUser.GWL_EXSTYLE);
        User32.INSTANCE.SetWindowLong(hwnd, WinUser.GWL_EXSTYLE, wl | WinUser.WS_EX_LAYERED | WinUser.WS_EX_TRANSPARENT);
    }
}