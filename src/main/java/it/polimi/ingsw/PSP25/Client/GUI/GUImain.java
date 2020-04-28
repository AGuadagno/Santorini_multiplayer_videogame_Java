package it.polimi.ingsw.PSP25.Client.GUI;

import it.polimi.ingsw.PSP25.Client.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class GUImain extends Application implements Runnable {

    public Label helloWorld;

    @Override
    public void run() {
        launch();
    }

    @Override
    /*public void start(Stage primaryStage) {
        Label l = new Label("Hello world!");
        l.setAlignment(Pos.CENTER);

        AnchorPane.setBottomAnchor(l,30.0);
        AnchorPane.setTopAnchor(l,30.0);
        AnchorPane.setLeftAnchor(l,30.0);
        AnchorPane.setRightAnchor(l,30.0);
        AnchorPane pane = new AnchorPane(l);

        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }*/

    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
