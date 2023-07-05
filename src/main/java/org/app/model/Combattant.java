package org.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Combattant {

    private IntegerProperty id_combattant;
    private StringProperty nom_combattant;
    private StringProperty prenom_combattant;
    private StringProperty date_naissance_combattant;
    private StringProperty genre_combattant;
    private IntegerProperty poids_combattant;
    private StringProperty grade_combattant;
    private IntegerProperty id_club_combattant;
    private IntegerProperty id_category_combattant;
    private StringProperty nom_club_combattant;
    private StringProperty nom_categorie_combattant;

    public Combattant(){
        this.id_combattant = new SimpleIntegerProperty();
        this.nom_combattant = new SimpleStringProperty();
        this.prenom_combattant = new SimpleStringProperty();
        this.date_naissance_combattant = new SimpleStringProperty();
        this.genre_combattant = new SimpleStringProperty();
        this.poids_combattant = new SimpleIntegerProperty();
        this.grade_combattant = new SimpleStringProperty();
        this.id_club_combattant = new SimpleIntegerProperty();
        this.id_category_combattant = new SimpleIntegerProperty();
        this.nom_club_combattant = new SimpleStringProperty();
        this.nom_categorie_combattant = new SimpleStringProperty();
    }

    public int getId_combattant() {
        return id_combattant.get();
    }

    public IntegerProperty id_combattantProperty() {
        return id_combattant;
    }

    public void setId_combattant(int id_combattant) {
        this.id_combattant.set(id_combattant);
    }

    public String getNom_combattant() {
        return nom_combattant.get();
    }

    public StringProperty nom_combattantProperty() {
        return nom_combattant;
    }

    public void setNom_combattant(String nom_combattant) {
        this.nom_combattant.set(nom_combattant);
    }

    public String getPrenom_combattant() {
        return prenom_combattant.get();
    }

    public StringProperty prenom_combattantProperty() {
        return prenom_combattant;
    }

    public void setPrenom_combattant(String prenom_combattant) {
        this.prenom_combattant.set(prenom_combattant);
    }

    public String getDate_naissance_combattant() {
        return date_naissance_combattant.get();
    }

    public StringProperty date_naissance_combattantProperty() {
        return date_naissance_combattant;
    }

    public void setDate_naissance_combattant(String date_naissance_combattant) {
        this.date_naissance_combattant.set(date_naissance_combattant);
    }

    public String getGenre_combattant() {
        return genre_combattant.get();
    }

    public StringProperty genre_combattantProperty() {
        return genre_combattant;
    }

    public void setGenre_combattant(String genre_combattant) {
        this.genre_combattant.set(genre_combattant);
    }

    public int getPoids_combattant() {
        return poids_combattant.get();
    }

    public IntegerProperty poids_combattantProperty() {
        return poids_combattant;
    }

    public void setPoids_combattant(int poids_combattant) {
        this.poids_combattant.set(poids_combattant);
    }

    public String getGrade_combattant() {
        return grade_combattant.get();
    }

    public StringProperty grade_combattantProperty() {
        return grade_combattant;
    }

    public void setGrade_combattant(String grade_combattant) {
        this.grade_combattant.set(grade_combattant);
    }

    public int getId_club_combattant() {
        return id_club_combattant.get();
    }

    public IntegerProperty id_club_combattantProperty() {
        return id_club_combattant;
    }

    public void setId_club_combattant(int id_club_combattant) {
        this.id_club_combattant.set(id_club_combattant);
    }

    public int getId_category_combattant() {
        return id_category_combattant.get();
    }

    public IntegerProperty id_category_combattantProperty() {
        return id_category_combattant;
    }

    public void setId_category_combattant(int id_category_combattant) {
        this.id_category_combattant.set(id_category_combattant);
    }

    public String getNom_club_combattant() {
        return nom_club_combattant.get();
    }

    public StringProperty nom_club_combattantProperty() {
        return nom_club_combattant;
    }

    public void setNom_club_combattant(String nom_club_combattant) {
        this.nom_club_combattant.set(nom_club_combattant);
    }

    public String getNom_categorie_combattant() {
        return nom_categorie_combattant.get();
    }

    public StringProperty nom_categorie_combattantProperty() {
        return nom_categorie_combattant;
    }

    public void setNom_categorie_combattant(String nom_categorie_combattant) {
        this.nom_categorie_combattant.set(nom_categorie_combattant);
    }
}
