package it.polimi.ingsw.PSP25.Client.GUI;

import it.polimi.ingsw.PSP25.Client.ViewObserver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class GodPowerSceneController implements GUIObservable {

    private GUI gui;
    private Integer selectedIndex = null;
    private Button previousButton;

    @FXML
    private Label questionLabel;
    @FXML
    private Button confirmButton;
    @FXML
    private ImageView image1_3godPowers;
    @FXML
    private ImageView image2_3godPowers;
    @FXML
    private ImageView image3_3godPowers;
    @FXML
    private ImageView image1_2godPowers;
    @FXML
    private ImageView image2_2godPowers;
    @FXML
    private Button button1_3godPowers;
    @FXML
    private Button button2_3godPowers;
    @FXML
    private Button button3_3godPowers;
    @FXML
    private Button button1_2godPowers;
    @FXML
    private Button button2_2godPowers;
    @FXML
    private ImageView confirmImage;

    @Override
    public void subscribe(ViewObserver gui) {
        this.gui = (GUI) gui;
    }

    public void setQuestion(String playerName, List<String> godPowerNames) {
        questionLabel.setText(playerName + " choose your god power from the list: ");
        if (godPowerNames.size() == 2) {
            button1_2godPowers.setVisible(true);
            button2_2godPowers.setVisible(true);
            image1_2godPowers.setImage(new Image("/img/Godcards/"
                    + godPowerNames.get(0).toLowerCase() + ".png"));
            image2_2godPowers.setImage(new Image("/img/Godcards/"
                    + godPowerNames.get(1).toLowerCase() + ".png"));
        } else {
            button1_3godPowers.setVisible(true);
            button2_3godPowers.setVisible(true);
            button3_3godPowers.setVisible(true);
            image1_3godPowers.setImage(new Image("/img/Godcards/"
                    + godPowerNames.get(0).toLowerCase() + ".png"));
            image2_3godPowers.setImage(new Image("/img/Godcards/"
                    + godPowerNames.get(1).toLowerCase() + ".png"));
            image3_3godPowers.setImage(new Image("/img/Godcards/"
                    + godPowerNames.get(2).toLowerCase() + ".png"));
        }
    }

    public void handleConfirmButton(ActionEvent actionEvent) {
        confirmButton.setDisable(true);
        confirmImage.setImage(new Image("/img/CutConfirmPressed.png"));
        confirmImage.setOpacity(0.8);
        questionLabel.setText("Waiting for other players..");
        gui.updateGodPower(selectedIndex);
    }

    private void handleButton(ActionEvent actionEvent, int buttonNum) {
        Button pressedButton = (Button) actionEvent.getSource();
        if (selectedIndex == null) {
            selectedIndex = buttonNum;
            pressedButton.setOpacity(1);
            confirmButton.setVisible(true);
            confirmImage.setVisible(true);
        } else {
            selectedIndex = buttonNum;
            pressedButton.setOpacity(1);
            previousButton.setOpacity(0);
            if (previousButton == pressedButton) {
                confirmButton.setVisible(false);
                confirmImage.setVisible(false);
                selectedIndex = null;
            }
        }
        previousButton = pressedButton;
    }

    public void handleButton1(ActionEvent actionEvent) {
        handleButton(actionEvent, 1);
    }

    public void handleButton2(ActionEvent actionEvent) {
        handleButton(actionEvent, 2);
    }

    public void handleButton3(ActionEvent actionEvent) {
        handleButton(actionEvent, 3);
    }
}
