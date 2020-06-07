package com.dustinredmond.controller;

import com.dustinredmond.i18n.I18N;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainMenuController {

    public void showAboutInfo() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(I18N.get("mi.about"));
        stage.getIcons().add(new Image(ICON));
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10));
        grid.add(new Text(I18N.get("application.title")+"\n"), 0, 0);
        grid.add(new Text(I18N.get("mi.about.content")), 0, 1);
        stage.setScene(new Scene(grid));
        stage.sizeToScene();
        stage.show();
    }

    public void showHelpInfo() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(I18N.get("application.title"));
        stage.getIcons().add(new Image(ICON));
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10));
        grid.add(new Text(I18N.get("mi.help.content")), 0, 0);
        stage.setScene(new Scene(grid));
        stage.sizeToScene();
        stage.show();
    }

    private static final String ICON = "icons8-clipboard-64.png";

}
