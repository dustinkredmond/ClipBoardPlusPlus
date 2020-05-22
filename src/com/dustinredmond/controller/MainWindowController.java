package com.dustinredmond.controller;

import com.dustinredmond.i18n.I18N;
import com.dustinredmond.model.Clip;
import com.dustinredmond.view.EditWindow;
import com.dustinredmond.view.MainWindow;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import javafx.stage.Stage;

public class MainWindowController {

    public void applyCopyAction(Scene scene) {
        ObjectTable<Clip> table = MainWindow.getTable();
        scene.getAccelerators().put(copyCombination, () -> {
            if (!table.getSelectionModel().isEmpty()) {
                ClipboardContent content = new ClipboardContent();
                content.putString(table.getSelectionModel().getSelectedItem().getClip());
                Clipboard.getSystemClipboard().setContent(content);
            }
        });
    }

    public void applyPasteAction(Scene scene) {
        ObjectTable<Clip> table = MainWindow.getTable();
        scene.getAccelerators().put(pasteCombination, () -> {
            String clipboardContents = Clipboard.getSystemClipboard().getString();
            if (!(clipboardContents == null || clipboardContents.isEmpty())) {
                Clip clip = new Clip();
                clip.setClip(clipboardContents);
                table.getItems().add(clip);
            }
        });
    }

    public void handleDelete(ObjectTable<Clip> table) {
        if (!table.getSelectionModel().isEmpty()) {
            table.getItems().remove(table.getSelectionModel().getSelectedItem());
        }
    }
    public void applyEditAction() {
        ObjectTable<Clip> table = MainWindow.getTable();
        table.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2 && !table.getSelectionModel().isEmpty()) { // double click
                new EditWindow().show();
            }
        });
    }

    public void applyContextMenu() {
        ObjectTable<Clip> table = MainWindow.getTable();
        ContextMenu cm = getTableContextMenu();
        table.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (!table.getSelectionModel().isEmpty()) {
                if (e.getButton().equals(MouseButton.SECONDARY)) {
                    if (!cm.isShowing()) {
                        cm.show(table, e.getScreenX(), e.getScreenY());
                    }
                } else {
                    cm.hide();
                }
            }
        });
    }

    private static ContextMenu getTableContextMenu() {
        ContextMenu cm = new ContextMenu();
        ObjectTable<Clip> table = MainWindow.getTable();
        MenuItem menuItemEdit = new MenuItem(I18N.get("title.edit"));
        menuItemEdit.setOnAction(e -> new EditWindow().show());
        MenuItem menuItemDelete = new MenuItem(I18N.get("title.delete"));
        menuItemDelete.setOnAction(e -> table.getItems().remove(table.getSelectionModel().getSelectedItem()));
        cm.getItems().addAll(menuItemEdit, menuItemDelete);
        return cm;
    }

    private static final KeyCodeCombination copyCombination = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);

    private static final KeyCodeCombination pasteCombination = new KeyCodeCombination(KeyCode.V, KeyCodeCombination.CONTROL_ANY);

}
