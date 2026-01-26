package MaintainDataPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import DataModel.DataBooks;
import DataModel.DataLoans;
import DataModel.DataUser;
import DataModel.DataVisitors;
import DatabaseProcess.Koneksi;

public class MaintainDatabase {
    public void insertBook(DataBooks book) {
        String rawQuery = "INSERT INTO `books` (`judul`, `penulis`, `penerbit`, `stok`) VALUES (?,?,?,?)";
        try (
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pre = conn.prepareStatement(rawQuery);) {
            pre.setString(1, book.getJudul());
            pre.setString(2, book.getPenulis());
            pre.setString(3, book.getPenerbit());
            pre.setInt(4, book.getStok());
            pre.executeUpdate();
            System.out.println("Proses Simpan Data Buku Berhasil");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Proses Simpan Data Buku Gagal");
        }
    }

    public void updateBook(DataBooks book) {
        String rawQuery = "UPDATE `books` SET `judul`=?, `penulis`=?, `penerbit`=?, `stok`=? WHERE `id`=?";
        try (
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pre = conn.prepareStatement(rawQuery);) {
            pre.setString(1, book.getJudul());
            pre.setString(2, book.getPenulis());
            pre.setString(3, book.getPenerbit());
            pre.setInt(4, book.getStok());
            pre.setInt(5, book.getId());
            pre.executeUpdate();
            System.out.println("Proses Update Data Buku Berhasil");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Proses Update Data Buku Gagal");
        }
    }

    public void deleteBook(DataBooks book) {
        String rawQuery = "DELETE FROM `books` WHERE `id`=?";
        try (
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pre = conn.prepareStatement(rawQuery);) {
            pre.setInt(1, book.getId());
            pre.executeUpdate();
            System.out.println("Proses Delete Data Buku Berhasil");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Proses Delete Data Buku Gagal");
        }
    }

