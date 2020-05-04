package it.polimi.ingsw.PSP25.Client.GUI;

import it.polimi.ingsw.PSP25.Client.ViewObserver;
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

    /*public void handleConfirmButton(ActionEvent actionEvent) {
        confirmButton.setDisable(true);
        questionLabel.setText("Waiting for other players..");
        gui.updateGodPower(selectedIndex);
    }*/

    /*private void handleButton(ActionEvent actionEvent, int buttonNum){
        Button pressedButton = (Button) actionEvent.getSource();
        if(selectedIndex == null) {
            selectedIndex = buttonNum;
            pressedButton.setOpacity(1);
            confirmButton.setVisible(true);
        } else {
            selectedIndex = buttonNum;
            pressedButton.setOpacity(1);
            previousButton.setOpacity(0);
            if(previousButton == pressedButton){
                confirmButton.setVisible(false);
                selectedIndex = null;
            }
        }
        previousButton = pressedButton;
    }*/

    /*public void handleButton1(ActionEvent actionEvent) {
        handleButton(actionEvent, 1);
    }

    public void handleButton2(ActionEvent actionEvent) {
       handleButton(actionEvent, 2);
    }

    public void handleButton3(ActionEvent actionEvent) {
        handleButton(actionEvent, 3);
    }*/
}
