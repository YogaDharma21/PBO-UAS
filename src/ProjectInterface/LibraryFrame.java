package ProjectInterface;

import DataModel.DataBooks;
import DataModel.DataLoans;
import DataModel.DataVisitors;
import MaintainDataPackage.MaintainDatabase;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LibraryFrame {
    private CardLayout cardLayout;
    private JPanel panelCenter;
    private final MaintainDatabase db;

    public LibraryFrame() {
        db = new MaintainDatabase();
    }

    private JPanel createTopButtonPanel(Dimension screen) {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        jPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel.setPreferredSize(new Dimension(0, (int) (screen.height * 0.08)));

        JButton btDashboard = new JButton("Dashboard");
        btDashboard.setPreferredSize(new Dimension(150, 50));
        btDashboard.setFont(new Font("Arial", Font.BOLD, 16));
        btDashboard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(panelCenter, "Dashboard");
            }
        });

        JButton btInputBooks = new JButton(" Books");
        btInputBooks.setPreferredSize(new Dimension(150, 50));
        btInputBooks.setFont(new Font("Arial", Font.BOLD, 16));
        btInputBooks.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(panelCenter, "Books");
            }
        });

        JButton btLoans = new JButton("Loans");
        btLoans.setPreferredSize(new Dimension(150, 50));
        btLoans.setFont(new Font("Arial", Font.BOLD, 16));
        btLoans.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(panelCenter, "Loans");
            }
        });

        JButton btVisitors = new JButton("Visitors");
        btVisitors.setPreferredSize(new Dimension(150, 50));
        btVisitors.setFont(new Font("Arial", Font.BOLD, 16));
        btVisitors.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(panelCenter, "Visitors");
            }
        });

        jPanel.add(btDashboard);
        jPanel.add(btInputBooks);
        jPanel.add(btLoans);
        jPanel.add(btVisitors);
        return jPanel;
    }

    private JPanel createBooksPanel(Dimension screen) {
        JPanel jPanel = new JPanel();
        jPanel.setBackground(new Color(234, 239, 143));
        jPanel.setLayout(new BorderLayout());
        jPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel labelTitle = new JLabel("BOOKS MANAGEMENT", SwingConstants.CENTER);
        labelTitle.setFont(new Font("Arial", Font.BOLD, 36));
        labelTitle.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel labelInfo = new JLabel("This is the Books Management page. You can input and manage books here.",
                SwingConstants.CENTER);
        labelInfo.setFont(new Font("Arial", Font.PLAIN, 20));
        labelInfo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        jPanel.add(labelTitle, BorderLayout.NORTH);
        jPanel.add(labelInfo, BorderLayout.CENTER);
        return jPanel;
    }

    private void loadLoansData(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        ArrayList<DataLoans> loans = db.getLoans();
        for (DataLoans loan : loans) {
            Object[] row = {
                loan.getId(),
                loan.getNamaPeminjam(),
                loan.getBookId(),
                loan.getTanggalPinjam(),
                loan.getTanggalKembali(),
                loan.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    private void clearForm(JTextField txtId, JTextField txtNama, JTextField txtBookId, 
                          JTextField txtTanggalPinjam, JTextField txtTanggalKembali, JComboBox<String> cbStatus) {
        txtId.setText("");
        txtNama.setText("");
        txtBookId.setText("");
        txtTanggalPinjam.setText("");
        txtTanggalKembali.setText("");
        cbStatus.setSelectedIndex(0);
    }

    private JPanel createLoansPanel(Dimension screen) {
        JPanel jPanel = new JPanel();
        jPanel.setBackground(new Color(242, 239, 232));
        jPanel.setLayout(new BorderLayout());
        jPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JLabel labelTitle = new JLabel("LOANS MANAGEMENT", SwingConstants.CENTER);
        labelTitle.setFont(new Font("Arial", Font.BOLD, 28));
        labelTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(242, 239, 232));
        formPanel.setLayout(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblId = new JLabel("Loan ID:");
        lblId.setFont(new Font("Arial", Font.PLAIN, 12));
        JTextField txtId = new JTextField();
        txtId.setEditable(false);

        JLabel lblNama = new JLabel("Nama Peminjam:");
        lblNama.setFont(new Font("Arial", Font.PLAIN, 12));
        JTextField txtNama = new JTextField();

        JLabel lblBookId = new JLabel("Book ID:");
        lblBookId.setFont(new Font("Arial", Font.PLAIN, 12));
        JTextField txtBookId = new JTextField();

        JLabel lblTanggalPinjam = new JLabel("Tanggal Pinjam (yyyy-MM-dd):");
        lblTanggalPinjam.setFont(new Font("Arial", Font.PLAIN, 12));
        JTextField txtTanggalPinjam = new JTextField();

        JLabel lblTanggalKembali = new JLabel("Tanggal Kembali (yyyy-MM-dd):");
        lblTanggalKembali.setFont(new Font("Arial", Font.PLAIN, 12));
        JTextField txtTanggalKembali = new JTextField();

        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 12));
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"dipinjam", "kembali"});

        formPanel.add(lblId);
        formPanel.add(txtId);
        formPanel.add(lblNama);
        formPanel.add(txtNama);
        formPanel.add(lblBookId);
        formPanel.add(txtBookId);
        formPanel.add(lblTanggalPinjam);
        formPanel.add(txtTanggalPinjam);
        formPanel.add(lblTanggalKembali);
        formPanel.add(txtTanggalKembali);
        formPanel.add(lblStatus);
        formPanel.add(cbStatus);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(242, 239, 232));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnAdd = new JButton("Add");
        btnAdd.setPreferredSize(new Dimension(100, 35));
        btnAdd.setFont(new Font("Arial", Font.BOLD, 12));

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setPreferredSize(new Dimension(100, 35));
        btnUpdate.setFont(new Font("Arial", Font.BOLD, 12));

        JButton btnDelete = new JButton("Delete");
        btnDelete.setPreferredSize(new Dimension(100, 35));
        btnDelete.setFont(new Font("Arial", Font.BOLD, 12));

        JButton btnClear = new JButton("Clear");
        btnClear.setPreferredSize(new Dimension(100, 35));
        btnClear.setFont(new Font("Arial", Font.BOLD, 12));

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        // Table Panel
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(new Color(242, 239, 232));
        tablePanel.setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Nama Peminjam", "Book ID", "Tanggal Pinjam", "Tanggal Kembali", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 11));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        JScrollPane scrollPane = new JScrollPane(table);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Load data ke table
        loadLoansData(tableModel);

        // Action listeners
        btnAdd.addActionListener(e -> {
            try {
                if (txtNama.getText().isEmpty() || txtBookId.getText().isEmpty() || 
                    txtTanggalPinjam.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(jPanel, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                DataLoans loan = new DataLoans();
                loan.setNamaPeminjam(txtNama.getText());
                loan.setBookId(Integer.parseInt(txtBookId.getText()));
                loan.setTanggalPinjam(Date.valueOf(txtTanggalPinjam.getText()));
                if (!txtTanggalKembali.getText().isEmpty()) {
                    loan.setTanggalKembali(Date.valueOf(txtTanggalKembali.getText()));
                }
                loan.setStatus(cbStatus.getSelectedItem().toString());

                db.insertLoan(loan);
                loadLoansData(tableModel);
                clearForm(txtId, txtNama, txtBookId, txtTanggalPinjam, txtTanggalKembali, cbStatus);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(jPanel, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                if (txtId.getText().isEmpty() || txtNama.getText().isEmpty() || txtBookId.getText().isEmpty() || 
                    txtTanggalPinjam.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(jPanel, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                DataLoans loan = new DataLoans();
                loan.setId(Integer.parseInt(txtId.getText()));
                loan.setNamaPeminjam(txtNama.getText());
                loan.setBookId(Integer.parseInt(txtBookId.getText()));
                loan.setTanggalPinjam(Date.valueOf(txtTanggalPinjam.getText()));
                if (!txtTanggalKembali.getText().isEmpty()) {
                    loan.setTanggalKembali(Date.valueOf(txtTanggalKembali.getText()));
                }
                loan.setStatus(cbStatus.getSelectedItem().toString());

                db.updateLoan(loan);
                loadLoansData(tableModel);
                clearForm(txtId, txtNama, txtBookId, txtTanggalPinjam, txtTanggalKembali, cbStatus);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(jPanel, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                if (txtId.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(jPanel, "Pilih data yang ingin dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(jPanel, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    DataLoans loan = new DataLoans();
                    loan.setId(Integer.parseInt(txtId.getText()));
                    db.deleteLoan(loan);
                    loadLoansData(tableModel);
                    clearForm(txtId, txtNama, txtBookId, txtTanggalPinjam, txtTanggalKembali, cbStatus);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(jPanel, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnClear.addActionListener(e -> clearForm(txtId, txtNama, txtBookId, txtTanggalPinjam, txtTanggalKembali, cbStatus));

        // Table selection listener
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtId.setText(table.getValueAt(row, 0).toString());
                    txtNama.setText(table.getValueAt(row, 1).toString());
                    txtBookId.setText(table.getValueAt(row, 2).toString());
                    txtTanggalPinjam.setText(table.getValueAt(row, 3).toString());
                    txtTanggalKembali.setText(table.getValueAt(row, 4) != null ? table.getValueAt(row, 4).toString() : "");
                    cbStatus.setSelectedItem(table.getValueAt(row, 5).toString());
                }
            }
        });

        // Layout
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(242, 239, 232));
        topPanel.setLayout(new BorderLayout());
        topPanel.add(labelTitle, BorderLayout.NORTH);
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        jPanel.add(topPanel, BorderLayout.NORTH);
        jPanel.add(tablePanel, BorderLayout.CENTER);

        return jPanel;
    }

    private JPanel createVisitorsPanel(Dimension screen) {
        JPanel jPanel = new JPanel();
        jPanel.setBackground(new Color(230, 240, 250));
        jPanel.setLayout(new BorderLayout());
        jPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel labelTitle = new JLabel("VISITORS MANAGEMENT", SwingConstants.CENTER);
        labelTitle.setFont(new Font("Arial", Font.BOLD, 36));
        labelTitle.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel labelInfo = new JLabel("This is the Visitors Management page. You can manage library visitors here.",
                SwingConstants.CENTER);
        labelInfo.setFont(new Font("Arial", Font.PLAIN, 20));
        labelInfo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        jPanel.add(labelTitle, BorderLayout.NORTH);
        jPanel.add(labelInfo, BorderLayout.CENTER);
        return jPanel;
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

    private JPanel createDashboardPanel(Dimension screen) {
        JPanel dashboardPanel = new JPanel(new BorderLayout(10, 10));
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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
        String[] bookColumns = {"ID", "Judul", "Penulis", "Penerbit", "Stok"};
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
        String[] loanColumns = {"ID", "Nama Peminjam", "Book ID", "Tgl Pinjam", "Tgl Kembali", "Status"};
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
        String[] visitorColumns = {"ID", "Nama Pengunjung", "Tujuan", "Waktu Datang"};
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

        dashboardPanel.add(statsPanel, BorderLayout.NORTH);
        dashboardPanel.add(tablesPanel, BorderLayout.CENTER);

        return dashboardPanel;
    }

    public void showFrame() {
        JFrame jF = new JFrame("Library Management System");
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        jF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jF.setSize(screen.width, screen.height);
        jF.setLayout(new BorderLayout(5, 5));
        ((JComponent) jF.getContentPane()).setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        jF.add(createCenterPanel(screen), BorderLayout.CENTER);
        jF.add(createTopButtonPanel(screen), BorderLayout.NORTH);
        jF.setVisible(true);
    }

    private JPanel createCenterPanel(Dimension screen) {
        cardLayout = new CardLayout();
        panelCenter = new JPanel(cardLayout);
        panelCenter.add(createDashboardPanel(screen), "Dashboard");
        panelCenter.add(createBooksPanel(screen), "Books");
        panelCenter.add(createLoansPanel(screen), "Loans");
        panelCenter.add(createVisitorsPanel(screen), "Visitors");
        cardLayout.show(panelCenter, "Dashboard");
        return panelCenter;
    }
}
