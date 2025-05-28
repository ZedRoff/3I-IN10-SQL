package src.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import src.model.Client;
import src.model.ClientCommandeStat;

public class ClientService {
    private final Connection conn;
    public ClientService(Connection pConn) {
        this.conn = pConn;
    }
    public List<ClientCommandeStat> getClientsAuDessusDeLaMoyenne() throws SQLException {
    String sql = """
        SELECT c.id, c.nom, c.prenom, COUNT(liv.id) AS nb_commandes
        FROM Client c
        JOIN Livraison liv ON c.id = liv.id_client
        GROUP BY c.id, c.nom, c.prenom
        HAVING COUNT(liv.id) > (
            SELECT AVG(nb) FROM (
                SELECT COUNT(*) AS nb
                FROM Livraison
                GROUP BY id_client
            ) AS sous_req
        );
    """;
    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        List<ClientCommandeStat> result = new ArrayList<>();
        while (rs.next()) {
            result.add(new ClientCommandeStat(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getInt("nb_commandes")
            ));
        }
        return result;
    }
}
public Client createClient(String nom, String prenom) throws SQLException {
    String sql = "INSERT INTO client (nom, prenom, solde, pizzas_achetees, total_depenses) VALUES (?, ?, 0, 0, 0)";
    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setString(1, nom);
        ps.setString(2, prenom);
        ps.executeUpdate();
        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                int id = rs.getInt(1);
                return new Client(id, nom, prenom, 0.0, 0, 0.0);
            }
        }
    }
    return null;
}
public Client getClient(String nom, String prenom) throws SQLException {
    String sql = "SELECT * FROM Client WHERE nom = ? AND prenom = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, nom);
        ps.setString(2, prenom);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Client(rs.getString("nom"), rs.getString("prenom"));
            }
        }
    }
    return null;
}
public ClientCommandeStat getMeilleurClient() throws SQLException {
    String sql = """
        SELECT c.id, c.nom, c.prenom, COUNT(liv.id) AS nb_commandes
        FROM Client c
        JOIN Livraison liv ON c.id = liv.id_client
        GROUP BY c.id
        ORDER BY nb_commandes DESC
        LIMIT 1;
    """;
    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            return new ClientCommandeStat(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getInt("nb_commandes")
            );
        }
        return null;
    }
}

    public double getMoyenneCommandesParClient() throws SQLException {
        String sql = """
            SELECT AVG(nb) AS moyenne_commandes
            FROM (
                SELECT COUNT(*) AS nb
                FROM Livraison
                GROUP BY id_client
            ) AS sous_req;
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("moyenne_commandes");
            }
        }
        return 0.0;
    }

    public List<ClientCommandeStat> getNombreCommandesParClient() throws SQLException {
        String sql = """
            SELECT c.id, c.nom, c.prenom, COUNT(liv.id) AS nb_commandes
            FROM Client c
            LEFT JOIN Livraison liv ON c.id = liv.id_client
            GROUP BY c.id, c.nom, c.prenom
        """;

        List<ClientCommandeStat> stats = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ClientCommandeStat stat = new ClientCommandeStat(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getInt("nb_commandes")
                );
                stats.add(stat);
            }
        }
        return stats;
    }
    public List<Integer> getCommandesClient(int clientId) throws SQLException {
    String sql = """
        SELECT id
        FROM Livraison
        WHERE id_client = ?
        ORDER BY date_livraison DESC
    """;

    List<Integer> commandes = new ArrayList<>();
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, clientId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                commandes.add(rs.getInt("id"));
            }
        }
    }
    return commandes;
}
public void updateSoldeEtStats(int idClient, double nouveauSolde, int pizzasAchetees, double totalDepenses) throws SQLException {
    String sql = "UPDATE Client SET solde = ?, pizzas_achetees = ?, total_depenses = ? WHERE id = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setDouble(1, nouveauSolde);
        ps.setInt(2, pizzasAchetees);
        ps.setDouble(3, totalDepenses);
        ps.setInt(4, idClient);
        ps.executeUpdate();
    }
}

}
