package it.polimi.ingsw.PSP25.Client.GUI;

import it.polimi.ingsw.PSP25.Client.ViewObserver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Scene2Controller implements GUIObservable {

    public Label SelectNumOfPlayers;
    public Label ConnectionMessage;
    public Button Button2Players;
    public Button Button3Players;
    private GUI gui;

    @Override
    public void subscribe(ViewObserver gui) {
        this.gui = (GUI) gui;
    }

    public void setConnectionMessage(String message) {
        ConnectionMessage.setText(message);
    }

    public void askNumOfPlayers() {
        SelectNumOfPlayers.setVisible(true);
        Button2Players.setVisible(true);
        Button3Players.setVisible(true);
    }

    @FXML
    private void handleButton2Players(ActionEvent event) {
        gui.updateNumOfPlayers(2);
    }

    @FXML
    private void handleButton3Players(ActionEvent event) {
        gui.updateNumOfPlayers(3);
    }
}