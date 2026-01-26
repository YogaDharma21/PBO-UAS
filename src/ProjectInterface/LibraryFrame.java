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
        panelCenter.add(new DashboardPanel(), "Dashboard");
        panelCenter.add(new BooksPanel(screen), "Books");
        panelCenter.add(new LoansPanel(screen), "Loans");
        panelCenter.add(new VisitorsPanel(screen), "Visitors");
        cardLayout.show(panelCenter, "Dashboard");
        return panelCenter;
    }
}
