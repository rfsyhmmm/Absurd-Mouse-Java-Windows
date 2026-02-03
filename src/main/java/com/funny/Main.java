package com.funny;

import org.jnativehook.GlobalScreen;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // SOLUSI PRESISI: Mematikan UI Scaling agar koordinat overlay pas dengan kursor
        System.setProperty("sun.java2d.uiScale", "1.0");

        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        try {
            GlobalScreen.registerNativeHook();

            CursorOverlay overlay = new CursorOverlay();
            overlay.setVisible(true);
            overlay.makeClickThrough();

            GlobalScreen.addNativeMouseMotionListener(new GlobalMouse(overlay));

            // UI Slider
            JFrame frame = new JFrame("Absurd Mouse");
            frame.setSize(300, 100);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JSlider slider = new JSlider(32, 1000, 100);
            slider.addChangeListener(e -> overlay.setCursorSize(slider.getValue()));

            frame.add(slider);
            frame.setAlwaysOnTop(true);
            frame.setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}