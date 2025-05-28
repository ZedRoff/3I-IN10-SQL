package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class App {

    public void showInterface() {
        JFrame frame = new JFrame("RaPizz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        // Header with padding
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.LIGHT_GRAY);
        //header.setPreferredSize(new Dimension(0, 100));
        header.setBorder(new EmptyBorder(20, 20, 20, 20));  // padding

        // Title panel to center vertically
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 35)); // 35px vertical gap to center
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("RaPizz");
        titleLabel.setPreferredSize(new Dimension(200, 30)); // smaller height for better vertical centering
        titlePanel.add(titleLabel);
        header.add(titlePanel, BorderLayout.WEST);

        // Breadcrumb panel to center vertically
        JPanel breadcrumbPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 1)); // horizontal gap 5, vertical 35
        breadcrumbPanel.setOpaque(false);
        breadcrumbPanel.add(new JLabel("Home"));
        breadcrumbPanel.add(new JLabel(">"));
        breadcrumbPanel.add(new JLabel("Menu"));
        breadcrumbPanel.add(new JLabel(">"));
        breadcrumbPanel.add(new JLabel("Pizza"));
        header.add(breadcrumbPanel, BorderLayout.EAST);

        // Main content
        JPanel main = new JPanel();
        main.setBackground(Color.WHITE);
        

        JLabel mainLabel = new JLabel("Main Content Area");
        main.add(mainLabel);

        // Footer
        JPanel footer = new JPanel();
        footer.setBackground(Color.LIGHT_GRAY);
        footer.setPreferredSize(new Dimension(0, 50));
        JLabel footerLabel = new JLabel("© 2025 RaPizz Inc.");
        footer.add(footerLabel);

        // Add panels to frame
        frame.add(header, BorderLayout.NORTH);
        frame.add(main, BorderLayout.CENTER);
        frame.add(footer, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
