package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import java.io.IOException;
import java.util.List;


/**
 * AskAllGodPowers Message Class.
 * This message is sent to the player who creates the game.
 * He's asked to chose the list of God Power that will be used by the players during the game.
 */
public class AskAllGodPowers extends Message {
    private String playerName;
    private int numOfPlayers;
    private List<String> godPowerNames;

    public AskAllGodPowers(String playerName, int numOfPlayers, List<String> godPowerNames) {
        this.playerName = playerName;
        this.numOfPlayers = numOfPlayers;
        this.godPowerNames = godPowerNames;
    }

    public void process(NetworkHandler nh, Client client) throws IOException {
        List<Integer> selectedIndexes = client.askAllGodPowers(playerName, numOfPlayers, godPowerNames);
        nh.submit(selectedIndexes);
    }
}
