
package DatabaseProcess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    static String url = "jdbc:mysql://localhost:3306/pbo_uas";
    static String username = "root";
    static String password = "";

    public static Connection getKoneksi() {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (SQLException e) {
            System.out.println("Koneksi Gagal: " + e.getMessage());
        }
        return null;
    }
}
