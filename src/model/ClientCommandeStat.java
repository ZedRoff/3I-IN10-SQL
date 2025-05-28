package src.model;

public class ClientCommandeStat {
    private int id;
    private String nom;
    private String prenom;
    private int nbCommandes;

    public ClientCommandeStat(int id, String nom, String prenom, int nbCommandes) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.nbCommandes = nbCommandes;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public int getNbCommandes() { return nbCommandes; }
}
