package it.polimi.ingsw.PSP25.Client.GUI;

import it.polimi.ingsw.PSP25.Client.ViewObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

public class Scene4Controller implements GUIObservable {

    private GUI gui;

    @FXML
    private Label questionLabel;

    public void setQuestion(String playerName, int numOfPlayers, List<String> godPowerNames) {
        questionLabel.setText(playerName + " choose " + numOfPlayers + " god powers from the list: ");
    }

    @Override
    public void subscribe(ViewObserver o) {
        this.gui = (GUI) gui;
    }
}
