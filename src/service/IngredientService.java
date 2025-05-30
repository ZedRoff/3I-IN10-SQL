package src.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import src.model.IngredientStat;

public class IngredientService {
    private final Connection conn;
    public IngredientService(Connection pConn) {
        this.conn = pConn;
    }
    public IngredientStat getMeilleurIngredient() throws SQLException {
    String sql = """
        SELECT i.id, i.nom, SUM(cp.quantite) AS nb_utilisation
        FROM Ingredient i
        JOIN Composition c ON i.id = c.id_ingredient
        JOIN Pizza p ON c.id_pizza = p.id
        JOIN Commande_pizza cp ON p.id = cp.id_pizza
        WHERE i.nom NOT IN ('Tomate','Fromage')  -- Exclusion uniquement de la sauce tomate
        GROUP BY i.id, i.nom
        ORDER BY nb_utilisation DESC
        LIMIT 1;
    """;
    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            return new IngredientStat(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getInt("nb_utilisation")
            );
        }
        return null;
    }
   
}

}