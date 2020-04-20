package it.polimi.ingsw.PSP25.Utility.Messages;

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

    public void process(NetworkHandler nh) throws IOException {
        int chosenMovementSpace = workerMovementSelection(validMovementSpaces);
        nh.submit(chosenMovementSpace);
    }

    private int workerMovementSelection(List<SpaceCopy> validMovementSpacesW) {

        Scanner scanner = new Scanner(System.in);

        System.out.println(validMovementSpacesW.toString());
        System.out.println(playerName + ": Choose movement space");
        int chosenMovementSpace = scanner.nextInt();
        while (!(validMovementSpacesW.stream().map(SpaceCopy::getNumber).collect(Collectors.toList())).
                contains(chosenMovementSpace)) {
            System.out.println(chosenMovementSpace + " is not in the valid movement spaces list");
            chosenMovementSpace = scanner.nextInt();
        }

        return chosenMovementSpace;
    }

}
