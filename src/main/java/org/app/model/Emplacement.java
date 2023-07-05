package org.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Emplacement {

    private IntegerProperty id_emplacement;
    private StringProperty nom_emplacement;
    private StringProperty couleur_tatami;

    public Emplacement(){
        this.id_emplacement = new SimpleIntegerProperty();
        this.nom_emplacement = new SimpleStringProperty();
        this.couleur_tatami = new SimpleStringProperty();
    }

    public int getId_emplacement() {
        return id_emplacement.get();
    }

    public IntegerProperty id_emplacementProperty() {
        return id_emplacement;
    }

    public void setId_emplacement(int id_emplacement) {
        this.id_emplacement.set(id_emplacement);
    }

    public String getNom_emplacement() {
        return nom_emplacement.get();
    }

    public StringProperty nom_emplacementProperty() {
        return nom_emplacement;
    }

    public void setNom_emplacement(String nom_emplacement) {
        this.nom_emplacement.set(nom_emplacement);
    }

    public String getCouleur_tatami() {
        return couleur_tatami.get();
    }

    public StringProperty couleur_tatamiProperty() {
        return couleur_tatami;
    }

    public void setCouleur_tatami(String couleur_tatami) {
        this.couleur_tatami.set(couleur_tatami);
    }
}
