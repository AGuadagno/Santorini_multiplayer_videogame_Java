package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Ask WorkerMovementPrometheus Message Class.
 * This message is sent to the player who controls Prometheus to ask him to select a valid movement
 * space where the selected worker (selected previously) will be moved to.
 */
public class AskWorkerMovementPrometheus extends Message {

    List<SpaceCopy> validMovementSpaces;
    String playerName;

    public AskWorkerMovementPrometheus(String playerName, List<SpaceCopy> validMovementSpaces) {
        this.validMovementSpaces = validMovementSpaces;
        this.playerName = playerName;
    }

    public void process(NetworkHandler nh, Client client) throws IOException {
        int chosenMovementSpace = client.askWorkerMovementPrometheus(playerName, validMovementSpaces);
        nh.submit(chosenMovementSpace);
    }

}

