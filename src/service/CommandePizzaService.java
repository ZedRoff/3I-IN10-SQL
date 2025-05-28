package src.service;

import java.sql.*;

public class CommandePizzaService {
    private final Connection conn;
    public CommandePizzaService(Connection pConn) {
        this.conn = pConn;
    }

    public void createCommandePizza(int idLivraison, int idPizza, int idTaille, int quantite, double prixFacture, boolean estGratuite, String raisonGratuite) throws SQLException {
        String sql = "INSERT INTO Commande_pizza (id_livraison, id_pizza, id_taille, quantite, prix_facture, est_gratuite, raison_gratuite) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idLivraison);
            ps.setInt(2, idPizza);
            ps.setInt(3, idTaille);
            ps.setInt(4, quantite);
            ps.setDouble(5, prixFacture);
            ps.setBoolean(6, estGratuite);
            ps.setString(7, raisonGratuite);
            ps.executeUpdate();
        }
    }
}