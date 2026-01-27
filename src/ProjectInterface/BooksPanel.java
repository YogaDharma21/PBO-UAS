package ProjectInterface;

import DataModel.DataBooks;
import MaintainDataPackage.MaintainDatabase;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BooksPanel extends JPanel {
    private final MaintainDatabase db;
    private int selectedBookId = -1;

    public BooksPanel() {
        super(new BorderLayout(15, 15));
        db = new MaintainDatabase();
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initComponents();
    }

    private void initComponents() {
        String[] columnNames = { "ID", "Judul", "Penulis", "Penerbit", "Stok" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable tableBuku = new JTable(tableModel);
        Font inputFont = new Font("Arial", Font.PLAIN, 18);

        JTextField txtJudul = new JTextField();
        JTextField txtPenulis = new JTextField();
        JTextField txtPenerbit = new JTextField();
        JTextField txtStok = new JTextField();

        Dimension fieldSize = new Dimension(0, 35);
        txtJudul.setPreferredSize(fieldSize);
        txtPenulis.setPreferredSize(fieldSize);
        txtPenerbit.setPreferredSize(fieldSize);
        txtStok.setPreferredSize(fieldSize);

        txtJudul.setFont(inputFont);
        txtPenulis.setFont(inputFont);
        txtPenerbit.setFont(inputFont);
        txtStok.setFont(inputFont);

        JLabel labelTitle = new JLabel("BOOKS MANAGEMENT", SwingConstants.CENTER);
        labelTitle.setFont(new Font("Arial", Font.BOLD, 32));
        add(labelTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(400, 0));
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Input Data Buku", 0, 0, new Font("Arial", Font.BOLD, 14)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = { "Judul:", "Penulis:", "Penerbit:", "Stok:" };
        JTextField[] fields = { txtJudul, txtPenulis, txtPenerbit, txtStok };

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0.1;
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Arial", Font.BOLD, 16));
            formPanel.add(lbl, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.9;
            formPanel.add(fields[i], gbc);
        }

        add(formPanel, BorderLayout.WEST);

        tableBuku.setRowHeight(30);
        tableBuku.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(tableBuku);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnAdd = new JButton("Tambah");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Hapus");

        Dimension btnSize = new Dimension(130, 40);
        btnAdd.setPreferredSize(btnSize);
        btnUpdate.setPreferredSize(btnSize);
        btnDelete.setPreferredSize(btnSize);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        add(buttonPanel, BorderLayout.SOUTH);

        Runnable refreshTable = () -> {
            tableModel.setRowCount(0);
            ArrayList<DataBooks> list = db.getBooks();
            for (DataBooks b : list) {
                tableModel
                        .addRow(new Object[] { b.getId(), b.getJudul(), b.getPenulis(), b.getPenerbit(), b.getStok() });
            }
        };

        Runnable clearForm = () -> {
            txtJudul.setText("");
            txtPenulis.setText("");
            txtPenerbit.setText("");
            txtStok.setText("");
            selectedBookId = -1;
        };

        tableBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tableBuku.getSelectedRow();
                if (row != -1) {
                    selectedBookId = (int) tableModel.getValueAt(row, 0);
                    txtJudul.setText(tableModel.getValueAt(row, 1).toString());
                    txtPenulis.setText(tableModel.getValueAt(row, 2).toString());
                    txtPenerbit.setText(tableModel.getValueAt(row, 3).toString());
                    txtStok.setText(tableModel.getValueAt(row, 4).toString());
                }
            }
        });

        btnAdd.addActionListener(e -> {
            try {
                DataBooks b = new DataBooks();
                b.setJudul(txtJudul.getText());
                b.setPenulis(txtPenulis.getText());
                b.setPenerbit(txtPenerbit.getText());
                b.setStok(Integer.parseInt(txtStok.getText()));

                db.insertBook(b);
                refreshTable.run();
                clearForm.run();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Input tidak valid!");
            }
        });

        btnUpdate.addActionListener(e -> {
            if (selectedBookId != -1) {
                try {
                    DataBooks b = new DataBooks();
                    b.setId(selectedBookId);
                    b.setJudul(txtJudul.getText());
                    b.setPenulis(txtPenulis.getText());
                    b.setPenerbit(txtPenerbit.getText());
                    b.setStok(Integer.parseInt(txtStok.getText()));

                    db.updateBook(b);
                    refreshTable.run();
                    clearForm.run();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Update gagal!");
                }
            }
        });

        btnDelete.addActionListener(e -> {
            if (selectedBookId != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus buku ini?", "Hapus",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    DataBooks b = new DataBooks();
                    b.setId(selectedBookId);
                    db.deleteBook(b);
                    refreshTable.run();
                    clearForm.run();
                }
            }
        });

        refreshTable.run();
    }
}
