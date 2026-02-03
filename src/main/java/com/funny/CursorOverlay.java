package com.funny;

import com.sun.jna.Native;

import com.sun.jna.platform.win32.User32;

import com.sun.jna.platform.win32.WinDef;

import com.sun.jna.platform.win32.WinUser;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class CursorOverlay extends JWindow {
    private int cursorSize = 100;

    public CursorOverlay() {
        // Penting: Mengatur background transparan total
        setBackground(new Color(0, 0, 0, 0));
        setAlwaysOnTop(true);
        setFocusableWindowState(false);
        setType(Type.UTILITY);
        // Ukuran canvas besar agar kursor raksasa tidak terpotong saat di pinggir layar
        setSize(2000, 2000);
    }
    public void makeClickThrough() {
        WinDef.HWND hwnd = new WinDef.HWND(Native.getWindowPointer(this));
        int wl = User32.INSTANCE.GetWindowLong(hwnd, WinUser.GWL_EXSTYLE);
        User32.INSTANCE.SetWindowLong(hwnd, WinUser.GWL_EXSTYLE,
                wl | WinUser.WS_EX_LAYERED | WinUser.WS_EX_TRANSPARENT);
    }

    public void setCursorSize(int size) {
        this.cursorSize = size;
        repaint();
    }

    public void updatePosition(int x, int y) {
        // Hotspot Alignment:
        // Titik (0,0) pada window ini akan diletakkan tepat di ujung kursor asli
        setLocation(x, y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        // Menghaluskan garis kursor
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Skala pengali (Base size kursor standar adalah 18 unit)
        double s = cursorSize / 18.0;

        // Path kursor yang lebih akurat dengan ujung tepat di 0,0
        Path2D cursorPath = new Path2D.Double();
        cursorPath.moveTo(0, 0);                         // Ujung (Hotspot)
        cursorPath.lineTo(0, 15 * s);                    // Sisi kiri
        cursorPath.lineTo(4.5 * s, 11 * s);              // Lekukan bawah kiri
        cursorPath.lineTo(7.5 * s, 18 * s);              // Ekor kiri
        cursorPath.lineTo(9.5 * s, 17 * s);              // Ekor kanan
        cursorPath.lineTo(6.5 * s, 10.5 * s);            // Lekukan bawah kanan
        cursorPath.lineTo(11 * s, 10.5 * s);             // Sisi kanan
        cursorPath.closePath();

        // Gambar isi kursor (Putih)
        g2d.setColor(Color.WHITE);
        g2d.fill(cursorPath);

        // Gambar garis tepi (Hitam)
        g2d.setColor(Color.BLACK);
        float strokeWidth = (float) Math.max(1.5, s * 0.7);
        g2d.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.draw(cursorPath);
    }
}