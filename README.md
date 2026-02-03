# 🖱️ Absurd Mouse

<p align="left">
  <img src="https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java Version">
  <img src="https://img.shields.io/badge/Platform-Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white" alt="Platform">
  <img src="https://img.shields.io/badge/License-MIT-green?style=for-the-badge" alt="License">
</p>

[**🇮🇩 Bahasa Indonesia**](#bahasa-indonesia) | [**🇬🇧 English**](#english)

---

## 🇮🇩 Bahasa Indonesia

### 📝 Latar Belakang
Proyek ini dikembangkan menggunakan pendekatan **Vibe Coding** bersama **Gemini**. Fokus utamanya adalah eksplorasi teknis dalam menggabungkan Java Swing dengan Windows Native API untuk memanipulasi perilaku jendela di tingkat sistem operasi secara dinamis.

### 🎥 Demo
![Absurd Mouse Demo](https://github.com/user-attachments/assets/cd7cfa0d-1955-4636-80c0-e8edeb749759)

### ✨ Fitur Utama
- **Dynamic Scaling**: Mengubah ukuran kursor secara *real-time* dari 32px hingga 1000px melalui UI slider.
- **Native Click-Through**: Mengimplementasikan Windows API agar jendela kursor tidak menghalangi input mouse pada aplikasi lain di latar belakang.
- **Global Mouse Tracking**: Memantau pergerakan mouse di seluruh sistem melalui *native hooks*, bahkan saat aplikasi kehilangan fokus.
- **DPI Awareness**: Sinkronisasi kursor raksasa tetap 1:1 dengan kursor asli pada layar resolusi tinggi.

### 🛠️ Prasyarat
- **Java JDK 17**.
- **Windows OS** (Win32 API integration).
- **Apache Maven**.

---

## 🇬🇧 English

### 📝 Background
This project was developed using the **Vibe Coding** approach with **Gemini**. The main focus is a technical exploration of combining Java Swing with the Windows Native API to dynamically manipulate window behavior at the OS level.

### 🎥 Demo
![Absurd Mouse Demo](https://github.com/user-attachments/assets/cd7cfa0d-1955-4636-80c0-e8edeb749759)

### ✨ Key Features
- **Dynamic Scaling**: Change cursor size in real-time from 32px to 1000px via UI slider.
- **Native Click-Through**: Implements Windows API so the cursor window does not block mouse input for background applications.
- **Global Mouse Tracking**: Tracks mouse movement system-wide via native hooks, even when the app loses focus.
- **DPI Awareness**: Ensures the giant cursor position stays in 1:1 sync with the native cursor.

### 🛠️ Prerequisites
- **Java JDK 17**.
- **Windows OS** (Required for JNA Win32 integration).
- **Apache Maven**.

---

## 🚀 Installation & Technical Details (Common)

### Build & Run
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.funny.Main"
