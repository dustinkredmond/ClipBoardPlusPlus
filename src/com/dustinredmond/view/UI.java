package com.dustinredmond.view;

import com.dustinredmond.controller.ObjectTable;
import com.dustinredmond.i18n.I18N;
import com.dustinredmond.model.Clip;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        //Preferences.userRoot().remove("ClipBoard++"); // uncomment to test first run behavior
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setGraphic(new ImageView("icons8-clipboard-64.png"));
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icons8-clipboard-64.png"));
            alert.setHeaderText(I18N.get("application.greeting"));
            alert.setTitle(I18N.get("application.title"));
            alert.setContentText(I18N.get("application.welcome"));
            alert.show();
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
