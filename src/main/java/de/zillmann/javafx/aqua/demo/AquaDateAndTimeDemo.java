package de.zillmann.javafx.aqua.demo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuItemBuilder;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import de.zillmann.javafx.aqua.AquaFx;

public class AquaDateAndTimeDemo extends Application {
    @Override public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.UNIFIED);
        stage.setResizable(false);

        BorderPane pane = new BorderPane();

        ToolBar toolBar = new ToolBar();

        Button tbBack = ButtonBuilder.create().text("<").styleClass("left-pill").build();
        Button tbForward = ButtonBuilder.create().text(">").styleClass("right-pill").build();
        HBox separator = new HBox();
        separator.setPrefSize(15, 1);
        Button btnAll = new Button("Alle einblenden");
        toolBar.getItems().addAll(tbBack, tbForward, separator, btnAll);

        pane.setTop(toolBar);

        /**
         * TabPane for Content
         */
        TabPane contentTabPane = new TabPane();
        contentTabPane.setPadding(new Insets(25, 15, 15, 15));
        Tab tabDate = new Tab();
        tabDate.setText("Datum & Uhrzeit");
        contentTabPane.getTabs().add(tabDate);
        Tab tabTimezone = new Tab();
        tabTimezone.setText("Zeitzone");
        contentTabPane.getTabs().add(tabTimezone);
        Tab tabClock = new Tab();
        tabClock.setText("Uhr");

        /*
         * The Clock-Content
         */

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(25, 30, 0, 60));

        CheckBox box1 = new CheckBox("Datum und Uhrzeit in der Menüleiste anzeigen");
        box1.setSelected(true);
        grid.add(box1, 0, 0, 3, 1);

        Label labelTime = new Label("Zeitoptionen:");
        ToggleGroup group = new ToggleGroup();
        RadioButton radioButtonDigital = new RadioButton("Digital");
        radioButtonDigital.setToggleGroup(group);
        radioButtonDigital.setSelected(true);
        RadioButton radioButtonAnalog = new RadioButton("Analog");
        radioButtonAnalog.setToggleGroup(group);
        grid.addRow(2, labelTime, radioButtonDigital, radioButtonAnalog);
        GridPane.setHalignment(labelTime, HPos.RIGHT);
        GridPane.setMargin(radioButtonAnalog, new Insets(0, 0, 0, 20));
        
        CheckBox box2 = new CheckBox("Uhrzeit mit Sekunden anzeigen");
        grid.add(box2, 1, 3, 2, 1);
        CheckBox box3 = new CheckBox("Blinkende Trennzeichen");
        grid.add(box3, 1, 4, 2, 1);
        CheckBox box4 = new CheckBox("24 Stunden verwenden");
        box4.setSelected(true);
        grid.add(box4, 1, 5, 2, 1);
        CheckBox box5 = new CheckBox("Uhrzeit mit Suffix anzeigen");
        box5.setDisable(true);
        grid.add(box5, 1, 6, 2, 1);

        Label labelDate = new Label("Datumsoptionen:");
        grid.add(labelDate, 0, 8);
        GridPane.setHalignment(labelDate, HPos.RIGHT);
        GridPane.setMargin(labelDate, new Insets(0, 0, 0, 40));
        CheckBox box6 = new CheckBox("Wochentag anzeigen");
        box6.setSelected(true);
        grid.add(box6, 1, 8, 2, 1);
        CheckBox box7 = new CheckBox("Datum anzeigen");
        grid.add(box7, 1, 9, 2, 1);

        HBox hbox = HBoxBuilder.create().spacing(5).build();
        CheckBox box8 = new CheckBox("Zeit vorlesen:");
        ChoiceBox<String> choices = new ChoiceBox<String>(FXCollections.observableArrayList("Zur vollen Stunde",
                "Zur halben Stunde", "Zur Viertelstunde"));
        choices.setDisable(true);
        choices.getSelectionModel().selectFirst();
        Button abjustVoice = new Button();
        abjustVoice.setText("Stimme anpassen ...");
        abjustVoice.setDisable(true);
        hbox.getChildren().addAll(box8, choices, abjustVoice);
        grid.add(hbox, 0, 14, 3, 1);

        tabClock.setContent(grid);
        /*
         * Content finished.. add it to Tab
         */

        contentTabPane.getTabs().add(tabClock);
        contentTabPane.getSelectionModel().select(tabClock);
        pane.setCenter(contentTabPane);

        /*
         * Footer
         */
        HBox footerBox = HBoxBuilder.create().spacing(5).alignment(Pos.BOTTOM_LEFT).build();
        Image image = new Image(AquaFx.class.getResource("demo/images/lock_open.png").toExternalForm());
        ImageView lockView = ImageViewBuilder.create().image(image).preserveRatio(true).fitHeight(36).build();
        footerBox.getChildren().add(lockView);
        Label info = new Label("Zum Schützen auf das Schloss klicken.");
        info.setFont(new Font(12));
        info.setPadding(new Insets(0, 0, 3, 0));
        footerBox.getChildren().add(info);
        
        AnchorPane anchorpane = new AnchorPane();
        Button helpBtn = new Button("?");
        anchorpane.getChildren().addAll(footerBox, helpBtn); 
        AnchorPane.setBottomAnchor(footerBox, 15.0);
        AnchorPane.setLeftAnchor(footerBox, 17.0);
        AnchorPane.setBottomAnchor(helpBtn, 15.0);
        AnchorPane.setRightAnchor(helpBtn, 15.0);
        
        pane.setBottom(anchorpane);

        /*
         * MenuBar
         */
        MenuBar menuBar = new MenuBar();
        Menu menuSystemPreferences = new Menu("Systemeinstellungen");
        Menu miDienste = new Menu("Dienste");
        MenuItem noService = MenuItemBuilder.create().disable(true).text("Keine Dienste Verfügbar").build();
        MenuItem servPrfe = MenuItemBuilder.create().text("Dienste Enistellungen ...").build();
        miDienste.getItems().addAll(noService, servPrfe);
        MenuItem ausblenden = MenuItemBuilder.create().text("Sytemeinstellungen ausblenden").accelerator(
                KeyCombination.keyCombination("shortcut+H")).build();
        MenuItem andereAusblenden = MenuItemBuilder.create().text("Andere ausblenden").accelerator(
                KeyCombination.keyCombination("Alt+shortcut+H")).build();
        MenuItem einlenden = MenuItemBuilder.create().text("Alle einblenden").disable(true).build();
        menuSystemPreferences.getItems().addAll(new MenuItem("Über Systemeinstellungen"), new SeparatorMenuItem(), miDienste,
                new SeparatorMenuItem(), ausblenden, andereAusblenden, einlenden, new MenuItem("Systemeinstellungen beenden"));

        Menu menuEdit = new Menu("Bearbeiten");
        menuEdit.getItems().addAll(new MenuItem("..."));

        Menu menuPreferences = new Menu("Einstellungen");
        menuPreferences.getItems().addAll(new MenuItem("..."));

        Menu menuWindow = new Menu("Fenster");
        MenuItem windowClose = MenuItemBuilder.create().text("Schließen").accelerator(KeyCombination.keyCombination("shortcut+W")).build();
        MenuItem windowDock = MenuItemBuilder.create().text("Im Dock ablegen").accelerator(
                KeyCombination.keyCombination("shortcut+M")).build();
        CheckMenuItem actual = new CheckMenuItem("Datum & Uhrzeit");
        actual.setSelected(true);
        menuWindow.getItems().addAll(windowClose, windowDock, new SeparatorMenuItem(), actual);

        Menu menuHelp = new Menu("Hilfe");
        MenuItem help = MenuItemBuilder.create().text("Systemeinstellungen-Hilfe").accelerator(
                KeyCombination.keyCombination("shortcut+?")).build();
        menuHelp.getItems().addAll(new MenuItem("Schließen"), help);

        menuBar.getMenus().addAll(menuSystemPreferences, menuEdit, menuPreferences, menuWindow, menuHelp);
        pane.getChildren().add(menuBar);

        Scene myScene = new Scene(pane, 667, 563);
        AquaFx.style();
        stage.setTitle("AquaFX");
        stage.setScene(myScene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
