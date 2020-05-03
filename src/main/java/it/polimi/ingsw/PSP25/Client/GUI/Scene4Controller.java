package it.polimi.ingsw.PSP25.Client.GUI;

import it.polimi.ingsw.PSP25.Client.ViewObserver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class Scene4Controller implements GUIObservable {

    private GUI gui;
    private List<Integer> selectedIndexes;
    private int numOfPlayers;

    @FXML
    private Label questionLabel;
    @FXML
    private Button confirmButton;

    @Override
    public void subscribe(ViewObserver gui) {
        this.gui = (GUI) gui;
    }

    public void setQuestion(String playerName, int numOfPlayers, List<String> godPowerNames) {
        questionLabel.setText(playerName + " choose " + numOfPlayers + " god powers from the list: ");
        selectedIndexes = new ArrayList<>(numOfPlayers);
        this.numOfPlayers = numOfPlayers;
    }

    public void handleConfirmButton(ActionEvent actionEvent) {
        confirmButton.setVisible(false);
        gui.updateAllGodPower(selectedIndexes);
    }

    public void handleButtonGodPowers(ActionEvent actionEvent) {
        Button pressedButton = (Button) actionEvent.getSource();
        int buttonNumber = Integer.parseInt(pressedButton.getId());

        if (!selectedIndexes.contains(buttonNumber) && selectedIndexes.size() < numOfPlayers) {
            selectedIndexes.add(buttonNumber);
            pressedButton.setOpacity(1);
            if (selectedIndexes.size() == numOfPlayers)
                confirmButton.setVisible(true);
        } else {
            if (selectedIndexes.remove(Integer.valueOf(buttonNumber))) {
                confirmButton.setVisible(false);
                pressedButton.setOpacity(0);
            }
        }
    }
}
