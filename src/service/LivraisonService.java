package src.service;

import java.sql.*;

public class LivraisonService {
    private final Connection conn;
    public LivraisonService(Connection pConn) {
        this.conn = pConn;
    }

    public int createLivraison(Timestamp date, int retard, int idClient, int idLivreur, int idVehicule) throws SQLException {
        String sql = "INSERT INTO Livraison (date, retard, id_client, id_livreur, id_vehicule) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setTimestamp(1, date);
            ps.setInt(2, retard);
            ps.setInt(3, idClient);
            ps.setInt(4, idLivreur);
            ps.setInt(5, idVehicule);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }
}