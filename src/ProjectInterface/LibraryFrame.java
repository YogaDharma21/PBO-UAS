package ProjectInterface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import DataModel.DataVisitors;
import MaintainDataPackage.MaintainDatabase;

public class LibraryFrame {
    private CardLayout cardLayout;
    private JPanel panelCenter;

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
        JPanel tabelPanel = new JPanel();
        tabelPanel.setBackground(new Color(230, 240, 250));
        tabelPanel.setLayout(new BorderLayout());
        tabelPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel labelTitle = new JLabel("VISITORS MANAGEMENT", SwingConstants.CENTER);
        labelTitle.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitle.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(230, 240, 250));
        
        tabelPanel.add(contentPanel, BorderLayout.CENTER);
        tabelPanel.add(labelTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 50, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setBackground(new Color(230, 240, 250));

        Font font = new Font("Arial", Font.PLAIN, 16);

        JLabel JlabelId = new JLabel("ID Pengunjung:");
        JTextField JTextFieldId = new JTextField();
        JTextFieldId.setText("");
        JTextFieldId.setEditable(false);
        JlabelId.setFont(font);
        

        JLabel JlabelNama = new JLabel("Nama Pengunjung:");
        JTextField JTextFieldNama = new JTextField();
        JlabelNama.setFont(font);

        JLabel JlabelTujuan = new JLabel("Tujuan:");
        JTextField JTextFieldTujuan = new JTextField();
        JlabelTujuan.setFont(font);

        JLabel JlabelWaktuDatang = new JLabel("Waktu Datang:");
        JTextField JTextFieldWaktuDatang = new JTextField();
        JTextFieldWaktuDatang.setText("");
        JTextFieldWaktuDatang.setEditable(false);
        JlabelWaktuDatang.setFont(font);

        JButton btnSimpan = new JButton("simpan");
        JButton btUpdate=new JButton("Update");
        JButton btDelete=new JButton("Delete");
        btnSimpan.setFont(new Font("Arial", Font.BOLD, 16));


        formPanel.add(JlabelId);
        formPanel.add(JTextFieldId);
        formPanel.add(JlabelNama);
        formPanel.add(JTextFieldNama);
        formPanel.add(JlabelTujuan);
        formPanel.add(JTextFieldTujuan);
        formPanel.add(JlabelWaktuDatang);
        formPanel.add(JTextFieldWaktuDatang);
        formPanel.add(new JLabel(""));
        formPanel.add(btnSimpan);
        formPanel.add(btDelete);
        formPanel.add(btUpdate);
        contentPanel.add(formPanel, BorderLayout.NORTH);

        DefaultTableModel mdTableModel=new DefaultTableModel(DataVisitors.kolom,0);
        MaintainDatabase mdb = new MaintainDatabase();
        JPanel Jtable=new JPanel();
        Jtable.setBackground(new Color(230, 240, 250));
        Jtable.setLayout(new BorderLayout());
    
        JTable tabelForm  = new JTable(mdTableModel);
        JTableHeader jtHeader = tabelForm.getTableHeader();
        jtHeader.setFont(new Font("Arial",Font.BOLD,16));
        
        jtHeader.setPreferredSize(
            new Dimension(0, (int)(screen.height*0.04))
        );

        JScrollPane scrollPane=new JScrollPane(tabelForm);
        for (DataVisitors m : mdb.getVisitors()) {
            mdTableModel.addRow(new Object[]{
                m.getId(),
                m.getNamaPengunjung(),
                m.getTujuan(),
                m.getWaktuDatang(),
            
            });
        }
      

        tabelForm.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tabelForm.getSelectedRow();

                if (row >= 0) {
                    int id = Integer.parseInt(tabelForm.getValueAt(row, 0).toString());
                    String nama = tabelForm.getValueAt(row, 1).toString();
                    String tujuan = tabelForm.getValueAt(row, 2).toString();
                    String waktu = tabelForm.getValueAt(row, 3).toString();

                    JTextFieldId.setText(String.valueOf(id));
                    JTextFieldNama.setText(nama);
                    JTextFieldTujuan.setText(tujuan);
                    JTextFieldWaktuDatang.setText(waktu);
                }
            }
        });

        btnSimpan.addActionListener(e -> {
            String nama = JTextFieldNama.getText().trim();
            String tujuan = JTextFieldTujuan.getText().trim();

            if (nama.isEmpty() || tujuan.isEmpty()) {
                JOptionPane.showMessageDialog(tabelPanel,
                        "Nama, Tujuan tidak boleh kosong!",
                        "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                DataVisitors dv = new DataVisitors();
                dv.setNamaPengunjung(nama);
                dv.setTujuan(tujuan);
                mdb.insertVisitor(dv);

                mdTableModel.setRowCount(0);
                    for (DataVisitors m : mdb.getVisitors()) {
                        mdTableModel.addRow(new Object[]{
                            m.getId(),
                            m.getNamaPengunjung(),
                            m.getTujuan(),
                            m.getWaktuDatang(),
                        });
                    }
                
                    JTextFieldNama.setText("");
                    JTextFieldTujuan.setText("");
                
                JOptionPane.showMessageDialog(tabelPanel, "Data pengunjung berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
               
            }catch (Exception ex) {
                JOptionPane.showMessageDialog(tabelPanel,
                        "Terjadi kesalahan saat menyimpan data: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btUpdate.addActionListener(e -> {
            int row = tabelForm.getSelectedRow();
            int id = Integer.parseInt(tabelForm.getValueAt(row, 0).toString());
            String nama = JTextFieldNama.getText();
            String tujuan = JTextFieldTujuan.getText();

            if (nama.isEmpty() || tujuan.isEmpty()) {
                JOptionPane.showMessageDialog(tabelPanel,
                        "Nama, Tujuan tidak boleh kosong!",
                        "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (row == -1) {
                JOptionPane.showMessageDialog(tabelForm, "Pilih data terlebih dahulu");
                return;
            }
                try {
                    DataVisitors dv = new DataVisitors();
                    dv.setId(id);
                    dv.setNamaPengunjung(nama);
                    dv.setTujuan(tujuan);
                    mdb.updateVisitor(dv);

                mdTableModel.setRowCount(0);
                    for (DataVisitors m : mdb.getVisitors()) {
                        mdTableModel.addRow(new Object[]{
                            m.getId(),
                            m.getNamaPengunjung(),
                            m.getTujuan(),
                            m.getWaktuDatang(),
                        });
                    }
                
                    JTextFieldNama.setText("");
                    JTextFieldTujuan.setText("");
                    JTextFieldId.setText("");
                    JTextFieldWaktuDatang.setText("");
                
                JOptionPane.showMessageDialog(tabelPanel, "Data berhasil diperbaharui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
               
            }catch (Exception ex) {
                JOptionPane.showMessageDialog(tabelPanel,
                        "Terjadi kesalahan saat Update data: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btDelete.addActionListener(e ->{
            int row = tabelForm.getSelectedRow();
            if (row == -1) return;

            int confirm = JOptionPane.showConfirmDialog(
            tabelForm,
            "Yakin ingin menghapus data ini?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION
        );
        
            if (confirm != JOptionPane.YES_OPTION) return;

                try {
                    int id = Integer.parseInt(tabelForm.getValueAt(row, 0).toString());

                    DataVisitors dv = new DataVisitors();
                    dv.setId(id);
                    mdb.deleteVisitor(dv); 

                    mdTableModel.setRowCount(0);
                    for (DataVisitors m : mdb.getVisitors()) {
                        mdTableModel.addRow(new Object[]{
                        m.getId(),
                        m.getNamaPengunjung(),
                        m.getTujuan(),
                        m.getWaktuDatang(),
                        });
                    }
                            JTextFieldId.setText("");
                            JTextFieldNama.setText("");
                            JTextFieldTujuan.setText("");

                            JOptionPane.showMessageDialog(tabelForm, "Data berhasil dihapus");

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                            tabelForm,
                            "Gagal menghapus data: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                });

        tabelForm.setRowHeight(26);
        Jtable.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(Jtable, BorderLayout.CENTER);

        return tabelPanel;
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
