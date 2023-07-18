package org.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Combattants {
    private int id_combattant;
    private String nom_combattant;
    private String prenom_combattant;
    private String date_naissance_combattant;
    private String genre_combattant;
    private int poids_combattant;
    private String grade_combattant;
    private int id_club_combattant;
    private int id_category_combattant;
    private String categories_combattant;
    private String club_combattant;


    public Combattants(int id_combattant, String nom_combattant, String prenom_combattant, String date_naissance_combattant, String genre_combattant, int poids_combattant, String grade_combattant, String club_combattant, String categories_combattant) {
        this.id_combattant = id_combattant;
        this.nom_combattant = nom_combattant;
        this.prenom_combattant = prenom_combattant;
        this.date_naissance_combattant = date_naissance_combattant;
        this.genre_combattant = genre_combattant;
        this.poids_combattant = poids_combattant;
        this.grade_combattant = grade_combattant;
        this.club_combattant = club_combattant;
        this.categories_combattant = categories_combattant;
    }


    public Combattants(String nom_combattant, String prenom_combattant, String date_naissance_combattant, String genre_combattant, int poids_combattant, String grade_combattant, int id_club_combattant, int id_category_combattant) {
        this.id_combattant = -1;
        this.nom_combattant = nom_combattant;
        this.prenom_combattant = prenom_combattant;
        this.date_naissance_combattant = date_naissance_combattant;
        this.genre_combattant = genre_combattant;
        this.poids_combattant = poids_combattant;
        this.grade_combattant = grade_combattant;
        this.id_club_combattant = id_club_combattant;
        this.id_category_combattant = id_category_combattant;
    }

    public int getId_combattant() {
        return id_combattant;
    }

    public void setId_combattant(int id_combattant) {
        this.id_combattant = id_combattant;
    }

    public String getNom_combattant() {
        return nom_combattant;
    }

    public void setNom_combattant(String nom_combattant) {
        this.nom_combattant = nom_combattant;
    }

    public String getPrenom_combattant() {
        return prenom_combattant;
    }

    public void setPrenom_combattant(String prenom_combattant) {
        this.prenom_combattant = prenom_combattant;
    }

    public String getDate_naissance_combattant() {
        return date_naissance_combattant;
    }

    public void setDate_naissance_combattant(String date_naissance_combattant) {
        this.date_naissance_combattant = date_naissance_combattant;
    }

    public String getGenre_combattant() {
        return genre_combattant;
    }

    public void setGenre_combattant(String genre_combattant) {
        this.genre_combattant = genre_combattant;
    }

    public int getPoids_combattant() {
        return poids_combattant;
    }

    public void setPoids_combattant(int poids_combattant) {
        this.poids_combattant = poids_combattant;
    }

    public String getGrade_combattant() {
        return grade_combattant;
    }

    public void setGrade_combattant(String grade_combattant) {
        this.grade_combattant = grade_combattant;
    }

    public int getId_club_combattant() {
        return id_club_combattant;
    }

    public void setId_club_combattant(int id_club_combattant) {
        this.id_club_combattant = id_club_combattant;
    }

    public int getId_category_combattant() {
        return id_category_combattant;
    }

    public void setId_category_combattant(int id_category_combattant) {
        this.id_category_combattant = id_category_combattant;
    }

    public String getCategories_combattant() {
        return categories_combattant;
    }

    public void setCategories_combattant(String categories_combattant) {
        this.categories_combattant = categories_combattant;
    }

    public String getClub_combattant() {
        return club_combattant;
    }

    public void setClub_combattant(String club_combattant) {
        this.club_combattant = club_combattant;
    }
}
