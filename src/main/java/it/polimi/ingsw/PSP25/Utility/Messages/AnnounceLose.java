package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.NetworkHandler;

public class AnnounceLose extends Message {
    private String playerName;

    public AnnounceLose(String playerName) {
        this.playerName = playerName;
    }

    public void process(NetworkHandler nh) {
        System.out.println(playerName + " lost the Game! Can't move or build!");
    }

}
