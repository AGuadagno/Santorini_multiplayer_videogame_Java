package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;
import java.io.IOException;
import java.util.List;

/**
 * AskHephaestusBuild Message Class.
 * This Message is sent to the player who controls Hephaestus to ask him if he wants to build 2 block in the
 * selected building space.
 */
public class AskHephaestusBuild extends Message {

    private List<SpaceCopy> validBuildingSpaces;
    private String playerName;

    public AskHephaestusBuild(String playerName, List<SpaceCopy> validBuildingSpaces) {
        this.playerName = playerName;
        this.validBuildingSpaces = validBuildingSpaces;
    }

    public void process(NetworkHandler nh, Client client) throws IOException {
        int[] spaceAndDoubleBuilding = client.askHephaestusBuild(playerName, validBuildingSpaces);
        nh.submit(spaceAndDoubleBuilding);
    }
}
