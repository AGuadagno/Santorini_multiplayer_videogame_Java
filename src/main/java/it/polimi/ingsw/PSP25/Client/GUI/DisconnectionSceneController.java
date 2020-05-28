package it.polimi.ingsw.PSP25.Client.GUI;

import it.polimi.ingsw.PSP25.Client.ViewObserver;
import javafx.stage.Stage;

public class DisconnectionSceneController implements GUIObservable {

    private GUI gui;
    private Stage window;

    @Override
    public void subscribe(ViewObserver gui) {
        this.gui = (GUI) gui;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    public void handleYesButton() {
        window.close();
        gui.playAgain(true);
    }

    public void handleNoButton() {
        window.close();
        gui.playAgain(false);
    }
}
