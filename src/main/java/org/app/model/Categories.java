package org.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Categories {

    private IntegerProperty id_category;
    private StringProperty nom_category;
    private IntegerProperty poids_min;
    private IntegerProperty poids_max;

    public Categories(){
        this.id_category = new SimpleIntegerProperty();
        this.nom_category = new SimpleStringProperty();
        this.poids_max = new SimpleIntegerProperty();
        this.poids_min = new SimpleIntegerProperty();
    }

    public int getId_category() {
        return id_category.get();
    }

    public IntegerProperty id_categoryProperty() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category.set(id_category);
    }

    public String getNom_category() {
        return nom_category.get();
    }

    public StringProperty nom_categoryProperty() {
        return nom_category;
    }

    public void setNom_category(String nom_category) {
        this.nom_category.set(nom_category);
    }

    public int getPoids_min() {
        return poids_min.get();
    }

    public IntegerProperty poids_minProperty() {
        return poids_min;
    }

    public void setPoids_min(int poids_min) {
        this.poids_min.set(poids_min);
    }

    public int getPoids_max() {
        return poids_max.get();
    }

    public IntegerProperty poids_maxProperty() {
        return poids_max;
    }

    public void setPoids_max(int poids_max) {
        this.poids_max.set(poids_max);
    }
}
