
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import src.App;
import src.Provider.DAOProvider;
import src.Provider.ServiceProvider;
import src.SQLManager;
import src.model.Client;
import src.model.ClientCommandeStat;
import src.model.IngredientStat;
import src.model.LivreurRetardStat;
import src.model.PizzaStat;
import src.model.Vehicule;

public class Main {
    SQLManager sqlManager;
    App app;
    static ServiceProvider serviceProvider;
    DAOProvider daoProvider;
    static Client user;
    public Main() {
        sqlManager = new SQLManager();
        sqlManager.connectDatabase();
        serviceProvider = new ServiceProvider(sqlManager.getConnection());
        daoProvider = new DAOProvider(sqlManager.getConnection());
        app = new App(serviceProvider);

    }
      public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main main = new Main();
            main.app.showInterface();

            try {

                 System.out.println("============");
                // 🚗 Véhicules jamais utilisés
                List<Vehicule> notUsedVehicles = serviceProvider.vehiculeService.getVehiculesJamaisServi();
                for (Vehicule vehicule : notUsedVehicles) {
                    System.out.println("Véhicule non utilisé: " + vehicule.type + " - " + vehicule.immatriculation);
                }

                 System.out.println("============");
                // 👤 Nombre de commandes par client
                List<ClientCommandeStat> clientStats = serviceProvider.clientService.getNombreCommandesParClient();
                for (ClientCommandeStat stat : clientStats) {
                    System.out.println("Client ID: " + stat.getId() + ", Nom: " + stat.getNom() + " " + stat.getPrenom() +
                            ", Nombre de commandes: " + stat.getNbCommandes());
                }
                 System.out.println("============");

                // 📊 Moyenne des commandes par client
                double averageOrders = serviceProvider.clientService.getMoyenneCommandesParClient();
                System.out.println("Moyenne des commandes par client: " + averageOrders);

                 System.out.println("============");
                // 🥇 Meilleur client
                ClientCommandeStat meilleurClient = serviceProvider.clientService.getMeilleurClient();
                if (meilleurClient != null) {
                    System.out.println("Meilleur client: " + meilleurClient.getNom() + " " + meilleurClient.getPrenom() +
                            ", Nombre de commandes: " + meilleurClient.getNbCommandes());
                }

                 System.out.println("============");
                // 📈 Clients au-dessus de la moyenne
                List<ClientCommandeStat> clientsAuDessus = serviceProvider.clientService.getClientsAuDessusDeLaMoyenne();
                System.out.println("Clients au-dessus de la moyenne des commandes :");
                for (ClientCommandeStat stat : clientsAuDessus) {
                    System.out.println("- " + stat.getNom() + " " + stat.getPrenom() + ": " + stat.getNbCommandes() + " commandes");
                }

                 System.out.println("============");
                // 🍕 Meilleure pizza
                PizzaStat meilleurePizza = serviceProvider.pizzaService.getMeilleurePizza();
                if (meilleurePizza != null) {
                    System.out.println("Pizza la plus commandée : " + meilleurePizza.getNom() + " (" + meilleurePizza.getNbCommandes() + " commandes)");
                }

                 System.out.println("============");
                // 🧄 Ingrédient le plus utilisé
                IngredientStat meilleurIngredient = serviceProvider.ingredientService.getMeilleurIngredient();
                if (meilleurIngredient != null) {
                    System.out.println("Ingrédient le plus utilisé : " + meilleurIngredient.getNom() + " (" + meilleurIngredient.getNbUtilisation() + " utilisations)");
                }

                 System.out.println("============");
                // 🚴‍♂️ Pire livreur (retards)
                LivreurRetardStat pireLivreur = serviceProvider.livreurService.getPireLivreur();

                if (pireLivreur != null) {
                    System.out.println("Pire livreur : " + pireLivreur.getNom() +
                            ", Nombre de retards: " + pireLivreur.getNbRetards() +
                            ", Minutes de retard totales: " + pireLivreur.getTotalRetardMinutes());
                }

                 System.out.println("============");
                // Creation de compte
                System.out.println("Création d'un compte client");
                String nom = "SAGAERT";
                String prenom = "Auguste";
                Client client = serviceProvider.clientService.createClient(nom, prenom);
                if(client != null)  {
                    System.out.println("Utilisateur créé avec succès");
                    System.out.println("Nom : " + client.getNom() + "\n" + "Prenom : " + client.getPrenom() + "\n" + "Solde : " + client.getSolde());
                } else {
                    System.out.println("Erreur création utilisateur");
                }

                 System.out.println("============");
                 // Login a un compte
                 System.out.println("Connexion d'un utilisateur existant");
                String nomClient = "HAKIM";
                String prenomClient = "Justine";
                user = serviceProvider.clientService.getClient(nomClient, prenomClient);
                if (user != null) {
                    System.out.println("Utilisateur connecté : " + user.getNom() + " " + user.getPrenom());
                } else {
                    System.out.println("Erreur de connexion utilisateur");
                }
                System.out.println("============");
                

            } catch (SQLException e) {
                System.err.println("Erreur SQL :");
                e.printStackTrace();
            }
        });
    }
}