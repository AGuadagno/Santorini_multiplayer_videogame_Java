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

        /*Scanner scanner = new Scanner(System.in);

        // SELECTION OF WORKER
        if (validMovementSpacesW1.size() == 0) {
            System.out.println("Worker 1 can't move! Worker 2 is automatically selected");
            workerChoice = 2;
        } else if (validMovementSpacesW2.size() == 0) {
            System.out.println("Worker 2 can't move! Worker 1 is automatically selected");
            workerChoice = 1;
        } else {
            System.out.println(playerName + ": Choose a worker");
            workerChoice = scanner.nextInt();
            while (workerChoice < 1 || workerChoice > 2) {
                System.out.println("Worker number is not valid. Choose 1 or 2");
                workerChoice = scanner.nextInt();
            }
        }

        //SELECTION OF MOVEMENT SPACE
        int chosenMovementSpace = workerMovementSelection(workerChoice == 1 ?
                validMovementSpacesW1 : validMovementSpacesW2);

        int[] workerAndSpace = new int[2];

        workerAndSpace[0] = workerChoice;
        workerAndSpace[1] = chosenMovementSpace;
        */

        int[] workerAndSpace = client.askWorkerMovement(playerName, validMovementSpacesW1, validMovementSpacesW2);

        nh.submit(workerAndSpace);
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
