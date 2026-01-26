package ProjectInterface;

import DataModel.DataVisitors;
import MaintainDataPackage.MaintainDatabase;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class VisitorsPanel extends JPanel {
    private final MaintainDatabase mdb;
    private DefaultTableModel mdTableModel;
    private JTextField JTextFieldId, JTextFieldNama, JTextFieldTujuan, JTextFieldWaktuDatang;
    private JTable tabelForm;

    public VisitorsPanel(Dimension screen) {
        super(new BorderLayout());
        mdb = new MaintainDatabase();
        setBackground(new Color(230, 240, 250));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        initComponents(screen);
    }

    private void initComponents(Dimension screen) {
        JLabel labelTitle = new JLabel("VISITORS MANAGEMENT", SwingConstants.CENTER);
        labelTitle.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitle.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(230, 240, 250));

        add(contentPanel, BorderLayout.CENTER);
        add(labelTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 50, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setBackground(new Color(230, 240, 250));

        Font font = new Font("Arial", Font.PLAIN, 16);

        JLabel JlabelId = new JLabel("ID Pengunjung:");
        JTextFieldId = new JTextField();
        JTextFieldId.setText("");
        JTextFieldId.setEditable(false);
        JlabelId.setFont(font);


        JLabel JlabelNama = new JLabel("Nama Pengunjung:");
        JTextFieldNama = new JTextField();
        JlabelNama.setFont(font);

        JLabel JlabelTujuan = new JLabel("Tujuan:");
        JTextFieldTujuan = new JTextField();
        JlabelTujuan.setFont(font);

        JLabel JlabelWaktuDatang = new JLabel("Waktu Datang:");
        JTextFieldWaktuDatang = new JTextField();
        JTextFieldWaktuDatang.setText("");
        JTextFieldWaktuDatang.setEditable(false);
        JlabelWaktuDatang.setFont(font);

        JButton btnSimpan = new JButton("simpan");
        JButton btUpdate=new JButton("Update");
        JButton btDelete=new JButton("Delete");
        JButton btnReset = new JButton("Reset");
        btnReset.setFont(new Font("Arial", Font.BOLD, 16));
        btnSimpan.setFont(new Font("Arial", Font.BOLD, 16));
        btDelete.setFont(new Font("Arial", Font.BOLD, 16));
        btUpdate.setFont(new Font("Arial", Font.BOLD, 16));


        formPanel.add(JlabelId);
        formPanel.add(JTextFieldId);
        formPanel.add(JlabelNama);
        formPanel.add(JTextFieldNama);
        formPanel.add(JlabelTujuan);
        formPanel.add(JTextFieldTujuan);
        formPanel.add(JlabelWaktuDatang);
        formPanel.add(JTextFieldWaktuDatang);
        formPanel.add(btnSimpan);
        formPanel.add(btDelete);
        formPanel.add(btUpdate);
        formPanel.add(btnReset);

        contentPanel.add(formPanel, BorderLayout.NORTH);

        mdTableModel=new DefaultTableModel(DataVisitors.kolom,0);

        JPanel Jtable=new JPanel();
        Jtable.setBackground(new Color(230, 240, 250));
        Jtable.setLayout(new BorderLayout());

        tabelForm  = new JTable(mdTableModel);
        JTableHeader jtHeader = tabelForm.getTableHeader();
        jtHeader.setFont(new Font("Arial",Font.BOLD,16));

        jtHeader.setPreferredSize(
            new Dimension(0, (int)(screen.height*0.04))
        );

        JScrollPane scrollPane=new JScrollPane(tabelForm);
        loadVisitorsData();

        tabelForm.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tabelForm.getSelectedRow();

                if (row >= 0) {
                    int id = Integer.parseInt(tabelForm.getValueAt(row, 0).toString());
                    String nama = tabelForm.getValueAt(row, 1).toString();
                    String tujuan = tabelForm.getValueAt(row, 2).toString();
                    Object waktuObj = tabelForm.getValueAt(row, 3);
                    String waktu = waktuObj != null ? waktuObj.toString() : "";

                    JTextFieldId.setText(String.valueOf(id));
                    JTextFieldNama.setText(nama);
                    JTextFieldTujuan.setText(tujuan);
                    JTextFieldWaktuDatang.setText(waktu);
                }
            }
        });

        btnReset.addActionListener(e -> clearForm());

        btnSimpan.addActionListener(e -> insertVisitor());

        btUpdate.addActionListener(e -> updateVisitor());

        btDelete.addActionListener(e -> deleteVisitor());

        tabelForm.setRowHeight(26);
        Jtable.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(Jtable, BorderLayout.CENTER);
    }

    private void loadVisitorsData() {
        mdTableModel.setRowCount(0);
        for (DataVisitors m : mdb.getVisitors()) {
            mdTableModel.addRow(new Object[]{
                m.getId(),
                m.getNamaPengunjung(),
                m.getTujuan(),
                m.getWaktuDatang(),
            });
        }
    }

    private void clearForm() {
        JTextFieldId.setText("");
        JTextFieldNama.setText("");
        JTextFieldTujuan.setText("");
        JTextFieldWaktuDatang.setText("");
        tabelForm.clearSelection();
    }

    private void insertVisitor() {
        String nama = JTextFieldNama.getText().trim();
        String tujuan = JTextFieldTujuan.getText().trim();

        if (nama.isEmpty() || tujuan.isEmpty()) {
            JOptionPane.showMessageDialog(this,
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

            loadVisitorsData();
            JTextFieldNama.setText("");
            JTextFieldTujuan.setText("");

            JOptionPane.showMessageDialog(this, "Data pengunjung berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);

        }catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Terjadi kesalahan saat menyimpan data: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateVisitor() {
        int row = tabelForm.getSelectedRow();

        String nama = JTextFieldNama.getText();
        String tujuan = JTextFieldTujuan.getText();

        if (nama.isEmpty() || tujuan.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nama, Tujuan tidak boleh kosong!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (row == -1) {
            JOptionPane.showMessageDialog(tabelForm, "Pilih data terlebih dahulu");
            return;
        }

        int id = Integer.parseInt(tabelForm.getValueAt(row, 0).toString());

        try {
            DataVisitors dv = new DataVisitors();
            dv.setId(id);
            dv.setNamaPengunjung(nama);
            dv.setTujuan(tujuan);
            mdb.updateVisitor(dv);

            loadVisitorsData();
            clearForm();

            JOptionPane.showMessageDialog(this, "Data berhasil diperbaharui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);

        }catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Terjadi kesalahan saat Update data: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteVisitor() {
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

            loadVisitorsData();
            clearForm();

            JOptionPane.showMessageDialog(tabelForm, "Data berhasil dihapus");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                tabelForm,
                "Gagal menghapus data: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
