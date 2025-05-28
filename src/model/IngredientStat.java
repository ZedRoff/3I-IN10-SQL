package src.model;
public class IngredientStat {
    public int id;
    public String nom;
    public int nbUtilisation;
    public IngredientStat(int id, String nom, int nbUtilisation) {
        this.id = id;
        this.nom = nom;
        this.nbUtilisation = nbUtilisation;
    }
    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public int getNbUtilisation() {
        return nbUtilisation;
    }
}