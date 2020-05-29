package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;
import java.io.IOException;
import java.util.List;

/**
 * AskDemeterSecondBuilding Message Class.
 * This message is sent to the player who controls Demeter to ask him if he wants to build a second time in a different space.
 * If the answer is yes, the player is asked to chose a second valid building space (different from the previous building space)
 * where the selected worker has to build.
 */
public class AskDemeterSecondBuilding extends Message {
    private List<SpaceCopy> validBuildingSpaces;
    private String playerName;

    public AskDemeterSecondBuilding(String playerName, List<SpaceCopy> validBuildingSpaces) {
        this.playerName = playerName;
        this.validBuildingSpaces = validBuildingSpaces;
    }

    public void process(NetworkHandler nh, Client client) throws IOException {
        int chosenBuildingSpace = client.askDemeterSecondBuilding(playerName, validBuildingSpaces);
        nh.submit(chosenBuildingSpace);
    }
}
