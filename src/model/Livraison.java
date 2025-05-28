package src.model;

import java.security.Timestamp;

public class Livraison {
    private int id;
    private Timestamp date;
    private int retard; // Retard in minutes
    private int idClient;
    private int idLivreur;
    private int idVehicule;

    public Livraison(int id, Timestamp date, int retard, int idClient, int idLivreur, int idVehicule) {
        this.id = id;
        this.date = date;
        this.retard = retard;
        this.idClient = idClient;
        this.idLivreur = idLivreur;
        this.idVehicule = idVehicule;
    }
}