package it.polimi.ingsw.PSP25.Client.GUI;

import it.polimi.ingsw.PSP25.Client.ViewObserver;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class Scene6Controller implements GUIObservable {

    private GUI gui;
    private Integer selectedIndex = null;
    private List<String> godPowerNames;
    private Button previousButton;
    @FXML
    private ImageView towerLevels[][];
    @FXML
    private ImageView domes[][];
    @FXML
    private ImageView workers[][];

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
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                towerLevels[i][j].setImage(new Image("/img/Board/" + "TowerLevel" + board[i][j].getTowerHeight() + ".png"));
                if(board[i][j].hasDome()){
                    domes[i][j].setVisible(true);
                }
                if(board[i][j].hasWorker()) {
                    workers[i][j].setImage(new Image("/img/Board/" + "P" + board[i][j].getID().substring(2,3) + "W" + board[i][j].getWorkerNumber()));
                }
            }
        }
    }
}