    public ArrayList<DataBooks> getBooks() {
        String QueryString = "SELECT `id`, `judul`, `penulis`, `penerbit`, `stok` FROM `books` WHERE 1";
        try (
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pre = conn.prepareStatement(QueryString);
                ResultSet resultset = pre.executeQuery();) {
            ArrayList<DataBooks> lsBooks = new ArrayList<>();
            while (resultset.next()) {
                DataBooks temp = new DataBooks();
                temp.setId(resultset.getInt("id"));
                temp.setJudul(resultset.getString("judul"));
                temp.setPenulis(resultset.getString("penulis"));
                temp.setPenerbit(resultset.getString("penerbit"));
                temp.setStok(resultset.getInt("stok"));
                lsBooks.add(temp);
            }
            return lsBooks;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage().toString());
        }
        return new ArrayList<DataBooks>();
    }

    public void insertLoan(DataLoans loan) {
        String rawQuery = "INSERT INTO `loans`(`nama_peminjam`, `book_id`, `tanggal_pinjam`, `tanggal_kembali`, `status`) VALUES (?,?,?,?,?)";
        try (
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pre = conn.prepareStatement(rawQuery);) {
            pre.setString(1, loan.getNamaPeminjam());
            pre.setInt(2, loan.getBookId());
            pre.setDate(3, loan.getTanggalPinjam());
            if (loan.getTanggalKembali() != null) {
                pre.setDate(4, loan.getTanggalKembali());
            } else {
                pre.setDate(4, null);
            }
            pre.setString(5, loan.getStatus());
            pre.executeUpdate();
            System.out.println("Proses Simpan Data Peminjaman Berhasil");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Proses Simpan Data Peminjaman Gagal");
        }
    }

    public void updateLoan(DataLoans loan) {
        String rawQuery = "UPDATE `loans` SET `nama_peminjam`=?, `book_id`=?, `tanggal_pinjam`=?, `tanggal_kembali`=?, `status`=? WHERE `id`=?";
        try (
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pre = conn.prepareStatement(rawQuery);) {
            pre.setString(1, loan.getNamaPeminjam());
            pre.setInt(2, loan.getBookId());
            pre.setDate(3, loan.getTanggalPinjam());
            if (loan.getTanggalKembali() != null) {
                pre.setDate(4, loan.getTanggalKembali());
            } else {
                pre.setDate(4, null);
            }
            pre.setString(5, loan.getStatus());
            pre.setInt(6, loan.getId());
            pre.executeUpdate();
            System.out.println("Proses Update Data Peminjaman Berhasil");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Proses Update Data Peminjaman Gagal");
        }
    }

    public void deleteLoan(DataLoans loan) {
        String rawQuery = "DELETE FROM `loans` WHERE `id`=?";
        try (
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pre = conn.prepareStatement(rawQuery);) {
            pre.setInt(1, loan.getId());
            pre.executeUpdate();
            System.out.println("Proses Delete Data Peminjaman Berhasil");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Proses Delete Data Peminjaman Gagal");
        }
    }

    public ArrayList<DataLoans> getLoans() {
        String QueryString = "SELECT `id`, `nama_peminjam`, `book_id`, `tanggal_pinjam`, `tanggal_kembali`, `status` FROM `loans` WHERE 1";
        try (
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pre = conn.prepareStatement(QueryString);
                ResultSet resultset = pre.executeQuery();) {
            ArrayList<DataLoans> lsLoans = new ArrayList<>();
            while (resultset.next()) {
                DataLoans temp = new DataLoans();
                temp.setId(resultset.getInt("id"));
                temp.setNamaPeminjam(resultset.getString("nama_peminjam"));
                temp.setBookId(resultset.getInt("book_id"));
                temp.setTanggalPinjam(resultset.getDate("tanggal_pinjam"));
                temp.setTanggalKembali(resultset.getDate("tanggal_kembali"));
                temp.setStatus(resultset.getString("status"));
                lsLoans.add(temp);
            }
            return lsLoans;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage().toString());
        }
        return new ArrayList<DataLoans>();
    }

    public void insertVisitor(DataVisitors visitor) {
        String rawQuery = "INSERT INTO `visitors`(`nama_pengunjung`, `tujuan`) VALUES (?,?)";
        try (
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pre = conn.prepareStatement(rawQuery);) {
            pre.setString(1, visitor.getNamaPengunjung());
            pre.setString(2, visitor.getTujuan());
            pre.executeUpdate();
            System.out.println("Proses Simpan Data Pengunjung Berhasil");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Proses Simpan Data Pengunjung Gagal");
        }
    }

    public void updateVisitor(DataVisitors visitor) {
        String rawQuery = "UPDATE `visitors` SET `nama_pengunjung`=?, `tujuan`=? WHERE `id`=?";
        try (
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pre = conn.prepareStatement(rawQuery);) {
            pre.setString(1, visitor.getNamaPengunjung());
            pre.setString(2, visitor.getTujuan());
            pre.setInt(3, visitor.getId());
            pre.executeUpdate();
            System.out.println("Proses Update Data Pengunjung Berhasil");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Proses Update Data Pengunjung Gagal");
        }
    }

    public void deleteVisitor(DataVisitors visitor) {
        String rawQuery = "DELETE FROM `visitors` WHERE `id`=?";
        try (
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pre = conn.prepareStatement(rawQuery);) {
            pre.setInt(1, visitor.getId());
            pre.executeUpdate();
            System.out.println("Proses Delete Data Pengunjung Berhasil");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Proses Delete Data Pengunjung Gagal");
        }
    }

    public ArrayList<DataVisitors> getVisitors() {
        String QueryString = "SELECT `id`, `nama_pengunjung`, `tujuan`, `waktu_datang` FROM `visitors` WHERE 1";
        try (
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pre = conn.prepareStatement(QueryString);
                ResultSet resultset = pre.executeQuery();) {
            ArrayList<DataVisitors> lsVisitors = new ArrayList<>();
            while (resultset.next()) {
                DataVisitors temp = new DataVisitors();
                temp.setId(resultset.getInt("id"));
                temp.setNamaPengunjung(resultset.getString("nama_pengunjung"));
                temp.setTujuan(resultset.getString("tujuan"));
                temp.setWaktuDatang(resultset.getTimestamp("waktu_datang"));
                lsVisitors.add(temp);
            }
            return lsVisitors;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage().toString());
        }
        return new ArrayList<DataVisitors>();
    }

    public int getBooksCount() {
        String QueryString = "SELECT COUNT(*) FROM `books`";
        try (
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pre = conn.prepareStatement(QueryString);
                ResultSet resultset = pre.executeQuery();) {
            if (resultset.next()) {
                return resultset.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage().toString());
        }
        return 0;
    }

    public int getLoansCount() {
        String QueryString = "SELECT COUNT(*) FROM `loans`";
        try (
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pre = conn.prepareStatement(QueryString);
                ResultSet resultset = pre.executeQuery();) {
            if (resultset.next()) {
                return resultset.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage().toString());
        }
        return 0;
    }

    public int getVisitorsCount() {
        String QueryString = "SELECT COUNT(*) FROM `visitors`";
        try (
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pre = conn.prepareStatement(QueryString);
                ResultSet resultset = pre.executeQuery();) {
            if (resultset.next()) {
                return resultset.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage().toString());
        }
        return 0;
    }

    public boolean authenticateUser(String username, String password) {
        String QueryString = "SELECT `id`, `username`, `password`, `nama_lengkap` FROM `users` WHERE `username`=? AND `password`=?";
        try (
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pre = conn.prepareStatement(QueryString);) {
            pre.setString(1, username);
            pre.setString(2, password);
            ResultSet resultset = pre.executeQuery();
            if (resultset.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage().toString());
            return false;
        }
    }
}
