package it.polimi.ingsw.PSP25.Client.GUI;

import it.polimi.ingsw.PSP25.Client.CLI;
import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.ViewObservable;
import it.polimi.ingsw.PSP25.Client.ViewObserver;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

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
            ((Scene4Controller) controller).setQuestion(playerName, numOfPlayers);
        });
    }

    public void updateAllGodPower(List<Integer> selectedIndexes) {
        client.updateAllGodPower(selectedIndexes);
    }

    @Override
    public void askGodPower(String playerName, List<String> godPowerNames) {
        Scene scene = loadScene("fxml/Scene5.fxml");

        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.show();
            ((Scene5Controller) controller).setQuestion(playerName, godPowerNames);
        });
    }


    public void updateGodPower(int selectedIndex) {
        client.updateGodPower(selectedIndex);
    }


    @Override
    public void showPlayersGodPowers(List<String> playerNames, List<String> godPowerNames) {
        Scene scene = loadScene("fxml/Scene6.fxml");

        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.show();
            ((Scene6Controller) controller).showPlayersGodPowers(playerNames, godPowerNames);
        });
    }

    @Override
    public void showBoard(SpaceCopy[][] board) {
        ((Scene6Controller) controller).showBoard(board);
    }

    @Override
    public void askWorkerPosition(String playerName, int workerNumber, int previousPos, SpaceCopy[][] board) {
        Platform.runLater(() -> {
            ((Scene6Controller) controller).askWorkerPosition(playerName, workerNumber, previousPos, board);
        });
    }

    @Override
    public void updateWorkerPosition(int pos) {
        client.updateWorkerPosition(pos);
    }

    @Override
    public void askWorkerMovement(String playerName, List<SpaceCopy> validMovementSpacesW1, List<SpaceCopy> validMovementSpacesW2) {
        Platform.runLater(() -> {
            ((Scene6Controller) controller).askWorkerMovement(playerName, validMovementSpacesW1, validMovementSpacesW2);
        });
    }

    @Override
    public void updateWorkerMovement(int[] workerAndSpace) {
        client.updateWorkerMovement(workerAndSpace);
    }

    @Override
    public void askBuildingSpace(String playerName, List<SpaceCopy> validBuildingSpaces) {
        Platform.runLater(() -> {
            ((Scene6Controller) controller).askBuildingSpace(playerName, validBuildingSpaces);
        });
    }

    @Override
    public void updateBuildingSpace(int chosenBuildingSpace) {
        client.updateBuildingSpace(chosenBuildingSpace);
    }

}
