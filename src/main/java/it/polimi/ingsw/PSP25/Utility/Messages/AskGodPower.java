package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import java.io.IOException;
import java.util.List;

/**
 * AskGodPower Message class.
 * This message is sent to all the players (except for the creator of the game) to ask them to chose a
 * GodPower from the List of the God Power chosen by the creator of the game.
 */
public class AskGodPower extends Message {
    private String playerName;
    private List<String> godPowerNames;

    public AskGodPower(String playerName, List<String> godPowerNames) {
        this.playerName = playerName;
        this.godPowerNames = godPowerNames;
    }

    public void process(NetworkHandler nh, Client client) throws IOException {
        int selectedIndex = client.askGodPower(playerName, godPowerNames);
        nh.submit(selectedIndex);
    }
}
