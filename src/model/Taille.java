package src.model;


public class Taille {
    public int id;
    public String nom;
    public double coefficientPrix;

    public Taille(int id, String nom, double coefficientPrix) {
        this.id = id;
        this.nom = nom;
        this.coefficientPrix = coefficientPrix;
    }
    public int getId() { return id; }
    public String getNom() { return nom; }
    public double getCoefficientPrix() { return coefficientPrix; }

}
