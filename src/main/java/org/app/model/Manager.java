package org.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Manager {

    private IntegerProperty id_manager;
    private StringProperty username_manager;
    private StringProperty password_manager;

    public Manager(){
        this.id_manager = new SimpleIntegerProperty();
        this.username_manager = new SimpleStringProperty();
        this.password_manager = new SimpleStringProperty();
    }

    public int getId_manager() {
        return id_manager.get();
    }

    public IntegerProperty id_managerProperty() {
        return id_manager;
    }

    public void setId_manager(int id_manager) {
        this.id_manager.set(id_manager);
    }

    public String getUsername_manager() {
        return username_manager.get();
    }

    public StringProperty username_managerProperty() {
        return username_manager;
    }

    public void setUsername_manager(String username_manager) {
        this.username_manager.set(username_manager);
    }

    public String getPassword_manager() {
        return password_manager.get();
    }

    public StringProperty password_managerProperty() {
        return password_manager;
    }

    public void setPassword_manager(String password_manager) {
        this.password_manager.set(password_manager);
    }
}
