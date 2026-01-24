package ProjectInterface;

import DataModel.DataBooks;
import MaintainDataPackage.MaintainDatabase;
import java.awt.*;
import java.awt.event.*;
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

        jPanel.add(btInputBooks);
        jPanel.add(btLoans);
        jPanel.add(btVisitors);
        return jPanel;
    }

    private JPanel createBooksPanel(Dimension screen) {
        MaintainDatabase db = new MaintainDatabase();
        int[] selectedBookId = {-1}; 
        
        String[] columnNames = {"ID", "Judul", "Penulis", "Penerbit", "Stok"};
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

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel labelTitle = new JLabel("BOOKS MANAGEMENT", SwingConstants.CENTER);
        labelTitle.setFont(new Font("Arial", Font.BOLD, 32));
        mainPanel.add(labelTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(400, 0)); 
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Input Data Buku", 0, 0, new Font("Arial", Font.BOLD, 14)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Judul:", "Penulis:", "Penerbit:", "Stok:"};
        JTextField[] fields = {txtJudul, txtPenulis, txtPenerbit, txtStok};
        
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            gbc.weightx = 0.1;
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Arial", Font.BOLD, 16));
            formPanel.add(lbl, gbc);
            
            gbc.gridx = 1;
            gbc.weightx = 0.9;
            formPanel.add(fields[i], gbc);
        }
        
        mainPanel.add(formPanel, BorderLayout.WEST);

        tableBuku.setRowHeight(30);
        tableBuku.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(tableBuku);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

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
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        Runnable refreshTable = () -> {
            tableModel.setRowCount(0);
            ArrayList<DataBooks> list = db.getBooks();
            for (DataBooks b : list) {
                tableModel.addRow(new Object[]{b.getId(), b.getJudul(), b.getPenulis(), b.getPenerbit(), b.getStok()});
            }
        };

        Runnable clearForm = () -> {
            txtJudul.setText(""); txtPenulis.setText(""); 
            txtPenerbit.setText(""); txtStok.setText("");
            selectedBookId[0] = -1;
        };

        tableBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tableBuku.getSelectedRow();
                if (row != -1) {
                    selectedBookId[0] = (int) tableModel.getValueAt(row, 0);
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
                JOptionPane.showMessageDialog(mainPanel, "Input tidak valid!");
            }
        });

        btnUpdate.addActionListener(e -> {
            if (selectedBookId[0] != -1) {
                try {
                    DataBooks b = new DataBooks();
                    b.setId(selectedBookId[0]);
                    b.setJudul(txtJudul.getText());
                    b.setPenulis(txtPenulis.getText());
                    b.setPenerbit(txtPenerbit.getText());
                    b.setStok(Integer.parseInt(txtStok.getText()));
                    
                    db.updateBook(b);
                    refreshTable.run();
                    clearForm.run();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Update gagal!");
                }
            }
        });

        btnDelete.addActionListener(e -> {
            if (selectedBookId[0] != -1) {
                int confirm = JOptionPane.showConfirmDialog(mainPanel, "Yakin hapus buku ini?", "Hapus", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    DataBooks b = new DataBooks();
                    b.setId(selectedBookId[0]);
                    db.deleteBook(b);
                    refreshTable.run();
                    clearForm.run();
                }
            }
        });

        refreshTable.run();
        return mainPanel;
    }

    private JPanel createLoansPanel(Dimension screen) {
        JPanel jPanel = new JPanel();
        jPanel.setBackground(new Color(242, 239, 232));
        jPanel.setLayout(new BorderLayout());
        jPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel labelTitle = new JLabel("LOANS MANAGEMENT", SwingConstants.CENTER);
        labelTitle.setFont(new Font("Arial", Font.BOLD, 36));
        labelTitle.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel labelInfo = new JLabel("This is the Loans Management page. You can manage book loans and returns here.",
                SwingConstants.CENTER);
        labelInfo.setFont(new Font("Arial", Font.PLAIN, 20));
        labelInfo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        jPanel.add(labelTitle, BorderLayout.NORTH);
        jPanel.add(labelInfo, BorderLayout.CENTER);
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
        panelCenter.add(createBooksPanel(screen), "Books");
        panelCenter.add(createLoansPanel(screen), "Loans");
        panelCenter.add(createVisitorsPanel(screen), "Visitors");
        return panelCenter;
    }
}
