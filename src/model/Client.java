package src.model;

public class Client {
    private int id;
    private String nom;
    private String prenom;
    private double solde;
    private int pizzasAchetees;
    private double totalDepenses;

    public Client(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.solde = 0.0;
        this.pizzasAchetees = 0;
        this.totalDepenses = 0.0;
    }
    public Client(int id, String nom, String prenom, double solde, int pizzasAchetees, double totalDepenses) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.solde = solde;
        this.pizzasAchetees = pizzasAchetees;
        this.totalDepenses = totalDepenses;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public double getSolde() {
        return solde;
    }
    public void setSolde(double solde) {
        this.solde = solde;
    }
    public int getPizzasAchetees() {
        return pizzasAchetees;
    }
    public void setPizzasAchetees(int pizzasAchetees) {
        this.pizzasAchetees = pizzasAchetees;
    }
    public double getTotalDepenses() {
        return totalDepenses;
    }
    public void setTotalDepenses(double totalDepenses) {
        this.totalDepenses = totalDepenses;
    }
    
   
}