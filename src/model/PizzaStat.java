package src.model;
public class PizzaStat {
    public int id;
    public String nom;
    public int nbCommandes;
    public PizzaStat(int id, String nom, int nbCommandes) {
        this.id = id;
        this.nom = nom;
        this.nbCommandes = nbCommandes;
    }
    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public int getNbCommandes() {
        return nbCommandes;
    }
    
}
