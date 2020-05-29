package it.polimi.ingsw.PSP25.Client.GUI;

import it.polimi.ingsw.PSP25.Client.ViewObserver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class FirstPlayerSceneController implements GUIObservable {
    private GUI gui;
    private int firstPlayerIndex;
    private int numOfPlayers;

    @FXML
    private Label questionLabel;
    @FXML
    private Button confirmButton;
    @FXML
    private ImageView gp_left;
    @FXML
    private ImageView gp_center;
    @FXML
    private ImageView gp_right;
    @FXML
    private ImageView name_left;
    @FXML
    private ImageView name_center;
    @FXML
    private ImageView name_right;
    @FXML
    private Label leftLabel;
    @FXML
    private Label centerLabel;
    @FXML
    private Label rightLabel;
    @FXML
    private Button leftButton;
    @FXML
    private Button centerButton;
    @FXML
    private Button rightButton;
    @FXML
    private ImageView confirmImage;

    @Override
    public void subscribe(ViewObserver gui) {
        this.gui = (GUI) gui;
    }

    public void askFirstPlayer(List<String> playerNames, List<String> godPowerNames) {
        questionLabel.setText("Select the first player");
        numOfPlayers = playerNames.size();
        if (godPowerNames.size() == 2) {
            leftButton.setVisible(true);
            leftButton.setOpacity(0);
            rightButton.setVisible(true);
            rightButton.setOpacity(0);
            name_left.setVisible(true);
            name_right.setVisible(true);
            leftLabel.setVisible(true);
            rightLabel.setVisible(true);
            leftLabel.setText(playerNames.get(0));
            rightLabel.setText(playerNames.get(1));
            gp_left.setVisible(true);
            gp_right.setVisible(true);
            gp_left.setImage(new Image("/img/Godcards/" + godPowerNames.get(0).toLowerCase() + ".png"));
            gp_right.setImage(new Image("/img/Godcards/" + godPowerNames.get(1).toLowerCase() + ".png"));
        } else {
            leftButton.setVisible(true);
            leftButton.setOpacity(0);
            centerButton.setVisible(true);
            centerButton.setOpacity(0);
            rightButton.setVisible(true);
            rightButton.setOpacity(0);
            name_left.setVisible(true);
            name_center.setVisible(true);
            name_right.setVisible(true);
            leftLabel.setVisible(true);
            centerLabel.setVisible(true);
            rightLabel.setVisible(true);
            leftLabel.setText(playerNames.get(0));
            centerLabel.setText(playerNames.get(1));
            rightLabel.setText(playerNames.get(2));
            gp_left.setVisible(true);
            gp_center.setVisible(true);
            gp_right.setVisible(true);
            gp_left.setImage(new Image("/img/Godcards/" + godPowerNames.get(0).toLowerCase() + ".png"));
            gp_center.setImage(new Image("/img/Godcards/" + godPowerNames.get(1).toLowerCase() + ".png"));
            gp_right.setImage(new Image("/img/Godcards/" + godPowerNames.get(2).toLowerCase() + ".png"));
        }
    }


    public void handleConfirmButton(ActionEvent actionEvent) {
        confirmButton.setDisable(true);
        confirmImage.setImage(new Image("/img/CutConfirmPressed.png"));
        confirmImage.setOpacity(0.8);
        questionLabel.setText("Waiting for other players..");
        gui.updateFirstPlayer(firstPlayerIndex);
    }

    public void handleButton0(ActionEvent actionEvent) {
        leftButton.setOpacity(1);
        centerButton.setVisible(false);
        rightButton.setVisible(false);
        gui.updateFirstPlayer(1);
    }

    public void handleButton1(ActionEvent actionEvent) {
        leftButton.setVisible(false);
        centerButton.setOpacity(1);
        rightButton.setVisible(false);
        gui.updateFirstPlayer(2);
    }

    public void handleButton2(ActionEvent actionEvent) {
        leftButton.setVisible(false);
        centerButton.setVisible(false);
        rightButton.setOpacity(1);
        gui.updateFirstPlayer(numOfPlayers);
    }
}
