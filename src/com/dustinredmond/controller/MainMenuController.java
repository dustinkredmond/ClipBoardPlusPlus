package com.dustinredmond.controller;

import com.dustinredmond.i18n.I18N;
import javafx.scene.control.Alert;

public class MainMenuController {

    public void showAboutInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(I18N.get("mi.about"));
        alert.setHeaderText(I18N.get("application.title"));
        alert.setContentText(I18N.get("mi.about.content"));
        alert.showAndWait();
    }

    public void showHelpInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(I18N.get("application.title"));
        alert.setHeaderText(I18N.get("application.title"));
        alert.setContentText(I18N.get("mi.help.content"));
        alert.showAndWait();
    }
}
