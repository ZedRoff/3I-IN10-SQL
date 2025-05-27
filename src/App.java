package src;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class App {
    
    public void showInterface() {
    JFrame frame = new JFrame("Pizzeria");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 300);
    
    JLabel label = new JLabel("loullll un message oug oug");
    label.setHorizontalAlignment(SwingConstants.CENTER);
    
    frame.getContentPane().add(label);
    frame.setVisible(true);
}
}
