package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * AskWorkerMovement Message Class.
 * This message is sent during the turn sequence to the current player to ask him to chose a Worker and a valid movement
 * space where the selected worker will be moved to.
 */
public class AskWorkerMovement extends Message{

    List<SpaceCopy> validMovementSpacesW1;
    List<SpaceCopy> validMovementSpacesW2;
    String playerName;
    int workerChoice;

    public AskWorkerMovement(String playerName, List<SpaceCopy> validMovementSpacesW1, List<SpaceCopy> validMovementSpacesW2) {
        this.validMovementSpacesW1 = validMovementSpacesW1;
        this.validMovementSpacesW2 = validMovementSpacesW2;
        this.playerName = playerName;
    }

    public void process(NetworkHandler nh, Client client) throws IOException {
        int[] workerAndSpace = client.askWorkerMovement(playerName, validMovementSpacesW1, validMovementSpacesW2);
        nh.submit(workerAndSpace);
    }
}
