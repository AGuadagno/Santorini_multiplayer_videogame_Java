package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import java.io.IOException;

/**
 * AskNumberOfPlayers Message Class.
 * This Message is sent to the player who crates the game to ask him the number of players he wants for the game.
 */

public class AskNumberOfPlayers extends Message {
    private String question = "Select the number of players: ";

    public void process(NetworkHandler nh, Client client) throws IOException {
        int numOfPlayers = client.askNumOfPlayers(question);
        nh.submit(numOfPlayers);
    }

}
