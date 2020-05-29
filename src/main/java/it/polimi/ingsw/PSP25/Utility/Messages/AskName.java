package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import java.io.IOException;

/**
 * AskName Message Class.
 * This message is sent to all players to ask their name.
 */
public class AskName extends Message {
    private int playerNumber;
    private String question;

    public AskName(int playerNumber) {
        this.playerNumber = playerNumber;
        question = "Player " + playerNumber + " enter your name (2 Characters or more): ";
    }

    public void process(NetworkHandler nh, Client client) throws IOException {
        String name = client.askName(question);
        nh.submit(name);
    }
}

