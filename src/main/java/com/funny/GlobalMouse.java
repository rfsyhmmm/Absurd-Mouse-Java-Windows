package com.funny;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;

public class GlobalMouse implements NativeMouseMotionListener {
    private final CursorOverlay overlay;

    public GlobalMouse(CursorOverlay overlay) {
        this.overlay = overlay;
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {
        overlay.updatePosition(e.getX(), e.getY());
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e) {
        overlay.updatePosition(e.getX(), e.getY());
    }
}