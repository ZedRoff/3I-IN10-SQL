package src.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import src.model.Vehicule;

public class VehiculeService {
    private final Connection conn;
    public VehiculeService(Connection pConn) {
        this.conn = pConn;
    }

    public List<Vehicule> getVehiculesJamaisServi() throws SQLException {
        String sql = """
            SELECT v.id, v.type, v.immatriculation
            FROM Vehicule v
            LEFT JOIN Livraison liv ON v.id = liv.id_vehicule
            WHERE liv.id IS NULL;
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Vehicule> vehicules = new ArrayList<>();
            while (rs.next()) {
                vehicules.add(new Vehicule(
                    rs.getInt("id"),
                    rs.getString("type"),
                    rs.getString("immatriculation")
                ));
            }
            return vehicules;
        }
    }
    public void incrementUtilisation(int idVehicule) throws SQLException {
        String sql = "UPDATE Vehicule SET nombre_utilisations = nombre_utilisations + 1 WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idVehicule);
            ps.executeUpdate();
        }
    }

    public List<Vehicule> getAllVehicules() throws SQLException {
        String sql = "SELECT id, type, immatriculation FROM Vehicule";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Vehicule> vehicules = new ArrayList<>();
            while (rs.next()) {
                vehicules.add(new Vehicule(
                    rs.getInt("id"),
                    rs.getString("type"),
                    rs.getString("immatriculation")
                ));
            }
            return vehicules;
        }
    }
}
