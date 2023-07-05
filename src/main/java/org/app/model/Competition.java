package org.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class Competition {

    private IntegerProperty id_competition;
    private StringProperty nom_competition;
    private StringProperty date_debut;
    private StringProperty date_fin;
    private StringProperty lieu_competition;

    public Competition(){
        this.id_competition = new SimpleIntegerProperty();
        this.nom_competition = new SimpleStringProperty();
        this.date_debut = new SimpleStringProperty();
        this.date_fin = new SimpleStringProperty();
        this.lieu_competition = new SimpleStringProperty();
    }

    public int getId_competition() {
        return id_competition.get();
    }

    public IntegerProperty id_competitionProperty() {
        return id_competition;
    }

    public void setId_competition(int id_competition) {
        this.id_competition.set(id_competition);
    }

    public String getNom_competition() {
        return nom_competition.get();
    }

    public StringProperty nom_competitionProperty() {
        return nom_competition;
    }

    public void setNom_competition(String nom_competition) {
        this.nom_competition.set(nom_competition);
    }

    public String getDate_debut() {
        return date_debut.get();
    }

    public StringProperty date_debutProperty() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut.set(date_debut);
    }

    public String getDate_fin() {
        return date_fin.get();
    }

    public StringProperty date_finProperty() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin.set(date_fin);
    }

    public String getLieu_competition() {
        return lieu_competition.get();
    }

    public StringProperty lieu_competitionProperty() {
        return lieu_competition;
    }

    public void setLieu_competition(String lieu_competition) {
        this.lieu_competition.set(lieu_competition);
    }
}
