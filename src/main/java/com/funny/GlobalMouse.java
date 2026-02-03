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

/**
 * Handles system-wide mouse event hooks and window click-through configuration.
 */
public class GlobalMouse {
    private static final Logger LOGGER = Logger.getLogger(GlobalMouse.class.getName());

    public static void init(CursorOverlay overlay) {
        // Disable internal JNativeHook logging to keep console clean
        Logger jnhLogger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        jnhLogger.setLevel(Level.OFF);
        jnhLogger.setUseParentHandlers(false);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            LOGGER.log(Level.SEVERE, "Failed to register Global Native Hook. Tracking will be disabled.", e);
            return;
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

        // Use JNA to set the window as Click-Through (transparent to mouse input)
        WinDef.HWND hwnd = new WinDef.HWND(Native.getWindowPointer(overlay));
        int wl = User32.INSTANCE.GetWindowLong(hwnd, WinUser.GWL_EXSTYLE);
        User32.INSTANCE.SetWindowLong(hwnd, WinUser.GWL_EXSTYLE, wl | WinUser.WS_EX_LAYERED | WinUser.WS_EX_TRANSPARENT);

        LOGGER.info("Global Mouse Tracking and Click-Through initialized successfully.");
    }
}