package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        //int chosenBuildingSpace = buildingSpaceSelection(validBuildingSpaces);
        int chosenBuildingSpace = client.askDemeterSecondBuilding(playerName, validBuildingSpaces);
        nh.submit(chosenBuildingSpace);
    }

    /*private int buildingSpaceSelection(List<SpaceCopy> validBuildingSpaces) {

        int chosenBuildingSpace = -1;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you want to build an additional block? y/n");
        String answer = scanner.next();
        if (answer.equals("y")) {
            System.out.println(validBuildingSpaces.toString());
            System.out.println(playerName + ": Choose building space");
            // Selection of the second building space
            chosenBuildingSpace = scanner.nextInt();
            while (!(validBuildingSpaces.stream().map(SpaceCopy::getNumber).collect(Collectors.toList())).
                    contains(chosenBuildingSpace)) {
                System.out.println(chosenBuildingSpace + " is not in the valid building spaces list");
                chosenBuildingSpace = scanner.nextInt();
            }
        }

        return chosenBuildingSpace;
    }*/
}
