package DataModel;

public class DataBooks {
    private int id;
    private String judul;
    private String penulis;
    private String penerbit;
    private int stok;

    public int getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public int getStok() {
        return stok;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public String toString() {
        return "ID: " + id + "; Judul: " + judul + "; Penulis: " + penulis + "; Penerbit: " + penerbit + "; Stok: " + stok;
    }
}
