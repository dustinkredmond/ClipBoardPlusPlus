package com.dustinredmond.view;

import com.dustinredmond.controller.ObjectTable;
import com.dustinredmond.i18n.I18N;
import com.dustinredmond.model.Clip;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

public class UI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        UI.stage = stage;
        stage.getIcons().add(new Image("icons8-clipboard-64.png"));
        new MainWindow().show();
        Preferences.userRoot().remove("ClipBoard++"); // uncomment to test first run behavior
        showDialogOnFirstRun();
    }

    @Override
    public void stop() throws Exception {
        ObjectTable<Clip> table = MainWindow.getTable();
        if (table != null && table.getItems().size() > 0) {
            try {
                FileOutputStream fos = new FileOutputStream("clipboard++.history");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                for (Clip c: table.getItems()) {
                    oos.writeObject(c);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Files.deleteIfExists(Paths.get("clipboard++.history"));
        }
    }

    private void showDialogOnFirstRun() {
        if (Preferences.userRoot().get("ClipBoard++","").isEmpty()) { // checks if application is first use
            Stage stage = new Stage();
            stage.getIcons().add(new Image("icons8-clipboard-64.png"));
            stage.requestFocus();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(I18N.get("application.title"));
            GridPane grid = new GridPane();
            grid.setHgap(5);
            grid.setVgap(25);
            grid.setPadding(new Insets(25));
            grid.add(new Text(I18N.get("applicaton.greeting")), 0, 0);
            grid.add(new Text(I18N.get("application.welcome")), 0, 1);
            stage.setScene(new Scene(grid));
            stage.sizeToScene();
            stage.show();
            Preferences.userRoot().put("ClipBoard++", "launched");
        }
    }

    public void startUi(String[] args) {
        Application.launch(args);
    }

    public static Stage getStage() {
        return UI.stage;
    }
    private static Stage stage;
}
