# Rapport Technique - RaPizz

## 1. Architecture de la Base de Données

### 1.1 Schéma Relationnel
```sql
Client (
    id INT PK,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    solde DECIMAL(8,2),
    pizzas_achetees INT,
    total_depenses DECIMAL(10,2)
)

Pizza (
    id INT PK,
    nom VARCHAR(100),
    prix_base DECIMAL(5,2)
)

Taille (
    id INT PK,
    nom VARCHAR(50),
    coefficient_prix DECIMAL(3,2)
)

Ingredient (
    id INT PK,
    nom VARCHAR(100)
)

Composition (
    id_pizza INT FK,
    id_ingredient INT FK,
    PRIMARY KEY (id_pizza, id_ingredient)
)

Vehicule (
    id INT PK,
    type VARCHAR(50),
    immatriculation VARCHAR(20),
    nombre_utilisations INT
)

Livreur (
    id INT PK,
    nom VARCHAR(100),
    type_conducteur VARCHAR(50),
    nombre_retards INT
)

Livraison (
    id INT PK,
    date DATETIME,
    retard INT,
    id_client INT FK,
    id_livreur INT FK,
    id_vehicule INT FK
)

Commande_pizza (
    id_livraison INT FK,
    id_pizza INT FK,
    id_taille INT FK,
    quantite INT,
    prix_facture DECIMAL(6,2),
    est_gratuite BOOLEAN,
    raison_gratuite VARCHAR(255),
    PRIMARY KEY (id_livraison, id_pizza, id_taille)
)
```

### 1.2 Choix de Conception
- Séparation claire entre commandes et livraisons
- Gestion des tailles de pizza avec coefficients de prix
- Traçabilité complète des livraisons
- Statistiques intégrées (retards, utilisations)

## 2. Requêtes SQL Principales

### 2.1 Statistiques Clients
```sql
-- Moyenne des commandes par client
SELECT 
    CAST(COUNT(DISTINCT liv.id) AS DECIMAL) / COUNT(DISTINCT c.id) AS moyenne_commandes
FROM Client c
LEFT JOIN Livraison liv ON c.id = liv.id_client;

-- Clients au-dessus de la moyenne
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

-- Meilleur client
SELECT c.id, c.nom, c.prenom, COUNT(liv.id) AS nb_commandes
FROM Client c
JOIN Livraison liv ON c.id = liv.id_client
GROUP BY c.id
ORDER BY nb_commandes DESC
LIMIT 1;
```

### 2.2 Statistiques Livraisons
```sql
-- Pire livreur (retards)
SELECT l.id, l.nom,
       COUNT(*) AS nb_retards,
       SUM(liv.retard) AS total_retard_minutes
FROM Livreur l
JOIN Livraison liv ON l.id = liv.id_livreur
WHERE liv.retard > 0
GROUP BY l.id, l.nom
ORDER BY nb_retards DESC, total_retard_minutes DESC
LIMIT 1;

-- Véhicules jamais utilisés
SELECT v.id, v.type, v.immatriculation
FROM Vehicule v
LEFT JOIN Livraison liv ON v.id = liv.id_vehicule
WHERE liv.id IS NULL;
```

### 2.3 Statistiques Produits
```sql
-- Pizza la plus commandée
SELECT p.id, p.nom, COUNT(*) AS nb_commandes
FROM Pizza p
JOIN Commande_pizza c ON p.id = c.id_pizza
GROUP BY p.id, p.nom
ORDER BY nb_commandes DESC
LIMIT 1;

-- Ingrédient le plus utilisé (hors base)
SELECT i.id, i.nom, COUNT(*) AS nb_utilisation
FROM Ingredient i
JOIN Composition c ON i.id = c.id_ingredient
WHERE i.nom NOT IN ('Tomate','Fromage')
GROUP BY i.id, i.nom
ORDER BY nb_utilisation DESC
LIMIT 1;
```

## 3. Architecture du Code

### 3.1 Structure MVC
```
src/
├── model/          # Classes modèles (entités)
├── service/        # Logique métier et requêtes
├── Provider/       # Gestion des connexions
└── App.java        # Interface utilisateur
```

### 3.2 Services Principaux
- **ClientService** : Gestion des clients et statistiques
- **LivraisonService** : Gestion des livraisons
- **PizzaService** : Gestion du menu et statistiques
- **VehiculeService** : Gestion des véhicules
- **LivreurService** : Gestion des livreurs

### 3.3 Modèles de Données

#### 3.3.1 Classes d'Entités
```java
public class Client {
    private int id;
    private String nom;
    private String prenom;
    private BigDecimal solde;
    private int pizzasAchetees;
    private BigDecimal totalDepenses;
    
    // Méthodes principales
    public void ajouterSolde(BigDecimal montant)
    public void debiterSolde(BigDecimal montant)
    public void incrementerPizzas()
}

public class Pizza {
    private int id;
    private String nom;
    private BigDecimal prixBase;
    private List<Ingredient> ingredients;
    
    // Méthodes principales
    public BigDecimal calculerPrix(Taille taille)
    public void ajouterIngredient(Ingredient ingredient)
}

public class Taille {
    private int id;
    private String nom;
    private BigDecimal coefficientPrix;
    
    // Méthodes principales
    public BigDecimal appliquerCoefficient(BigDecimal prixBase)
}

public class Vehicule {
    private int id;
    private String type;
    private String immatriculation;
    private int nombreUtilisations;
    
    // Méthodes principales
    public void incrementerUtilisations()
    public boolean estDisponible()
}

public class Livreur {
    private int id;
    private String nom;
    private String typeConducteur;
    private int nombreRetards;
    
    // Méthodes principales
    public void ajouterRetard()
    public double calculerScore()
}

public class Livraison {
    private int id;
    private LocalDateTime date;
    private int retard;
    private Client client;
    private Livreur livreur;
    private Vehicule vehicule;
    private List<CommandePizza> commandes;
    
    // Méthodes principales
    public int calculerTempsLivraison()
    public boolean estEnRetard()
    public String genererFicheLivraison()
}

public class CommandePizza {
    private Livraison livraison;
    private Pizza pizza;
    private Taille taille;
    private int quantite;
    private BigDecimal prixFacture;
    private boolean estGratuite;
    private String raisonGratuite;
    
    // Méthodes principales
    public BigDecimal calculerPrixTotal()
    public void appliquerGratuite(String raison)
}
```

