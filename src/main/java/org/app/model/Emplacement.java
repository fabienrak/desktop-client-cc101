package org.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Emplacement {

    private int id_emplacement;
    private String nom_emplacement;
    private String couleur_tatami;

    public Emplacement(int id_emplacement, String nom_emplacement, String couleur_tatami) {
        this.id_emplacement = id_emplacement;
        this.nom_emplacement = nom_emplacement;
        this.couleur_tatami = couleur_tatami;
    }

    public Emplacement(String nom_emplacement, String couleur_tatami) {
        this.id_emplacement = -1;
        this.nom_emplacement = nom_emplacement;
        this.couleur_tatami = couleur_tatami;
    }

    public int getId_emplacement() {
        return id_emplacement;
    }

    public void setId_emplacement(int id_emplacement) {
        this.id_emplacement = id_emplacement;
    }

    public String getNom_emplacement() {
        return nom_emplacement;
    }

    public void setNom_emplacement(String nom_emplacement) {
        this.nom_emplacement = nom_emplacement;
    }

    public String getCouleur_tatami() {
        return couleur_tatami;
    }

    public void setCouleur_tatami(String couleur_tatami) {
        this.couleur_tatami = couleur_tatami;
    }
}
