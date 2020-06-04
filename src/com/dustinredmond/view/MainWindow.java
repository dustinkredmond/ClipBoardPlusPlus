package com.dustinredmond.view;

import com.dustinredmond.controller.MainMenuController;
import com.dustinredmond.controller.MainWindowController;
import com.dustinredmond.controller.ObjectTable;
import com.dustinredmond.i18n.I18N;
import com.dustinredmond.model.Clip;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.HashMap;

public class MainWindow implements Window {
    @Override
    public void show() {

        Stage stage = UI.getStage();
        stage.getIcons().add(new Image("icons8-clipboard-64.png"));
        stage.setTitle(I18N.get("application.title"));

        BorderPane root = new BorderPane();
        GridPane grid = new GridPane();
        root.setCenter(grid);
        root.setTop(getMainMenu());
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);

        Scene scene = new Scene(root, 600, 400);
        createControls(grid, scene);

        stage.setScene(scene);
        stage.show();
    }

    private void createControls(GridPane grid, Scene scene) {

        Button buttonNew = new Button("_"+I18N.get("option.new")); // add underscore for mnemonic parsing
        Button buttonEdit = new Button("_"+I18N.get("option.edit"));
        Button buttonDelete = new Button("_"+I18N.get("option.delete"));
        for (Button button : Arrays.asList(buttonNew, buttonEdit, buttonDelete)) {
            // enable mnemonic parsing e.g. (Alt + N) triggers buttonNew
            button.setMnemonicParsing(true);
        }

        HBox buttonBox = new HBox(10, buttonNew, buttonEdit, buttonDelete);
        grid.add(buttonBox, 0, 0);

// TODO: Add Search Field
//        TextField textFieldSearch = new TextField();
//        textFieldSearch.setPromptText("Search...");
//        grid.add(textFieldSearch, 0, 1);

        table.excludeColumn("SDF"); // property SimpleDateFormat (not needed in model)
        table.setPlaceholder(new Label(I18N.get("table.empty")));
        table.setFixedCellSize(25);
        table.applyColumnNameMapping(getColumnMapping());
        ObservableList<Clip> savedData = getInitialItems();

// TODO: Add search capability
//        FilteredList<Clip> filteredList = new FilteredList<>(savedData, clip -> true);
//        textFieldSearch.textProperty().addListener(((observable, oldValue, newValue) -> {
//            filteredList.setPredicate(clip -> {
//                if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//                String filterText = newValue.toLowerCase();
//                if (clip.getNotes().toLowerCase().contains(filterText)) {
//                    return true;
//                } else if (clip.getClip().toLowerCase().contains(filterText)) {
//                    return true;
//                }
//                return false; // no matching predicate
//            });
//        }));
//        SortedList<Clip> sortedList = new SortedList<>(filteredList);
//        sortedList.comparatorProperty().bind(table.comparatorProperty());
// NOTE SortedList will throw Exception when additional data is added
//      Maybe try table.setItems() each time, fairly large refactor
        table.setItems(savedData);

        controller.applyCopyAction(scene);
        controller.applyPasteAction(scene);
        controller.applyEditAction();
        controller.applyContextMenu();
        controller.applyClipboardPolling();
        buttonDelete.setOnAction(e -> controller.handleDelete(table));
        buttonEdit.setOnAction(e -> new EditWindow().show());
        buttonNew.setOnAction(e -> new CreateNewWindow().show());

        GridPane.setHgrow(table, Priority.ALWAYS);
        GridPane.setVgrow(table, Priority.ALWAYS);

        grid.add(table, 0, 1);
    }

    private ObservableList<Clip> getInitialItems() {

        ObservableList<Clip> initialItems = FXCollections.observableArrayList();

        try {
            FileInputStream fis = new FileInputStream("clipboard++.history");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Clip c;
            while ((c = (Clip) ois.readObject()) != null) {
                initialItems.add(c);
            }
        } catch (IOException | ClassNotFoundException ignored) {
            // no big deal if we can't recover previous objects/history
        }
        return initialItems;
    }

    private HashMap<String,String> getColumnMapping() {
        HashMap<String,String> map = new HashMap<>();
        // original column names (from object properties), new column names from I18N
        map.put("created",I18N.get("table.created"));
        map.put("clip",I18N.get("table.clip"));
        map.put("notes",I18N.get("table.notes"));
        return map;
    }

    private MenuBar getMainMenu() {
        MenuBar menuBar = new MenuBar();

        Menu menuOptions = new Menu(I18N.get("menu.options"));
        CheckMenuItem miAlwaysOnTop = new CheckMenuItem(I18N.get("mi.alwaysOnTop"));
        miAlwaysOnTop.setOnAction(e -> UI.getStage().setAlwaysOnTop(miAlwaysOnTop.isSelected()));
        menuOptions.getItems().add(miAlwaysOnTop);
        menuBar.getMenus().add(menuOptions);

        Menu menuAbout = new Menu(I18N.get("menu.about"));
        MenuItem menuItemAbout = new MenuItem(I18N.get("mi.about"));
        menuItemAbout.setOnAction(e -> new MainMenuController().showAboutInfo());
        MenuItem menuItemHelp = new MenuItem(I18N.get("mi.help"));
        menuItemHelp.setOnAction(e -> new MainMenuController().showHelpInfo());
        menuAbout.getItems().addAll(menuItemAbout, menuItemHelp);

        menuBar.getMenus().add(menuAbout);

        return menuBar;
    }

    public static ObjectTable<Clip> getTable() { return table; }
    private static final ObjectTable<Clip> table = new ObjectTable<>(Clip.class);
    private final MainWindowController controller = new MainWindowController();
}
