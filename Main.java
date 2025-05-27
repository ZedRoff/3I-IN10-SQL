import javax.swing.*;

import src.App;
import src.SQLManager; 

public class Main {
    SQLManager sqlManager;
    App app;
    public Main() {
        sqlManager = new SQLManager();
        app = new App();
    }
     public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main main = new Main();
            main.sqlManager.connectDatabase();
            main.app.showInterface();
        });
    }
}