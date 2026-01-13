package MaintainDataPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DataModel.DataMahasiswa;
import DatabaseProcess.Koneksi;

public class MaintainDatabase {
    public void insertData(DataMahasiswa dm) {
        String rawQuery = "INSERT INTO " + "`mhs`(`Nim`, `Nama`, `Umur`) " + "VALUES (?,?,?)";
        try (
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement pre = conn.prepareStatement(rawQuery);) {
            pre.setString(1, dm.getNim());
            pre.setString(2, dm.getNama());
            pre.setInt(3, dm.getUmur());
            pre.executeUpdate();
            System.out.println("Proses Simpan Data Berhasil");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Proses Simpan Data Gagal");
        }
    }

    public void updateData(DataMahasiswa dm) {
        String rawQuery = "UPDATE `mhs` " + "SET `Nama`=?," + "`Umur`=? " + "WHERE `Nim`=?";
        try (
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement pre = conn.prepareStatement(rawQuery);
        ) {
            pre.setString(1, dm.getNama());
            pre.setInt(2, dm.getUmur());
            pre.setString(3, dm.getNim());
            pre.executeUpdate();
            System.out.println("Proses Update Data Berhasil");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Proses Update Data Gagal");
        }
    }

    public void deleteData(DataMahasiswa dm) {
        String rawQuery = "DELETE FROM `mhs` " + "WHERE `Nim`=?";
        try (
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement pre = conn.prepareStatement(rawQuery);
        ) {
            pre.setString(1, dm.getNim());
            pre.executeUpdate();
            System.out.println("Proses Delete Data Berhasil");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Proses Delete Data Gagal");
        }
    }

    public ArrayList<DataMahasiswa> getData() {
        String QueryString = "SELECT `Nim`, `Nama`, `Umur` FROM `mhs` WHERE 1";

        try (
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement pre = conn.prepareStatement(QueryString);
            ResultSet resultset = pre.executeQuery();
        ) {
            ArrayList<DataMahasiswa> lsMhs = new ArrayList<>();
            while (resultset.next()) {
                DataMahasiswa temp = new DataMahasiswa();
                temp.setNim(resultset.getString("Nim"));
                temp.setNama(resultset.getString("Nama"));
                temp.setUmur(resultset.getInt("Umur"));
                lsMhs.add(temp);
            }
            return lsMhs;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage().toString());
        }

        return new ArrayList<DataMahasiswa>();
    }
}
