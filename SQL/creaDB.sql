-- Création de la base de données
CREATE DATABASE IF NOT EXISTS pizzeria;
USE pizzeria;

-- Table Client
CREATE TABLE Client (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    solde DECIMAL(8,2) DEFAULT 0.00,
    pizzas_achetees INT DEFAULT 0,
    total_depenses DECIMAL(10,2) DEFAULT 0.00
);

-- Table Pizza
CREATE TABLE Pizza (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prix_base DECIMAL(5,2) NOT NULL
);

-- Table Taille
CREATE TABLE Taille (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    coefficient_prix DECIMAL(3,2) NOT NULL
);

-- Insertion des tailles prédéfinies
INSERT INTO Taille (nom, coefficient_prix) VALUES 
('naine', 0.67),
('humaine', 1.0),
('ogresse', 1.33);

-- Table Ingredient
CREATE TABLE Ingredient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

-- Table Composition
CREATE TABLE Composition (
    id_pizza INT,
    id_ingredient INT,
    PRIMARY KEY (id_pizza, id_ingredient),
    FOREIGN KEY (id_pizza) REFERENCES Pizza(id) ON DELETE CASCADE,
    FOREIGN KEY (id_ingredient) REFERENCES Ingredient(id) ON DELETE CASCADE
);

-- Table Vehicule
CREATE TABLE Vehicule (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    immatriculation VARCHAR(20) NOT NULL UNIQUE,
    nombre_utilisations INT DEFAULT 0
);

-- Table Livreur
CREATE TABLE Livreur (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    type_conducteur VARCHAR(50) NOT NULL,
    nombre_retards INT DEFAULT 0
);

-- Table Livraison
CREATE TABLE Livraison (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATETIME NOT NULL,
    retard INT DEFAULT 0 COMMENT 'Retard en minutes',
    id_client INT,
    id_livreur INT,
    id_vehicule INT,
    FOREIGN KEY (id_client) REFERENCES Client(id),
    FOREIGN KEY (id_livreur) REFERENCES Livreur(id),
    FOREIGN KEY (id_vehicule) REFERENCES Vehicule(id)
);

-- Table Commande_pizza
CREATE TABLE Commande_pizza (
    id_livraison INT,
    id_pizza INT,
    id_taille INT,
    quantite INT NOT NULL DEFAULT 1,
    prix_facture DECIMAL(6,2) NOT NULL,
    est_gratuite BOOLEAN DEFAULT FALSE,
    raison_gratuite VARCHAR(255),
    PRIMARY KEY (id_livraison, id_pizza, id_taille),
    FOREIGN KEY (id_livraison) REFERENCES Livraison(id) ON DELETE CASCADE,
    FOREIGN KEY (id_pizza) REFERENCES Pizza(id),
    FOREIGN KEY (id_taille) REFERENCES Taille(id)
);
