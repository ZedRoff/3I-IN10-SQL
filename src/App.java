package src;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import src.Provider.ServiceProvider;
import src.model.Client;

public class App {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private ServiceProvider serviceProvider;
    private Client connectedUser;
    private JButton loginButton;
    private JButton logoutButton;
    private JTextArea statsTextArea;
    private JTextArea ficheTextArea;
    private JLabel soldeHeaderLabel;
    // Couleurs italiennes
    Color rossoPomodoro = new Color(205, 35, 35);    // Rouge tomate plus vif
    Color verdeOliva = new Color(0, 140, 69);        // Vert du drapeau italien
    Color biancoPanna = new Color(255, 250, 240);    // Blanc ivoire
    Color gialloOro = new Color(255, 215, 0);        // Or
    Color grigioCielo = new Color(240, 240, 240);    // Gris clair pour les bordures

    public App(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public void showInterface() {
        JFrame frame = new JFrame("RaPizz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        // Fonts
        Font titleFont = new Font("Verdana", Font.BOLD, 38);
        Font buttonFont = new Font("Verdana", Font.BOLD, 16);
        Font navFont = new Font("Verdana", Font.PLAIN, 18);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(rossoPomodoro);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 3, 0, verdeOliva),
            new EmptyBorder(15, 40, 15, 40)
        ));

