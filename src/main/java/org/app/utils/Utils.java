package org.app.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    /**
     * Alert Avertissement
     */
    public void warningAlertDialog(String headerText, String contentMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("AVERTISSEMENT");
        alert.setHeaderText(headerText);
        alert.setContentText(contentMessage);
        alert.setGraphic(new ImageView(this.getClass().getResource("/images/attention.png").toString()));
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.out.println("BUTTON CLIKER kjdghvhjksdgvjhsdfghjv"));
    }

    /**
     * Alert Erreur
     */
    public void erreurAlertDialog(String headerText, String contentMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("AVERTISSEMENT");
        alert.setHeaderText(headerText);
        alert.setContentText(contentMessage);
        alert.setGraphic(new ImageView(this.getClass().getResource("/images/cross.png").toString()));
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.out.println("BUTTON CLIKER kjdghvhjksdgvjhsdfghjv"));
    }

    /**
     * Alert Success
     */
    public void successAlertDialog(String headerText, String contentMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCCESS");
        alert.setHeaderText(headerText);
        alert.setContentText(contentMessage);
        alert.setGraphic(new ImageView(this.getClass().getResource("/images/successful.png").toString()));
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.out.println("BUTTON CLIKER kjdghvhjksdgvjhsdfghjv"));
    }

    /**
     * Crypter mot de passe
     * @param mot_de_passe
     */
    public String crypterMotDePasse(String mot_de_passe){
        try{
            MessageDigest digestor = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digestor.digest(mot_de_passe.getBytes(StandardCharsets.UTF_8));
            StringBuilder encryptionValue = new StringBuilder(2 * encodedhash.length);
            for (int i = 0; i < encodedhash.length; i++) {
                String hexVal = Integer.toHexString(0xff & encodedhash[i]);
                if (hexVal.length() == 1) {
                    encryptionValue.append('0');
                }
                encryptionValue.append(hexVal);
            }
            return encryptionValue.toString();
        } catch (NoSuchAlgorithmException e) {
            return e.getMessage();
        }
    }
}
