package src.model;

public class VehiculeStat {
    private int id;
    private String type;
    private String immatriculation;
    private int nbUtilisations;

    public VehiculeStat(int id, String type, String immatriculation, int nbUtilisations) {
        this.id = id;
        this.type = type;
        this.immatriculation = immatriculation;
        this.nbUtilisations = nbUtilisations;
    }

    public int getId() { return id; }
    public String getType() { return type; }
    public String getImmatriculation() { return immatriculation; }
    public int getNbUtilisations() { return nbUtilisations; }
} 