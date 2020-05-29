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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class GUI extends Application implements ViewObservable, ViewObserver {
    private ViewObserver client;
    private Stage stage;
    private GUIObservable controller;
    private boolean gameEnded;
    private List<String> godPowerNames;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to play with the GUI or CLI? (Default: GUI)");
        String answer = scanner.next();
        if (answer.equalsIgnoreCase("CLI")) {
            ViewObservable view = new CLI();
            Client client = new Client(view, true);
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
        Client client = new Client(this, false);
        Thread clientThread = new Thread(client);
        this.subscribe(client);
        this.stage = stage;
        clientThread.start();

        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/IPAddressScene.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        IPAddressSceneController controller = loader.getController();
        controller.subscribe(this);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Santorini");
        stage.getIcons().add(new Image("img/Icon.png"));
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

    public void restartFromNumOfPlayersScene() {
        Scene scene = loadScene("fxml/NumOfPlayersScene.fxml");

        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.show();
        });
    }

    @Override
    public void updateIPAddress(String ip) {
        client.updateIPAddress(ip);
    }

    @Override
    public void setConnectionMessage(String s) {
        Scene scene = loadScene("fxml/NumOfPlayersScene.fxml");

        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.show();
            ((NumOfPlayersSceneController) controller).setConnectionMessage(s);
        });
    }

    @Override
    public void askNumOfPlayers(String question) {
        ((NumOfPlayersSceneController) controller).askNumOfPlayers();
    }

    public void updateNumOfPlayers(int numOfPlayers) {
        client.updateNumOfPlayers(numOfPlayers);
    }

    @Override
    public void askName(String question) {
        Scene scene = loadScene("fxml/NameScene.fxml");

        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.show();
            ((NameSceneController) controller).setQuestion(question);
        });
    }

    public void updateName(String name) {
        client.updateName(name);
        stage.setTitle("Santorini " + name);
    }

    @Override
    public void askAllGodPowers(String playerName, int numOfPlayers, List<String> godPowerNames) {
        Scene scene = loadScene("fxml/AllGodPowersScene.fxml");

        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.show();
            ((AllGodPowersSceneController) controller).setQuestion(playerName, numOfPlayers);
        });
    }

    public void updateAllGodPower(List<Integer> selectedIndexes) {
        client.updateAllGodPower(selectedIndexes);
    }

    @Override
    public void askGodPower(String playerName, List<String> godPowerNames) {
        Scene scene = loadScene("fxml/GodPowerScene.fxml");

        this.godPowerNames = godPowerNames;
        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.show();
            ((GodPowerSceneController) controller).setQuestion(playerName, godPowerNames);
        });
    }

    public void updateGodPower(int selectedIndex) {
        client.updateGodPower(selectedIndex);
        stage.setTitle(stage.getTitle() + " " + godPowerNames.get(selectedIndex - 1));
    }

    @Override
    public void tellAssignedGodPower(String playerName, List<String> godPowerNames) {
        this.godPowerNames = godPowerNames;
        Platform.runLater(() -> {
            stage.setTitle(stage.getTitle() + " " + godPowerNames.get(0));
        });
    }

    @Override
    public void showPlayersGodPowers(List<String> playerNames, List<String> godPowerNames) {
        Scene scene = loadScene("fxml/BoardScene.fxml");

        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.show();
            ((BoardSceneController) controller).showPlayersGodPowers(playerNames, godPowerNames);
        });
    }

    @Override
    public void askFirstPlayer(List<String> playerNames) {
        Scene scene = loadScene("fxml/FirstPlayerScene.fxml");

        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.show();
            ((FirstPlayerSceneController) controller).askFirstPlayer(playerNames, godPowerNames);
        });
    }

    @Override
    public void updateFirstPlayer(int firstPlayerIndex) {
        client.updateFirstPlayer(firstPlayerIndex);
    }

    @Override
    public void showBoard(SpaceCopy[][] board) {
        ((BoardSceneController) controller).showBoard(board);
    }

    @Override
    public void askWorkerPosition(String playerName, int workerNumber, int previousPos, SpaceCopy[][] board) {
        Platform.runLater(() -> {
            ((BoardSceneController) controller).askWorkerPosition(playerName, workerNumber, previousPos, board);
        });
    }

    @Override
    public void updateWorkerPosition(int pos) {
        client.updateWorkerPosition(pos);
    }

    @Override
    public void askWorkerMovement(String playerName, List<SpaceCopy> validMovementSpacesW1, List<SpaceCopy> validMovementSpacesW2) {
        Platform.runLater(() -> {
            ((BoardSceneController) controller).askWorkerMovement(playerName, validMovementSpacesW1, validMovementSpacesW2);
        });
    }

    @Override
    public void updateWorkerMovement(int[] workerAndSpace) {
        client.updateWorkerMovement(workerAndSpace);
    }

    @Override
    public void askBuildingSpace(String playerName, List<SpaceCopy> validBuildingSpaces) {
        Platform.runLater(() -> {
            ((BoardSceneController) controller).askBuildingSpace(playerName, validBuildingSpaces);
        });
    }

    @Override
    public void updateBuildingSpace(int chosenBuildingSpace) {
        client.updateBuildingSpace(chosenBuildingSpace);
    }

    @Override
    public void askAtlasBuild(String playerName, List<SpaceCopy> validBuildingSpaces) {
        Platform.runLater(() -> {
            ((BoardSceneController) controller).askAtlasBuild(playerName, validBuildingSpaces);
        });
    }

    @Override
    public void updateAtlasBuild(int[] selectedSpaceAndBuildDome) {
        client.updateAtlasBuild(selectedSpaceAndBuildDome);
    }

    @Override
    public void askBuildBeforeMovePrometheus(String playerName, boolean w1CanMove, boolean w1CanBuild, boolean w2CanMove, boolean w2CanBuild) {
        Platform.runLater(() -> {
            ((BoardSceneController) controller).askBuildBeforeMovePrometheus(playerName, w1CanMove, w1CanBuild, w2CanMove, w2CanBuild);
        });
    }

    @Override
    public void updateBuildBeforeMovePrometheus(int[] workerAndBuildBeforeMove) {
        client.updateBuildBeforeMovePrometheus(workerAndBuildBeforeMove);
    }

    @Override
    public void askWorkerMovementPrometheus(String playerName, List<SpaceCopy> validMovementSpaces) {
        Platform.runLater(() -> {
            ((BoardSceneController) controller).askWorkerMovementPrometheus(playerName, validMovementSpaces);
        });
    }

    @Override
    public void updateWorkerMovementPrometheus(int chosenMovementSpace) {
        client.updateWorkerMovementPrometheus(chosenMovementSpace);
    }

    @Override
    public void askArtemisSecondMove(String playerName, List<SpaceCopy> validSecondMovementSpaces) {
        Platform.runLater(() -> {
            ((BoardSceneController) controller).askArtemisSecondMove(playerName, validSecondMovementSpaces);
        });
    }

    @Override
    public void updateArtemisSecondMove(int artemisSecondMoveSpace) {
        client.updateArtemisSecondMove(artemisSecondMoveSpace);
    }

    @Override
    public void askDemeterSecondBuilding(String playerName, List<SpaceCopy> validBuildingSpaces) {
        Platform.runLater(() -> {
            ((BoardSceneController) controller).askDemeterSecondBuilding(playerName, validBuildingSpaces);
        });
    }

    @Override
    public void askHephaestusBuild(String playerName, List<SpaceCopy> validBuildingSpaces) {
        Platform.runLater(() -> {
            ((BoardSceneController) controller).askHephaestusBuild(playerName, validBuildingSpaces);
        });
    }

    @Override
    public void updateHephaestusBuild(int[] spaceAndDoubleBuildingHephaestus) {
        client.updateHephaestusBuild(spaceAndDoubleBuildingHephaestus);
    }

    @Override
    public void askRemoveBlockAres(String playerName, List<SpaceCopy> validRemoveSpaces, int nonSelectedWorkerNumber) {
        Platform.runLater(() -> {
            ((BoardSceneController) controller).askRemoveBlockAres(playerName, validRemoveSpaces, nonSelectedWorkerNumber);
        });
    }

    @Override
    public void announceVictory(String playerName) {
        gameEnded = true;
        Platform.runLater(() -> {
            ((BoardSceneController) controller).announceVictory(playerName);
        });
    }

    @Override
    public void announceLose(String playerName) {
        gameEnded = true;
        Platform.runLater(() -> {
            ((BoardSceneController) controller).announceLose(playerName);
        });
    }

    @Override
    public void manageServerDisconnection() {
        if (!gameEnded) {
            Scene scene = loadScene("fxml/DisconnectionScene.fxml");

            Platform.runLater(() -> {
                Stage window = new Stage();
                window.setScene(scene);
                window.setResizable(false);
                window.show();
                ((DisconnectionSceneController) controller).setWindow(window);
            });
        }
    }

    public void playAgain(boolean b) {
        client.playAgain(b);
        gameEnded = false;
        if (!b)
            stage.close();
    }
}
