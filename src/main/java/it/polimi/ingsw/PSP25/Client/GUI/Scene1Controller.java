package it.polimi.ingsw.PSP25.Client.GUI;

import it.polimi.ingsw.PSP25.Client.ViewObservable;
import it.polimi.ingsw.PSP25.Client.ViewObserver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

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
