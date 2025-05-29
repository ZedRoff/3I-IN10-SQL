package src.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import src.model.Ingredient;
import src.model.Pizza;
import src.model.PizzaStat;

public class PizzaService {
    private final Connection conn;
    public PizzaService(Connection pConn) {
        this.conn = pConn;
    }
public PizzaStat getMeilleurePizza() throws SQLException {
    String sql = """
        SELECT 
            p.id, 
            p.nom, 
            SUM(cp.quantite) AS nb_commandes
        FROM Pizza p
        INNER JOIN Commande_pizza cp ON p.id = cp.id_pizza
        GROUP BY p.id, p.nom
        ORDER BY nb_commandes DESC
        LIMIT 1
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
    // --- Add this method ---
    public List<Pizza> getAllPizzas() throws SQLException {
        List<Pizza> pizzas = new ArrayList<>();
        String sql = "SELECT id, nom, prix_base FROM Pizza";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                pizzas.add(new Pizza(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getDouble("prix_base")
                ));
            }
        }
        return pizzas;
    }
public List<src.model.Taille> getAllTailles() throws SQLException {
    List<src.model.Taille> tailles = new ArrayList<>();
    String sql = "SELECT id, nom, coefficient_prix FROM Taille";
    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            tailles.add(new src.model.Taille(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getDouble("coefficient_prix")
            ));
        }
    }
    return tailles;
}
    // --- Add this method ---
    public List<Ingredient> getIngredientsForPizza(int pizzaId) throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();
        String sql = """
            SELECT i.id, i.nom
            FROM Ingredient i
            JOIN Composition c ON i.id = c.id_ingredient
            WHERE c.id_pizza = ?
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pizzaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ingredients.add(new Ingredient(
                        rs.getInt("id"),
                        rs.getString("nom")
                    ));
                }
            }
        }
        return ingredients;
    }
}