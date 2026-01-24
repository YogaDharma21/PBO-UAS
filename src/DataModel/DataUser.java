package DataModel;

public class DataUser {
    private int id;
    private String username;
    private String password;
    private String namaLengkap;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String toString() {
        return "ID: " + id + "; Username: " + username + "; Nama Lengkap: " + namaLengkap;
    }
}
