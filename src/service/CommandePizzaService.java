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
            ps.setDouble(5, Math.round(prixFacture));
            ps.setBoolean(6, estGratuite);
            ps.setString(7, raisonGratuite);
            ps.executeUpdate();
        }
    }
    public double getChiffreAffairesTotal() throws SQLException {
    String sql = "SELECT SUM(prix_facture) FROM Commande_pizza WHERE est_gratuite = FALSE";
    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            return rs.getDouble(1);
        }
    }
    return 0.0;
}

}