#### 3.3.2 Classes Statistiques
```java
public class ClientCommandeStat {
    private Client client;
    private int nombreCommandes;
    private BigDecimal montantTotal;
    private BigDecimal moyenneParCommande;
    
    // Méthodes principales
    public void calculerMoyenne()
    public boolean estClientPremium()
    public Map<String, Integer> getPizzasPreferees()
}

public class LivreurRetardStat {
    private Livreur livreur;
    private int nombreLivraisons;
    private int nombreRetards;
    private Duration tempsRetardMoyen;
    
    // Méthodes principales
    public double getPourcentageRetard()
    public String evaluerPerformance()
    public List<Livraison> getRetardsSignificatifs()
}

public class PizzaPopulariteStat {
    private Pizza pizza;
    private int nombreCommandes;
    private BigDecimal chiffreAffaires;
    private Map<Taille, Integer> taillesPlusVendues;
    
    // Méthodes principales
    public void analyserTendances()
    public double getPourcentageVentes()
    public Taille getTaillePlusPopulaire()
}

public class VehiculeUtilisationStat {
    private Vehicule vehicule;
    private int nombreUtilisations;
    private int kilometrageTotal;
    private Duration tempsMoyenUtilisation;
    
    // Méthodes principales
    public double getTauxUtilisation()
    public boolean necessiteEntretien()
    public List<Periode> getPeriodesInactivite()
}
```

#### 3.3.3 Classes de Support
```java
public class ServiceProvider {
    private static ServiceProvider instance;
    private Connection connexion;
    private Map<Class<?>, Object> services;
    
    // Services disponibles
    private ClientService clientService;
    private LivraisonService livraisonService;
    private PizzaService pizzaService;
    private VehiculeService vehiculeService;
    private LivreurService livreurService;
    
    // Méthodes principales
    public static ServiceProvider getInstance()
    public <T> T getService(Class<T> serviceClass)
    public void initialiserServices()
}

public class DAOProvider {
    private Connection connexion;
    private Map<Class<?>, BaseDAO<?>> daos;
    
    // DAOs disponibles
    private ClientDAO clientDAO;
    private LivraisonDAO livraisonDAO;
    private PizzaDAO pizzaDAO;
    private VehiculeDAO vehiculeDAO;
    private LivreurDAO livreurDAO;
    
    // Méthodes principales
    public <T> BaseDAO<T> getDAO(Class<T> entityClass)
    public void initialiserDAOs()
}

public class DatabaseConnection {
    private static HikariDataSource dataSource;
    private static final String CONFIG_FILE = "database.properties";
    
    // Méthodes principales
    public static Connection getConnection()
    public static void initializePool()
    public static void closePool()
}

public class ConfigurationManager {
    private static Properties configuration;
    private static String environnement;
    
    // Méthodes principales
    public static void chargerConfiguration(String env)
    public static String getParametre(String cle)
    public static void setEnvironnement(String env)
}
```

## 4. Fonctionnalités Clés Implémentées

### 4.1 Système de Commande
- Sélection dynamique des pizzas et tailles
- Calcul automatique des prix avec coefficients
- Vérification du solde client
- Gestion des pizzas gratuites

### 4.2 Système de Livraison
- Attribution automatique des livreurs et véhicules
- Simulation de livraison avec retards aléatoires
- Génération de fiches de livraison détaillées
- Mise à jour automatique des statistiques

### 4.3 Gestion des Statistiques
- Calculs en temps réel
- Mise à jour automatique après chaque action
- Affichage formaté et organisé
- Filtres et agrégations diverses

## 5. Sécurité et Validation

### 5.1 Validation des Entrées
- Vérification des noms/prénoms (regex)
- Contrôle des montants et soldes
- Protection contre les doublons
- Validation des dates et heures

### 5.2 Gestion des Transactions
- Atomicité des commandes
- Cohérence des données
- Isolation des opérations
- Durabilité des enregistrements

## 6. Interface Utilisateur

### 6.1 Composants Swing
- JFrames et JPanels personnalisés
- Gestion des événements
- Navigation par CardLayout
- Thème aux couleurs italiennes

### 6.2 Retours Utilisateur
- Messages d'erreur explicites
- Confirmations d'actions
- Statuts de livraison en temps réel
- Mises à jour visuelles instantanées

## 7. Tests et Validation

### 7.1 Scénarios Testés
- Création de comptes
- Processus de commande complet
- Simulation de livraisons
- Calcul des statistiques

### 7.2 Jeu de Données
- Clients variés
- Historiques de commandes
- Performances des livreurs
- Utilisation des véhicules
