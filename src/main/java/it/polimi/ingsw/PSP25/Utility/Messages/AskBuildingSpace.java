package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;
import java.io.IOException;
import java.util.List;

/**
 * AskBuildingSpace Message Class.
 * This message is sent during the turn sequence to the current player to ask him to chose a valid building space
 * where the selected worker has to build a block (or a dome).
 */
public class AskBuildingSpace extends Message {

    private List<SpaceCopy> validBuildingSpaces;
    private String playerName;

    public AskBuildingSpace(String playerName, List<SpaceCopy> validBuildingSpaces){
        this.playerName = playerName;
        this.validBuildingSpaces=validBuildingSpaces;
    }

    public void process(NetworkHandler nh, Client client) throws IOException {
        int chosenBuildingSpace = client.askBuildingSpace(playerName, validBuildingSpaces);
        nh.submit(chosenBuildingSpace);
    }
}
