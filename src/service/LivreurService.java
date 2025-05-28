package src.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import src.model.Livreur;
import src.model.LivreurRetardStat;

public class LivreurService {
    private final Connection conn;
    public LivreurService(Connection pConn) {
        this.conn = pConn;
    }
   public LivreurRetardStat getPireLivreur() throws SQLException {
    String sql = """
        SELECT l.id, l.nom,
               COUNT(*) AS nb_retards,
               SUM(liv.retard) AS total_retard_minutes
        FROM Livreur l
        JOIN Livraison liv ON l.id = liv.id_livreur
        WHERE liv.retard > 0
        GROUP BY l.id, l.nom
        ORDER BY nb_retards DESC, total_retard_minutes DESC
        LIMIT 1;
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            return new LivreurRetardStat(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getInt("nb_retards"),
                rs.getInt("total_retard_minutes")
            );
        }
        return null;
    }

}
public List<Livreur> getAllLivreurs() throws SQLException {
        List<Livreur> livreurs = new ArrayList<>();
        String sql = "SELECT id, nom, type_conducteur, nombre_retards FROM Livreur";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                livreurs.add(new Livreur(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("type_conducteur"),
                    rs.getInt("nombre_retards")
                ));
            }
        }
        return livreurs;
    }
    public void incrementRetard(int livreurId) throws SQLException {
    String sql = "UPDATE Livreur SET nombre_retards = nombre_retards + 1 WHERE id = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, livreurId);
        ps.executeUpdate();
    }
}
}