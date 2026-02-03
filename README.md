# Absurd Mouse

[**Bahasa Indonesia**](#bahasa-indonesia) | [**English**](#english)

---

## Bahasa Indonesia

### Background
Proyek ini dikembangkan menggunakan pendekatan **Vibe Coding** bersama **Gemini**. Fokus utamanya adalah eksplorasi teknis dalam menggabungkan Java Swing dengan Windows Native API untuk memanipulasi perilaku jendela di tingkat sistem operasi secara dinamis.

### Demo
![Absurd Mouse Demo](https://github.com/user-attachments/assets/cd7cfa0d-1955-4636-80c0-e8edeb749759)

### Fitur
- **Dynamic Scaling**: Mengubah ukuran kursor secara *real-time* dari 32px hingga 1000px melalui UI slider.
- **Native Click-Through**: Mengimplementasikan Windows API agar jendela kursor tidak menghalangi input mouse pada aplikasi di latar belakang.
- **Global Mouse Tracking**: Memantau pergerakan mouse di seluruh sistem melalui *native hooks*, bahkan saat aplikasi kehilangan fokus.
- **DPI Awareness**: Mengatur properti `sun.java2d.uiScale` ke `1.0` untuk memastikan posisi kursor raksasa sinkron 1:1 dengan kursor asli.

### Prasyarat
- **Java JDK 17** (Sesuai konfigurasi di `pom.xml`).
- **Windows OS** (Diperlukan untuk JNA Win32).
- **Apache Maven**.

---

## English

### Background
This project was developed using the **Vibe Coding** approach with **Gemini**. The main focus is a technical exploration of combining Java Swing with the Windows Native API to dynamically manipulate window behavior at the OS level.

### Demo
![Absurd Mouse Demo](https://github.com/user-attachments/assets/cd7cfa0d-1955-4636-80c0-e8edeb749759)

### Features
- **Dynamic Scaling**: Change cursor size in real-time from 32px to 1000px via UI slider.
- **Native Click-Through**: Implements Windows API so the cursor window does not block mouse input for background applications.
- **Global Mouse Tracking**: Tracks mouse movement system-wide via native hooks, even when the app loses focus.
- **DPI Awareness**: Sets `sun.java2d.uiScale` to `1.0` to ensure the giant cursor position stays 1:1 in sync with the native cursor.

### Prerequisites
- **Java JDK 17** (As configured in `pom.xml`).
- **Windows OS** (Required for JNA Win32 integration).
- **Apache Maven**.

---

## Installation & Technical Details (Common)

### Build & Run
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.funny.Main"
