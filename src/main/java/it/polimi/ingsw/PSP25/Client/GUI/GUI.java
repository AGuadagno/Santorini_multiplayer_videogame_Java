package it.polimi.ingsw.PSP25.Client.GUI;

import it.polimi.ingsw.PSP25.Client.CLI;
import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.ViewObservable;
import it.polimi.ingsw.PSP25.Client.ViewObserver;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GUI extends Application implements ViewObservable, ViewObserver {
    private ViewObserver client;
    private Stage stage;
    private GUIObservable controller;

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("CLI")) {
            ViewObservable view = new CLI();
            Client client = new Client(view);
            view.subscribe(client);
            client.run();
        } else {
            launch();
        }
    }

    @Override
    public void subscribe(ViewObserver client) {
        this.client = client;
    }

    @Override
    public void askIPAddress() {
    }

    @Override
    public void start(Stage stage) {
        Client client = new Client(this);
        this.subscribe(client);
        Thread clientThread = new Thread(client);
        clientThread.start();

        this.stage = stage;

        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/Scene1.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene1Controller controller = loader.getController();
        controller.subscribe(this);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private Scene loadScene(String scenePath) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(scenePath));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller = loader.getController();
        controller.subscribe(this);
        Scene scene = new Scene(root);
        return scene;
    }

    @Override
    public void updateIPAddress(String ip) {
        client.updateIPAddress(ip);
    }

    @Override
    public void setConnectionMessage(String s) {
        Scene scene = loadScene("fxml/Scene2.fxml");

        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.show();
            ((Scene2Controller) controller).setConnectionMessage(s);
        });
    }

    @Override
    public void askNumOfPlayers(String question) {
        ((Scene2Controller) controller).askNumOfPlayers();
    }

    public void updateNumOfPlayers(int numOfPlayers) {
        client.updateNumOfPlayers(numOfPlayers);
    }

    @Override
    public void askName(String question) {
        Scene scene = loadScene("fxml/Scene3.fxml");

        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.show();
            ((Scene3Controller) controller).setQuestion(question);
        });
    }

    public void updateName(String name) {
        client.updateName(name);
    }

    @Override
    public void askAllGodPowers(String playerName, int numOfPlayers, List<String> godPowerNames) {
        Scene scene = loadScene("fxml/Scene4.fxml");

        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.show();
            ((Scene4Controller) controller).setQuestion(playerName, numOfPlayers, godPowerNames);
        });
    }
}
