package it.polimi.ingsw.PSP25.Client.GUI;

import it.polimi.ingsw.PSP25.Client.ViewObserver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

public class GodPowerSceneController implements GUIObservable {
    private GUI gui;
    private Integer selectedIndex = null;
    private Button previousButton;
    private Map<String, String> godPowersDescriptions = new HashMap<>();

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
        setDescriptions();
        questionLabel.setText(playerName + " choose your god power from the list: ");
        if (godPowerNames.size() == 2) {
            button1_2godPowers.setVisible(true);
            button1_2godPowers.getTooltip().setText(godPowersDescriptions.get(godPowerNames.get(0)));
            button2_2godPowers.setVisible(true);
            button2_2godPowers.getTooltip().setText(godPowersDescriptions.get(godPowerNames.get(1)));
            image1_2godPowers.setImage(new Image("/img/Godcards/" + godPowerNames.get(0).toLowerCase() + ".png"));
            image2_2godPowers.setImage(new Image("/img/Godcards/" + godPowerNames.get(1).toLowerCase() + ".png"));
        } else {
            button1_3godPowers.setVisible(true);
            button1_3godPowers.getTooltip().setText(godPowersDescriptions.get(godPowerNames.get(0)));
            button2_3godPowers.setVisible(true);
            button2_3godPowers.getTooltip().setText(godPowersDescriptions.get(godPowerNames.get(1)));
            button3_3godPowers.setVisible(true);
            button3_3godPowers.getTooltip().setText(godPowersDescriptions.get(godPowerNames.get(2)));
            image1_3godPowers.setImage(new Image("/img/Godcards/" + godPowerNames.get(0).toLowerCase() + ".png"));
            image2_3godPowers.setImage(new Image("/img/Godcards/" + godPowerNames.get(1).toLowerCase() + ".png"));
            image3_3godPowers.setImage(new Image("/img/Godcards/" + godPowerNames.get(2).toLowerCase() + ".png"));
        }
    }

    private void setDescriptions() {
        godPowersDescriptions.put("Apollo", "Your Worker may move into an opponent Worker’s space by forcing their worker to the space yours just vacated.");
        godPowersDescriptions.put("Artemis", "Your Worker may move one additional time, but not back to its initial space.");
        godPowersDescriptions.put("Athena", "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn");
        godPowersDescriptions.put("Atlas", "Your Worker may build a dome at any level. ");
        godPowersDescriptions.put("Demeter", "Your Worker may build one additional time, but not on the same space\"");
        godPowersDescriptions.put("Hephaestus", "Your Worker may build one additional block (not dome) on top of your first block.");
        godPowersDescriptions.put("Minotaur", "Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level. ");
        godPowersDescriptions.put("Pan", "You also win if your Worker moves down two or more levels");
        godPowersDescriptions.put("Prometheus", "If your Worker does not move up, it may build both before and after moving");
        godPowersDescriptions.put("Ares", "You may remove an unoccupied block (not dome) neighboring your unmoved Worker. You also remove any Tokens on the block");
        godPowersDescriptions.put("Hera", "An opponent cannot win by moving into a perimeter space.");
        godPowersDescriptions.put("Hypnus", "If one of your opponent’s Workers is higher than all of their others, it cannot move");
        godPowersDescriptions.put("Limus", "Opponent Workers cannot build on spaces neighboring your Workers, unless building a dome to create a Complete Tower.");
        godPowersDescriptions.put("Zeus", "Your Worker may build a block under itself");
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
