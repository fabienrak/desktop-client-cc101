package org.app.model;

public class Match {

    private int id_match;
    private int combattant_1;
    private int combattant_2;
    private int duree_match;
    private int tatami_id;
    private String tour_match;

    public Match(int id_match, int combattant_1, int combattant_2, int duree_match, int tatami_id, String tour_match) {
        this.id_match = id_match;
        this.combattant_1 = combattant_1;
        this.combattant_2 = combattant_2;
        this.duree_match = duree_match;
        this.tatami_id = tatami_id;
        this.tour_match = tour_match;
    }

    public Match(int combattant_1, int combattant_2, int duree_match, int tatami_id, String tour_match) {
        this.id_match = -1;
        this.combattant_1 = combattant_1;
        this.combattant_2 = combattant_2;
        this.duree_match = duree_match;
        this.tatami_id = tatami_id;
        this.tour_match = tour_match;
    }

    public int getId_match() {
        return id_match;
    }

    public void setId_match(int id_match) {
        this.id_match = id_match;
    }

    public int getCombattant_1() {
        return combattant_1;
    }

    public void setCombattant_1(int combattant_1) {
        this.combattant_1 = combattant_1;
    }

    public int getCombattant_2() {
        return combattant_2;
    }

    public void setCombattant_2(int combattant_2) {
        this.combattant_2 = combattant_2;
    }

    public int getDuree_match() {
        return duree_match;
    }

    public void setDuree_match(int duree_match) {
        this.duree_match = duree_match;
    }

    public int getTatami_id() {
        return tatami_id;
    }

    public void setTatami_id(int tatami_id) {
        this.tatami_id = tatami_id;
    }

    public String getTour_match() {
        return tour_match;
    }

    public void setTour_match(String tour_match) {
        this.tour_match = tour_match;
    }
}
