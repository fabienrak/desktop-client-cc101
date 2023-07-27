package org.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class Competition {

    private int id_competition;
    private String nom_competition;
    private String date_debut;
    private String date_fin;
    private String lieu_competition;

    public Competition(String nom_competition, String date_debut, String date_fin, String lieu_competition) {
        this.id_competition = -1;
        this.nom_competition = nom_competition;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.lieu_competition = lieu_competition;
    }

    public Competition(int id_competition, String nom_competition, String date_debut, String date_fin, String lieu_competition) {
        this.id_competition = id_competition;
        this.nom_competition = nom_competition;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.lieu_competition = lieu_competition;
    }

    public int getId_competition() {
        return id_competition;
    }

    public void setId_competition(int id_competition) {
        this.id_competition = id_competition;
    }

    public String getNom_competition() {
        return nom_competition;
    }

    public void setNom_competition(String nom_competition) {
        this.nom_competition = nom_competition;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public String getLieu_competition() {
        return lieu_competition;
    }

    public void setLieu_competition(String lieu_competition) {
        this.lieu_competition = lieu_competition;
    }
}
