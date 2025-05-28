package src;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import src.Provider.ServiceProvider;
import src.model.Client;

public class App {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private ServiceProvider serviceProvider;
    private Client connectedUser;
    private JButton loginButton;
    private JTextArea statsTextArea;
    private JTextArea ficheTextArea;
    private JLabel soldeHeaderLabel;

    public App(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public void showInterface() {
        JFrame frame = new JFrame("RaPizz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 30, 60));
        header.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Title Panel
        JLabel titleLabel = new JLabel("<html><span style='font-size:32px; color:#FFD700;'>RaPizz</span></html>");
        titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        titleLabel.addMouseListener(createPageSwitchListener("home"));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel);
        header.add(titlePanel, BorderLayout.WEST);

        // Nav Panel
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 30));
        navPanel.setOpaque(false);

        navPanel.add(createNavLabel("🏠 Accueil", "home"));
        navPanel.add(createNavLabel("🧾 Commande", "commande"));
        navPanel.add(createNavLabel("📊 Statistiques", "stats"));
        navPanel.add(createNavLabel("📋 Fiche", "fiche"));

        header.add(navPanel, BorderLayout.CENTER);

        // Login Button
        loginButton = new JButton("Se Connecter");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> showLoginDialog(frame));

        soldeHeaderLabel = new JLabel();
        soldeHeaderLabel.setFont(new Font("Arial", Font.BOLD, 14));
        soldeHeaderLabel.setForeground(Color.WHITE);
        updateSoldeHeader(); // Affiche le solde initial

        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        loginPanel.setOpaque(false);
        loginPanel.add(soldeHeaderLabel); // Ajoute le label du solde AVANT le bouton
        loginPanel.add(loginButton);
        header.add(loginPanel, BorderLayout.EAST);

        // Main Panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createHomePage(), "home");
        mainPanel.add(createCommandePage(), "commande");
        mainPanel.add(createStatsPage(), "stats");
        mainPanel.add(createFichePage(), "fiche");

        // Footer
        JPanel footer = new JPanel();
        footer.setBackground(new Color(30, 30, 60));
        footer.setPreferredSize(new Dimension(0, 50));
        footer.add(new JLabel("<html><font color='white'>© 2025 RaPizz Inc.</font></html>"));

        // Assemble
        frame.add(header, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(footer, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private JPanel createStatsPage() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 248, 250));

        statsTextArea = new JTextArea();
        statsTextArea.setEditable(false);
        statsTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        statsTextArea.setMargin(new Insets(15, 15, 15, 15));
        statsTextArea.setBackground(Color.WHITE);
        statsTextArea.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JScrollPane scrollPane = new JScrollPane(statsTextArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📊 Tableau de bord - Statistiques"));
        panel.add(scrollPane, BorderLayout.CENTER);

        updateStats();
        return panel;
    }

    private void updateStats() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("📊 Statistiques globales RaPizz\n\n");

            sb.append("🚗 Véhicules jamais utilisés:\n");
            var notUsedVehicles = serviceProvider.vehiculeService.getVehiculesJamaisServi();
            for (var v : notUsedVehicles) {
                sb.append("  - ").append(v.type).append(" (").append(v.immatriculation).append(")\n");
            }
            sb.append("\n");

            sb.append("👤 Nombre de commandes par client:\n");
            var clientStats = serviceProvider.clientService.getNombreCommandesParClient();
            for (var stat : clientStats) {
                sb.append(String.format("  - %s %s: %d commandes\n", stat.getPrenom(), stat.getNom(), stat.getNbCommandes()));
            }
            sb.append("\n");

            double avg = serviceProvider.clientService.getMoyenneCommandesParClient();
            sb.append(String.format("📈 Moyenne des commandes par client: %.2f\n\n", avg));

            var meilleurClient = serviceProvider.clientService.getMeilleurClient();
            sb.append("🥇 Meilleur client:\n");
            sb.append(String.format("  %s %s (%d commandes)\n\n", meilleurClient.getPrenom(), meilleurClient.getNom(), meilleurClient.getNbCommandes()));

            var clientsAbove = serviceProvider.clientService.getClientsAuDessusDeLaMoyenne();
            sb.append("📈 Clients au-dessus de la moyenne:\n");
            for (var c : clientsAbove) {
                sb.append(String.format("  - %s %s: %d commandes\n", c.getPrenom(), c.getNom(), c.getNbCommandes()));
            }
            sb.append("\n");

            var bestPizza = serviceProvider.pizzaService.getMeilleurePizza();
            sb.append(String.format("🍕 Pizza la plus commandée: %s (%d commandes)\n\n", bestPizza.getNom(), bestPizza.getNbCommandes()));

            var bestIngredient = serviceProvider.ingredientService.getMeilleurIngredient();
            sb.append(String.format("🧄 Ingrédient le plus utilisé: %s (%d utilisations)\n\n", bestIngredient.getNom(), bestIngredient.getNbUtilisation()));

            var worst = serviceProvider.livreurService.getPireLivreur();
            sb.append("🚴‍♂️ Pire livreur:\n");
            sb.append(String.format("  %s (%d retards, %d minutes)\n", worst.getNom(), worst.getNbRetards(), worst.getTotalRetardMinutes()));

            statsTextArea.setText(sb.toString());
        } catch (Exception e) {
            statsTextArea.setText("Erreur lors du chargement des statistiques :\n" + e.getMessage());
        }
    }

    private JLabel createNavLabel(String text, String cardName) {
        JLabel label = new JLabel("<html><div style='padding:6px 12px;'>" + text + "</div></html>");
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(Color.WHITE);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(new Color(255, 215, 0));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(Color.WHITE);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, cardName);
                if ("stats".equals(cardName)) updateStats();
                if ("fiche".equals(cardName)) updateFiche();
            }
        });
        return label;
    }

    private MouseAdapter createPageSwitchListener(String cardName) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, cardName);
                if ("stats".equals(cardName)) updateStats();
                if ("fiche".equals(cardName)) updateFiche();
            }
        };
    }

    private JPanel createPage(String title) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel(title);
        label.setFont(new Font("SansSerif", Font.BOLD, 30));
        panel.add(label);
        return panel;
    }

    private JPanel createHomePage() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(30, 50, 30, 50));

        JLabel sloganLabel = new JLabel("<html><div style='text-align:center;'>"
            + "<span style='font-size:30px; font-weight:bold; color:#1E90FF;'>RaPizz</span><br>"
            + "<span style='font-size:20px; font-style:italic; color:#444;'>La meilleure pizza, livrée en un éclair !</span>"
            + "</div></html>", JLabel.CENTER);
        panel.add(sloganLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        ImageIcon icon = new ImageIcon(new ImageIcon("https://upload.wikimedia.org/wikipedia/commons/6/6a/Pizza_on_stone.jpg")
            .getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH));
        centerPanel.add(new JLabel(icon), BorderLayout.WEST);

        JTextArea description = new JTextArea(
            "Bienvenue chez RaPizz, votre spécialiste de la pizza artisanale.\n"
            + "Nous sommes basés au 9 boulevard Copernic.\n"
            + "Projet réalisé pour l’unité BDD 3I_IN10.\n\n"
            + "Découvrez notre application rapide et savoureuse !");
        description.setFont(new Font("Serif", Font.PLAIN, 16));
        description.setWrapStyleWord(true);
        description.setLineWrap(true);
        description.setEditable(false);
        description.setBackground(panel.getBackground());
        description.setBorder(new EmptyBorder(10, 20, 10, 10));
        centerPanel.add(description, BorderLayout.CENTER);

        panel.add(centerPanel, BorderLayout.CENTER);

        JButton demoButton = new JButton("🎬 Lancer la démo");
        demoButton.setFont(new Font("Arial", Font.BOLD, 18));
        demoButton.setBackground(new Color(30, 144, 255));
        demoButton.setForeground(Color.BLACK);
        demoButton.setFocusPainted(false);
        demoButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLUE, 2),
            new EmptyBorder(10, 20, 10, 20)
        ));
        demoButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        demoButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new java.net.URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Erreur d'ouverture du lien.");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(demoButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    // --- Fiche page implementation ---
    private JPanel createFichePage() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        ficheTextArea = new JTextArea();
        ficheTextArea.setEditable(false);
        ficheTextArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        ficheTextArea.setMargin(new Insets(15, 15, 15, 15));
        ficheTextArea.setBackground(Color.WHITE);
        ficheTextArea.setBorder(BorderFactory.createTitledBorder("📋 Fiche utilisateur"));

        // --- Ajout du bouton Ajouter 10€ à côté des infos utilisateur ---
        JButton add10eButton = new JButton("Ajouter 10€");
        add10eButton.setFont(new Font("Arial", Font.BOLD, 14));
        add10eButton.addActionListener(e -> {
            if (connectedUser != null) {
                connectedUser.setSolde(connectedUser.getSolde() + 10.0);
                updateFiche();
                updateSoldeHeader();
            }
        });

        JPanel fichePanel = new JPanel(new BorderLayout());
        fichePanel.setOpaque(false);
        fichePanel.add(ficheTextArea, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(add10eButton);

        fichePanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(fichePanel, BorderLayout.CENTER);

        updateFiche();
        return panel;
    }

    private void updateFiche() {
        if (ficheTextArea == null) return;
        if (connectedUser == null) {
            ficheTextArea.setText("Aucun utilisateur connecté.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Informations du client connecté :\n\n");
        sb.append("Nom : ").append(connectedUser.getNom()).append("\n");
        sb.append("Prénom : ").append(connectedUser.getPrenom()).append("\n");
        try {
            sb.append("Solde : ").append(connectedUser.getSolde()).append(" €\n");
        } catch (Exception e) {}
        try {
            sb.append("Pizzas achetées : ").append(connectedUser.getPizzasAchetees()).append("\n");
        } catch (Exception e) {}
        try {
            sb.append("Total dépenses : ").append(connectedUser.getTotalDepenses()).append(" €\n");
        } catch (Exception e) {}
        ficheTextArea.setText(sb.toString());
    }
    // --- End fiche page ---

    // --- Commande page implementation ---
    private JPanel createCommandePage() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(Color.WHITE);

    JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel infoLabel = new JLabel("Sélectionnez une pizza à commander :");
    infoLabel.setFont(new Font("Arial", Font.BOLD, 16));
    topPanel.add(infoLabel);

    panel.add(topPanel, BorderLayout.NORTH);

    // Pizza list
    DefaultListModel<String> pizzaListModel = new DefaultListModel<>();
    JList<String> pizzaList = new JList<>(pizzaListModel);
    pizzaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    pizzaList.setFont(new Font("Arial", Font.PLAIN, 16));
    JScrollPane pizzaScroll = new JScrollPane(pizzaList);
    pizzaScroll.setPreferredSize(new Dimension(350, 200));
    pizzaScroll.setBorder(BorderFactory.createTitledBorder("Pizzas"));

    // Taille ComboBox
    final List<src.model.Taille> tailles = new ArrayList<>();
    JComboBox<String> tailleCombo = new JComboBox<>();
    try {
        tailles.addAll(serviceProvider.pizzaService.getAllTailles());
        for (var t : tailles) {
          tailleCombo.addItem(t.getNom() + " (x" + t.getCoefficientPrix() + ")");
        }
    } catch (Exception e) {
        tailleCombo.addItem("Erreur tailles");
    }
    if (tailles.isEmpty()) {
        tailleCombo.addItem("Aucune taille");
    }

    // Ingredients area
    JTextArea ingredientsArea = new JTextArea();
    ingredientsArea.setEditable(false);
    ingredientsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
    ingredientsArea.setBackground(new Color(245, 248, 250));
    ingredientsArea.setBorder(BorderFactory.createTitledBorder("Ingrédients"));
    ingredientsArea.setPreferredSize(new Dimension(250, 100));

    // --- Liste des pizzas ---
    List<src.model.Pizza> pizzas = new ArrayList<>();
    try {
        pizzas.addAll(serviceProvider.pizzaService.getAllPizzas());
    } catch (Exception e) {
        pizzaListModel.addElement("Erreur de chargement des pizzas");
    }
 // Prix dynamique
    JLabel prixLabel = new JLabel("Prix : ");
    prixLabel.setFont(new Font("Arial", Font.BOLD, 16));

    // Met à jour le prix affiché selon la pizza et la taille
    Runnable updatePrix = () -> {
        int idxPizza = pizzaList.getSelectedIndex();
        int idxTaille = tailleCombo.getSelectedIndex();
        if (idxPizza >= 0 && idxPizza < pizzas.size() && idxTaille >= 0 && idxTaille < tailles.size()) {
            double prix = pizzas.get(idxPizza).getPrixBase() * tailles.get(idxTaille).coefficientPrix;
            prixLabel.setText("Prix : " + String.format("%.2f", prix) + " €");
        } else {
            prixLabel.setText("Prix : -");
        }
    };
    // Fonction pour remplir la liste avec les bons prix selon la taille
    Runnable updatePizzaListModel = () -> {
        int selectedIdx = pizzaList.getSelectedIndex();
        pizzaListModel.clear();
        int idxTaille = tailleCombo.getSelectedIndex();
        double coef = (idxTaille >= 0 && idxTaille < tailles.size()) ? tailles.get(idxTaille).getCoefficientPrix() : 1.0;
        for (var p : pizzas) {
            double prix = p.getPrixBase() * coef;
            pizzaListModel.addElement(p.getNom() + " (" + String.format("%.2f", prix) + " €)");
        }
        if (selectedIdx >= 0 && selectedIdx < pizzaListModel.size()) pizzaList.setSelectedIndex(selectedIdx);
    };

    // Appelle cette fonction au chargement et à chaque changement de taille
    updatePizzaListModel.run();
    tailleCombo.addActionListener(e -> {
        updatePizzaListModel.run();
        updatePrix.run();
    });

   

    pizzaList.addListSelectionListener(e -> {
        int idx = pizzaList.getSelectedIndex();
        if (idx >= 0 && idx < pizzas.size()) {
            src.model.Pizza pizza = pizzas.get(idx);
            StringBuilder sb = new StringBuilder();
            try {
                var ingredients = serviceProvider.pizzaService.getIngredientsForPizza(pizza.getId());
                for (var ing : ingredients) {
                    sb.append("- ").append(ing.getNom()).append("\n");
                }
            } catch (Exception ex) {
                sb.append("Erreur chargement ingrédients.");
            }
            ingredientsArea.setText(sb.toString());
        } else {
            ingredientsArea.setText("");
        }
        updatePrix.run();
    });
    tailleCombo.addActionListener(e -> updatePrix.run());

    // Center panel for pizza + ingredients + taille + prix
    JPanel leftPanel = new JPanel(new BorderLayout());
    leftPanel.setBackground(Color.WHITE);
    leftPanel.add(pizzaScroll, BorderLayout.CENTER);

    JPanel taillePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    taillePanel.setOpaque(false);
    taillePanel.add(new JLabel("Taille : "));
    taillePanel.add(tailleCombo);
    leftPanel.add(taillePanel, BorderLayout.SOUTH);

    JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
    centerPanel.setBackground(Color.WHITE);
    centerPanel.add(leftPanel);
    centerPanel.add(ingredientsArea);

    panel.add(centerPanel, BorderLayout.CENTER);

    // Prix affiché sous la liste
    JPanel prixPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    prixPanel.setOpaque(false);
    prixPanel.add(prixLabel);
    panel.add(prixPanel, BorderLayout.SOUTH);

    // Order button and status
    JButton orderButton = new JButton("Commander cette pizza");
    orderButton.setFont(new Font("Arial", Font.BOLD, 16));
    JLabel statusLabel = new JLabel(" ");
    statusLabel.setFont(new Font("Arial", Font.ITALIC, 15));
    statusLabel.setForeground(new Color(30, 144, 255));

    // Panel pour bouton et status
    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    bottomPanel.setBackground(Color.WHITE);
    bottomPanel.add(orderButton);
    bottomPanel.add(statusLabel);

    panel.add(bottomPanel, BorderLayout.PAGE_END);

    orderButton.addActionListener(e -> {
        if (connectedUser == null) {
            JOptionPane.showMessageDialog(panel, "Veuillez vous connecter pour commander.", "Non connecté", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idxPizza = pizzaList.getSelectedIndex();
        int idxTaille = tailleCombo.getSelectedIndex();
        if (idxPizza < 0 || idxPizza >= pizzas.size() || idxTaille < 0 || idxTaille >= tailles.size()) {
            JOptionPane.showMessageDialog(panel, "Veuillez sélectionner une pizza et une taille.", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }
        src.model.Pizza pizza = pizzas.get(idxPizza);
        src.model.Taille taille = tailles.get(idxTaille);
       double prix = pizza.getPrixBase() * taille.getCoefficientPrix();
        // --- Vérification solde ---
        if (connectedUser.getSolde() < prix) {
            JOptionPane.showMessageDialog(panel, "Solde insuffisant pour commander cette pizza (" + String.format("%.2f", prix) + " €).", "Solde insuffisant", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // --- Random livreur ---
        List<src.model.Livreur> livreurs = new ArrayList<>();
        try {
            livreurs.addAll(serviceProvider.livreurService.getAllLivreurs());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, "Erreur chargement livreurs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (livreurs.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Aucun livreur disponible.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        src.model.Livreur livreur = livreurs.get((int)(Math.random() * livreurs.size()));

        // --- Random times ---
        final int expectedTime = 10 + (int)(Math.random() * 16); // 10-25 min
        int tempRealTime = expectedTime + (int)(Math.random() * 26) - 5; // -5 à +20 min
        if (tempRealTime < 5) tempRealTime = 5; // minimum 5 min
        final int realTime = tempRealTime;
        final int retard = Math.max(0, realTime - expectedTime);

        orderButton.setEnabled(false);
        statusLabel.setText("Votre pizza est en cours d'expédition...");

        Timer timer = new Timer(realTime * 200, ev -> {
            boolean gratuite = retard > 10;
            StringBuilder sb = new StringBuilder();
            sb.append("<html>Votre pizza est arrivée !<br>");
           sb.append("🍕 Pizza : ").append(pizza.getNom()).append(" (").append(taille.getNom()).append(")<br>");
            sb.append("🚴 Livreur : ").append(livreur.getNom()).append("<br>");
            sb.append("⏱️ Temps prévu : ").append(expectedTime).append(" min<br>");
            sb.append("⏱️ Temps réel : ").append(realTime).append(" min<br>");
            sb.append("⏳ Retard : ").append(retard).append(" min<br>");
            if (gratuite) {
                sb.append("<b style='color:green;'>Pizza OFFERTE (retard &gt; 10 min) !</b><br>");
            } else {
                sb.append("<b>Pizza facturée : ").append(String.format("%.2f", prix)).append(" €</b><br>");
            }
            sb.append("Solde après commande : ").append(String.format("%.2f", gratuite ? connectedUser.getSolde() : connectedUser.getSolde() - prix)).append(" €<br>");
            sb.append("</html>");
            statusLabel.setText(sb.toString());
            orderButton.setEnabled(true);

            // Facturation ou gratuité
            if (!gratuite) {
                connectedUser.setSolde(connectedUser.getSolde() - prix);
            }
            if (retard > 0) {
                try {
                    serviceProvider.livreurService.incrementRetard(livreur.getId());
                } catch (Exception ex) {}
            }
            updateFiche();
            updateSoldeHeader();
        });
        timer.setRepeats(false);
        timer.start();
    });

    updatePrix.run();
    return panel;
}
    // --- End Commande page ---

    private void showLoginDialog(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Connexion", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(parent);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Nom
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(new JLabel("Nom :"), gbc);

        JTextField nomField = new JTextField(20);
        gbc.gridx = 1;
        dialog.add(nomField, gbc);

        // Prénom
        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(new JLabel("Prénom :"), gbc);

        JTextField prenomField = new JTextField(20);
        gbc.gridx = 1;
        dialog.add(prenomField, gbc);

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Annuler");
        JButton confirmButton = new JButton("Confirmer");

        cancelButton.addActionListener(e -> dialog.dispose());

        confirmButton.addActionListener(e -> {
            String nom = nomField.getText().trim();
            String prenom = prenomField.getText().trim();
            if (!nom.isEmpty() && !prenom.isEmpty()) {
                try {
                    // Recherche client existant
                    Client client = serviceProvider.clientService.getClient(nom, prenom);
                    if (client == null) {
                        // Création client
                        client = serviceProvider.clientService.createClient(nom, prenom);
                        if (client == null) {
                            JOptionPane.showMessageDialog(dialog,
                                "Erreur création utilisateur.",
                                "Erreur", JOptionPane.ERROR_MESSAGE);
                            return;
                        } else {
                            JOptionPane.showMessageDialog(dialog,
                                "Compte créé et connecté.\nBienvenue " + prenom + " !",
                                "Succès", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(dialog,
                            "Connexion réussie.\nBienvenue " + prenom + " !",
                            "Succès", JOptionPane.INFORMATION_MESSAGE);
                    }
                    connectedUser = client;

                    // Met à jour bouton
                    loginButton.setText(prenom);
                    loginButton.setEnabled(false);

                    updateFiche();
                    updateSoldeHeader();

                    dialog.dispose();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Erreur lors de la connexion : " + ex.getMessage(),
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(dialog,
                        "Veuillez remplir tous les champs.",
                        "Attention", JOptionPane.WARNING_MESSAGE);
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(confirmButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        dialog.add(buttonPanel, gbc);

        dialog.setVisible(true);
    }

    private void updateSoldeHeader() {
        if (connectedUser != null) {
            loginButton.setText(connectedUser.getPrenom());
            soldeHeaderLabel.setText(" | Solde : " + String.format("%.2f", connectedUser.getSolde()) + " €   ");
        } else {
            loginButton.setText("Se Connecter");
            soldeHeaderLabel.setText("");
        }
    }
}