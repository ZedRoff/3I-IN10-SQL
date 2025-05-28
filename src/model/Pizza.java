package src.model;
public class Pizza {
    private int id;
    private String nom;
    private double prixBase;

    public Pizza(int id, String nom, double prixBase) {
        this.id = id;
        this.nom = nom;
        this.prixBase = prixBase;
    }
     public int getId() { return id; }
    public String getNom() { return nom; }
    public double getPrixBase() { return prixBase; }
}