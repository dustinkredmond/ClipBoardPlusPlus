package com.dustinredmond.controller;

import com.dustinredmond.i18n.I18N;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainMenuController {

    public void showAboutInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setGraphic(new ImageView("icons8-clipboard-64.png"));
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icons8-clipboard-64.png"));
        alert.setTitle(I18N.get("mi.about"));
        alert.setHeaderText(I18N.get("application.title"));
        alert.setContentText(I18N.get("mi.about.content"));
        alert.showAndWait();
    }

    public void showHelpInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setGraphic(new ImageView("icons8-clipboard-64.png"));
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icons8-clipboard-64.png"));
        alert.setTitle(I18N.get("application.title"));
        alert.setHeaderText(I18N.get("application.title"));
        alert.setContentText(I18N.get("mi.help.content"));
        alert.showAndWait();
    }

}
