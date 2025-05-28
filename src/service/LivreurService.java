package src.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}