package DataModel;

import java.sql.Date;

public class DataLoans {
    private int id;
    private String namaPeminjam;
    private int bookId;
    private Date tanggalPinjam;
    private Date tanggalKembali;
    private String status;

    public int getId() {
        return id;
    }

    public String getNamaPeminjam() {
        return namaPeminjam;
    }

    public int getBookId() {
        return bookId;
    }

    public Date getTanggalPinjam() {
        return tanggalPinjam;
    }

    public Date getTanggalKembali() {
        return tanggalKembali;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNamaPeminjam(String namaPeminjam) {
        this.namaPeminjam = namaPeminjam;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setTanggalPinjam(Date tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public void setTanggalKembali(Date tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ID: " + id + "; Nama Peminjam: " + namaPeminjam + "; Book ID: " + bookId +
                "; Tanggal Pinjam: " + tanggalPinjam + "; Tanggal Kembali: " + tanggalKembali +
                "; Status: " + status;
    }
}
