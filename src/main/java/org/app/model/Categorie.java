package org.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Categorie {

    private int id_category;
    private String nom_category;
    private String nom_sous_category;
    private int poids_max;
    private int age_max;

    public Categorie(int id_category, String nom_category, String nom_sous_category, int poids_max, int age_max) {
        this.id_category = id_category;
        this.nom_category = nom_category;
        this.nom_sous_category = nom_sous_category;
        this.poids_max = poids_max;
        this.age_max = age_max;
    }

    public Categorie(String nom_category, String nom_sous_category, int poids_max, int age_max) {
        this.id_category = -1;
        this.nom_category = nom_category;
        this.nom_sous_category = nom_sous_category;
        this.poids_max = poids_max;
        this.age_max = age_max;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public String getNom_category() {
        return nom_category;
    }

    public void setNom_category(String nom_category) {
        this.nom_category = nom_category;
    }

    public String getNom_sous_category() {
        return nom_sous_category;
    }

    public void setNom_sous_category(String nom_sous_category) {
        this.nom_sous_category = nom_sous_category;
    }

    public int getPoids_max() {
        return poids_max;
    }

    public void setPoids_max(int poids_max) {
        this.poids_max = poids_max;
    }

    public int getAge_max() {
        return age_max;
    }

    public void setAge_max(int age_max) {
        this.age_max = age_max;
    }
}
