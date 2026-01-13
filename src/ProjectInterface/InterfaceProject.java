package ProjectInterface;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;
import DataModel.DataMahasiswa;
import MaintainDataPackage.MaintainDatabase;

public class InterfaceProject {
    private CardLayout cardLayout;
    private JPanel panelCenter;
    private MaintainDatabase mDB = new MaintainDatabase();

    private JTextField jTextFieldNim, jTextFieldNama, jTextFieldUmur;
    private DefaultTableModel dmTableModel;
    private JButton btSubmit;
    private JLabel labelJudulForm;
    private boolean isEditMode = false;

    private JPanel tampilanSampingButton(Dimension screen) {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel.setPreferredSize(new Dimension((int) (screen.width * 0.15), 0));

        JButton btInputData = new JButton("Input Data");
        btInputData.setAlignmentX(Component.LEFT_ALIGNMENT);
        btInputData.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        btInputData.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                prepareInsertMode();
                cardLayout.show(panelCenter, "Input");
            }
        });

        JButton btViewData = new JButton("View Data");
        btViewData.setAlignmentX(Component.LEFT_ALIGNMENT);
        btViewData.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        btViewData.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refreshTableData();
                cardLayout.show(panelCenter, "View");
            }
        });

        jPanel.add(btInputData);
        jPanel.add(Box.createVerticalStrut(5));
        jPanel.add(btViewData);
        return jPanel;
    }

    private JPanel inputCenter(Dimension screen) {
        JPanel jPanel = new JPanel();
        jPanel.setBackground(new Color(234, 239, 143));
        jPanel.setBorder(BorderFactory.createEmptyBorder(100, 80, 200, 200));
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        Font fntLabel = new Font("Arial", Font.BOLD, 28);

        labelJudulForm = new JLabel("INPUT DATA MAHASISWA");
        labelJudulForm.setFont(new Font("Arial", Font.BOLD, 32));
        labelJudulForm.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel jLabelNim = new JLabel("Nim Mahasiswa : ");
        JLabel jLabelNama = new JLabel("Nama Mahasiswa : ");
        JLabel jLabelUmur = new JLabel("Umur Mahasiswa : ");

        jTextFieldNim = new JTextField();
        jTextFieldNama = new JTextField();
        jTextFieldUmur = new JTextField();

        JPanel jPnlBut = new JPanel();
        jPnlBut.setLayout(new BorderLayout());
        jPnlBut.setMaximumSize(new Dimension((int) (screen.width * 0.65), (int) (screen.height * 0.08)));
        jPnlBut.setBackground(new Color(234, 239, 143));

        btSubmit = new JButton("Submit");
        btSubmit.setPreferredSize(new Dimension(200, 0));
        btSubmit.addActionListener(e -> {
            try {
                DataMahasiswa dm = new DataMahasiswa();
                dm.setNim(jTextFieldNim.getText());
                dm.setNama(jTextFieldNama.getText());
                dm.setUmur(Integer.parseInt(jTextFieldUmur.getText()));

                if (isEditMode) {
                    mDB.updateData(dm);
                    JOptionPane.showMessageDialog(null, "Data Berhasil Diupdate!");
                } else {
                    mDB.insertData(dm);
                    JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan!");
                }
                prepareInsertMode();
                refreshTableData();
                cardLayout.show(panelCenter, "View");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        jPnlBut.add(btSubmit, BorderLayout.EAST);

        jTextFieldNim.setMinimumSize(new Dimension(50, 20));
        jTextFieldNim.setMaximumSize(new Dimension(200, 48));
        jTextFieldNim.setFont(new Font("Arial", Font.PLAIN, 25));
        jTextFieldNama.setMinimumSize(new Dimension(50, 20));
        jTextFieldNama.setMaximumSize(new Dimension(500, 48));
        jTextFieldNama.setFont(new Font("Arial", Font.PLAIN, 25));
        jTextFieldUmur.setMinimumSize(new Dimension(50, 20));
        jTextFieldUmur.setMaximumSize(new Dimension(100, 48));
        jTextFieldUmur.setFont(new Font("Arial", Font.PLAIN, 25));

        jLabelNim.setFont(fntLabel);
        jLabelNama.setFont(fntLabel);
        jLabelUmur.setFont(fntLabel);
        jLabelNim.setAlignmentX(Component.LEFT_ALIGNMENT);
        jLabelNama.setAlignmentX(Component.LEFT_ALIGNMENT);
        jLabelUmur.setAlignmentX(Component.LEFT_ALIGNMENT);
        jTextFieldNim.setAlignmentX(Component.LEFT_ALIGNMENT);
        jTextFieldNama.setAlignmentX(Component.LEFT_ALIGNMENT);
        jTextFieldUmur.setAlignmentX(Component.LEFT_ALIGNMENT);

        jPanel.add(labelJudulForm);
        jPanel.add(Box.createVerticalStrut(20));
        jPanel.add(jLabelNim);
        jPanel.add(jTextFieldNim);
        jPanel.add(Box.createVerticalStrut(20));
        jPanel.add(jLabelNama);
        jPanel.add(jTextFieldNama);
        jPanel.add(Box.createVerticalStrut(20));
        jPanel.add(jLabelUmur);
        jPanel.add(jTextFieldUmur);
        jPanel.add(Box.createVerticalStrut(20));
        jPanel.add(jPnlBut);

        return jPanel;
    }

    private JPanel viewCenter(Dimension screen) {
        JPanel jPanel = new JPanel();
        jPanel.setBackground(new Color(242, 239, 232));
        jPanel.setLayout(new BorderLayout());

        String[] kolom = { "NIM", "Nama", "Umur", "Aksi" };
        dmTableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        JTable jTable = new JTable(dmTableModel);

        jTable.setRowHeight(50);
        jTable.setFont(new Font("Arial", Font.PLAIN, 18));

        JTableHeader jtHeader = jTable.getTableHeader();
        jtHeader.setFont(new Font("Arial", Font.BOLD, 20));
        jtHeader.setPreferredSize(new Dimension(0, (int) (screen.height * 0.04)));

        jTable.getColumnModel().getColumn(3).setPreferredWidth(200);
        jTable.getColumnModel().getColumn(3).setCellRenderer(new ActionPanelRenderer());
        jTable.getColumnModel().getColumn(3).setCellEditor(new ActionPanelEditor());

        JScrollPane scrollPane = new JScrollPane(jTable);
        jPanel.add(scrollPane);
        return jPanel;
    }

    private void refreshTableData() {
        dmTableModel.setRowCount(0);
        ArrayList<DataMahasiswa> listDM = mDB.getData();
        for (DataMahasiswa dm : listDM) {
            Object[] row = { dm.getNim(), dm.getNama(), dm.getUmur(), "" };
            dmTableModel.addRow(row);
        }
    }

    private void prepareEditMode(String nim, String nama, String umur) {
        isEditMode = true;
        labelJudulForm.setText("EDIT DATA MAHASISWA");
        btSubmit.setText("Update Data");
        jTextFieldNim.setText(nim);
        jTextFieldNim.setEditable(false);
        jTextFieldNama.setText(nama);
        jTextFieldUmur.setText(umur);
    }

    private void prepareInsertMode() {
        isEditMode = false;
        labelJudulForm.setText("INPUT DATA MAHASISWA");
        btSubmit.setText("Submit");
        jTextFieldNim.setText("");
        jTextFieldNim.setEditable(true);
        jTextFieldNama.setText("");
        jTextFieldUmur.setText("");
    }

    class ActionPanelRenderer extends JPanel implements TableCellRenderer {
        JButton btnEdit = new JButton("Update");
        JButton btnDelete = new JButton("Delete");

        public ActionPanelRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            add(btnEdit);
            add(btnDelete);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            return this;
        }
    }

    class ActionPanelEditor extends AbstractCellEditor implements TableCellEditor {
        JPanel panel = new JPanel();
        JButton btnEdit = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JTable table;
        int row;

        public ActionPanelEditor() {
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            btnEdit.addActionListener(e -> {
                prepareEditMode(
                        table.getValueAt(row, 0).toString(),
                        table.getValueAt(row, 1).toString(),
                        table.getValueAt(row, 2).toString());
                cardLayout.show(panelCenter, "Input");
                fireEditingStopped();
            });

            btnDelete.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(panel, "Hapus data NIM: " + table.getValueAt(row, 0) + "?");
                if (confirm == JOptionPane.YES_OPTION) {
                    DataMahasiswa dm = new DataMahasiswa();
                    dm.setNim(table.getValueAt(row, 0).toString());
                    mDB.deleteData(dm);
                    refreshTableData();
                }
                fireEditingStopped();
            });
            
            panel.add(btnEdit);
            panel.add(btnDelete);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            this.table = table;
            this.row = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }

    public void InterfaceUtama() {
        JFrame jF = new JFrame("Master Data Siswa");
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        jF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jF.setSize(screen.width, screen.height);
        jF.setLayout(new BorderLayout(5, 5));
        ((JComponent) jF.getContentPane()).setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        jF.add(centerFill(screen), BorderLayout.CENTER);
        jF.add(tampilanSampingButton(screen), BorderLayout.WEST);
        jF.setVisible(true);
    }

    private JPanel centerFill(Dimension screen) {
        cardLayout = new CardLayout();
        panelCenter = new JPanel(cardLayout);
        panelCenter.add(inputCenter(screen), "Input");
        panelCenter.add(viewCenter(screen), "View");
        return panelCenter;
    }
}
