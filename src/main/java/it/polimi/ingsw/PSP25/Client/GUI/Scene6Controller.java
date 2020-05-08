package it.polimi.ingsw.PSP25.Client.GUI;

import it.polimi.ingsw.PSP25.Client.ViewObserver;
import it.polimi.ingsw.PSP25.Model.Space;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.stream.Collectors;

public class Scene6Controller implements GUIObservable {

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
    private int positioningWorker=1;
    private List<SpaceCopy> validBuildingSpaces;

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

        this.board = board;
    }

    public void askWorkerPosition(String playerName, int workerNumber, int previousPos, SpaceCopy[][] board) {
        this.previousPos = previousPos;
        this.playerName = playerName;

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
                    workerAndSpace[0] = board[buttonNumber % 5][buttonNumber / 5].getWorkerNumber();
                    workerMovementSelection(playerName, workerAndSpace[0] == 1 ? validMovementSpacesW1 : validMovementSpacesW2);
                }
                break;
            case 3:
                if(playerName.substring(playerName.length() - 4, playerName.length() - 1).equals(board[buttonNumber % 5][buttonNumber / 5].getID()) &&
                        board[buttonNumber % 5][buttonNumber / 5].getWorkerNumber()!=workerAndSpace[0]){
                    disableAllButtons();
                    setAllOpacity(0);
                    workerAndSpace[0]=board[buttonNumber % 5][buttonNumber / 5].getWorkerNumber();
                    workerMovementSelection(playerName, workerAndSpace[0] == 1 ? validMovementSpacesW1 : validMovementSpacesW2);
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
            workerAndSpace[0] = 2;
        } else if (validMovementSpacesW2.size() == 0) {
            messageLabel.setText("Worker 2 can't move! Worker 1 is automatically selected");
            workerAndSpace[0] = 1;
        } else {
            messageLabel.setText(playerName + ": Choose a worker");
        }

        this.buttonAction = 2;
    }

    private void workerMovementSelection(String playerName, List<SpaceCopy> validMovementSpacesW) {
        Button button;

        messageLabel.setText(playerName + ": Choose movement space");

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
                if(playerName.substring(playerName.length() - 4, playerName.length() - 1).equals(board[j][i].getID()) && board[j][i].getWorkerNumber()!=workerAndSpace[0]){
                    button.setVisible(true);
                }
            }
        }
        this.buttonAction = 3;
    }

    public void askBuildingSpace(String playerName, List<SpaceCopy> validBuildingSpaces) {
        this.playerName = playerName;
        this.validBuildingSpaces = validBuildingSpaces;
        Button button;
        messageLabel.setText(playerName + ": Choose building space");

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                button = (Button) boardButtons.getChildren().get(5 * i + j);
                button.setVisible(false);
                for (SpaceCopy space : validBuildingSpaces) {
                    if (space.getNumber() == (5 * i + j)) {
                        //System.out.println(button.getId() + " visibile");
                        button.setVisible(true);
                        button.setOpacity(1);
                    }
                }
            }
        }

        this.buttonAction=4;
    }

    private void activateAllButtons() {
        for (int i = 0; i < 25; i++) {
            boardButtons.getChildren().get(i).setVisible(true);
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

}
