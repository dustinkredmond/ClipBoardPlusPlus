package com.dustinredmond.controller;

import com.dustinredmond.model.Clip;
import com.dustinredmond.view.MainWindow;
import javafx.scene.control.TextArea;

public class CreateNewWindowController {
    
    public void handleNew(TextArea taClip, TextArea taNotes) {
        ObjectTable<Clip> table = MainWindow.getTable();

        Clip clip = new Clip();
        clip.setClip(taClip.getText());
        clip.setNotes(taNotes.getText());

        table.getItems().add(clip);
    }

}
