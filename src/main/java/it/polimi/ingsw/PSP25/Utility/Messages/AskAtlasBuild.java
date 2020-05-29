package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;
import java.io.IOException;
import java.util.List;

/**
 * AskAtlasBuild Message Class.
 * This Message is sent to the player who controls Atlas to ask him if he wants to build a block or a Dome.
 */
public class AskAtlasBuild extends Message {
    private String playerName;
    private List<SpaceCopy> validBuildingSpaces;

    public AskAtlasBuild(String playerName, List<SpaceCopy> validBuildingSpaces) {
        this.playerName = playerName;
        this.validBuildingSpaces = validBuildingSpaces;
    }

    @Override
    public void process(NetworkHandler nh, Client client) throws IOException {
        int[] selectedSpaceAndBuildDome = client.askAtlasBuild(playerName, validBuildingSpaces);
        nh.submit(selectedSpaceAndBuildDome);
    }

}