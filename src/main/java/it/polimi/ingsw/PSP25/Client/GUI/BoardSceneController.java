package it.polimi.ingsw.PSP25.Client.GUI;

import it.polimi.ingsw.PSP25.Client.ViewObserver;
import it.polimi.ingsw.PSP25.Model.Space;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static javafx.application.Application.launch;

public class BoardSceneController implements GUIObservable {

    private GUI gui;
    private Integer selectedIndex = null;
    private List<String> godPowerNames;
    private Button previousButton;
    private int previousPos;
    private SpaceCopy[][] board;
    private int buttonAction = 0;
    private int[] workerAndSpace = null;
    private String playerName;
    private List<SpaceCopy> validMovementSpacesW1;
    private List<SpaceCopy> validMovementSpacesW2;
    private int positioningWorker = 1;
    private List<SpaceCopy> validBuildingSpaces;
    private int[] spaceAndDome;
    private int[] workerAndBuildBeforeMove;
    private boolean w1CanBuild;
    private boolean w2CanBuild;
    private List<SpaceCopy> validSecondMovementSpaces;
    private Integer secondSpaceArtemis;
    private boolean messageLabelAppended;
    private int[] spaceAndDoubleBuildingHephaestus;
    private List<SpaceCopy> validRemoveSpaces;

    @FXML
    private Group towerLevels;
    @FXML
    private Group domes;
    @FXML
    private Group workers;
    @FXML
    private Group boardButtons;
    @FXML
    private Label messageLabel;
    @FXML
    private Button domeButton;
    @FXML
    private Button blockButton;
    @FXML
    private Button buildButton;
    @FXML
    private Button moveButton;
    @FXML
    private Button yesArtemisButton;
    @FXML
    private Button noArtemisButton;
    @FXML
    private Button yesDemeterButton;
    @FXML
    private Button noDemeterButton;
    @FXML
    private Button yesHephaestusButton;
    @FXML
    private Button noHephaestusButton;
    @FXML
    private Button yesAresButton;
    @FXML
    private Button noAresButton;
    @FXML
    private ImageView leftButtonImage;
    @FXML
    private ImageView rightButtonImage;
    @FXML
    private Ellipse workerBorder;
    @FXML
    private ImageView victoryWindowImage;
    @FXML
    private ImageView victoryCloseImage;
    @FXML
    private Label victoryLabel;
    @FXML
    private Button victoryCloseButton;
    @FXML
    private Button yesPlayAgainButton;
    @FXML
    private Button noPlayAgainButton;

    @FXML
    private ImageView background;
    @FXML
    private ImageView godPower1_2Players;
    @FXML
    private ImageView godPower2_2Players;
    @FXML
    private ImageView godPower1_3Players;
    @FXML
    private ImageView godPower2_3Players;
    @FXML
    private ImageView godPower3_3Players;
    @FXML
    private Label playerName1_2Players;
    @FXML
    private Label playerName2_2Players;
    @FXML
    private Label playerName1_3Players;
    @FXML
    private Label playerName2_3Players;
    @FXML
    private Label playerName3_3Players;


    @Override
    public void subscribe(ViewObserver gui) {
        this.gui = (GUI) gui;
    }

