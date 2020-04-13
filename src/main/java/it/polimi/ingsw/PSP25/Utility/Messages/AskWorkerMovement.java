package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import it.polimi.ingsw.PSP25.Space;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AskWorkerMovement extends Message{

    List<SpaceCopy> validMovementSpacesW1;
    List<SpaceCopy> validMovementSpacesW2;
    String playerName;
    int workerchoice;

    public AskWorkerMovement(String playerName, List<SpaceCopy> validMovementSpacesW1, List<SpaceCopy> validMovementSpacesW2) {
        this.validMovementSpacesW1 = validMovementSpacesW1;
        this.validMovementSpacesW2 = validMovementSpacesW2;
        this.playerName = playerName;
    }

    public void process(NetworkHandler nh) throws IOException {

        Scanner scanner = new Scanner(System.in);

        // SELECTION OF WORKER
        if (validMovementSpacesW1.size() == 0) {
            System.out.println("Worker 1 can't move! Worker 2 is automatically selected");
            workerchoice = 2;
        } else if (validMovementSpacesW2.size() == 0) {
            System.out.println("Worker 2 can't move! Worker 1 is automatically selected");
            workerchoice = 1;
        } else {
            System.out.println(playerName + ": Choose a worker");
            workerchoice = scanner.nextInt();
            while (workerchoice < 1 || workerchoice > 2) {
                System.out.println("Worker number is not valid. Choose 1 or 2");
                workerchoice = scanner.nextInt();
            }
        }

        //SELECTION OF MOVEMENT SPACE
        int chosenMovementSpace = workerMovementSelection(workerchoice == 1 ?
                validMovementSpacesW1 : validMovementSpacesW2);

        int[] workerAndSpace = new int[2];

        workerAndSpace[0] = workerchoice;
        workerAndSpace[1] = chosenMovementSpace;

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
