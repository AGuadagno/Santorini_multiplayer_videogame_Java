package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;

import java.io.IOException;
import java.util.List;

public class AskFirstPlayer extends Message {
    private List<String> playerNames;

    public AskFirstPlayer(List<String> playerNames) {
        this.playerNames = playerNames;
    }

    public void process(NetworkHandler nh, Client client) throws IOException {
        int firstPlayerIndex = client.askFirstPlayer(playerNames);
        nh.submit(firstPlayerIndex);
    }
}
