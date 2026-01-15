package ProjectInterface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
