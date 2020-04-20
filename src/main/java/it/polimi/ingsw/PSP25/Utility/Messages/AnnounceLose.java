package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.NetworkHandler;

/**
 * AnnounceLose Message class.
 * This message is sent to the player who has lost the game.
 */
public class AnnounceLose extends Message {
    private String playerName;

    public AnnounceLose(String playerName) {
        this.playerName = playerName;
    }

    /**
     * The player client prints the message of victory.
     *
     * @param nh network handler of the client who is the recipient of the message.
     */
    public void process(NetworkHandler nh) {
        System.out.println(playerName + " lost the Game! Can't move or build!");
    }

}
