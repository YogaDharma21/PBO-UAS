# Aplikasi Manajemen Perpustakaan - Library Management System

## Deskripsi Proyek

Aplikasi Manajemen Perpustakaan adalah sebuah aplikasi desktop berbasis Java yang dirancang untuk mengelola data perpustakaan secara komprehensif. Aplikasi ini memungkinkan pengguna untuk mengelola data buku, peminjaman dan pengunjung dengan antarmuka grafis yang user-friendly.

## Tim Pengembang

| No  | Nama                              | NIM       |
| --- | --------------------------------- | --------- |
| 1   | Ida Bagus Yoga Dharma Putra       | 240040002 |
| 2   | I Komang Rizky Trigana Putra      | 240040038 |
| 3   | Nanda Aditya Hamzah               | 240040125 |
| 4   | I Dewa Agung Teguh Pradnyaningrat | 240040155 |
| 5   | I Putu Adi Tirta Saputra          | 240040008 |

## Fitur Utama

- **Manajemen Buku**: Tambah, edit, hapus, dan cari data buku
- **Manajemen Peminjaman**: Kelola data peminjaman dan pengembalian buku
- **Manajemen Pengunjung**: Catat data pengunjung perpustakaan
- **Sistem Login**: Autentikasi pengguna untuk keamanan data
- **Dashboard**: Tampilkan informasi ringkas perpustakaanterstruktur

## Struktur Folder

```
pbo-uas/
├── README.md                          # Dokumentasi proyek
├── src/                               # Folder sumber kode
│   ├── App.java                       # Entry point aplikasi
│   ├── DatabaseProcess/
│   │   └── Koneksi.java               # Koneksi database JDBC
│   ├── DataModel/
│   │   ├── DataBooks.java             # Model data buku
│   │   ├── DataLoans.java             # Model data peminjaman
│   │   ├── DataUser.java              # Model data pengguna
│   │   └── DataVisitors.java          # Model data pengunjung
│   ├── MaintainDataPackage/
│   │   └── MaintainDatabase.java      # CRUD operations
│   └── ProjectInterface/
│       ├── LoginFrame.java            # Form login
│       ├── LibraryFrame.java          # Jendela utama aplikasi
│       ├── BooksPanel.java            # Panel manajemen buku
│       ├── LoansPanel.java            # Panel manajemen peminjaman
│       ├── VisitorsPanel.java         # Panel manajemen pengunjung
│       └── DashboardPanel.java        # Panel dashboard
├── bin/                               # Folder output kompilasi
├── lib/                               # Folder untuk library eksternal
└── database/
    ├── pbo_uas.sql                    # Schema dan data awal
    └── pbo_uas.json                   # File data JSON
```

## Teknologi yang Digunakan

- **Bahasa Pemrograman**: Java (JDK 8+)
- **GUI Framework**: Java Swing
- **Database**: MySQL/SQL Server
- **JDBC Driver**: MySQL Connector Java atau sesuai database yang digunakan
- **IDE**: Visual Studio Code dengan Extension Java

## Instalasi dan Setup

### Prasyarat

1. Java Development Kit (JDK) 8 atau lebih tinggi
2. MySQL atau database SQL lainnya
3. JDBC Driver yang sesuai (contoh: mysql-connector-java)
4. VS Code dengan Extension Java

### Langkah-langkah Instalasi

1. **Clone atau Download Proyek**

    ```bash
    git clone <repository-url>
    cd pbo-uas
    ```

2. **Setup Database**
    - Buka **phpMyAdmin** di browser Anda
    - Buat database baru dengan nama `pbo_uas`
    - Pilih database `pbo_uas` dan klik tab **Import**
    - Pilih file `database/pbo_uas.sql` dari folder proyek
    - Klik tombol **Import** untuk mengimpor schema dan data awal

3. **Konfigurasi Koneksi Database**
    - Buka file `src/DatabaseProcess/Koneksi.java`
    - Sesuaikan parameter koneksi:
        - `url`: alamat database
        - `user`: username database
        - `password`: password database
        - `driver`: driver JDBC yang digunakan

4. **Setup Library Eksternal**
    - Letakkan JDBC driver di folder `lib/`
    - Contoh: `lib/mysql-connector-java-8.0.33.jar`

## Cara Menjalankan

### Kompilasi

```bash
javac -d bin `
  src/App.java `
  src/DatabaseProcess/Koneksi.java `
  src/DataModel/*.java `
  src/MaintainDataPackage/*.java `
  src/ProjectInterface/*.java
```

### Jalankan Aplikasi

```bash
java -cp "bin;lib/*" App
```

**Catatan**: Pada Windows, gunakan `;` sebagai path separator. Pada Linux/Mac, gunakan `:`.

### Menggunakan VS Code

1. Buka folder proyek di VS Code
2. Tekan `Ctrl+F5` atau gunakan menu `Run java`
3. Aplikasi akan terbuka dengan window login

## Alur Penggunaan

1. **Login**: Masukkan username dan password pada form login
2. **Dashboard**: Lihat ringkasan data perpustakaan
3. **Navigasi**: Gunakan menu untuk mengakses:
    - **Buku**: Kelola data koleksi buku
    - **Peminjaman**: Catat dan kelola peminjaman buku
    - **Pengunjung**: Kelola data pengunjung

## Arsitektur Aplikasi

```
┌─────────────────────────────────┐
│     User Interface (Swing)      │
│  (LoginFrame, LibraryFrame)     │
└────────────────┬────────────────┘
                 │
┌────────────────▼────────────────┐
│    Business Logic Layer         │
│  (MaintainDatabase.java)        │
└────────────────┬────────────────┘
                 │
┌────────────────▼────────────────┐
│    Data Model Layer             │
│  (DataBooks, DataLoans, etc)    │
└────────────────┬────────────────┘
                 │
┌────────────────▼────────────────┐
│    Database Layer               │
│  (Koneksi.java + JDBC)          │
└────────────────┬────────────────┘
                 │
              MySQL/SQL
```

## Dependency Management

Proyek ini menggunakan struktur folder berikut untuk manajemen dependency:

- `src/`: Kode sumber aplikasi
- `lib/`: Library eksternal (JDBC driver, dll)
- `bin/`: Output kompilasi otomatis

Gunakan fitur **JAVA PROJECTS** di VS Code untuk mengelola dependency lebih lanjut.

## Catatan Pengembangan

- Pastikan versi JDBC driver kompatibel dengan versi database Anda
- Setiap model data di folder `DataModel/` mewakili satu tabel di database
- File `pbo_uas.json` dapat digunakan untuk import/export data atau konfigurasi awal
- Untuk customisasi folder struktur, edit file `.vscode/settings.json`

## Troubleshooting

### Koneksi Database Gagal

- Pastikan MySQL service sudah running
- Verifikasi username, password, dan URL di `Koneksi.java`
- Pastikan database `pbo_uas` sudah dibuat

### Import Error

- Pastikan JDBC driver ada di folder `lib/`
- Periksa classpath saat kompilasi dan menjalankan aplikasi

### GUI Tidak Muncul

- Pastikan Java Swing library tersedia (biasanya included di JDK)
- Cek console untuk error messages

## Lisensi

Proyek ini dibuat untuk keperluan akademik dalam mata kuliah Pemrograman Berorientasi Objek.

## Kontak dan Support

Untuk pertanyaan atau bantuan, silakan hubungi salah satu anggota tim pengembang.
