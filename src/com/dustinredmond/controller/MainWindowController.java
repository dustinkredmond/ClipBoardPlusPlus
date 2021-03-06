package com.dustinredmond.controller;

import com.dustinredmond.i18n.I18N;
import com.dustinredmond.model.Clip;
import com.dustinredmond.view.EditWindow;
import com.dustinredmond.view.MainWindow;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;

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
        // Since we implemented copy, let's implement paste as well
        // ClipBoard++ should have already added whatever was copy, but
        // user may want to paste something on application open that wasn't
        // in the ClipBoard++ table
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
            // if double click and table item is selected
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
                    // otherwise multiple context menus can be opened
                    // by multiple right-click actions
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

    public void applyClipboardPolling() {
        ObjectTable<Clip> table = MainWindow.getTable();
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        new com.sun.glass.ui.ClipboardAssistance(com.sun.glass.ui.Clipboard.SYSTEM) {
            @Override
            public void contentChanged() {
                // called every time system clipboard is changed
                // if the clipboard has a new String, add it to the TableView
                if (clipboard.hasString()) {
                    String toBeCopied = clipboard.getString();
                    // If the user hasn't selected anything in the table, we can safely add item
                    //
                    // If the user has selected something, and the selected clip differs from
                    // the String being copied, then we can add it.
                    if (table.getSelectionModel().isEmpty()
                            || !table.getSelectionModel().getSelectedItem().getClip().equals(toBeCopied)) {
                        Clip clip = new Clip();
                        clip.setClip(clipboard.getString());
                        Clip lastCopied = (table.getItems().size() > 1) ? table.getItems().get(table.getItems().size() - 1) : null;
                        if (lastCopied != null && lastCopied.getClip().equals(toBeCopied)) {
                            // sometimes the contentChanged() method is called without the
                            // content actually having changed, don't add in this case
                            return;
                        }
                        table.getItems().add(clip);
                    }
                }
            }
        };
    }

    // TODO: Does this hold true for MacOS??? Is there a KeyCode.COMMAND_ANY
    // or something similar for alternate keyboard/OS
    private static final KeyCodeCombination copyCombination = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);

    private static final KeyCodeCombination pasteCombination = new KeyCodeCombination(KeyCode.V, KeyCodeCombination.CONTROL_ANY);
}
