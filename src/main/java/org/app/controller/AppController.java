package org.app.controller;

import javafx.fxml.FXML;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppController {

    // Couleur : C80B1B

    private Executor executor;

    @FXML
    private void initialize(){
        executor = Executors.newCachedThreadPool((runnable) -> {
           Thread thread = new Thread(runnable);
           thread.setDaemon(true);
           return thread;
        });
    }
}
