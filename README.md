# Simple Absurd Mouse for WIndows

A Java-based Windows utility to create a comically large, click-through cursor overlay.

## Background
Proyek ini dikembangkan menggunakan pendekatan **Vibe Coding** bersama **Gemini**. Fokus utamanya adalah eksplorasi teknis dalam menggabungkan Java Swing dengan Windows Native API untuk memanipulasi perilaku jendela di tingkat sistem operasi secara dinamis.

## Demo
<div align="center">
  <video src="https://github.com/user-attachments/assets/e8429159-203e-4266-ac4b-7bd2e3f32323" width="100%" autoplay loop muted playsinline>
    Your browser does not support the video tag.
  </video>
</div>

## Features
- **Dynamic Scaling**: Mengubah ukuran kursor dari 32px hingga 1000px secara real-time melalui UI slider.
- **Native Click-Through**: Menggunakan Windows API agar kursor raksasa tidak menghalangi input mouse pada aplikasi lain di latar belakang.
- **Global Mouse Tracking**: Mendeteksi pergerakan mouse di seluruh sistem melalui native hooks, bahkan saat aplikasi kehilangan fokus.
- **High-Precision Alignment**: Implementasi DPI awareness (UI Scale 1.0) untuk memastikan ujung kursor raksasa tepat menimpa ujung kursor asli dengan akurasi 1:1.

## Prerequisites
- **Java JDK 17** (Target kompatibilitas utama).
- **Windows OS** (Diperlukan untuk pemanggilan fungsi Win32 API via JNA).
- **Apache Maven** untuk manajemen dependensi dan proses build.

## Installation & Usage

1. **Clone Repository**
   ```bash
   git clone [https://github.com/rfsyhmmm/Absurd-Mouse-Java-Windows.git](https://github.com/rfsyhmmm/Absurd-Mouse-Java-Windows.git)
   cd absurd-mouse
