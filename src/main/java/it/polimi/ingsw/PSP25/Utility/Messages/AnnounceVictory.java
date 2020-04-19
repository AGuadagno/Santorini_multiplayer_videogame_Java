package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.NetworkHandler;

public class AnnounceVictory extends Message {
    private String playerName;

    public AnnounceVictory(String playerName) {
        this.playerName = playerName;
    }

    public void process(NetworkHandler nh) {
        System.out.println(playerName + " won the Game! Congratulations!");
    }

}
