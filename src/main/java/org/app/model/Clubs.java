package org.app.model;

public class Clubs {

    private int id_club;
    private String nom_club;
    private String adresse_club;

    public Clubs(int id_club, String nom_club, String adresse_club) {
        this.id_club = id_club;
        this.nom_club = nom_club;
        this.adresse_club = adresse_club;
    }

    public Clubs(String nom_club, String adresse_club){
        this.id_club = -1;
        this.nom_club = nom_club;
        this.adresse_club = adresse_club;
    }

    public int getId_club() {
        return id_club;
    }

    public void setId_club(int id_club) {
        this.id_club = id_club;
    }

    public String getNom_club() {
        return nom_club;
    }

    public void setNom_club(String nom_club) {
        this.nom_club = nom_club;
    }

    public String getAdresse_club() {
        return adresse_club;
    }

    public void setAdresse_club(String adresse_club) {
        this.adresse_club = adresse_club;
    }
}
