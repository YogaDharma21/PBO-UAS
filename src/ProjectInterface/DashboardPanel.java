package ProjectInterface;

import DataModel.DataBooks;
import DataModel.DataLoans;
import DataModel.DataVisitors;
import MaintainDataPackage.MaintainDatabase;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DashboardPanel extends JPanel {
    private final MaintainDatabase db;

    public DashboardPanel() {
        super(new BorderLayout(10, 10));
        db = new MaintainDatabase();
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initComponents();
    }

    private void initComponents() {
        // Stats Panel
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Library Statistics"));

        int booksCount = db.getBooksCount();
        int loansCount = db.getLoansCount();
        int visitorsCount = db.getVisitorsCount();

        JLabel lblBooksCount = new JLabel("Total Books: " + booksCount);
        lblBooksCount.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel lblLoansCount = new JLabel("Total Loans: " + loansCount);
        lblLoansCount.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel lblVisitorsCount = new JLabel("Total Visitors: " + visitorsCount);
        lblVisitorsCount.setFont(new Font("Arial", Font.BOLD, 18));

        statsPanel.add(lblBooksCount);
        statsPanel.add(lblLoansCount);
        statsPanel.add(lblVisitorsCount);

        // Tables Panel
        JPanel tablesPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        tablesPanel.setBorder(BorderFactory.createTitledBorder("Data Overview"));

        // Books Table
        ArrayList<DataBooks> books = db.getBooks();
        String[] bookColumns = { "ID", "Judul", "Penulis", "Penerbit", "Stok" };
        Object[][] bookData = new Object[books.size()][5];
        for (int i = 0; i < books.size(); i++) {
            bookData[i][0] = books.get(i).getId();
            bookData[i][1] = books.get(i).getJudul();
            bookData[i][2] = books.get(i).getPenulis();
            bookData[i][3] = books.get(i).getPenerbit();
            bookData[i][4] = books.get(i).getStok();
        }
        JTable booksTable = createReadOnlyTable(bookData, bookColumns);
        JScrollPane booksScrollPane = new JScrollPane(booksTable);
        booksScrollPane.setBorder(BorderFactory.createTitledBorder("Books Data"));
        tablesPanel.add(booksScrollPane);

        // Loans Table
        ArrayList<DataLoans> loans = db.getLoans();
        String[] loanColumns = { "ID", "Nama Peminjam", "Book ID", "Tgl Pinjam", "Tgl Kembali", "Status" };
        Object[][] loanData = new Object[loans.size()][6];
        for (int i = 0; i < loans.size(); i++) {
            loanData[i][0] = loans.get(i).getId();
            loanData[i][1] = loans.get(i).getNamaPeminjam();
            loanData[i][2] = loans.get(i).getBookId();
            loanData[i][3] = loans.get(i).getTanggalPinjam();
            loanData[i][4] = loans.get(i).getTanggalKembali();
            loanData[i][5] = loans.get(i).getStatus();
        }
        JTable loansTable = createReadOnlyTable(loanData, loanColumns);
        JScrollPane loansScrollPane = new JScrollPane(loansTable);
        loansScrollPane.setBorder(BorderFactory.createTitledBorder("Loans Data"));
        tablesPanel.add(loansScrollPane);

        // Visitors Table
        ArrayList<DataVisitors> visitors = db.getVisitors();
        String[] visitorColumns = { "ID", "Nama Pengunjung", "Tujuan", "Waktu Datang" };
        Object[][] visitorData = new Object[visitors.size()][4];
        for (int i = 0; i < visitors.size(); i++) {
            visitorData[i][0] = visitors.get(i).getId();
            visitorData[i][1] = visitors.get(i).getNamaPengunjung();
            visitorData[i][2] = visitors.get(i).getTujuan();
            visitorData[i][3] = visitors.get(i).getWaktuDatang();
        }
        JTable visitorsTable = createReadOnlyTable(visitorData, visitorColumns);
        JScrollPane visitorsScrollPane = new JScrollPane(visitorsTable);
        visitorsScrollPane.setBorder(BorderFactory.createTitledBorder("Visitors Data"));
        tablesPanel.add(visitorsScrollPane);

        add(statsPanel, BorderLayout.NORTH);
        add(tablesPanel, BorderLayout.CENTER);
    }

    private JTable createReadOnlyTable(Object[][] data, String[] columnNames) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setRowHeight(25);
        return table;
    }
}
