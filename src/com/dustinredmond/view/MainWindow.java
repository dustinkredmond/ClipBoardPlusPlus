package com.dustinredmond.view;

import com.dustinredmond.controller.MainMenuController;
import com.dustinredmond.controller.MainWindowController;
import com.dustinredmond.controller.ObjectTable;
import com.dustinredmond.i18n.I18N;
import com.dustinredmond.model.Clip;
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

        Button buttonNew = new Button(I18N.get("option.new"));
        Button buttonEdit = new Button(I18N.get("option.edit"));
        Button buttonDelete = new Button(I18N.get("option.delete"));

        HBox buttonBox = new HBox(10, buttonNew, buttonEdit, buttonDelete);
        grid.add(buttonBox, 0, 0);

        table.excludeColumn("SDF"); // property SimpleDateFormat (not needed in model)
        table.setPlaceholder(new Label(I18N.get("table.empty")));
        table.setFixedCellSize(20);
        table.applyColumnNameMapping(getColumnMapping());
        initializeItems();

        controller.applyCopyAction(scene);
        controller.applyPasteAction(scene);
        controller.applyEditAction();
        controller.applyContextMenu();
        buttonDelete.setOnAction(e -> controller.handleDelete(table));
        buttonEdit.setOnAction(e -> new EditWindow().show());
        buttonNew.setOnAction(e -> new CreateNewWindow().show());

        GridPane.setHgrow(table, Priority.ALWAYS);
        GridPane.setVgrow(table, Priority.ALWAYS);

        grid.add(table, 0, 1);
    }

    private void initializeItems() {
        try {
            FileInputStream fis = new FileInputStream("clipboard++.history");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Clip c;
            while ((c = (Clip) ois.readObject()) != null) {
                table.getItems().add(c);
            }
        } catch (IOException | ClassNotFoundException ignored) {
            // no big deal if we can't recover previous objects/history
        }
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

        Menu menuAbout = new Menu("About");
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
