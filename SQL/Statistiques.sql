
-- Véhicule n'ayant jamais servi
SELECT v.id, v.type, v.immatriculation
FROM Vehicule v
LEFT JOIN Livraison liv ON v.id = liv.id_vehicule
WHERE liv.id IS NULL;

-- Nombre de commandes par client
SELECT c.id, c.nom, c.prenom, COUNT(liv.id) AS nb_commandes
FROM Client c
LEFT JOIN Livraison liv ON c.id = liv.id_client
GROUP BY c.id, c.nom, c.prenom;

-- Moyenne des commandes par client
SELECT AVG(nb) AS moyenne_commandes
FROM (
    SELECT COUNT(*) AS nb
    FROM Livraison
    GROUP BY id_client
) AS sous_req;

-- Client ayant commandé plus que la moyenne
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

-- LE meilleur client
SELECT c.id, c.nom, c.prenom, COUNT(liv.id) AS nb_commandes
FROM Client c
JOIN Livraison liv ON c.id = liv.id_client
GROUP BY c.id
ORDER BY nb_commandes DESC
LIMIT 1;

-- LE pire livreur
SELECT l.id, l.nom,
       COUNT(*) AS nb_retards,
       SUM(liv.retard) AS total_retard_minutes
FROM Livreur l
JOIN Livraison liv ON l.id = liv.id_livreur
WHERE liv.retard > 0
GROUP BY l.id, l.nom
ORDER BY nb_retards DESC, total_retard_minutes DESC
LIMIT 1;


-- LA meilleure pizza 
SELECT p.id, p.nom, COUNT(*) AS nb_commandes
FROM Pizza p
JOIN Commande_pizza c ON p.id = c.id_pizza
GROUP BY p.id, p.nom
ORDER BY nb_commandes DESC
LIMIT 1;

-- LE meilleur ingrédient
SELECT i.id, i.nom, COUNT(*) AS nb_utilisation
FROM Ingredient i
JOIN Composition c ON i.id = c.id_ingredient
GROUP BY i.id, i.nom
ORDER BY nb_utilisation DESC
LIMIT 1;
