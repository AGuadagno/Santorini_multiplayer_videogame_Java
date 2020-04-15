package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AskWorkerMovementPrometheus extends Message {

    List<SpaceCopy> validMovementSpaces;
    String playerName;

    public AskWorkerMovementPrometheus(String playerName, List<SpaceCopy> validMovementSpaces) {
        this.validMovementSpaces = validMovementSpaces;
        this.playerName = playerName;
    }

    public void process(NetworkHandler nh) throws IOException {
        //SELECTION OF MOVEMENT SPACE
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
