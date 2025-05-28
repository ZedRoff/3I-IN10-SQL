package src;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLManager {
    Connection conn;
    String DB_URL;
    String DB_USER;
    String DB_PASSWORD;
    public SQLManager() {
         DB_URL = "jdbc:mysql://127.0.0.1:3306/pizzeria";
        DB_USER= "root";
        DB_PASSWORD = "";
    }
public void connectDatabase() {
 try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            if (conn != null) {
                System.out.println("Connexion DB OK.");
            } else {
                System.out.println("Connexion DB échoué.");
        }
     } catch(ClassNotFoundException e) {
            System.out.println("Driver JDBC manquant.");
            e.printStackTrace();
    } catch (SQLException e) {
            System.out.println("Erreur connexion DB.");
            e.printStackTrace();
    }
}
public Connection getConnection() {
        return conn;
    }
}