    public void showPlayersGodPowers(List<String> playerNames, List<String> godPowerNames) {
        this.godPowerNames = godPowerNames;
        if (godPowerNames.size() == 2) {
            background.setImage(new Image("/img/2PlayerV3.png"));
            godPower1_2Players.setVisible(true);
            godPower2_2Players.setVisible(true);
            godPower1_2Players.setImage(new Image("/img/Godcards/"
                    + godPowerNames.get(0).toLowerCase() + ".png"));
            godPower2_2Players.setImage(new Image("/img/Godcards/"
                    + godPowerNames.get(1).toLowerCase() + ".png"));
            playerName1_2Players.setVisible(true);
            playerName2_2Players.setVisible(true);
            playerName1_2Players.setText(playerNames.get(0));
            playerName2_2Players.setText(playerNames.get(1));
        } else {
            background.setImage(new Image("/img/3PlayerV3.png"));
            godPower1_3Players.setVisible(true);
            godPower2_3Players.setVisible(true);
            godPower3_3Players.setVisible(true);
            godPower1_3Players.setImage(new Image("/img/Godcards/"
                    + godPowerNames.get(0).toLowerCase() + ".png"));
            godPower2_3Players.setImage(new Image("/img/Godcards/"
                    + godPowerNames.get(1).toLowerCase() + ".png"));
            godPower3_3Players.setImage(new Image("/img/Godcards/"
                    + godPowerNames.get(2).toLowerCase() + ".png"));
            playerName1_3Players.setVisible(true);
            playerName2_3Players.setVisible(true);
            playerName3_3Players.setVisible(true);
            playerName1_3Players.setText(playerNames.get(0));
            playerName2_3Players.setText(playerNames.get(1));
            playerName3_3Players.setText(playerNames.get(2));
        }
    }

