package it.polimi.ingsw.PSP25.Client.GUI;

import it.polimi.ingsw.PSP25.Client.ViewObserver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Scene3Controller implements GUIObservable {

    private GUI gui;

    @FXML
    private Label questionLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button okButton;

    @FXML
    private Label waitingLabel;

    @Override
    public void subscribe(ViewObserver gui) {
        this.gui = (GUI) gui;
    }

    public void setQuestion(String question) {
        questionLabel.setText(question);
    }

    @FXML
    private void handleButton(ActionEvent event) {
        String name = nameTextField.getText();
        if (name.length() < 2) {
            errorLabel.setText("Name is too short. Enter another name (2 Characters or more)");
        } else {
            errorLabel.setText("");
            okButton.setDisable(true);
            waitingLabel.setVisible(true);
            nameTextField.setDisable(true);
            gui.updateName(nameTextField.getText());
        }
    }
}
