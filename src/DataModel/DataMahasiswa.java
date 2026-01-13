
package DataModel;

public class DataMahasiswa {
    private String nim,nama;
    private int umur;

    public String getNama(){
        return nama;
    }

    public String getNim(){
        return nim;
    }

    public int getUmur(){
        return umur;
    }

    public void setNama(String nama){
        this.nama = nama;
    }

    public void setNim(String nim){
        this.nim = nim;
    }

    public void setUmur(int umur){
        this.umur = umur;
    }

    public String toString(){
        return (
            "Nim: " + nim + "; Nama: " + nama + "; Umur: " + umur
        );
    }
}
