package it.polimi.ingsw.PSP25.Client.GUI;

import it.polimi.ingsw.PSP25.Client.ViewObserver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Scene2Controller implements GUIObservable {

    private GUI gui;

    @FXML
    private Label SelectNumOfPlayers;
    @FXML
    private Label ConnectionMessage;
    @FXML
    private Button Button2Players;
    @FXML
    private Button Button3Players;
    @FXML
    private Label waitingLabel;

    @Override
    public void subscribe(ViewObserver gui) {
        this.gui = (GUI) gui;
    }

    public void setConnectionMessage(String message) {
        ConnectionMessage.setText(message);
    }

    public void askNumOfPlayers() {
        waitingLabel.setVisible(false);
        SelectNumOfPlayers.setVisible(true);
        Button2Players.setVisible(true);
        Button3Players.setVisible(true);
    }

    @FXML
    private void handleButton2Players(ActionEvent event) {
        Button2Players.setDisable(true);
        Button3Players.setDisable(true);
        waitingLabel.setVisible(true);
        gui.updateNumOfPlayers(2);
    }

    @FXML
    private void handleButton3Players(ActionEvent event) {
        Button2Players.setDisable(true);
        Button3Players.setDisable(true);
        waitingLabel.setVisible(true);
        gui.updateNumOfPlayers(3);
    }
}