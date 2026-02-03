package com.funny;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CursorOverlay extends JWindow {
    private static final Logger LOGGER = Logger.getLogger(CursorOverlay.class.getName());
    private int cursorSize = 200;
    private int offX = 0, offY = 0; // Kalibrasi manual
    private BufferedImage customIcon;

    public CursorOverlay() {
        setBackground(new Color(0, 0, 0, 0));
        setAlwaysOnTop(true);
        setSize(5000, 5000);
    }

    public void setIcon(File file) {
        try {
            BufferedImage raw = ImageIO.read(file);
            this.customIcon = trimImage(raw);
            repaint();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Gagal memuat gambar", e);
        }
    }

    private BufferedImage trimImage(BufferedImage img) {
        int width = img.getWidth(), height = img.getHeight();
        int top = height, left = width, bottom = 0, right = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int alpha = (img.getRGB(x, y) >> 24) & 0xff;
                // THRESHOLD: Abaikan pixel yang sangat transparan (noise background)
                if (alpha > 15) {
                    if (x < left) left = x;
                    if (y < top) top = y;
                    if (x > right) right = x;
                    if (y > bottom) bottom = y;
                }
            }
        }
        if (right < left || bottom < top) return img;
        return img.getSubimage(left, top, (right - left) + 1, (bottom - top) + 1);
    }

    public void updateSize(int size) { this.cursorSize = size; repaint(); }

    // Update offset manual dari UI
    public void setManualOffsets(int x, int y) {
        this.offX = x;
        this.offY = y;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        if (customIcon != null) {
            // Gambar digeser berdasarkan offset manual (dikali skala agar konsisten)
            g2d.drawImage(customIcon, offX, offY, cursorSize, cursorSize, null);
        } else {
            drawDefaultWhiteCursor(g2d, cursorSize);
        }
    }

    private void drawDefaultWhiteCursor(Graphics2D g2d, int s) {
        Path2D p = new Path2D.Double();
        p.moveTo(0, 0); p.lineTo(0, s*0.7); p.lineTo(s*0.2, s*0.5); p.lineTo(s*0.4, s*0.8);
        p.lineTo(s*0.5, s*0.75); p.lineTo(s*0.3, s*0.45); p.lineTo(s*0.5, s*0.45); p.closePath();
        g2d.setColor(Color.WHITE); g2d.fill(p);
        g2d.setColor(Color.BLACK); g2d.setStroke(new BasicStroke(Math.max(1, s*0.02f))); g2d.draw(p);
    }
}