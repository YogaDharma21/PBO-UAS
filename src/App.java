import ProjectInterface.LoginFrame;

import java.sql.Connection;

import DatabaseProcess.Koneksi;
public class App {
    public static void main(String[] args) throws Exception {
      Connection conn=Koneksi.getKoneksi();
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
    }
}
