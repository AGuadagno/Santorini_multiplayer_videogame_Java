package it.polimi.ingsw.PSP25.Server.Messages;

import it.polimi.ingsw.PSP25.Client.NetworkHandler;

import java.io.IOException;

public class AskNumberOfPlayers extends Message {

    private String question = "Select the number of players: ";

    public void process(NetworkHandler nh) throws IOException {
        System.out.println(question);
        nh.scanAndSubmit();
    }

}
