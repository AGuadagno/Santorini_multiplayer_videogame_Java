package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;

/**
 * AnnounceLose Message class.
 * This message is sent to the player who won the game.
 */
public class AnnounceVictory extends Message {
    private String playerName;

    public AnnounceVictory(String playerName) {
        this.playerName = playerName;
    }

    public void process(NetworkHandler nh, Client client) {
        client.announceVictory(playerName);
    }

}
