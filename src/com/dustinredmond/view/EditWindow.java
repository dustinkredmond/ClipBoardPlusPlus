package com.dustinredmond.view;

import com.dustinredmond.controller.ObjectTable;
import com.dustinredmond.i18n.I18N;
import com.dustinredmond.model.Clip;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class EditWindow implements Window {
    @Override
    public void show() {
        ObjectTable<Clip> table = MainWindow.getTable();

        if (table.getSelectionModel().isEmpty()) {
            // Can't edit with no selection, thus return
            // handle here to avoid .size check in controllers
            return;
        }

        Stage stage = new Stage();
        stage.getIcons().add(new Image("icons8-clipboard-64.png"));
        stage.setTitle(I18N.get("application.title"));

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(5);
        grid.setVgap(5);

        Clip currentClip = table.getSelectionModel().getSelectedItem();

        TextField tfCreated = new TextField(currentClip.getCreated());
        tfCreated.setDisable(true);
        grid.add(new Label(I18N.get("edit.created")), 0, 0);
        grid.add(tfCreated, 1, 0);

        TextArea taClipboardContents = new TextArea(currentClip.getClip());
        grid.add(new Label(I18N.get("edit.contents")), 0, 1);
        grid.add(taClipboardContents, 1, 1);

        TextArea taNotes = new TextArea(currentClip.getNotes());
        grid.add(new Label(I18N.get("edit.notes")), 0, 2);
        grid.add(taNotes, 1, 2);

        Button buttonSave = new Button(I18N.get("edit.save"));
        grid.add(buttonSave, 0, 3);

        buttonSave.setOnAction(e -> {
            currentClip.setNotes(taNotes.getText());
            currentClip.setClip(taClipboardContents.getText());
            table.refresh(); // must force update for fields to change
            stage.hide();
        });

        GridPane.setVgrow(taClipboardContents, Priority.ALWAYS);
        GridPane.setHgrow(taClipboardContents, Priority.ALWAYS);

        stage.setScene(new Scene(grid));
        stage.show();

    }
}
