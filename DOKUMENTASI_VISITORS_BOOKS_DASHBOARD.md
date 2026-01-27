# Dokumentasi Lengkap: Visitors, Books, dan Dashboard

## 📚 Daftar Isi
1. [Overview Sistem](#overview-sistem)
2. [Visitors Panel](#visitors-panel)
3. [Books Panel](#books-panel)
4. [Loans Panel](#loans-panel)
5. [Dashboard Panel](#dashboard-panel)
6. [Struktur Database](#struktur-database)
7. [Alur Kerja](#alur-kerja)
8. [FAQ & Kemungkinan Pertanyaan](#faq--kemungkinan-pertanyaan)

---

## Overview Sistem

Sistem manajemen perpustakaan ini terdiri dari 4 modul utama yang terintegrasi:
- **Visitors Panel**: Mengelola data pengunjung perpustakaan
- **Books Panel**: Mengelola katalog dan stok buku
- **Loans Panel**: Mengelola data peminjaman buku
- **Dashboard Panel**: Menampilkan ringkasan statistik dan data keseluruhan

Semua modul tersambung melalui `MaintainDatabase.java` yang menghubungkan ke database MySQL.

---

## 1. VISITORS PANEL ⭐

### Apa itu Visitors Panel?
Panel untuk mencatat dan mengelola data pengunjung perpustakaan dengan nama, tujuan kunjungan, dan waktu datang otomatis.

### Lokasi File
- **UI**: `src/ProjectInterface/VisitorsPanel.java`
- **Data Model**: `src/DataModel/DataVisitors.java`
- **Database**: `database/pbo_uas.sql` (tabel `visitors`)

### Struktur Data (DataVisitors.java)

```
DataVisitors
├── id (int) - ID unik pengunjung
├── namaPengunjung (String) - Nama lengkap pengunjung
├── tujuan (String) - Tujuan kunjungan (cth: "Membaca novel", "Mencari referensi")
└── waktuDatang (Timestamp) - Waktu kedatangan otomatis (default: CURRENT_TIMESTAMP)

Static Variable:
└── kolom[] = {"ID", "Nama Pengunjung", "Tujuan", "Waktu Datang"}
```

### Komponen UI (VisitorsPanel.java)

#### 1. **Form Input** (GridLayout 7x2)
```
┌─────────────────────────────────┐
│ VISITORS MANAGEMENT             │
├─────────────────────────────────┤
│ ID Pengunjung:      [Text]      │ (Read-only)
│ Nama Pengunjung:    [Input]     │
│ Tujuan:             [Input]     │
│ Waktu Datang:       [Text]      │ (Read-only, auto-filled)
│ [Simpan] [Delete]               │
│ [Update] [Reset]                │
└─────────────────────────────────┘
```

#### 2. **Tabel Data** (JTable dengan DefaultTableModel)
- Menampilkan semua data visitors dari database
- Scrollable dengan row height 26px
- Kolom: ID, Nama Pengunjung, Tujuan, Waktu Datang

#### 3. **Event Listeners**

| Button | Aksi | Method |
|--------|------|--------|
| **Simpan** | Tambah visitor baru | `insertVisitor()` |
| **Update** | Ubah data visitor terpilih | `updateVisitor()` |
| **Delete** | Hapus data visitor | `deleteVisitor()` |
| **Reset** | Bersihkan form input | `clearForm()` |
| **Tabel** | Klik untuk memilih row | `mouseClicked()` → Isi form |

### Alur Kerja Visitors Panel

#### **Menambah Visitor Baru**
```
User klik [Simpan] 
    ↓
Ambil data dari form (Nama, Tujuan)
    ↓
Buat object DataVisitors
    ↓
Panggil db.insertVisitor(visitor)
    ↓
Database INSERT waktu otomatis
    ↓
Refresh tabel & Clear form
```

#### **Update Data Visitor**
```
User klik row di tabel
    ↓
Form terisi otomatis (ID + data lainnya)
    ↓
User ubah data yang diperlukan
    ↓
User klik [Update]
    ↓
Panggil db.updateVisitor(visitor)
    ↓
Database UPDATE WHERE id=?
    ↓
Refresh tabel
```

#### **Hapus Visitor**
```
User klik row di tabel
    ↓
User klik [Delete]
    ↓
Panggil db.deleteVisitor(visitor)
    ↓
Database DELETE WHERE id=?
    ↓
Refresh tabel & Clear form
```

### Database Query (MaintainDatabase.java)

```java
// INSERT
INSERT INTO `visitors`(`nama_pengunjung`, `tujuan`) VALUES (?,?)

// UPDATE
UPDATE `visitors` SET `nama_pengunjung`=?, `tujuan`=? WHERE `id`=?

// DELETE
DELETE FROM `visitors` WHERE `id`=?

// SELECT ALL
SELECT `id`, `nama_pengunjung`, `tujuan`, `waktu_datang` FROM `visitors` WHERE 1
```

### Sample Data
```
ID | Nama Pengunjung  | Tujuan                      | Waktu Datang
1  | Rian Perkasa     | Mencari referensi skripsi   | 2026-01-15 00:24:16
2  | Dewi Sartika     | Membaca novel santai        | 2026-01-15 00:24:16
3  | Bambang Permadi  | Mengerjakan tugas kelompok  | 2026-01-15 00:24:16
4  | Lestari Wahyuni  | Mengembalikan buku          | 2026-01-15 00:24:16
```

---

## 2. BOOKS PANEL 📖

### Apa itu Books Panel?
Panel untuk mengelola katalog buku perpustakaan termasuk judul, penulis, penerbit, dan stok buku.

### Lokasi File
- **UI**: `src/ProjectInterface/BooksPanel.java`
- **Data Model**: `src/DataModel/DataBooks.java`
- **Database**: `database/pbo_uas.sql` (tabel `books`)

### Struktur Data (DataBooks.java)

```
DataBooks
├── id (int) - ID unik buku (Primary Key)
├── judul (String) - Judul buku
├── penulis (String) - Nama penulis
├── penerbit (String) - Nama penerbit
└── stok (int) - Jumlah buku tersedia
```

### Komponen UI (BooksPanel.java)

#### 1. **Form Input** (GridBagLayout)
```
┌──────────────────────────────┐
│ BOOKS MANAGEMENT             │
├──────────────────────────────┤
│ Judul:       [Input Field]   │
│ Penulis:     [Input Field]   │
│ Penerbit:    [Input Field]   │
│ Stok:        [Input Field]   │
└──────────────────────────────┘
```

#### 2. **Tabel Data** (JTable dengan DefaultTableModel)
- Menampilkan semua buku dari database
- Scrollable, row height 30px
- Kolom: ID, Judul, Penulis, Penerbit, Stok

#### 3. **Button Panel**
```
[Tambah] [Update] [Hapus]
```

#### 4. **Event Listeners**

| Button | Aksi | Method |
|--------|------|--------|
| **Tambah** | Menambah buku baru | `btnAdd.addActionListener()` |
| **Update** | Mengubah data buku | `btnUpdate.addActionListener()` |
| **Hapus** | Menghapus buku | `btnDelete.addActionListener()` |
| **Tabel** | Pilih buku, isi form | `mouseClicked()` → Load ke form |

### Alur Kerja Books Panel

#### **Menambah Buku**
```
User isi form (Judul, Penulis, Penerbit, Stok)
    ↓
Klik [Tambah]
    ↓
Parse stok ke integer
    ↓
Create DataBooks object
    ↓
Panggil db.insertBook(book)
    ↓
Database INSERT
    ↓
Refresh tabel dengan refreshTable runnable
    ↓
Clear form dengan clearForm runnable
```

#### **Update Buku**
```
User klik baris di tabel
    ↓
ID disimpan di selectedBookId
    ↓
Form terisi dengan data buku
    ↓
User ubah data
    ↓
Klik [Update]
    ↓
Validasi integer stok
    ↓
Create DataBooks object dengan ID
    ↓
Panggil db.updateBook(book)
    ↓
Database UPDATE WHERE id=?
    ↓
Refresh tabel
```

#### **Hapus Buku**
```
User klik baris di tabel
    ↓
ID tersimpan di selectedBookId
    ↓
Klik [Hapus]
    ↓
Create DataBooks dengan ID
    ↓
Panggil db.deleteBook(book)
    ↓
Database DELETE
    ↓
Refresh tabel & Clear form
```

### Database Query (MaintainDatabase.java)

```java
// INSERT
INSERT INTO `books` (`judul`, `penulis`, `penerbit`, `stok`) VALUES (?,?,?,?)

// UPDATE
UPDATE `books` SET `judul`=?, `penulis`=?, `penerbit`=?, `stok`=? WHERE `id`=?

// DELETE
DELETE FROM `books` WHERE `id`=?

// SELECT ALL
SELECT `id`, `judul`, `penulis`, `penerbit`, `stok` FROM `books` WHERE 1

// COUNT
SELECT COUNT(*) FROM `books`
```

### Sample Data
```
ID | Judul                      | Penulis              | Penerbit        | Stok
1  | Laskar Pelangi             | Andrea Hirata        | Bentang Pustaka | 5
2  | Bumi                       | Tere Liye            | Gramedia        | 3
3  | Filosofi Teras             | Henry Manampiring    | Kompas          | 10
4  | Algoritma Pemrograman      | Sianipar             | Andi Offset     | 2
5  | Negeri 5 Menara            | Ahmad Fuadi          | Gramedia        | 4
```

---

## 3. LOANS PANEL 📚📋

### Apa itu Loans Panel?
Panel untuk mencatat dan mengelola data peminjaman buku. Melacak siapa yang meminjam buku apa, kapan dipinjam, kapan dikembalikan, dan status peminjaman (dipinjam/kembali).

### Lokasi File
- **UI**: `src/ProjectInterface/LoansPanel.java`
- **Data Model**: `src/DataModel/DataLoans.java`
- **Database**: `database/pbo_uas.sql` (tabel `loans`)

### Struktur Data (DataLoans.java)

```
DataLoans
├── id (int) - ID unik peminjaman (Primary Key)
├── namaPeminjam (String) - Nama orang yang meminjam
├── bookId (int) - ID buku yang dipinjam (Foreign Key → books.id)
├── tanggalPinjam (Date) - Tanggal peminjaman (format: yyyy-MM-dd)
├── tanggalKembali (Date) - Tanggal pengembalian (nullable)
└── status (String) - Status: "dipinjam" atau "kembali"
```

### Komponen UI (LoansPanel.java)

#### 1. **Title**
```
LOANS MANAGEMENT (Font: Arial Bold, 28pt)
```

#### 2. **Form Input** (GridLayout 6x2)
```
┌──────────────────────────────────────────┐
│ Loan ID:                  [Text]         │ (Read-only)
│ Nama Peminjam:            [Input]        │ (Required)
│ Book ID:                  [Input]        │ (Required, Integer)
│ Tanggal Pinjam (yyyy-MM-dd): [Input]     │ (Required, Date format)
│ Tanggal Kembali (yyyy-MM-dd): [Input]    │ (Optional, Nullable)
│ Status:                   [Dropdown]     │ (dipinjam / kembali)
└──────────────────────────────────────────┘
```

#### 3. **Button Panel** (FlowLayout)
```
[Add] [Update] [Delete] [Clear]
```

#### 4. **Tabel Data** (JTable dengan DefaultTableModel)
- Menampilkan semua data peminjaman
- Kolom: ID, Nama Peminjam, Book ID, Tanggal Pinjam, Tanggal Kembali, Status
- Scrollable dengan JScrollPane
- Row height standar

#### 5. **Event Listeners**

| Button | Aksi | Method |
|--------|------|--------|
| **Add** | Tambah data peminjaman baru | `addLoan()` |
| **Update** | Ubah data peminjaman | `updateLoan()` |
| **Delete** | Hapus data peminjaman + konfirmasi | `deleteLoan()` |
| **Clear** | Bersihkan form | `clearForm()` |
| **Tabel** | Klik row untuk memilih & isi form | `mouseClicked()` |

### Validasi Input (LoansPanel.java)

#### **Saat Add/Update:**
1. **Nama Peminjam**: Tidak boleh kosong
2. **Book ID**: Tidak boleh kosong, harus integer
3. **Tanggal Pinjam**: Tidak boleh kosong, format `yyyy-MM-dd`
4. **Tanggal Kembali**: Optional (nullable), jika diisi harus format `yyyy-MM-dd`
5. **Status**: Dropdown, otomatis valid

```java
if (txtNama.getText().isEmpty() || txtBookId.getText().isEmpty() ||
    txtTanggalPinjam.getText().isEmpty()) {
    JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error");
    return;
}

loan.setBookId(Integer.parseInt(txtBookId.getText())); // Bisa error jika bukan angka
loan.setTanggalPinjam(Date.valueOf(txtTanggalPinjam.getText())); // Bisa error jika bukan format yyyy-MM-dd
```

#### **Saat Delete:**
```
User klik [Delete]
    ↓
Check: Apakah ID sudah dipilih (tidak kosong)?
    ↓
If not → Show error: "Pilih data yang ingin dihapus!"
    ↓
If yes → Show confirmation dialog: "Yakin ingin menghapus data ini?"
    ↓
If YES → Delete dari database
If NO → Cancel
    ↓
Refresh tabel
```

### Alur Kerja Loans Panel

#### **Menambah Peminjaman Baru**
```
User isi form:
  - Nama Peminjam: [nama orang]
  - Book ID: [ID buku yang tersedia]
  - Tanggal Pinjam: [2026-01-20]
  - Tanggal Kembali: [kosongkan, karena belum dikembalikan]
  - Status: [dipinjam]
    ↓
Klik [Add]
    ↓
Validasi input (tidak kosong, format benar)
    ↓
Parse Book ID ke integer
    ↓
Parse Tanggal Pinjam ke Date
    ↓
Create DataLoans object
    ↓
Panggil db.insertLoan(loan)
    ↓
Database INSERT
    ↓
loadLoansData() → Refresh tabel
    ↓
clearForm() → Bersihkan form
```

#### **Update Status: Buku Dikembalikan**
```
User klik row peminjaman di tabel
    ↓
Form terisi:
  - ID terisi
  - Nama peminjam terisi
  - Book ID terisi
  - Tanggal pinjam terisi
  - Tanggal kembali kosong
  - Status: "dipinjam"
    ↓
User ubah:
  - Tanggal Kembali: [2026-01-27]
  - Status: [kembali]
    ↓
Klik [Update]
    ↓
Validasi (semua field kecuali Tgl Kembali harus diisi)
    ↓
Parse data (ID, Book ID, tanggal)
    ↓
Create DataLoans dengan ID
    ↓
Panggil db.updateLoan(loan)
    ↓
Database UPDATE WHERE id=?
    ↓
Refresh tabel
    ↓
clearForm()
```

#### **Menghapus Peminjaman**
```
User klik row peminjaman
    ↓
Form terisi dengan data peminjaman
    ↓
Klik [Delete]
    ↓
Check: Apakah ID kosong?
    ↓
If kosong → Error: "Pilih data yang ingin dihapus!"
    ↓
If tidak → Show confirm dialog: "Yakin ingin menghapus data ini?"
    ↓
User klik YES:
  - Create DataLoans dengan ID
  - Panggil db.deleteLoan(loan)
  - Database DELETE WHERE id=?
  - Refresh tabel
  - Clear form
    ↓
User klik NO → Cancel, tidak ada yang terjadi
```

### Database Query (MaintainDatabase.java)

```java
// INSERT
INSERT INTO `loans`(`nama_peminjam`, `book_id`, `tanggal_pinjam`, `tanggal_kembali`, `status`) 
VALUES (?,?,?,?,?)

// UPDATE
UPDATE `loans` 
SET `nama_peminjam`=?, `book_id`=?, `tanggal_pinjam`=?, `tanggal_kembali`=?, `status`=? 
WHERE `id`=?

// DELETE
DELETE FROM `loans` WHERE `id`=?

// SELECT ALL
SELECT `id`, `nama_peminjam`, `book_id`, `tanggal_pinjam`, `tanggal_kembali`, `status` 
FROM `loans` WHERE 1

// COUNT
SELECT COUNT(*) FROM `loans`
```

### Sample Data
```
ID | Nama Peminjam  | Book ID | Tanggal Pinjam | Tanggal Kembali | Status
1  | Rian Perkasa   | 1       | 2023-10-01     | NULL            | dipinjam
2  | Dewi Sartika   | 2       | 2023-10-05     | 2023-10-12      | kembali
3  | Andi Wijaya    | 3       | 2023-10-10     | NULL            | dipinjam
```

### Fitur Khusus Loans Panel

#### **1. Date Input Format**
- User harus input format `yyyy-MM-dd` (misal: `2026-01-28`)
- Jika format salah → Exception, show error dialog
- Tanggal Kembali boleh kosong (nullable)

#### **2. ComboBox Status**
- Otomatis hanya ada 2 pilihan: `dipinjam` atau `kembali`
- Default pilihan: `dipinjam`
- User tidak bisa input status custom

#### **3. Book ID Validation**
- Harus integer
- Jika input bukan angka → Exception, show error dialog
- Tidak ada validasi apakah book_id ada di tabel books (bisa insert salah referensi!)

#### **4. Tanggal Kembali Optional**
```java
if (!txtTanggalKembali.getText().isEmpty()) {
    loan.setTanggalKembali(Date.valueOf(txtTanggalKembali.getText()));
}
// Jika kosong, tidak di-set (NULL di database)
```

#### **5. Delete Confirmation**
```java
int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", 
    "Konfirmasi", JOptionPane.YES_NO_OPTION);
if (confirm == JOptionPane.YES_OPTION) {
    // Delete
}
```

---

## 4. DASHBOARD PANEL 📊

### Apa itu Dashboard Panel?
Panel untuk menampilkan ringkasan statistik perpustakaan dan overview data dari ketiga modul (Books, Loans, Visitors).

### Lokasi File
- **UI**: `src/ProjectInterface/DashboardPanel.java`
- Menggunakan: `DataBooks.java`, `DataLoans.java`, `DataVisitors.java`

### Komponen UI (DashboardPanel.java)

#### 1. **Stats Panel** (FlowLayout)
```
┌─────────────────────────────────────────────────────┐
│        Library Statistics                           │
│                                                     │
│   Total Books: 5    Total Loans: 3    Total Visitors: 4  │
└─────────────────────────────────────────────────────┘
```

**Sumber Data:**
- Total Books: `db.getBooksCount()` → COUNT(*) FROM books
- Total Loans: `db.getLoansCount()` → COUNT(*) FROM loans
- Total Visitors: `db.getVisitorsCount()` → COUNT(*) FROM visitors

#### 2. **Data Overview Panel** (GridLayout 3x1)

Menampilkan 3 tabel read-only (tidak bisa diedit):

##### **Books Table**
```
ID | Judul | Penulis | Penerbit | Stok
1  | ... 
2  | ...
```

##### **Loans Table**
```
ID | Nama Peminjam | Book ID | Tgl Pinjam | Tgl Kembali | Status
1  | ...
2  | ...
3  | ...
```

##### **Visitors Table**
```
ID | Nama Pengunjung | Tujuan | Waktu Datang
1  | ...
2  | ...
3  | ...
4  | ...
```

### Karakteristik Tabel Dashboard

- **Read-only**: `isCellEditable()` return false
- **Font**: Arial, 12pt untuk data, 14pt bold untuk header
- **Row Height**: 25px
- **Scrollable**: JScrollPane untuk setiap tabel
- **Bordered**: Titled border dengan nama tabel

### Database Query (MaintainDatabase.java)

```java
// COUNT queries
getBooksCount()     → SELECT COUNT(*) FROM `books`
getLoansCount()     → SELECT COUNT(*) FROM `loans`
getVisitorsCount()  → SELECT COUNT(*) FROM `visitors`

// Data queries
getBooks()          → SELECT all books
getLoans()          → SELECT all loans
getVisitors()       → SELECT all visitors
```

### Alur Kerja Dashboard

```
Dashboard Panel dibuka
    ↓
Constructor memanggil initComponents()
    ↓
Hitung statistik:
  - db.getBooksCount()
  - db.getLoansCount()
  - db.getVisitorsCount()
    ↓
Ambil semua data:
  - db.getBooks()        → ArrayList<DataBooks>
  - db.getLoans()        → ArrayList<DataLoans>
  - db.getVisitors()     → ArrayList<DataVisitors>
    ↓
Konversi data ke Object[][]
    ↓
Buat JTable dengan createReadOnlyTable()
    ↓
Setup layout dengan BorderLayout
    ↓
Display di UI
```

### Kunci Perbedaan Dashboard vs Panel Lain

| Aspek | Books/Visitors/Loans | Dashboard |
|-------|----------------------|-----------|
| **Edit Data** | Ya (CRUD) | Tidak (Read-only) |
| **Insert** | Ya | Tidak |
| **Update** | Ya | Tidak |
| **Delete** | Ya | Tidak |
| **Jumlah Tabel** | 1 | 3 |
| **Fungsi** | Manage data | View overview |
| **Interaktif** | Form input + tabel | Tabel saja |

---

## 5. STRUKTUR DATABASE 🗄️

### Tabel Books
```sql
CREATE TABLE `books` (
  `id` int NOT NULL AUTO_INCREMENT,
  `judul` varchar(255) NOT NULL,
  `penulis` varchar(100),
  `penerbit` varchar(100),
  `stok` int DEFAULT 0,
  PRIMARY KEY (`id`)
)
```

### Tabel Visitors
```sql
CREATE TABLE `visitors` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nama_pengunjung` varchar(100) NOT NULL,
  `tujuan` text,
  `waktu_datang` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
```

### Tabel Loans
```sql
CREATE TABLE `loans` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nama_peminjam` varchar(100) NOT NULL,
  `book_id` int,
  `tanggal_pinjam` date,
  `tanggal_kembali` date,
  `status` enum('dipinjam','kembali') DEFAULT 'dipinjam',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`book_id`) REFERENCES `books`(`id`) ON DELETE CASCADE
)
```

### Relasi Database
```
books ──┐
        │ 1:N
        ├──> loans
        │
visitors (independent)
users (untuk login)
```

**Catatan Penting**: Loans memiliki FOREIGN KEY ke books. Jika buku dihapus, semua loan untuk buku itu akan dihapus otomatis (ON DELETE CASCADE).

---

## 6. ALUR KERJA KESELURUHAN 🔄

### Alur User Lengkap

```
1. LOGIN
   └─> LoginPanel → Authentikasi user di tabel users
                   └─> Success → LibraryFrame

2. DALAM APLIKASI
   LibraryFrame (Main Frame)
   ├─> Tab: Dashboard      → DashboardPanel (View-only)
   ├─> Tab: Books         → BooksPanel (CRUD)
   ├─> Tab: Visitors      → VisitorsPanel (CRUD)
   └─> Tab: Loans         → LoansPanel (CRUD)

3. SAAT MEMBUKA TAB VISITORS
   ├─> initComponents() dipanggil
   ├─> loadVisitorsData() → db.getVisitors()
   ├─> Tabel diisi dengan data dari database
   └─> User bisa: Simpan, Update, Delete, Reset

4. SAAT MEMBUKA TAB BOOKS
   ├─> initComponents() dipanggil
   ├─> Tabel dikosongkan (empty)
   ├─> refreshTable() → db.getBooks()
   ├─> Tabel diisi dengan data
   └─> User bisa: Tambah, Update, Hapus

5. SAAT MEMBUKA TAB LOANS
   ├─> initComponents() dipanggil
   ├─> loadLoansData() → db.getLoans()
   ├─> Tabel diisi dengan data peminjaman
   └─> User bisa: Add, Update, Delete, Clear

6. SAAT MEMBUKA TAB DASHBOARD
   ├─> initComponents() dipanggil
   ├─> Hitung COUNT(*) dari 4 tabel (books, loans, visitors)
   ├─> Ambil semua data dengan getBooks(), getLoans(), getVisitors()
   ├─> Display di 3 tabel read-only
   └─> User hanya bisa melihat (tidak bisa edit)
```

### Alur Insert Data

```
USER FORM
   ↓
[Simpan/Tambah] Button
   ↓
Parse Input (Validasi)
   ↓
Create Data Object (DataBooks/DataVisitors)
   ↓
MaintainDatabase.insert___()
   ↓
→ Connection.getKoneksi()
→ PreparedStatement.setString/setInt/etc
→ executeUpdate()
   ↓
Check Exception
   ↓
Print Status "Berhasil" atau "Gagal"
   ↓
Refresh Tabel
   ↓
Clear Form
```

### Alur Delete Data

```
Click Tabel Row
   ↓
selectedBookId / ID terisi
   ↓
[Delete/Hapus] Button
   ↓
Create Data Object dengan ID saja
   ↓
MaintainDatabase.delete___()
   ↓
DELETE FROM table WHERE id=?
   ↓
Refresh Tabel
   ↓
Clear Form
```

---

## 7. FAQ & KEMUNGKINAN PERTANYAAN 🤔

### **PERTANYAAN TENTANG VISITORS PANEL**

#### Q1: Bagaimana cara waktu otomatis terisi?
**A:** Waktu datang diatur di database dengan `DEFAULT CURRENT_TIMESTAMP`. Saat insert, field waktu_datang tidak diisi dari Java, database otomatis mengisi dengan waktu server MySQL. Field `JTextFieldWaktuDatang` di UI adalah read-only untuk menunjukkan waktu dari database.

```java
// Saat insert, waktu TIDAK disetel di Java
pre.setString(1, visitor.getNamaPengunjung());  // Nama
pre.setString(2, visitor.getTujuan());          // Tujuan
// waktu_datang otomatis dari database
```

#### Q2: Mengapa ID tidak bisa diedit?
**A:** ID adalah PRIMARY KEY dengan AUTO_INCREMENT. User tidak perlu (dan tidak boleh) menginput ID manual. Database otomatis generate ID.

#### Q3: Apa perbedaan antara [Simpan] dan [Update]?
**A:** 
- **Simpan**: INSERT baru (form kosong → klik Simpan)
- **Update**: MODIFY existing (klik tabel → form terisi → ubah → klik Update)

#### Q4: Bagaimana cara jika form salah input?
**A:** Klik **[Reset]** untuk clear form dan mulai dari awal.

#### Q5: Bisakah menambah visitor tanpa tujuan?
**A:** Tujuan adalah nullable di database (text), tapi di form tidak ada validasi khusus. Bisa dikosongkan.

---

### **PERTANYAAN TENTANG BOOKS PANEL**

#### Q6: Bagaimana cara validasi stok bukan angka?
**A:** Ada try-catch di `btnAdd.addActionListener()`:
```java
try {
    b.setStok(Integer.parseInt(txtStok.getText()));
    // ...
} catch (Exception ex) {
    JOptionPane.showMessageDialog(this, "Input tidak valid!");
}
```
Jika user input "abc" di stok, akan error dan show dialog "Input tidak valid!".

#### Q7: Apakah stok bisa negatif?
**A:** Tidak ada validasi di code, tapi secara logic perpustakaan tidak boleh. Bisa tambahkan validasi:
```java
if (b.getStok() < 0) {
    JOptionPane.showMessageDialog(this, "Stok tidak boleh negatif!");
}
```

#### Q8: Apa yang terjadi jika delete buku yang masih dipinjam?
**A:** Database `loans` punya FOREIGN KEY dengan `ON DELETE CASCADE`. Artinya, saat buku dihapus, semua loan untuk buku itu juga terhapus otomatis.

#### Q9: Bagaimana cara update stok setelah peminjaman?
**A:** **Tidak otomatis!** Di code sekarang, stok buku tetap sama saat loan dibuat. Ini adalah bug/fitur yang bisa diperbaiki dengan:
```java
// Saat insert loan, kurangi stok buku
// Saat return loan, tambah stok buku
```

#### Q10: Bisakah judul buku kosong?
**A:** Tidak, judul adalah `varchar(255) NOT NULL`. Database akan reject.

---

### **PERTANYAAN TENTANG LOANS PANEL** ⭐

#### Q11: Bagaimana format tanggal yang benar untuk input?
**A:** Format harus `yyyy-MM-dd` (4 digit tahun - 2 digit bulan - 2 digit hari). Contoh: `2026-01-28`. Jika format salah, akan error dan show dialog error.

```java
Date.valueOf(txtTanggalPinjam.getText()); // Input: "2026-01-28"
```

#### Q12: Apa bedanya Tanggal Kembali kosong dengan null?
**A:** Sama. Jika field Tanggal Kembali kosong, sistem tidak mengisi field tersebut (tidak memanggil `setTanggalKembali`), sehingga di database NULL. Ini menunjukkan buku belum dikembalikan.

#### Q13: Bagaimana cara tahu apakah buku sudah dikembalikan atau belum?
**A:** Lihat kolom **Status**:
- **Status: "dipinjam"** → Buku masih dipinjam (Tanggal Kembali kosong)
- **Status: "kembali"** → Buku sudah dikembalikan (Tanggal Kembali terisi)

Atau cek kolom **Tanggal Kembali**:
- NULL → Belum dikembalikan
- Ada tanggal → Sudah dikembalikan

#### Q14: Apakah Book ID harus ada di tabel books?
**A:** **Tidak ada validasi di code!** Sistem tidak mengecek apakah Book ID yang di-input ada atau tidak. Ini adalah bug. Bisa terjadi:
```
Insert Loan dengan Book ID 9999 (tidak ada di tabel books)
→ Berhasil di-insert
→ Foreign Key akan error atau tidak konsisten
```

Solusi: Tambahkan validasi:
```java
// Cek apakah book_id ada di database sebelum insert
ArrayList<DataBooks> books = db.getBooks();
boolean found = false;
for (DataBooks b : books) {
    if (b.getId() == bookId) found = true;
}
if (!found) {
    JOptionPane.showMessageDialog(this, "Book ID tidak ada!");
    return;
}
```

#### Q15: Bagaimana cara update peminjaman setelah buku dikembalikan?
**A:** 
1. Klik row peminjaman di tabel (statusnya "dipinjam")
2. Form terisi dengan data peminjaman
3. Ubah:
   - **Tanggal Kembali**: Isi dengan tanggal pengembalian (misal: `2026-01-28`)
   - **Status**: Ubah ke `"kembali"`
4. Klik **[Update]**
5. Data tersimpan

#### Q16: Bisakah menghapus loan yang statusnya "kembali"?
**A:** **Ya, bisa!** Tidak ada validasi untuk mencegahnya. User bisa delete loan apakah statusnya "dipinjam" atau "kembali". Solusi: tambahkan konfirmasi atau validasi:
```java
if ("dipinjam".equals(cbStatus.getSelectedItem())) {
    JOptionPane.showMessageDialog(this, "Tidak bisa hapus peminjaman yang masih berjalan!");
    return;
}
```

#### Q17: Mengapa ada konfirmasi saat delete loan?
**A:** Untuk mencegah delete accidental. User harus klik YES di dialog konfirmasi.

```java
int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?");
if (confirm == JOptionPane.YES_OPTION) {
    db.deleteLoan(loan);
}
```

#### Q18: Apa terjadi jika input Book ID bukan angka?
**A:** Exception akan terjadi saat parsing Integer, system akan show error dialog:
```java
loan.setBookId(Integer.parseInt(txtBookId.getText())); 
// Jika txtBookId = "abc" → NumberFormatException → Error dialog
```

#### Q19: Apakah Tanggal Pinjam boleh di masa depan?
**A:** **Tidak ada validasi di code!** Sistem tidak mengecek apakah tanggal pinjam sudah lewat atau masih di masa depan. Bisa terjadi:
```
Input Tanggal Pinjam: 2099-01-28 (100 tahun ke depan)
→ Berhasil di-insert
→ Ini illogis tapi sistem tidak menolak
```

Solusi: Tambahkan validasi:
```java
LocalDate pinjam = LocalDate.parse(txtTanggalPinjam.getText());
if (pinjam.isAfter(LocalDate.now())) {
    JOptionPane.showMessageDialog(this, "Tanggal pinjam tidak boleh di masa depan!");
    return;
}
```

#### Q20: Bagaimana jika Tanggal Kembali lebih awal dari Tanggal Pinjam?
**A:** **Tidak ada validasi di code!** Bisa terjadi:
```
Tanggal Pinjam: 2026-01-28
Tanggal Kembali: 2026-01-20 (lebih awal)
→ Berhasil di-insert
```

Solusi: Tambahkan validasi:
```java
if (!txtTanggalKembali.getText().isEmpty()) {
    LocalDate pinjam = LocalDate.parse(txtTanggalPinjam.getText());
    LocalDate kembali = LocalDate.parse(txtTanggalKembali.getText());
    if (kembali.isBefore(pinjam)) {
        JOptionPane.showMessageDialog(this, "Tanggal kembali tidak boleh sebelum tanggal pinjam!");
        return;
    }
}
```

---

### **PERTANYAAN TENTANG DASHBOARD PANEL**

#### Q21: Bagaimana cara data di dashboard selalu fresh?
**A:** Dashboard selalu ambil data saat dibuka. Jika user:
1. Buka Dashboard
2. Switch ke Books tab, tambah buku
3. Switch kembali ke Dashboard
4. Data sudah ter-update otomatis (initComponents dipanggil lagi)

#### Q22: Mengapa Dashboard read-only?
**A:** Untuk mencegah user mengubah data langsung dari view. Setiap edit harus melalui panel khusus (BooksPanel, VisitorsPanel, LoansPanel) yang punya validasi.

#### Q23: Bagaimana cara sort data di dashboard?
**A:** Tidak ada sorting di code sekarang. Data tampil sesuai urutan dari database (biasanya by ID). Bisa tambahkan:
```java
// Ubah query ke: SELECT ... ORDER BY id DESC
```

#### Q24: Apa perbedaan COUNT(*) dengan SELECT * dari dashboard?
**A:** 
- **COUNT(*)** hanya menghitung jumlah = lebih cepat
- **SELECT *** mengambil semua data = lebih lambat tapi dapat detail

#### Q25: Bagaimana jika data di database sangat banyak?
**A:** Dashboard akan slow. Solusi: tambah LIMIT di query atau pagination.

---

### **PERTANYAAN TENTANG DATABASE & STRUKTUR**

#### Q26: Bagaimana cara backup database?
**A:** Export dari phpMyAdmin atau:
```bash
mysqldump -u root -p pbo_uas > backup.sql
```

#### Q27: Bisakah menambah field baru di loans (misal: denda)?
**A:** Ya, alter table:
```sql
ALTER TABLE loans ADD COLUMN denda DECIMAL(10,2) DEFAULT 0;
```
Lalu update `DataLoans.java` dan `LoansPanel.java`.

#### Q28: Apa fungsi tabel users?
**A:** Untuk login authentication. Method `authenticateUser()` di `MaintainDatabase.java` cek username & password.

#### Q29: Apakah password di database di-encrypt?
**A:** **Tidak!** Password disimpan plain text. Ini **SANGAT TIDAK AMAN**. Seharusnya di-hash dengan bcrypt atau SHA.

#### Q30: Bagaimana relasi antara books dan loans?
**A:** 
```
1 book ──┐
         │ 1:N (satu buku bisa dipinjam berkali-kali)
         ├──> N loans
```
`loans.book_id` adalah FOREIGN KEY ke `books.id`. Jika buku dihapus, loan juga terhapus (ON DELETE CASCADE).

#### Q31: Apakah bisa ada multiple loans untuk buku yang sama?
**A:** **Ya, bisa!** Satu buku bisa dipinjam oleh banyak orang atau orang yang sama berkali-kali. Contoh:
```
Book ID 1 (Laskar Pelangi):
- Loan 1: Rian Perkasa meminjam 2023-10-01 (masih dipinjam)
- Loan 2: Dewi Sartika meminjam 2023-10-05 (sudah kembali)
- Loan 3: Andi Wijaya meminjam 2023-10-10 (masih dipinjam)
```

---

### **PERTANYAAN TENTANG CODE ARCHITECTURE**

#### Q32: Mengapa pakai MaintainDatabase class?
**A:** **Design Pattern: DAO (Data Access Object)**. Memisahkan logika database dari UI. Semua query ada di satu tempat.

#### Q33: Mengapa pakai ArrayList bukan Array?
**A:** ArrayList dinamis. Jumlah data bisa berubah. Array harus ukuran fixed.

#### Q34: Apa keuntungan DataLoans class?
**A:** **Encapsulation**. Data tersimpan dalam object dengan getter/setter. Lebih clean dan maintainable.

#### Q35: Bisakah edit method MaintainDatabase?
**A:** Ya, tapi harus update juga di UI. Misalnya ubah parameter method, harus update semua pemanggilan.

#### Q36: Mengapa ada try-catch di setiap query?
**A:** Jika error (koneksi gagal, query salah), tidak crash. Print error message dan lanjut.

---

### **PERTANYAAN TEKNIS SWING UI**

#### Q37: Apa perbedaan BorderLayout vs GridLayout?
**A:** 
- **BorderLayout**: Posisi (NORTH, SOUTH, EAST, WEST, CENTER)
- **GridLayout**: Grid rows x columns

#### Q38: Apa fungsi JScrollPane?
**A:** Menambah scrollbar jika content lebih besar dari panel.

#### Q39: Bisakah customize warna background?
**A:** Ya, `setBackground(new Color(230, 240, 250))`.

#### Q40: Bagaimana cara font lebih besar?
**A:** `new Font("Arial", Font.BOLD, 18)` - parameter 3 adalah size.

#### Q41: Mengapa pakai ComboBox untuk status?
**A:** Untuk membatasi pilihan hanya "dipinjam" atau "kembali". User tidak bisa input text custom. Lebih aman.

#### Q42: Bagaimana cara membuat combo box dropdown?
**A:** 
```java
JComboBox<String> cbStatus = new JComboBox<>(new String[] { "dipinjam", "kembali" });
cbStatus.setSelectedIndex(0); // Default: dipinjam
```

---

### **PERTANYAAN DEBUGGING**

#### Q43: Data tidak muncul di tabel loans, apa yang salah?
**A:** 
1. Cek koneksi database
2. Cek query `SELECT` di MaintainDatabase.getLoans()
3. Cek exception di console
4. Cek apakah loadLoansData() dipanggil?

#### Q44: Button klik tapi tidak ada yang terjadi?
**A:**
1. Cek apakah ActionListener di-register?
2. Cek exception di console (misal: date format error)
3. Cek apakah method yang dipanggil benar?

#### Q45: Form tidak ter-clear setelah insert loan?
**A:** `clearForm()` mungkin tidak dipanggil. Pastikan di akhir `addLoan()` ada:
```java
loadLoansData();
clearForm();
```

#### Q46: Error "Unparseable date" saat input tanggal?
**A:** Format tanggal salah. Harus `yyyy-MM-dd`. Cek input user, jangan ada space atau karakter lain.

```java
// ✓ Benar
Date.valueOf("2026-01-28")

// ✗ Salah (ada space)
Date.valueOf(" 2026-01-28 ")

// ✗ Salah (format berbeda)
Date.valueOf("28-01-2026")
Date.valueOf("01/28/2026")
```

#### Q47: Update tidak bekerja padahal tidak ada error?
**A:** `loadLoansData()` mungkin tidak dipanggil. Tambahkan di akhir `updateLoan()`.

#### Q48: Delete memerlukan ID tapi form terasa tidak jelas?
**A:** Tambahkan tooltip atau label yang jelas:
```java
txtId.setToolTipText("Pilih row di tabel terlebih dahulu");
```

---

### **PERTANYAAN REQUIREMENTS & IMPROVEMENT**

#### Q49: Bagaimana cara auto-kurangi stok saat loan dibuat?
**A:** Tambahkan logic di `insertLoan()`:
```java
public void insertLoan(DataLoans loan) {
    // ... existing code ...
    
    // Kurangi stok buku
    DataBooks book = getBookById(loan.getBookId());
    if (book != null && book.getStok() > 0) {
        book.setStok(book.getStok() - 1);
        updateBook(book);
    }
}
```

#### Q50: Bagaimana cara hitung lama peminjaman otomatis?
**A:** Tambahkan kolom baru atau hitung saat display:
```java
// Saat display di tabel
long hariPinjam = ChronoUnit.DAYS.between(
    LocalDate.parse(loan.getTanggalPinjam().toString()),
    LocalDate.now()
);
```

#### Q51: Bisakah generate laporan peminjaman?
**A:** Bisa. Tambah button "Export" yang generate PDF atau Excel dengan data loans yang dipilih.

#### Q52: Bagaimana cara cari peminjaman berdasarkan nama?
**A:** Tambahkan JTextField search:
```java
ArrayList<DataLoans> filtered = new ArrayList<>();
for (DataLoans loan : allLoans) {
    if (loan.getNamaPeminjam().toLowerCase().contains(searchText.toLowerCase())) {
        filtered.add(loan);
    }
}
// Display filtered list di tabel
```

#### Q53: Apakah bisa notify jika buku belum dikembalikan lama?
**A:** Tambahkan method untuk cek tangga pengembalian:
```java
public ArrayList<DataLoans> getOverdueLoans(int days) {
    ArrayList<DataLoans> result = new ArrayList<>();
    for (DataLoans loan : getLoans()) {
        if ("dipinjam".equals(loan.getStatus())) {
            LocalDate pinjam = loan.getTanggalPinjam().toLocalDate();
            long daysPassed = ChronoUnit.DAYS.between(pinjam, LocalDate.now());
            if (daysPassed > days) {
                result.add(loan);
            }
        }
    }
    return result;
}
```

---

### **PERTANYAAN PERFORMA & OPTIMIZATION**

#### Q54: Apakah bisa search/filter loans secara real-time?
**A:** Ya, tambahkan DocumentListener ke text field search:
```java
txtSearch.getDocument().addDocumentListener(new DocumentListener() {
    @Override
    public void insertUpdate(DocumentEvent e) {
        filterTable();
    }
    // ... method lain
});
```

#### Q55: Bagaimana jika ada ribuan data peminjaman?
**A:** Implementasikan pagination:
```java
int pageSize = 50;
int page = 0;
String query = "SELECT * FROM loans LIMIT " + (page * pageSize) + ", " + pageSize;
```

#### Q56: Apakah PreparedStatement lebih aman dari String concatenation?
**A:** **Ya!** PreparedStatement prevent SQL Injection.
```java
// ✗ Buruk (SQL Injection risk)
String query = "SELECT * FROM loans WHERE id=" + id;

// ✓ Baik (PreparedStatement)
PreparedStatement pre = conn.prepareStatement("SELECT * FROM loans WHERE id=?");
pre.setInt(1, id);
```

---

### **PERTANYAAN EDGE CASE & SPECIAL SCENARIO**

#### Q57: Apa terjadi jika insert duplicate loan?
**A:** Tidak ada check duplicate di code! Bisa terjadi:
```
Insert loan 2x untuk orang sama, buku sama, tanggal sama
→ Berhasil 2x
→ Data redundant di database
```

Solusi: Tambahkan validasi:
```java
ArrayList<DataLoans> loans = db.getLoans();
for (DataLoans existing : loans) {
    if (existing.getNamaPeminjam().equals(loan.getNamaPeminjam()) &&
        existing.getBookId() == loan.getBookId() &&
        "dipinjam".equals(existing.getStatus())) {
        JOptionPane.showMessageDialog(this, "Orang ini sudah meminjam buku ini!");
        return;
    }
}
```

#### Q58: Bagaimana jika delete buku yang sedang dipinjam?
**A:** Karena ada FOREIGN KEY dengan `ON DELETE CASCADE`, semua loan untuk buku itu juga terhapus otomatis. Ini **dangerous!** Solusi: prevent delete:
```java
public void deleteBook(DataBooks book) {
    ArrayList<DataLoans> loans = getLoans();
    for (DataLoans loan : loans) {
        if (loan.getBookId() == book.getId() && "dipinjam".equals(loan.getStatus())) {
            throw new Exception("Tidak bisa hapus buku yang sedang dipinjam!");
        }
    }
    // ... existing delete code ...
}
```

#### Q59: Bisakah undo operasi loan?
**A:** **Tidak** di aplikasi sekarang. Semua operasi langsung ke database tanpa undo. Solusi: implementasi audit log/history.

#### Q60: Bagaimana jika loan dipinjam orang bukan dari tabel users?
**A:** Tidak ada validasi! Siapapun bisa jadi nama peminjam. Ini adalah fitur atau bug tergantung kebutuhan.

---

### **PERTANYAAN KHUSUS LOANS PANEL LANJUTAN**

#### Q61: Apakah status bisa di-edit langsung di tabel?
**A:** **Tidak**, tabel di dashboard adalah read-only. Harus melalui form di Loans Panel.

#### Q62: Apa yang terjadi jika row peminjaman dihapus dari tabel tapi belum diklik?
**A:** Tidak bisa delete. Harus klik row terlebih dahulu agar ID terisi di form, baru bisa delete.

#### Q63: Bisakah menambah multiple loans sekaligus?
**A:** Di UI tidak ada fitur ini. Harus tambah satu per satu. Bisa tambahkan fitur bulk import dari Excel.

#### Q64: Bagaimana cara tahu siapa yang paling sering meminjam?
**A:** Query report:
```sql
SELECT nama_peminjam, COUNT(*) AS jumlah_pinjam 
FROM loans 
GROUP BY nama_peminjam 
ORDER BY jumlah_pinjam DESC;
```

#### Q65: Bisakah hitung total denda jika terlambat kembali?
**A:** Tambahkan logic:
```java
public double hitungDenda(DataLoans loan) {
    if ("kembali".equals(loan.getStatus())) {
        long hariTerlambat = ChronoUnit.DAYS.between(
            loan.getTanggalPinjam().toLocalDate(),
            loan.getTanggalKembali().toLocalDate()
        );
        
        if (hariTerlambat > 7) { // Jika lebih dari 7 hari
            return (hariTerlambat - 7) * 5000; // Rp 5000 per hari
        }
    }
    return 0;
}
```

---

## KESIMPULAN STRUKTUR

```
App.java (Main Entry)
├─ LibraryFrame.java (Container)
│  ├─ LoginPanel → Authentikasi
│  ├─ DashboardPanel → View-only summary (READ)
│  ├─ BooksPanel → CRUD buku
│  ├─ VisitorsPanel → CRUD visitors
│  └─ LoansPanel → CRUD loans
│
├─ DataModel/ (Objects)
│  ├─ DataBooks → Struktur buku
│  ├─ DataVisitors → Struktur pengunjung
│  └─ DataLoans → Struktur peminjaman
│
├─ MaintainDatabase.java (DAO)
│  └─ Semua method CRUD + Count
│
├─ Koneksi.java (Database Connection)
│  └─ getKoneksi() → Connection MySQL
│
└─ database/pbo_uas.sql (Schema)
   ├─ books table
   ├─ visitors table
   ├─ loans table (dengan FOREIGN KEY ke books)
   └─ users table
```

---

## CATATAN PENTING ⚠️

1. **Security Issues**:
   - Password plain text (gunakan bcrypt/SHA)
   - Tidak ada input validation (bisa SQL injection jika string concatenation)
   - Tidak ada authorization/role-based access

2. **Best Practices Tidak Diterapkan**:
   - Tidak ada logging
   - Tidak ada error handling yang comprehensive
   - Tidak ada transaction management
   - Tidak ada connection pooling

3. **Improvement Suggestions**:
   - Implementasi search/filter
   - Tambah pagination untuk data besar
   - Auto-update stok saat loan
   - Validasi input lebih ketat
   - Enkripsi password
   - Tambah audit trail

---

**Dokumentasi Selesai ✅**
