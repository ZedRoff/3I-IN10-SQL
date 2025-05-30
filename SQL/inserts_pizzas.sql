-- Ingredient
INSERT INTO Ingredient (id, nom) VALUES (1, 'Anchois');
INSERT INTO Ingredient (id, nom) VALUES (2, 'Artichaut');
INSERT INTO Ingredient (id, nom) VALUES (3, 'Aubergine');
INSERT INTO Ingredient (id, nom) VALUES (4, 'Calamar');
INSERT INTO Ingredient (id, nom) VALUES (5, 'Champignons');
INSERT INTO Ingredient (id, nom) VALUES (6, 'Chorizo');
INSERT INTO Ingredient (id, nom) VALUES (7, 'Courgette');
INSERT INTO Ingredient (id, nom) VALUES (8, 'Crème fraîche');
INSERT INTO Ingredient (id, nom) VALUES (9, 'Câpres');
INSERT INTO Ingredient (id, nom) VALUES (10, 'Fontina');
INSERT INTO Ingredient (id, nom) VALUES (11, 'Fromage');
INSERT INTO Ingredient (id, nom) VALUES (12, 'Fromage de chèvre');
INSERT INTO Ingredient (id, nom) VALUES (13, 'Gorgonzola');
INSERT INTO Ingredient (id, nom) VALUES (14, 'Jambon');
INSERT INTO Ingredient (id, nom) VALUES (15, 'Jambon d’italie');
INSERT INTO Ingredient (id, nom) VALUES (16, 'Lardons');
INSERT INTO Ingredient (id, nom) VALUES (17, 'Merguez');
INSERT INTO Ingredient (id, nom) VALUES (18, 'Mozzarella');
INSERT INTO Ingredient (id, nom) VALUES (19, 'Oeuf');
INSERT INTO Ingredient (id, nom) VALUES (20, 'Oeufs');
INSERT INTO Ingredient (id, nom) VALUES (21, 'Oignon');
INSERT INTO Ingredient (id, nom) VALUES (22, 'Olives');
INSERT INTO Ingredient (id, nom) VALUES (23, 'Parmesan');
INSERT INTO Ingredient (id, nom) VALUES (24, 'Persillade');
INSERT INTO Ingredient (id, nom) VALUES (25, 'Poivron');
INSERT INTO Ingredient (id, nom) VALUES (26, 'Pomme de terre');
INSERT INTO Ingredient (id, nom) VALUES (27, 'Poulet');
INSERT INTO Ingredient (id, nom) VALUES (28, 'Reblochon');
INSERT INTO Ingredient (id, nom) VALUES (29, 'Saumon fumé');
INSERT INTO Ingredient (id, nom) VALUES (30, 'Thon');
INSERT INTO Ingredient (id, nom) VALUES (31, 'Tomate');
INSERT INTO Ingredient (id, nom) VALUES (32, 'Viande hachée');

-- Pizza
INSERT INTO Pizza (id, nom, prix_base) VALUES (1, 'Giovanni', 13.50);
INSERT INTO Pizza (id, nom, prix_base) VALUES (2, 'Palermo', 13.00);
INSERT INTO Pizza (id, nom, prix_base) VALUES (3, 'Regina', 12.00);
INSERT INTO Pizza (id, nom, prix_base) VALUES (4, 'Fermière', 13.00);
INSERT INTO Pizza (id, nom, prix_base) VALUES (5, 'Verone soufflée', 13.50);
INSERT INTO Pizza (id, nom, prix_base) VALUES (6, 'Orientale', 13.00);
INSERT INTO Pizza (id, nom, prix_base) VALUES (7, 'Italia', 13.00);
INSERT INTO Pizza (id, nom, prix_base) VALUES (8, 'Quatre saisons', 13.50);
INSERT INTO Pizza (id, nom, prix_base) VALUES (9, 'Salmone', 14.00);
INSERT INTO Pizza (id, nom, prix_base) VALUES (10, 'Pescatore', 14.00);
INSERT INTO Pizza (id, nom, prix_base) VALUES (11, 'Espagnola', 13.50);
INSERT INTO Pizza (id, nom, prix_base) VALUES (12, 'Bologna', 13.00);
INSERT INTO Pizza (id, nom, prix_base) VALUES (13, 'Romana', 14.00);
INSERT INTO Pizza (id, nom, prix_base) VALUES (14, 'Parma', 14.00);
INSERT INTO Pizza (id, nom, prix_base) VALUES (15, 'Végétarienne', 13.00);
INSERT INTO Pizza (id, nom, prix_base) VALUES (16, 'Tartiflette', 14.00);
INSERT INTO Pizza (id, nom, prix_base) VALUES (17, 'Fangio', 13.50);
INSERT INTO Pizza (id, nom, prix_base) VALUES (18, 'Gino', 13.00);
INSERT INTO Pizza (id, nom, prix_base) VALUES (19, 'Napolitaine', 12.00);
INSERT INTO Pizza (id, nom, prix_base) VALUES (20, 'Al tonno', 13.00);
INSERT INTO Pizza (id, nom, prix_base) VALUES (21, 'Chèvre', 13.00);
INSERT INTO Pizza (id, nom, prix_base) VALUES (22, 'Margherita', 10.00);
INSERT INTO Pizza (id, nom, prix_base) VALUES (23, 'Quatre fromages', 13.50);
INSERT INTO Pizza (id, nom, prix_base) VALUES (24, 'Calzone soufflée', 13.00);

