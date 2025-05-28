package src.model;

public class LivreurRetardStat {
    private int id;
    private String nom;
    private int nbRetards;
    private int totalRetardMinutes;

    public LivreurRetardStat(int id, String nom, int nbRetards, int totalRetardMinutes) {
        this.id = id;
        this.nom = nom;
        this.nbRetards = nbRetards;
        this.totalRetardMinutes = totalRetardMinutes;
    }
    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public int getNbRetards() {
        return nbRetards;
    }
    public int getTotalRetardMinutes() {
        return totalRetardMinutes;
    }
    
}