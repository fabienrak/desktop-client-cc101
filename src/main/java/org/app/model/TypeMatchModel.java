package org.app.model;

public class TypeMatchModel {
    private int id;
    private String nom_type_match;

    public TypeMatchModel(int id, String nom_type_match) {
        this.id = id;
        this.nom_type_match = nom_type_match;
    }

    public TypeMatchModel(String nom_type_match) {
        this.id = -1;
        this.nom_type_match = nom_type_match;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_type_match() {
        return nom_type_match;
    }

    public void setNom_type_match(String nom_type_match) {
        this.nom_type_match = nom_type_match;
    }
}
