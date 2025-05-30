# 🍕 RaPizz - Application de Gestion de Pizzeria

## 📝 Description
RaPizz est une application Java de gestion de pizzeria qui permet de gérer les commandes, les livraisons et le suivi des clients. L'interface graphique est développée avec Swing et suit un design aux couleurs italiennes.

## ✨ Fonctionnalités

### 🛍️ Commande de Pizzas
- Sélection de pizzas parmi un menu varié
- Choix de la taille (naine, humaine, ogresse) avec coefficients de prix
- Affichage des ingrédients en temps réel
- Calcul automatique du prix selon la taille

### 🚚 Système de Livraison
- Attribution automatique des livreurs
- Simulation de livraison en temps réel
- Gestion des retards :
  - Temps de livraison prévu : 20 minutes
  - Pizza gratuite si retard > 30 minutes
  - Probabilité de retard augmentée pour les livreurs ayant des antécédents

### 💰 Gestion des Clients
- Système de compte client avec solde
- Programme de fidélité :
  - Pizza gratuite toutes les 10 commandes
- Historique des commandes
- Gestion du solde avec possibilité de recharge

### 📊 Statistiques
- Suivi du chiffre d'affaires
- Classement des meilleurs clients
- Statistiques des livreurs (retards, performances)
- Suivi de l'utilisation des véhicules
- Pizzas les plus populaires

## 🛠️ Installation

### Prérequis
- Java JDK 17 ou supérieur
- MySQL 8.0 ou supérieur

### Configuration de la Base de Données
1. Créez une base de données MySQL :
```sql
CREATE DATABASE pizzeria;
```

2. Exécutez les scripts SQL dans l'ordre :
```bash
mysql -u votre_utilisateur -p pizzeria < SQL/creaDB.sql
mysql -u votre_utilisateur -p pizzeria < SQL/inserts_pizzas.sql
```

### Compilation et Exécution
1. Compilez et lancez le projet :
```bash
./run.sh
```

## 👥 Équipe
- GHAZANFAR Aman
- HAKIM Justine
- SADDIK Amine
- SAGAERT Auguste

## 🎨 Interface

### Navigation
- **Accueil** : Présentation et accès rapide aux fonctionnalités
- **Commander** : Interface de commande de pizzas
- **Statistiques** : Tableaux de bord et analyses
- **Compte** : Gestion du profil client et du solde

### Thème
- 🔴 Rosso Pomodoro (Rouge tomate)
- 💚 Verde Oliva (Vert olive)
- ⚪ Bianco Panna (Blanc crème)
- 💛 Giallo Oro (Or)

## 🚗 Véhicules et Livraison

### Types de Véhicules
- 🛵 Motos
- 🚗 Voitures

### Système de Livraison
- Temps estimé : 20 minutes
- Retards possibles : 5-40 minutes
- Gratuité si retard > 30 minutes
- Suivi en temps réel de la livraison

## 💡 Conseils d'Utilisation
1. Créez un compte ou connectez-vous
2. Ajoutez du solde à votre compte
3. Sélectionnez une pizza et sa taille
4. Vérifiez les ingrédients et le prix
5. Validez la commande
6. Suivez la livraison en temps réel

## 🔒 Sécurité
- Authentification requise pour commander
- Vérification du solde avant commande
- Protection contre les commandes multiples

## 📱 Contact
Pour toute question ou support :
- 📍 9 boulevard Copernic
- 🌐 www.rapizz.fr
- 📧 contact@rapizz.fr

## 📄 Licence
© 2025 RaPizz - Tous droits réservés