-- Composition
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (1, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (1, 11);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (1, 14);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (1, 32);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (1, 19);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (1, 8);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (2, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (2, 11);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (2, 6);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (2, 25);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (2, 21);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (2, 19);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (3, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (3, 11);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (3, 14);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (3, 5);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (4, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (4, 11);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (4, 27);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (4, 21);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (4, 25);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (5, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (5, 11);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (5, 14);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (5, 19);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (5, 8);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (5, 5);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (6, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (6, 11);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (6, 17);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (6, 25);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (6, 19);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (7, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (7, 11);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (7, 15);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (7, 5);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (8, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (8, 11);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (8, 14);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (8, 5);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (8, 2);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (8, 25);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (9, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (9, 11);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (9, 29);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (9, 8);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (10, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (10, 4);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (10, 24);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (11, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (11, 12);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (11, 6);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (11, 19);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (12, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (12, 32);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (12, 19);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (13, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (13, 11);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (13, 3);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (13, 21);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (13, 32);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (14, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (14, 11);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (14, 15);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (14, 13);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (14, 19);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (14, 8);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (15, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (15, 11);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (15, 25);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (15, 7);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (15, 2);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (15, 3);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (15, 5);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (15, 21);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (16, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (16, 11);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (16, 28);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (16, 21);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (16, 16);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (16, 26);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (16, 8);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (17, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (17, 11);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (17, 21);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (17, 5);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (17, 16);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (17, 20);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (18, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (18, 11);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (18, 14);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (18, 19);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (18, 8);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (19, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (19, 11);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (19, 1);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (19, 9);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (19, 22);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (20, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (20, 11);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (20, 30);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (20, 19);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (21, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (21, 11);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (21, 12);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (22, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (22, 11);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (23, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (23, 18);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (23, 13);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (23, 23);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (23, 10);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (24, 31);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (24, 11);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (24, 14);
INSERT INTO Composition (id_pizza, id_ingredient) VALUES (24, 19);

-- Clients
INSERT INTO Client (id, nom, prenom, solde, pizzas_achetees, total_depenses) VALUES 
(1, 'HAKIM', 'Justine', 74.20, 12, 156.80),
(2, 'DUBOIS', 'Thomas', 25.50, 8, 98.40),
(3, 'MARTIN', 'Sophie', 42.30, 15, 187.50),
(4, 'PETIT', 'Lucas', 12.80, 3, 38.90),
(5, 'BERNARD', 'Emma', 95.60, 20, 245.30),
(6, 'ROBERT', 'Hugo', 5.20, 6, 78.60),
(7, 'RICHARD', 'Léa', 150.00, 0, 0.00),
(8, 'MOREAU', 'Gabriel', 28.90, 4, 52.40),
(9, 'LAURENT', 'Chloé', 63.70, 9, 118.80),
(10, 'SIMON', 'Arthur', 82.40, 7, 89.90);

-- Insertion des véhicules
INSERT INTO Vehicule (id, type, immatriculation, nombre_utilisations) VALUES 
(1, 'Moto', 'AB-001-CD', 45),
(2, 'Voiture', 'AB-002-CD', 38),
(3, 'Moto', 'AB-003-CD', 52),
(4, 'Voiture', 'AB-004-CD', 29),
(5, 'Moto', 'AB-005-CD', 41),
(6, 'Voiture', 'AB-006-CD', 15),
(7, 'Moto', 'AB-007-CD', 33),
(8, 'Voiture', 'AB-008-CD', 27);

-- Insertion des livreurs
INSERT INTO Livreur (id, nom, type_conducteur, nombre_retards) VALUES 
(1, 'DUPONT', 'A2', 2),
(2, 'MARTIN', 'B', 0),
(3, 'SADDIK', 'A2', 1),
(4, 'ROUSSEAU', 'A1', 3),
(5, 'GIRARD', 'B', 0),
(6, 'ROUX', 'A2', 1),
(7, 'FONTAINE', 'A1', 0),
(8, 'MERCIER', 'B', 2);

-- Insertion des tailles prédéfinies
INSERT INTO Taille (nom, coefficient_prix) VALUES 
('naine', 0.67),
('humaine', 1.0),
('ogresse', 1.33);

-- Insertion des livraisons
INSERT INTO Livraison ( date, retard, id_client, id_livreur, id_vehicule) VALUES 
('2025-02-15 19:30:00', 0, 1, 1, 1),
('2025-02-15 20:15:00', 5, 2, 3, 2),
('2025-02-16 17:15:00', 10, 1, 1, 2),
('2025-02-16 18:45:00', 0, 3, 2, 3),
('2025-02-17 19:20:00', 15, 4, 4, 1),
('2025-02-17 20:30:00', 0, 5, 5, 4),
('2025-02-18 12:15:00', 0, 6, 6, 5),
('2025-02-18 13:45:00', 8, 7, 7, 6),
('2025-02-19 19:10:00', 0, 8, 8, 7),
('2025-02-19 20:05:00', 0, 9, 1, 8),
('2025-02-20 18:30:00', 12, 10, 2, 1),
('2025-02-20 19:45:00', 0, 1, 3, 2),
('2025-02-21 20:20:00', 0, 2, 4, 3),
('2025-02-21 21:00:00', 20, 3, 5, 4),
('2025-02-22 18:15:00', 0, 4, 6, 5);

-- Commande
INSERT INTO Commande_pizza (id_livraison, id_pizza, id_taille, quantite, prix_facture, est_gratuite, raison_gratuite) VALUES 
(1, 1, 2, 1, 13.50, FALSE, NULL),
(1, 2, 1, 2, 17.42, TRUE, 'Fidélité client'),
(2, 3, 3, 1, 15.96, FALSE, NULL);
