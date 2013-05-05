package de.zillmann.javafx.aqua.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.stage.Stage;
import de.zillmann.javafx.aqua.AquaFx;

public class SplitPaneSample extends Application {

    @Override public void start(Stage stage) {
        HBox hbox = HBoxBuilder.create().padding(new Insets(20)).spacing(10).build();

        SplitPane splitPane1 = new SplitPane();
        splitPane1.setOrientation(Orientation.VERTICAL);
        splitPane1.setPrefSize(200, 200);
        final Label l1 = new Label("Top Label");
        final Label r1 = new Label("Bottom Label");
        splitPane1.getItems().addAll(l1, r1);
         
        SplitPane splitPane2 = new SplitPane();
        splitPane2.setOrientation(Orientation.HORIZONTAL);
        splitPane2.setPrefSize(400, 200);
        final Label c2 = new Label("Center Label");
        final Label r2 = new Label("Right Label");
        splitPane2.getItems().addAll(splitPane1, c2, r2);
        hbox.getChildren().add(splitPane2);

        Scene scene = new Scene(hbox);
        stage.setScene(scene);
        AquaFx.style();

        stage.setTitle("SplitPane");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}