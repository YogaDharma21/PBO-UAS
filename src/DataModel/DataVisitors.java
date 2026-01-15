package DataModel;

import java.sql.Timestamp;

public class DataVisitors {
    private int id;
    private String namaPengunjung;
    private String tujuan;
    private Timestamp waktuDatang;

    public int getId() {
        return id;
    }

    public String getNamaPengunjung() {
        return namaPengunjung;
    }

    public String getTujuan() {
        return tujuan;
    }

    public Timestamp getWaktuDatang() {
        return waktuDatang;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNamaPengunjung(String namaPengunjung) {
        this.namaPengunjung = namaPengunjung;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public void setWaktuDatang(Timestamp waktuDatang) {
        this.waktuDatang = waktuDatang;
    }

    public String toString() {
        return "ID: " + id + "; Nama Pengunjung: " + namaPengunjung + "; Tujuan: " + tujuan +
                "; Waktu Datang: " + waktuDatang;
    }
}
