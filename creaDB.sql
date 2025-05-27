-- Table Client
CREATE TABLE Client (
    id INT PRIMARY KEY,
    nom VARCHAR(50),
    prénom VARCHAR(50),
    solde DECIMAL(10, 2),
    pizzas_achetées INT
);

-- Table Pizza
CREATE TABLE Pizza (
    id INT PRIMARY KEY,
    nom VARCHAR(50),
    prix_base DECIMAL(10, 2)
);

-- Table Taille
CREATE TABLE Taille (
    id INT PRIMARY KEY,
    nom VARCHAR(20) UNIQUE,
    coefficient_prix DECIMAL(4, 2)
);

-- Table Ingrédient
CREATE TABLE Ingrédient (
    id INT PRIMARY KEY,
    nom VARCHAR(50)
);

-- Table Composition (relation n-n entre Pizza et Ingrédient)
CREATE TABLE Composition (
    id_pizza INT,
    id_ingredient INT,
    PRIMARY KEY (id_pizza, id_ingredient),
    FOREIGN KEY (id_pizza) REFERENCES Pizza(id),
    FOREIGN KEY (id_ingredient) REFERENCES Ingrédient(id)
);

-- Table Véhicule
CREATE TABLE Véhicule (
    id INT PRIMARY KEY,
    type VARCHAR(50),
    immatriculation VARCHAR(20) UNIQUE
);

-- Table Livreur
CREATE TABLE Livreur (
    id INT PRIMARY KEY,
    nom VARCHAR(50),
    type_conducteur VARCHAR(50)
);

-- Table Livraison
CREATE TABLE Livraison (
    id INT PRIMARY KEY,
    date DATE,
    retard BOOLEAN,
    id_client INT,
    id_livreur INT,
    id_vehicule INT,
    id_pizza INT,
    id_taille INT,
    prix_facturé DECIMAL(10, 2),
    FOREIGN KEY (id_client) REFERENCES Client(id),
    FOREIGN KEY (id_livreur) REFERENCES Livreur(id),
    FOREIGN KEY (id_vehicule) REFERENCES Véhicule(id),
    FOREIGN KEY (id_pizza) REFERENCES Pizza(id),
    FOREIGN KEY (id_taille) REFERENCES Taille(id)
);

