package it.polimi.ingsw.PSP25.Client.GUI;

import it.polimi.ingsw.PSP25.Client.ViewObserver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IPAddressSceneController implements GUIObservable {

    private ViewObserver gui;

    @FXML
    private TextField textField;
    @FXML
    private ImageView connectImage;

    @Override
    public void subscribe(ViewObserver gui) {
        this.gui = gui;
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        connectImage.setImage(new Image("/img/ButtonConnectPressed.png"));
        gui.updateIPAddress(textField.getText());
    }
}
