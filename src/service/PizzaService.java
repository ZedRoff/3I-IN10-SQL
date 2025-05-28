package src.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import src.model.PizzaStat;

public class PizzaService {
    private final Connection conn;
    public PizzaService(Connection pConn) {
        this.conn = pConn;
    }
    public PizzaStat getMeilleurePizza() throws SQLException {
    String sql = """
        SELECT p.id, p.nom, COUNT(*) AS nb_commandes
        FROM Pizza p
        JOIN Commande_pizza c ON p.id = c.id_pizza
        GROUP BY p.id, p.nom
        ORDER BY nb_commandes DESC
        LIMIT 1;
    """;
    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            return new PizzaStat(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getInt("nb_commandes")
            );
        }
        return null;
    }
}
}