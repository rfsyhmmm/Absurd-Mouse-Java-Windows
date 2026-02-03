# 🖱️ Absurd Mouse

<p align="left">
  <img src="https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java Version">
  <img src="https://img.shields.io/badge/Platform-Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white" alt="Platform">
  <img src="https://img.shields.io/badge/License-MIT-green?style=for-the-badge" alt="License">
</p>

---

# Absurd Mouse

Absurd Mouse is a lightweight Windows utility built in Java that replaces your standard system pointers with a gigantic, customizable cursor. Utilizing the Win32 API via JNA, this tool provides a seamless, click-through overlay that tracks your mouse across the entire operating system.

---

## 📌 Version
**Latest Release**: [v1.0.0](https://github.com/rfsyhmmm/Absurd-Mouse-Java-Windows/releases/tag/v1.0.0)

---

## 📺 Demos

### Dynamic Scaling
Easily adjust the cursor size from a standard pointer to an absurdly large 1000px icon.

![Scale Demo](https://github.com/user-attachments/assets/97f6f31b-c314-4f20-81e6-b1bcb4e3770c)

### Custom Image Support
Load any `.png` with a transparent background to use as your cursor. Includes an **Adjustable Offset** feature to ensure your "hotspot" stays perfectly aligned with the native click point.

![Image Custom Demo](https://github.com/user-attachments/assets/6a451692-d27a-4059-9c19-d92c69262d22)

---

## 🚀 Key Features

* **Ultimate Pointer Hiding**: Globally hides all Windows cursor states, including the standard arrow, text I-beam, hand pointers, and loading icons.
* **Precision Tracking**: Implements high-performance global mouse hooks using JNativeHook for 1:1 movement accuracy.
* **Auto-Trim Technology**: Scans and crops transparent edges of custom images using alpha thresholding to maintain perfect cursor alignment.
* **Manual Calibration**: Includes X and Y offset sliders to fine-tune the "hotspot" for complex custom images.
* **Safety First**: Built-in JVM shutdown hooks and an emergency "Restore" button ensure your original Windows pointers are always recovered.
* **Click-Through Overlay**: The cursor window uses `WS_EX_TRANSPARENT` styles, allowing you to click through the giant cursor and interact with windows behind it.

---

## 📂 Project Structure

* **`Main.java`**: The configuration UI and controller for Win32 API interactions (`SetSystemCursor`, `CreateCursor`).
* **`CursorOverlay.java`**: The rendering engine responsible for the transparent JWindow and image processing.
* **`GlobalMouse.java`**: Handles native system hooks and configures the overlay for click-through behavior.

---

## ⚠️ Important Note
This application manipulates system-wide pointers. If the application crashes, your cursors might remain hidden. Use the **"Restore Original Pointers"** button or restart your computer to reset them to the default Windows theme.

---

**Version**: 1.0.0  
**License**: MIT  
**Author**: rfsyhmmm
