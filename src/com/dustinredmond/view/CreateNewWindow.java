package com.dustinredmond.view;

import com.dustinredmond.controller.CreateNewWindowController;
import com.dustinredmond.i18n.I18N;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CreateNewWindow implements Window {
    @Override
    public void show() {
        Stage stage = new Stage();
        stage.getIcons().add(new Image("icons8-clipboard-64.png"));
        stage.setTitle(I18N.get("application.title"));

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);

        TextArea taClip = new TextArea(Clipboard.getSystemClipboard().getString());
        TextArea taNotes = new TextArea();

        grid.add(new Label(I18N.get("new.contents")), 0, 0);
        grid.add(taClip, 1, 0);

        grid.add(new Label(I18N.get("new.notes")), 0, 1);
        grid.add(taNotes, 1, 1);

        Button buttonSubmit = new Button(I18N.get("new.submit"));
        buttonSubmit.setOnAction(e -> {
                    new CreateNewWindowController().handleNew(taClip, taNotes);
                    stage.hide();
        });

        grid.add(buttonSubmit, 0, 2);

        stage.setScene(new Scene(grid));
        stage.show();
    }
}