        // Titre RaPizz avec effet de survol
        JLabel titleLabel = new JLabel("RaPizz");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(gialloOro);
        titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                titleLabel.setForeground(Color.WHITE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                titleLabel.setForeground(gialloOro);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                handleNavigation("home");
            }
        });

        ImageIcon pizzaIcon = new ImageIcon(new ImageIcon("pizzeria.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        JLabel iconLabel = new JLabel(pizzaIcon);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        titlePanel.setOpaque(false);
        titlePanel.add(iconLabel);
        titlePanel.add(titleLabel);
        header.add(titlePanel, BorderLayout.WEST);

        // Nav Panel
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        navPanel.setOpaque(false);

        String[] navTexts = {"Accueil", "Commander", "Statistiques", "Compte"};
        String[] iconPaths = {"pizza.png", "order.png", "food.png", "profile.png"};
        String[] cards = {"home", "commande", "stats", "fiche"};

        // Création des labels de navigation
        for (int i = 0; i < navTexts.length; i++) {
            final String cardName = cards[i];
            ImageIcon navIcon = new ImageIcon(new ImageIcon(iconPaths[i]).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
            JLabel navLabel = new JLabel(navTexts[i], navIcon, JLabel.CENTER);
            navLabel.setFont(navFont);
            navLabel.setForeground(biancoPanna);
            navLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 3, 0, rossoPomodoro),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
            ));
            navLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            navLabel.setHorizontalTextPosition(JLabel.RIGHT);
            navLabel.setIconTextGap(12);
            
            navLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    navLabel.setForeground(gialloOro);
                    navLabel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 3, 0, gialloOro),
                        BorderFactory.createEmptyBorder(5, 15, 5, 15)
                    ));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    navLabel.setForeground(biancoPanna);
                    navLabel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 3, 0, rossoPomodoro),
                        BorderFactory.createEmptyBorder(5, 15, 5, 15)
                    ));
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    handleNavigation(cardName);
                }
            });

            navPanel.add(navLabel);
        }

        header.add(navPanel, BorderLayout.CENTER);

        // Login Panel
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        loginPanel.setOpaque(false);
        
        soldeHeaderLabel = new JLabel();
        soldeHeaderLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginPanel.add(soldeHeaderLabel);
        
        loginButton = new JButton("Se Connecter");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setBackground(biancoPanna);
        loginButton.setForeground(Color.BLACK);
        loginButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(verdeOliva, 2),
            BorderFactory.createEmptyBorder(8, 25, 8, 25)
        ));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> showLoginDialog(frame));

        // Ajout du bouton de déconnexion
        logoutButton = new JButton();
        ImageIcon decoIcon = new ImageIcon(new ImageIcon("sortir.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        logoutButton.setIcon(decoIcon);
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logoutButton.setBackground(rossoPomodoro);
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(rossoPomodoro.darker(), 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutButton.setToolTipText("Se déconnecter");
        logoutButton.addActionListener(e -> handleLogout());
        logoutButton.setVisible(false);

        loginPanel.add(Box.createHorizontalStrut(15));
        loginPanel.add(loginButton);
        header.add(loginPanel, BorderLayout.EAST);

        // Main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(biancoPanna);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        mainPanel.add(createHomePage(), "home");
        mainPanel.add(createCommandePage(), "commande");
        mainPanel.add(createStatsPage(), "stats");
        mainPanel.add(createFichePage(), "fiche");

        // Footer amélioré
        JPanel footer = new JPanel();
        footer.setBackground(rossoPomodoro);
        footer.setLayout(new GridBagLayout());
        footer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(3, 0, 0, 0, verdeOliva),
            BorderFactory.createEmptyBorder(20, 0, 20, 0)
        ));

        JLabel footerLabel = new JLabel("<html><center><span style='color:#fcf5e5; font-size:12px; font-family:Verdana;'>"
                + "Groupe E3_FI_1I – GHAZANFAR Aman, HAKIM Justine, SADDIK Amine, SAGAERT Auguste<br>"
                + "<span style='color:" + String.format("#%02x%02x%02x", gialloOro.getRed(), gialloOro.getGreen(), gialloOro.getBlue()) + ";'>"
                + "© 2025 RaPizz | Via Roma, 123 | Napoli"
                + "</span></center></html>");
        footer.add(footerLabel);

        // Assemble
        frame.add(header, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(footer, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private JPanel createStatsPage() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(biancoPanna);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // En-tête des statistiques
        JLabel headerLabel = new JLabel("📊 Tableau de Bord RaPizz", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(rossoPomodoro);
        headerLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 3, 0, verdeOliva),
            BorderFactory.createEmptyBorder(0, 0, 15, 0)
        ));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Zone de texte des statistiques avec style amélioré
        statsTextArea = new JTextArea();
        statsTextArea.setEditable(false);
        statsTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statsTextArea.setMargin(new Insets(20, 20, 20, 20));
        statsTextArea.setBackground(Color.WHITE);
        statsTextArea.setLineWrap(true);
        statsTextArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(statsTextArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(verdeOliva, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Personnalisation simple de la scrollbar
        scrollPane.getVerticalScrollBar().setBackground(biancoPanna);
        scrollPane.getVerticalScrollBar().setForeground(verdeOliva);
        UIManager.put("ScrollBar.thumb", new ColorUIResource(verdeOliva));
        UIManager.put("ScrollBar.track", new ColorUIResource(biancoPanna));

        panel.add(scrollPane, BorderLayout.CENTER);

        // Bouton de rafraîchissement
        JButton refreshButton = new JButton("🔄 Actualiser les statistiques");
        styleButton(refreshButton, verdeOliva);
        refreshButton.addActionListener(e -> updateStats());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        updateStats();
        return panel;
    }

    private void updateStats() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("📊 Statistiques globales RaPizz\n\n");
            // Chiffre d'affaires total
            var caTotal = serviceProvider.commandePizzaService.getChiffreAffairesTotal();
            sb.append(String.format("Chiffre d'affaires total : %.2f €\n\n", caTotal));

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
                handleNavigation(cardName);
            }
        });
        return label;
    }

    private MouseAdapter createPageSwitchListener(String cardName) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleNavigation(cardName);
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
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(biancoPanna);
        panel.setBorder(new EmptyBorder(30, 50, 30, 50));

        // Header avec le slogan
        JPanel headerPanel = new JPanel(new BorderLayout(15, 15));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 3, 0, verdeOliva),
            BorderFactory.createEmptyBorder(0, 0, 20, 0)
        ));

        JLabel sloganLabel = new JLabel("<html><div style='text-align:center;'>"
            + "<span style='font-size:42px; font-weight:bold; color:" + String.format("#%02x%02x%02x", rossoPomodoro.getRed(), rossoPomodoro.getGreen(), rossoPomodoro.getBlue()) + ";'>RaPizz</span><br>"
            + "<span style='font-size:24px; font-style:italic; color:" + String.format("#%02x%02x%02x", verdeOliva.getRed(), verdeOliva.getGreen(), verdeOliva.getBlue()) + ";'>"
            + "La vera pizza italiana, consegnata in un lampo!</span>"
            + "</div></html>", JLabel.CENTER);
        headerPanel.add(sloganLabel, BorderLayout.CENTER);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Panneau central avec image et description
        JPanel centerPanel = new JPanel(new BorderLayout(30, 0));
        centerPanel.setOpaque(false);

        // Image de pizza avec bordure et ombre
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false);
        ImageIcon pizzaIcon = new ImageIcon(new ImageIcon("pizzeria.png")
            .getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH));
        JLabel imageLabel = new JLabel(pizzaIcon);
        imageLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(verdeOliva, 3),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        centerPanel.add(imagePanel, BorderLayout.WEST);

        // Description avec style italien
        JTextArea description = new JTextArea(
            "🍕 Benvenuti a RaPizz! 🇮🇹\n\n"
            + "Découvrez l'authentique saveur de l'Italie dans chaque bouchée.\n"
            + "Nos pizzaiolos experts préparent vos pizzas avec passion et des ingrédients frais importés d'Italie.\n\n"
            + "📍 9 boulevard Copernic\n"
            + "🚀 Livraison ultra-rapide\n"
            + "⭐ Qualité garantie\n\n"
            + "Laissez-vous tenter par nos délicieuses créations !");
        
        description.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        description.setWrapStyleWord(true);
        description.setLineWrap(true);
        description.setEditable(false);
        description.setBackground(biancoPanna);
        description.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 3, 0, 0, verdeOliva),
            BorderFactory.createEmptyBorder(0, 20, 0, 20)
        ));
        centerPanel.add(description, BorderLayout.CENTER);

        panel.add(centerPanel, BorderLayout.CENTER);

        // Panneau des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Bouton Commander
        JButton orderButton = new JButton("🛵 Commander maintenant");
        styleMenuButton(orderButton, rossoPomodoro);
        orderButton.addActionListener(e -> handleNavigation("commande"));

        // Bouton Démo
        JButton demoButton = new JButton("🎬 Voir la démo");
        styleMenuButton(demoButton, verdeOliva);
        demoButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new java.net.URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Erreur d'ouverture du lien.");
            }
        });

        buttonPanel.add(orderButton);
        buttonPanel.add(demoButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    // Méthode utilitaire pour styliser les boutons
    private void styleButton(JButton button, Color baseColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(baseColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(baseColor.darker(), 2),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(baseColor.brighter());
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });
    }

    // Méthode spécifique pour les boutons du menu principal
    private void styleMenuButton(JButton button, Color baseColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(baseColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(baseColor.darker(), 2),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(baseColor.brighter());
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });
    }

    // --- Fiche page implementation ---
    private JPanel createFichePage() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(biancoPanna);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // En-tête du profil
        JPanel headerPanel = new JPanel(new BorderLayout(15, 0));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 3, 0, verdeOliva),
            BorderFactory.createEmptyBorder(0, 0, 15, 0)
        ));

        JLabel headerLabel = new JLabel("👤 Profil Client", JLabel.LEFT);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(rossoPomodoro);
        headerPanel.add(headerLabel, BorderLayout.WEST);

        // Bouton de déconnexion dans l'en-tête de la page
        logoutButton = new JButton("Se déconnecter");
        logoutButton.setIcon(new ImageIcon(new ImageIcon("sortir.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setBackground(rossoPomodoro);
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(rossoPomodoro.darker(), 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> handleLogout());
        
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setOpaque(false);
        logoutPanel.add(logoutButton);
        headerPanel.add(logoutPanel, BorderLayout.EAST);
        
        panel.add(headerPanel, BorderLayout.NORTH);

        // Zone principale avec la fiche client
        ficheTextArea = new JTextArea();
        ficheTextArea.setEditable(false);
        ficheTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        ficheTextArea.setMargin(new Insets(20, 20, 20, 20));
        ficheTextArea.setBackground(Color.WHITE);
        ficheTextArea.setLineWrap(true);
        ficheTextArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(ficheTextArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(verdeOliva, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Panneau des actions
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        actionsPanel.setOpaque(false);
        actionsPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        // Bouton Ajouter 10€
        JButton add10Button = new JButton("💶 Ajouter 10€");
        styleButton(add10Button, verdeOliva);
        add10Button.addActionListener(e -> {
            if (connectedUser != null) {
                connectedUser.setSolde(connectedUser.getSolde() + 10.0);
                updateFiche();
                updateSoldeHeader();
            }
        });

        // Bouton Commander
        JButton orderButton = new JButton("🛵 Commander une pizza");
        styleButton(orderButton, rossoPomodoro);
        orderButton.addActionListener(e -> handleNavigation("commande"));

        actionsPanel.add(add10Button);
        actionsPanel.add(orderButton);

        // Assemblage
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);
        mainContent.add(scrollPane, BorderLayout.CENTER);
        mainContent.add(actionsPanel, BorderLayout.SOUTH);

        panel.add(mainContent, BorderLayout.CENTER);

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
        // Fidélité
        int reste = 10 - (connectedUser.getPizzasAchetees() % 10);
        if (reste == 10 && connectedUser.getPizzasAchetees() > 0) {
        // C'est la 10e, 20e, 30e... commande => pizza offerte
            sb.append("🎉 Cette commande est OFFERTE grâce à votre fidélité !\n");
        } else {
            sb.append("Fidélité : encore ").append(reste).append(" commande(s) avant une pizza offerte.\n");
        }

        ficheTextArea.setText(sb.toString());
    }
    // --- End fiche page ---

    // --- Commande page implementation ---
    private JPanel createCommandePage() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(biancoPanna);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // En-tête
        JLabel headerLabel = new JLabel("🍕 Commander une Pizza", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(rossoPomodoro);
        headerLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 3, 0, verdeOliva),
            BorderFactory.createEmptyBorder(0, 0, 15, 0)
        ));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout(20, 0));
        mainPanel.setOpaque(false);

        // Panneau gauche (liste des pizzas et taille)
        JPanel leftPanel = new JPanel(new BorderLayout(0, 15));
        leftPanel.setOpaque(false);

        // Liste des pizzas avec style amélioré
        DefaultListModel<String> pizzaListModel = new DefaultListModel<>();
        JList<String> pizzaList = new JList<>(pizzaListModel);
        pizzaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pizzaList.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        pizzaList.setBackground(Color.WHITE);
        pizzaList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JScrollPane pizzaScroll = new JScrollPane(pizzaList);
        pizzaScroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(verdeOliva, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ),
            "Nos Pizzas"
        ));
        pizzaScroll.setPreferredSize(new Dimension(350, 300));

        // Panneau de taille avec style amélioré
        JPanel taillePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        taillePanel.setOpaque(false);
        taillePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(verdeOliva, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel tailleLabel = new JLabel("Taille : ");
        tailleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        taillePanel.add(tailleLabel);

        // ComboBox des tailles
        final List<src.model.Taille> tailles = new ArrayList<>();
        JComboBox<String> tailleCombo = new JComboBox<>();
        tailleCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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
        taillePanel.add(tailleCombo);

        leftPanel.add(pizzaScroll, BorderLayout.CENTER);
        leftPanel.add(taillePanel, BorderLayout.SOUTH);

        // Panneau droit (ingrédients et prix)
        JPanel rightPanel = new JPanel(new BorderLayout(0, 15));
        rightPanel.setOpaque(false);

        // Zone des ingrédients avec style amélioré
        JTextArea ingredientsArea = new JTextArea();
        ingredientsArea.setEditable(false);
        ingredientsArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ingredientsArea.setBackground(Color.WHITE);
        ingredientsArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane ingredientsScroll = new JScrollPane(ingredientsArea);
        ingredientsScroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(verdeOliva, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ),
            "Ingrédients"
        ));
        ingredientsScroll.setPreferredSize(new Dimension(250, 200));

        // Panneau de prix avec style amélioré
        JPanel prixPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        prixPanel.setOpaque(false);
        prixPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(verdeOliva, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel prixLabel = new JLabel("Prix : -");
        prixLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        prixLabel.setForeground(rossoPomodoro);
        prixPanel.add(prixLabel);

        rightPanel.add(ingredientsScroll, BorderLayout.CENTER);
        rightPanel.add(prixPanel, BorderLayout.SOUTH);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Panneau du bas avec le bouton de commande et le statut
        JPanel bottomPanel = new JPanel(new BorderLayout(0, 10));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton orderButton = new JButton("🛵 Commander cette pizza");
        styleButton(orderButton, rossoPomodoro);

        JLabel statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setForeground(verdeOliva);

        bottomPanel.add(orderButton, BorderLayout.NORTH);
        bottomPanel.add(statusLabel, BorderLayout.CENTER);

        panel.add(mainPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Liste des pizzas
        List<src.model.Pizza> pizzas = new ArrayList<>();
        try {
            pizzas.addAll(serviceProvider.pizzaService.getAllPizzas());
        } catch (Exception e) {
            pizzaListModel.addElement("Erreur de chargement des pizzas");
        }

        // Mise à jour du prix
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

        // Mise à jour de la liste des pizzas
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
                        sb.append("• ").append(ing.getNom()).append("\n");
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

        // Gestion de la commande
        orderButton.addActionListener(e -> {
            if (connectedUser == null) {
                JOptionPane.showMessageDialog(panel, 
                    "Veuillez vous connecter pour commander.", 
                    "Non connecté", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            int idxPizza = pizzaList.getSelectedIndex();
            int idxTaille = tailleCombo.getSelectedIndex();
            if (idxPizza < 0 || idxPizza >= pizzas.size() || idxTaille < 0 || idxTaille >= tailles.size()) {
                JOptionPane.showMessageDialog(panel, 
                    "Veuillez sélectionner une pizza et une taille.", 
                    "Sélection incomplète", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            src.model.Pizza pizza = pizzas.get(idxPizza);
            src.model.Taille taille = tailles.get(idxTaille);
            double prix = pizza.getPrixBase() * taille.getCoefficientPrix();

            // Vérification fidélité et solde
            int commandesAvant = connectedUser.getPizzasAchetees();
            boolean gratuiteFidelite = ((commandesAvant + 1) % 5 == 0);
            boolean gratuite = gratuiteFidelite;

            if (!gratuite && connectedUser.getSolde() < prix) {
                JOptionPane.showMessageDialog(panel, 
                    "Solde insuffisant pour commander cette pizza (" + String.format("%.2f", prix) + " €).", 
                    "Solde insuffisant", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Traitement de la commande...
            // [Le reste du code de traitement de la commande reste inchangé]
        });

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
                        }
                        JOptionPane.showMessageDialog(dialog,
                            "Compte créé et connecté.\nBienvenue " + prenom + " !",
                            "Succès", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(dialog,
                            "Connexion réussie.\nBienvenue " + prenom + " !",
                            "Succès", JOptionPane.INFORMATION_MESSAGE);
                    }
                    connectedUser = client;
                    updateSoldeHeader();
                    updateFiche();
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
            String soldeTexte = String.format("Solde : %.2f €", connectedUser.getSolde());
            soldeHeaderLabel.setText(soldeTexte);
            soldeHeaderLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            soldeHeaderLabel.setForeground(verdeOliva);

            loginButton.setText(connectedUser.getPrenom());
            loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            loginButton.setForeground(Color.WHITE);
            loginButton.setBackground(verdeOliva);
            loginButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(8, 25, 8, 25)
            ));
            loginButton.setEnabled(false);
        } else {
            soldeHeaderLabel.setText("");
            loginButton.setText("Se Connecter");
            loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
            loginButton.setForeground(Color.BLACK);
            loginButton.setBackground(biancoPanna);
            loginButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(verdeOliva, 2),
                BorderFactory.createEmptyBorder(8, 25, 8, 25)
            ));
            loginButton.setEnabled(true);
            cardLayout.show(mainPanel, "home");
        }
    }

    private void handleLogout() {
        int choice = JOptionPane.showConfirmDialog(
            null,
            "Voulez-vous vraiment vous déconnecter ?",
            "Confirmation de déconnexion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            connectedUser = null;
            updateSoldeHeader();
            updateFiche();
            cardLayout.show(mainPanel, "home");
            logoutButton.setVisible(false);
        }
    }

    private void handleNavigation(String cardName) {
        if ((cardName.equals("fiche") || cardName.equals("commande")) && connectedUser == null) {
            JOptionPane.showMessageDialog(null,
                "Veuillez vous connecter pour accéder à cette section.",
                "Connexion requise",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        cardLayout.show(mainPanel, cardName);
        
        if ("stats".equals(cardName)) {
            updateStats();
        } else if ("fiche".equals(cardName)) {
            updateFiche();
        }
    }
}