    public void showBoard(SpaceCopy[][] board) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board[i][j].getTowerHeight() > 0) {
                    ((ImageView) towerLevels.getChildren().get(5 * i + j)).setImage(new Image("/img/Board/" + "TowerLevel" + board[i][j].getTowerHeight() + ".png"));
                } else {
                    ((ImageView) towerLevels.getChildren().get(5 * i + j)).setImage(null);
                }
                if (board[i][j].hasDome()) {
                    (domes.getChildren().get(5 * i + j)).setVisible(true);
                }
                if (board[i][j].hasWorker()) {
                    ((ImageView) workers.getChildren().get(5 * i + j)).setImage(new Image("/img/Board/" + "P" + board[i][j].getID().substring(2, 3) + "W" + board[i][j].getWorkerNumber() + ".png"));
                } else {
                    ((ImageView) workers.getChildren().get(5 * i + j)).setImage(null);
                }
            }
        }

        disableAllButtons();
        workerBorder.setVisible(false);
        leftButtonImage.setImage(null);
        rightButtonImage.setImage(null);
        this.board = board;
    }

    public void askWorkerPosition(String playerName, int workerNumber, int previousPos, SpaceCopy[][] board) {
        this.previousPos = previousPos;
        this.playerName = playerName;

        activateAllButtons();
        if (this.previousPos != -1) {
            boardButtons.getChildren().get(previousPos).setVisible(false);
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board[i][j].hasWorker()) {
                    boardButtons.getChildren().get(5 * j + i).setVisible(false);
                }
            }
        }

        this.board = board;
        this.buttonAction = 1;

        messageLabel.setText(playerName + " it's your turn! Choose the position of your "
                + ((workerNumber == 1) ? "first" : "second") + " worker");
    }

    public void handleBoardButton(ActionEvent actionEvent) {
        Button buttonPressed = (Button) actionEvent.getSource();
        int buttonNumber = Integer.parseInt(buttonPressed.getId());

        switch (this.buttonAction) {
            case 1:
                buttonPressed.setVisible(false);
                if(positioningWorker==1) {
                    ((ImageView) workers.getChildren().get(buttonNumber%5*5+buttonNumber/5)).setImage(new Image("/img/Board/" + "P" + playerName.substring(playerName.length() - 2, playerName.length() - 1) + "W1.png"));
                    positioningWorker++;
                }
                gui.updateWorkerPosition(buttonNumber);
                if(positioningWorker==2){
                    messageLabel.setText("Waiting for other players ...");
                }
                buttonAction = 0;
                break;
            case 2:
                // Dobbiamo controllare che il bottone cliccato sia in uno space che contiene un worker del giocatore
                if (board[buttonNumber % 5][buttonNumber / 5].hasWorker() && board[buttonNumber % 5][buttonNumber / 5].getID().
                        equals(playerName.substring(playerName.length() - 4, playerName.length() - 1))) {
                    moveWorkerBorder(buttonNumber);
                    workerAndSpace[0] = board[buttonNumber % 5][buttonNumber / 5].getWorkerNumber();
                    messageLabel.setText("");
                    workerMovementSelection(playerName, workerAndSpace[0] == 1 ? validMovementSpacesW1 : validMovementSpacesW2, true);
                }
                break;
            case 3:
                if(playerName.substring(playerName.length() - 4, playerName.length() - 1).equals(board[buttonNumber % 5][buttonNumber / 5].getID()) &&
                        board[buttonNumber % 5][buttonNumber / 5].getWorkerNumber()!=workerAndSpace[0]){
                    disableAllButtons();
                    setAllOpacity(0);
                    moveWorkerBorder(buttonNumber);
                    workerAndSpace[0] = board[buttonNumber % 5][buttonNumber / 5].getWorkerNumber();
                    workerMovementSelection(playerName, workerAndSpace[0] == 1 ? validMovementSpacesW1 : validMovementSpacesW2, true);
                }
                else{
                    workerAndSpace[1] = buttonNumber;
                    gui.updateWorkerMovement(workerAndSpace);
                    messageLabel.setText("");
                    disableAllButtons();
                    setAllOpacity(0);
                }
                break;
            case 4:
                disableAllButtons();
                gui.updateBuildingSpace(buttonNumber);
                messageLabel.setText("Waiting for other players ...");
                break;
            case 5:
                disableAllButtons();
                spaceAndDome[0] = buttonNumber;
                if (board[buttonNumber % 5][buttonNumber / 5].getTowerHeight() < 3) {
                    leftButtonImage.setImage(new Image("/img/blockunpressed.png"));
                    rightButtonImage.setImage(new Image("/img/domeunpressed.png"));
                    blockButton.setVisible(true);
                    domeButton.setVisible(true);
                } else {
                    spaceAndDome[1] = 1;
                    gui.updateAtlasBuild(spaceAndDome);
                    leftButtonImage.setImage(null);
                    rightButtonImage.setImage(null);
                    blockButton.setVisible(false);
                    domeButton.setVisible(false);
                }
                buttonPressed.setVisible(true);
                buttonPressed.setDisable(true);
                buttonPressed.setOpacity(1);
                messageLabel.setText("Waiting for other players ...");
                break;
            case 6:
                // Dobbiamo controllare che il bottone cliccato sia in uno space che contiene un worker del giocatore
                if (board[buttonNumber % 5][buttonNumber / 5].hasWorker() && board[buttonNumber % 5][buttonNumber / 5].getID().
                        equals(playerName.substring(playerName.length() - 4, playerName.length() - 1))) {
                    moveWorkerBorder(buttonNumber);
                    workerAndBuildBeforeMove[0] = board[buttonNumber % 5][buttonNumber / 5].getWorkerNumber();
                    workerBuildBeforeMove(workerAndBuildBeforeMove[0] == 1 ? w1CanBuild : w2CanBuild);
                }
                break;
            case 7:
                gui.updateWorkerMovementPrometheus(buttonNumber);
                messageLabel.setText("");
                disableAllButtons();
                setAllOpacity(0);
                break;
            case 8:
                secondSpaceArtemis = buttonNumber;
                gui.updateArtemisSecondMove(buttonNumber);
                messageLabel.setText("Waiting for other players ...");
                disableAllButtons();
                setAllOpacity(0);
                break;
            case 9: //Hephaestus first build
                hephaestusSecondBuild(buttonNumber, buttonPressed);
                break;
        }
    }

    public void askWorkerMovement(String playerName, List<SpaceCopy> validMovementSpacesW1, List<SpaceCopy> validMovementSpacesW2) {
        workerAndSpace = new int[2];
        this.playerName = playerName;
        this.validMovementSpacesW1 = validMovementSpacesW1;
        this.validMovementSpacesW2 = validMovementSpacesW2;

        activateAllButtons();
        setAllOpacity(0);

        // SELECTION OF WORKER
        if (validMovementSpacesW1.size() == 0) {
            messageLabel.setText("Worker 1 can't move! Worker 2 is automatically selected");
            boardButtons.getChildren().get(workerToPosition(1)).setVisible(false);
            workerAndSpace[0] = 2;
            moveWorkerBorder(workerToPosition(workerAndSpace[0]));
            workerMovementSelection(playerName, validMovementSpacesW2, false);
            this.buttonAction = 3;
            return;
        } else if (validMovementSpacesW2.size() == 0) {
            messageLabel.setText("Worker 2 can't move! Worker 1 is automatically selected");
            boardButtons.getChildren().get(workerToPosition(2)).setVisible(false);
            workerAndSpace[0] = 1;
            moveWorkerBorder(workerToPosition(workerAndSpace[0]));
            workerMovementSelection(playerName, validMovementSpacesW1, false);
            this.buttonAction = 3;
            return;
        } else {
            messageLabel.setText(playerName + ": Choose a worker");
        }

        messageLabelAppended = false;

        this.buttonAction = 2;
    }

    private void workerMovementSelection(String playerName, List<SpaceCopy> validMovementSpacesW, boolean otherWorkerCanMove) {
        Button button;

        if (messageLabelAppended == false) {
            messageLabel.setText(messageLabel.getText() + playerName + ": Choose movement space");
            messageLabelAppended = true;
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                button = (Button) boardButtons.getChildren().get(5 * i + j);
                button.setVisible(false);
                for (SpaceCopy space : validMovementSpacesW) {
                    if (space.getNumber() == (5 * i + j)) {
                        //System.out.println(button.getId() + " visibile");
                        button.setVisible(true);
                        button.setOpacity(1);
                    }
                }

                // Attiviamo il button del secondo worker
                if (otherWorkerCanMove && playerName.substring(playerName.length() - 4, playerName.length() - 1).
                        equals(board[j][i].getID()) && board[j][i].getWorkerNumber() != workerAndSpace[0]) {
                    button.setVisible(true);
                }
            }
        }

        this.buttonAction = 3;
    }

    public void askBuildingSpace(String playerName, List<SpaceCopy> validBuildingSpaces) {
        this.playerName = playerName;
        this.validBuildingSpaces = validBuildingSpaces;

        showValidBuildingSpaces();

        this.buttonAction = 4;
    }

    private void showValidBuildingSpaces() {
        Button button;
        messageLabel.setText(playerName + ": Choose building space");

        if (secondSpaceArtemis != null) {
            moveWorkerBorder(secondSpaceArtemis);
            secondSpaceArtemis = null;
        } else if (workerAndSpace != null)
            moveWorkerBorder(workerAndSpace[1]);
        else
            moveWorkerBorder(workerToPosition(workerAndBuildBeforeMove[0]));

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                button = (Button) boardButtons.getChildren().get(5 * i + j);
                button.setVisible(false);
                for (SpaceCopy space : validBuildingSpaces) {
                    if (space.getNumber() == (5 * i + j)) {
                        button.setVisible(true);
                        button.setOpacity(1);
                    }
                }
            }
        }

    }

    private void showValidAresRemoveSpaces() {
        Button button;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                button = (Button) boardButtons.getChildren().get(5 * i + j);
                button.setVisible(false);
                for (SpaceCopy space : validRemoveSpaces) {
                    if (space.getNumber() == (5 * i + j)) {
                        button.setVisible(true);
                        button.setOpacity(1);
                    }
                }
            }
        }
    }

    public void askAtlasBuild(String playerName, List<SpaceCopy> validBuildingSpaces) {
        this.spaceAndDome = new int[2];
        this.validBuildingSpaces = validBuildingSpaces;

        messageLabel.setText(playerName + ": Choose building space");
        Button button;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                button = (Button) boardButtons.getChildren().get(5 * i + j);
                button.setVisible(false);
                for (SpaceCopy space : validBuildingSpaces) {
                    if (space.getNumber() == (5 * i + j)) {
                        button.setVisible(true);
                        button.setOpacity(1);
                    }
                }
            }
        }

        moveWorkerBorder(workerToPosition(workerAndSpace[0]));

        this.buttonAction = 5;
    }

    public void askBuildBeforeMovePrometheus(String playerName, boolean w1CanMove, boolean w1CanBuild, boolean w2CanMove, boolean w2CanBuild) {
        this.workerAndBuildBeforeMove = new int[2];
        this.w1CanBuild = w1CanBuild;
        this.w2CanBuild = w2CanBuild;

        activateAllButtons();
        setAllOpacity(0);

        if (!w1CanMove) {
            messageLabel.setText("Worker 1 can't move! Worker 2 is automatically selected");
            boardButtons.getChildren().get(workerToPosition(1)).setVisible(false);
            this.workerAndBuildBeforeMove[0] = 2;
            disableAllButtons();
            moveWorkerBorder(workerToPosition(workerAndBuildBeforeMove[0]));
            workerBuildBeforeMove(w2CanBuild);
        } else if (!w2CanMove) {
            messageLabel.setText("Worker 2 can't move! Worker 1 is automatically selected");
            boardButtons.getChildren().get(workerToPosition(2)).setVisible(false);
            this.workerAndBuildBeforeMove[0] = 1;
            disableAllButtons();
            moveWorkerBorder(workerToPosition(workerAndBuildBeforeMove[0]));
            workerBuildBeforeMove(w1CanBuild);
        } else {
            messageLabel.setText(playerName + ": Choose a worker");
            this.buttonAction = 6;
        }

    }

    private void workerBuildBeforeMove(boolean wCanBuild) {
        if (wCanBuild) {
            messageLabel.setText("Do you want to build before move?");
            leftButtonImage.setImage(new Image("/img/buildunpressed.png"));
            rightButtonImage.setImage(new Image("/img/moveunpressed.png"));
            buildButton.setVisible(true);
            moveButton.setVisible(true);

        } else {
            messageLabel.setText(playerName + ": Choose a worker (Click 'move' to confirm selection) - your worker can't build");
            buildButton.setVisible(false);
            leftButtonImage.setImage(null);
            rightButtonImage.setImage(new Image("/img/moveunpressed.png"));
            moveButton.setVisible(true);
        }
    }

    public void askWorkerMovementPrometheus(String playerName, List<SpaceCopy> validMovementSpaces) {
        Button button;

        messageLabel.setText(playerName + ": Choose movement space");

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                button = (Button) boardButtons.getChildren().get(5 * i + j);
                button.setVisible(false);
                for (SpaceCopy space : validMovementSpaces) {
                    if (space.getNumber() == (5 * i + j)) {
                        button.setVisible(true);
                        button.setOpacity(1);
                    }
                }
            }
        }

        moveWorkerBorder(workerToPosition(workerAndBuildBeforeMove[0]));

        this.buttonAction = 7;
    }

    public void askArtemisSecondMove(String playerName, List<SpaceCopy> validSecondMovementSpaces) {
        this.validSecondMovementSpaces = validSecondMovementSpaces;
        messageLabel.setText("Do you want to move your Worker for the second time?");

        moveWorkerBorder(workerToPosition(workerAndSpace[0]));
        leftButtonImage.setImage(new Image("/img/yesunpressed.png"));
        rightButtonImage.setImage(new Image("/img/nounpressed.png"));
        yesArtemisButton.setVisible(true);
        noArtemisButton.setVisible(true);
    }

    public void askDemeterSecondBuilding(String playerName, List<SpaceCopy> validBuildingSpaces) {
        this.validBuildingSpaces = validBuildingSpaces;
        messageLabel.setText("Do you want to build an additional block?");

        leftButtonImage.setImage(new Image("/img/yesunpressed.png"));
        rightButtonImage.setImage(new Image("/img/nounpressed.png"));
        yesDemeterButton.setVisible(true);
        noDemeterButton.setVisible(true);
    }

    public void askRemoveBlockAres(String playerName, List<SpaceCopy> validRemoveSpaces, int nonSelectedWorkerNumber) {
        this.validRemoveSpaces = validRemoveSpaces;
        messageLabel.setText("Do you want to remove a non occupied block (without a dome) around your Worker "
                + nonSelectedWorkerNumber + "?");

        leftButtonImage.setImage(new Image("/img/yesunpressed.png"));
        rightButtonImage.setImage(new Image("/img/nounpressed.png"));
        yesAresButton.setVisible(true);
        noAresButton.setVisible(true);
    }

    public void askHephaestusBuild(String playerName, List<SpaceCopy> validBuildingSpaces) {
        this.validBuildingSpaces = validBuildingSpaces;

        showValidBuildingSpaces();

        this.buttonAction = 9;
    }

    private void hephaestusSecondBuild(int chosenBuildingSpace, Button buttonPressed) {
        disableAllButtons();
        buttonPressed.setVisible(true);
        buttonPressed.setDisable(true);
        buttonPressed.setOpacity(1);

        spaceAndDoubleBuildingHephaestus = new int[2];
        SpaceCopy space = null;

        int x = chosenBuildingSpace % 5;
        int y = chosenBuildingSpace / 5;
        for (SpaceCopy spaceCopy : validBuildingSpaces) {
            if (spaceCopy.getX() == x && spaceCopy.getY() == y)
                space = spaceCopy;
        }

        spaceAndDoubleBuildingHephaestus[0] = chosenBuildingSpace;

        // space.getTowerHeight() < 2 perchè l'altezza non viene davvero incrementata tra i 2 step
        if (space.getTowerHeight() < 2) { // can't build a dome
            // Choice to build another block
            ((ImageView) towerLevels.getChildren().get(chosenBuildingSpace % 5 * 5 + chosenBuildingSpace / 5)).
                    setImage(new Image("/img/Board/" + "TowerLevel" +
                            (board[chosenBuildingSpace % 5][chosenBuildingSpace / 5].getTowerHeight() + 1) + ".png"));
            messageLabel.setText("Do you want to build an additional block ?");
            leftButtonImage.setImage(new Image("/img/yesunpressed.png"));
            rightButtonImage.setImage(new Image("/img/nounpressed.png"));
            yesHephaestusButton.setVisible(true);
            noHephaestusButton.setVisible(true);
        } else {
            spaceAndDoubleBuildingHephaestus[1] = 1;
            messageLabel.setText("Waiting for other players ...");
            gui.updateHephaestusBuild(spaceAndDoubleBuildingHephaestus);
        }
    }

    private void moveWorkerBorder(int buttonNumber) {
        workerBorder.setLayoutX(boardButtons.getChildren().get(buttonNumber).getLayoutX());
        workerBorder.setLayoutY(boardButtons.getChildren().get(buttonNumber).getLayoutY());
        workerBorder.setVisible(true);
    }

    private int workerToPosition(int workerNumber) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (playerName.substring(playerName.length() - 4, playerName.length() - 1).equals(board[j][i].getID()) && board[j][i].getWorkerNumber() == workerNumber) {
                    return 5 * i + j;
                }
            }
        }
        return -1;
    }

    private void activateAllButtons() {
        for (int i = 0; i < 25; i++) {
            boardButtons.getChildren().get(i).setVisible(true);
            boardButtons.getChildren().get(i).setDisable(false);

        }
    }

    private void disableAllButtons() {
        for (int i = 0; i < 25; i++) {
            boardButtons.getChildren().get(i).setVisible(false);
        }
    }

    private void setAllOpacity(int opacity) {
        for (int i = 0; i < 25; i++) {
            boardButtons.getChildren().get(i).setOpacity(opacity);
        }
    }

    public void handleBlockButton(ActionEvent actionEvent) {
        spaceAndDome[1] = 0;
        gui.updateAtlasBuild(spaceAndDome);
        leftButtonImage.setImage(new Image("/img/blockpressed.png"));
        blockButton.setVisible(false);
        domeButton.setVisible(false);
    }

    public void handleDomeButton(ActionEvent actionEvent) {
        spaceAndDome[1] = 1;
        gui.updateAtlasBuild(spaceAndDome);
        rightButtonImage.setImage(new Image("/img/domepressed.png"));
        blockButton.setVisible(false);
        domeButton.setVisible(false);
    }

    public void handleBuildButton(ActionEvent actionEvent) {
        workerAndBuildBeforeMove[1] = 1;
        gui.updateBuildBeforeMovePrometheus(workerAndBuildBeforeMove);
        leftButtonImage.setImage(new Image("/img/buildpressed.png"));
        buildButton.setVisible(false);
        moveButton.setVisible(false);
        this.buttonAction = 7;
    }

    public void handleMoveButton(ActionEvent actionEvent) {
        workerAndBuildBeforeMove[1] = 0;
        gui.updateBuildBeforeMovePrometheus(workerAndBuildBeforeMove);
        rightButtonImage.setImage(new Image("/img/movepressed.png"));
        buildButton.setVisible(false);
        moveButton.setVisible(false);
        this.buttonAction = 7;
    }

    public void handleYesArtemisButton(ActionEvent actionEvent) {
        Button button;

        messageLabel.setText(playerName + ": Choose movement space");
        leftButtonImage.setImage(new Image("/img/yespressed.png"));
        yesArtemisButton.setVisible(false);
        noArtemisButton.setVisible(false);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                button = (Button) boardButtons.getChildren().get(5 * i + j);
                button.setVisible(false);
                for (SpaceCopy space : validSecondMovementSpaces) {
                    if (space.getNumber() == (5 * i + j)) {
                        //System.out.println(button.getId() + " visibile");
                        button.setVisible(true);
                        button.setOpacity(1);
                    }
                }
            }
        }

        this.buttonAction = 8;
    }

    public void handleNoArtemisButton(ActionEvent actionEvent) {
        rightButtonImage.setImage(new Image("/img/nopressed.png"));
        yesArtemisButton.setVisible(false);
        noArtemisButton.setVisible(false);
        gui.updateArtemisSecondMove(-1);
    }

    public void handleYesDemeterButton(ActionEvent actionEvent) {
        messageLabel.setText(playerName + ": Choose building space");
        leftButtonImage.setImage(new Image("/img/yespressed.png"));
        yesDemeterButton.setVisible(false);
        noDemeterButton.setVisible(false);

        askBuildingSpace(playerName, validBuildingSpaces);
    }

    public void handleNoDemeterButton(ActionEvent actionEvent) {
        rightButtonImage.setImage(new Image("/img/nopressed.png"));
        yesDemeterButton.setVisible(false);
        noDemeterButton.setVisible(false);

        messageLabel.setText("Waiting for other players ...");

        gui.updateBuildingSpace(-1);
    }

    public void handleYesAresButton(ActionEvent actionEvent) {
        messageLabel.setText(playerName + ": Choose the space where you want to remove a block");
        leftButtonImage.setImage(new Image("/img/yespressed.png"));
        yesAresButton.setVisible(false);
        noAresButton.setVisible(false);

        showValidAresRemoveSpaces();

        this.buttonAction = 4;
    }

    public void handleNoAresButton(ActionEvent actionEvent) {
        rightButtonImage.setImage(new Image("/img/nopressed.png"));
        yesAresButton.setVisible(false);
        noAresButton.setVisible(false);

        messageLabel.setText("Waiting for other players ...");

        gui.updateBuildingSpace(-1);
    }

    public void handleYesHephaestusButton(ActionEvent actionEvent) {
        messageLabel.setText("Waiting for other players ...");
        leftButtonImage.setImage(new Image("/img/yespressed.png"));
        yesHephaestusButton.setVisible(false);
        noHephaestusButton.setVisible(false);

        spaceAndDoubleBuildingHephaestus[1] = 2;

        gui.updateHephaestusBuild(spaceAndDoubleBuildingHephaestus);
    }

    public void handleNoHephaestusButton(ActionEvent actionEvent) {
        messageLabel.setText("Waiting for other players ...");
        rightButtonImage.setImage(new Image("/img/nopressed.png"));
        yesHephaestusButton.setVisible(false);
        noHephaestusButton.setVisible(false);

        spaceAndDoubleBuildingHephaestus[1] = 1;

        gui.updateHephaestusBuild(spaceAndDoubleBuildingHephaestus);
    }

    public void announceVictory(String playerName) {
        if (playerName.equals(this.playerName)) {
            victoryWindowImage.setImage(new Image("/img/WinningWindow.png"));
            victoryLabel.setText(playerName + " you won the Game! Congratulations!");
        } else {
            victoryWindowImage.setImage(new Image("/img/LosingWindow.png"));
            victoryLabel.setText(playerName + " won the Game!");
        }
        victoryWindowImage.setVisible(true);
        victoryLabel.setVisible(true);
        victoryCloseImage.setVisible(true);
        victoryCloseButton.setVisible(true);

        messageLabel.setText("Do you want to play again?");
        leftButtonImage.setImage(new Image("/img/yesunpressed.png"));
        rightButtonImage.setImage(new Image("/img/nounpressed.png"));
        yesPlayAgainButton.setVisible(true);
        noPlayAgainButton.setVisible(true);
    }

    public void handleVictoryCloseButton(ActionEvent actionEvent) {
        victoryCloseImage.setImage(new Image("/img/closebtn_pressed.png"));
        victoryWindowImage.setVisible(false);
        victoryLabel.setVisible(false);
        victoryCloseImage.setVisible(false);
        victoryCloseButton.setVisible(false);
    }

    public void announceLose(String playerName) {
        if (playerName.equals(this.playerName)) {
            victoryWindowImage.setImage(new Image("/img/LosingWindow.png"));
            victoryLabel.setText(playerName + " you lost the Game! Can't move or build!");

            messageLabel.setText("Do you want to play again?");
            leftButtonImage.setImage(new Image("/img/yesunpressed.png"));
            rightButtonImage.setImage(new Image("/img/nounpressed.png"));
            yesPlayAgainButton.setVisible(true);
            noPlayAgainButton.setVisible(true);
        } else {
            victoryWindowImage.setImage(new Image("/img/WinningWindow.png"));
            victoryLabel.setText(playerName + " lost the Game! Can't move or build!");
        }
        victoryWindowImage.setVisible(true);
        victoryLabel.setVisible(true);
        victoryCloseImage.setVisible(true);
        victoryCloseButton.setVisible(true);
    }

    public void handleYesPlayAgainButton(ActionEvent actionEvent) {
        leftButtonImage.setImage(new Image("/img/yespressed.png"));
        yesPlayAgainButton.setVisible(false);
        noPlayAgainButton.setVisible(false);

        gui.restartFromNumOfPlayersScene();

        gui.playAgain(true);
    }

    public void handleNoPlayAgainButton(ActionEvent actionEvent) {
        rightButtonImage.setImage(new Image("/img/nopressed.png"));
        yesPlayAgainButton.setVisible(false);
        noPlayAgainButton.setVisible(false);

        Button button = (Button) actionEvent.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();

        gui.playAgain(false);
    }

    public void manageServerDisconnection() {
        victoryWindowImage.setImage(new Image("/img/WinningWindow.png"));
        victoryLabel.setText("Disconnected from server");
        victoryWindowImage.setVisible(true);
        victoryLabel.setVisible(true);

        messageLabel.setText("Do you want to play again?");
        leftButtonImage.setImage(new Image("/img/yesunpressed.png"));
        rightButtonImage.setImage(new Image("/img/nounpressed.png"));
        yesPlayAgainButton.setVisible(true);
        noPlayAgainButton.setVisible(true);
    }
}
