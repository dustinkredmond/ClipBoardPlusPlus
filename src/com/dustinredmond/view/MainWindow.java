package com.dustinredmond.view;

import com.dustinredmond.controller.MainMenuController;
import com.dustinredmond.controller.MainWindowController;
import com.dustinredmond.controller.ObjectTable;
import com.dustinredmond.i18n.I18N;
import com.dustinredmond.model.Clip;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class MainWindow implements Window {
    @Override
    public void show() {

        Stage stage = UI.getStage();
        stage.setOnCloseRequest(this::handleClose);
        stage.getIcons().add(new Image("icons8-clipboard-64.png"));
        stage.setTitle(I18N.get("application.title"));

        BorderPane root = new BorderPane();
        GridPane grid = new GridPane();
        root.setCenter(grid);
        root.setTop(getMainMenu(stage));
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);

        Scene scene = new Scene(root, 600, 400);
        createControls(grid, scene);

        stage.setScene(scene);
        stage.show();
    }

    private void handleClose(WindowEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, I18N.get("application.exit"));
        alert.setHeaderText(null);
        alert.setTitle(I18N.get("application.title"));
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icons8-clipboard-64.png"));
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get().equals(ButtonType.YES)) {
            Platform.exit();
        } else {
            e.consume();
        }
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

        table.excludeColumn("SDF"); // property SimpleDateFormat (not needed in model)
        table.setPlaceholder(new Label(I18N.get("table.empty")));
        table.setFixedCellSize(25);
        table.applyColumnNameMapping(getColumnMapping());
        ObservableList<Clip> savedData = getInitialItems();
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

    private MenuBar getMainMenu(Stage stage) {
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
