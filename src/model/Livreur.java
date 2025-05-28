package src.model;
public class Livreur {
    private int id;
    private String nom;
    private String typeConducteur;
    private int nombreRetards;

    public Livreur(int id, String nom, String typeConducteur, int nombreRetards) {
        this.id = id;
        this.nom = nom;
        this.typeConducteur = typeConducteur;
        this.nombreRetards = nombreRetards;
    }
 public String getNom() { return nom; }
   public int getId() { return id; }
    public String getTypeConducteur() { return typeConducteur; }
    public int getNombreRetards() { return nombreRetards; }

   
}