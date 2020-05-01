package it.polimi.ingsw.PSP25.Client.GUI;

import it.polimi.ingsw.PSP25.Client.ViewObserver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Scene1Controller implements GUIObservable {

    private ViewObserver gui;

    @FXML
    private TextField textField;

    @Override
    public void subscribe(ViewObserver gui) {
        this.gui = gui;
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        //DEBUG
        System.out.println("You clicked me!");

        gui.updateIPAddress(textField.getText());
    }
}
