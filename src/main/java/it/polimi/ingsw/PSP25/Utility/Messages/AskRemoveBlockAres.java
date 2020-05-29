package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;
import java.io.IOException;
import java.util.List;

/**
 * AskRemoveBlock ares is a message sent to the player who controls Ares to ask him if he wants to remove a block
 * adjacent to his unmoved workers.
 */
public class AskRemoveBlockAres extends Message {
    private final int nonSelectedWorkerNumber;
    private final List<SpaceCopy> validRemoveSpaces;
    private final String playerName;

    public AskRemoveBlockAres(String playerName, List<SpaceCopy> validRemoveSpaces, int nonSelectedWorkerNumber) {
        this.playerName = playerName;
        this.validRemoveSpaces = validRemoveSpaces;
        this.nonSelectedWorkerNumber = nonSelectedWorkerNumber;
    }

    public void process(NetworkHandler nh, Client client) throws IOException {
        int selectedRemoveSpace = client.askRemoveBlockAres(playerName, validRemoveSpaces, nonSelectedWorkerNumber);
        nh.submit(selectedRemoveSpace);
    }
}
