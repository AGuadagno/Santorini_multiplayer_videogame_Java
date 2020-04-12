package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.NetworkHandler;

import java.io.IOException;
import java.util.List;

public class TellAssignedGodPower extends Message {
    private String playerName;
    private List<String> godPowerName;

    public TellAssignedGodPower(String playerName, List<String> godPowerName) {
        this.playerName = playerName;
        this.godPowerName = godPowerName;
    }

    public void process(NetworkHandler nh) throws IOException {
        System.out.println(playerName + " you got: " + godPowerName);
    }

}
