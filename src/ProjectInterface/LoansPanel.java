package ProjectInterface;

import DataModel.DataLoans;
import MaintainDataPackage.MaintainDatabase;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LoansPanel extends JPanel {
    private final MaintainDatabase db;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtNama, txtBookId, txtTanggalPinjam, txtTanggalKembali;
    private JComboBox<String> cbStatus;
    private JTable table;

    public LoansPanel(Dimension screen) {
        super(new BorderLayout());
        db = new MaintainDatabase();
        setBackground(new Color(242, 239, 232));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initComponents();
    }

    private void initComponents() {
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
        txtId = new JTextField();
        txtId.setEditable(false);

        JLabel lblNama = new JLabel("Nama Peminjam:");
        lblNama.setFont(new Font("Arial", Font.PLAIN, 12));
        txtNama = new JTextField();

        JLabel lblBookId = new JLabel("Book ID:");
        lblBookId.setFont(new Font("Arial", Font.PLAIN, 12));
        txtBookId = new JTextField();

        JLabel lblTanggalPinjam = new JLabel("Tanggal Pinjam (yyyy-MM-dd):");
        lblTanggalPinjam.setFont(new Font("Arial", Font.PLAIN, 12));
        txtTanggalPinjam = new JTextField();

        JLabel lblTanggalKembali = new JLabel("Tanggal Kembali (yyyy-MM-dd):");
        lblTanggalKembali.setFont(new Font("Arial", Font.PLAIN, 12));
        txtTanggalKembali = new JTextField();

        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 12));
        cbStatus = new JComboBox<>(new String[] { "dipinjam", "kembali" });

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

        String[] columnNames = { "ID", "Nama Peminjam", "Book ID", "Tanggal Pinjam", "Tanggal Kembali", "Status" };
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 11));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        JScrollPane scrollPane = new JScrollPane(table);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Load data
        loadLoansData();

        // Action listeners
        btnAdd.addActionListener(e -> addLoan());
        btnUpdate.addActionListener(e -> updateLoan());
        btnDelete.addActionListener(e -> deleteLoan());
        btnClear.addActionListener(e -> clearForm());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtId.setText(table.getValueAt(row, 0).toString());
                    txtNama.setText(table.getValueAt(row, 1).toString());
                    txtBookId.setText(table.getValueAt(row, 2).toString());
                    txtTanggalPinjam.setText(table.getValueAt(row, 3).toString());
                    Object tglKembali = table.getValueAt(row, 4);
                    txtTanggalKembali.setText(tglKembali != null ? tglKembali.toString() : "");
                    cbStatus.setSelectedItem(table.getValueAt(row, 5).toString());
                }
            }
        });

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(242, 239, 232));
        topPanel.setLayout(new BorderLayout());
        topPanel.add(labelTitle, BorderLayout.NORTH);
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
    }

    private void loadLoansData() {
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

    private void clearForm() {
        txtId.setText("");
        txtNama.setText("");
        txtBookId.setText("");
        txtTanggalPinjam.setText("");
        txtTanggalKembali.setText("");
        cbStatus.setSelectedIndex(0);
    }

    private void addLoan() {
        try {
            if (txtNama.getText().isEmpty() || txtBookId.getText().isEmpty() ||
                    txtTanggalPinjam.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
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
            loadLoansData();
            clearForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateLoan() {
        try {
            if (txtId.getText().isEmpty() || txtNama.getText().isEmpty() || txtBookId.getText().isEmpty() ||
                    txtTanggalPinjam.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
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
            loadLoansData();
            clearForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteLoan() {
        try {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                DataLoans loan = new DataLoans();
                loan.setId(Integer.parseInt(txtId.getText()));
                db.deleteLoan(loan);
                loadLoansData();
                clearForm();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
