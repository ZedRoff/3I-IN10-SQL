package src.Provider;

import java.sql.Connection;
import src.DAO.VehiculeDAO;

public class DAOProvider {
    private Connection conn;

    public VehiculeDAO vehiculeDAO;
    public DAOProvider(Connection pConn) {
        this.conn = pConn;
    }
}