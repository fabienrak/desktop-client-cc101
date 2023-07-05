package org.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Club {

    private IntegerProperty id_club;
    private StringProperty nom_club;
    private StringProperty adresse_club;

    public Club() {
        this.id_club = new SimpleIntegerProperty();
        this.nom_club = new SimpleStringProperty();
        this.adresse_club = new SimpleStringProperty();
    }

    public int getId_club() {
        return id_club.get();
    }

    public IntegerProperty id_clubProperty() {
        return id_club;
    }

    public void setId_club(int id_club) {
        this.id_club.set(id_club);
    }

    public String getNom_club() {
        return nom_club.get();
    }

    public StringProperty nom_clubProperty() {
        return nom_club;
    }

    public void setNom_club(String nom_club) {
        this.nom_club.set(nom_club);
    }

    public String getAdresse_club() {
        return adresse_club.get();
    }

    public StringProperty adresse_clubProperty() {
        return adresse_club;
    }

    public void setAdresse_club(String adresse_club) {
        this.adresse_club.set(adresse_club);
    }
}
