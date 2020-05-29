package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;
import java.io.IOException;
import java.util.List;

/**
 * AskArtemisSecondMove Message Class.
 * This message is sent to the player who controls Artemis to ask him if he wants to move for a second time.
 * If the player wants to move for a second time, he's asked to chose a valid movement space,
 * then the worker is moved to that space.
 */
public class AskArtemisSecondMove extends Message {
    List<SpaceCopy> validSecondMovementSpaces;
    String playerName;

    public AskArtemisSecondMove(String playerName, List<SpaceCopy> validSecondMovementSpaces) {
        this.validSecondMovementSpaces = validSecondMovementSpaces;
        this.playerName = playerName;
    }

    @Override
    public void process(NetworkHandler nh, Client client) throws IOException {
        int chosenMovementSpace = client.askArtemisSecondMove(playerName, validSecondMovementSpaces);
        nh.submit(chosenMovementSpace);
    }
}
