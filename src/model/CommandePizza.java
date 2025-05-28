package src.model;
public class CommandePizza {
    private int idLivraison;
    private int idPizza;
    private int idTaille;
    private int quantite;
    private double prixFacture;
    private boolean estGratuite;
    private String raisonGratuite;

    public CommandePizza(int idLivraison, int idPizza, int idTaille, int quantite, double prixFacture, boolean estGratuite, String raisonGratuite) {
        this.idLivraison = idLivraison;
        this.idPizza = idPizza;
        this.idTaille = idTaille;
        this.quantite = quantite;
        this.prixFacture = prixFacture;
        this.estGratuite = estGratuite;
        this.raisonGratuite = raisonGratuite;
    }
}