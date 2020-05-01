package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * AskHephaestusBuild Message Class.
 * This Message is sent to the player who controls Hephaestus to ask him if he wants to build 2 block in the
 * selected building space.
 */
public class AskHephaestusBuild extends Message {

    List<SpaceCopy> validBuildingSpaces;
    String playerName;

    public AskHephaestusBuild(String playerName, List<SpaceCopy> validBuildingSpaces) {
        this.playerName = playerName;
        this.validBuildingSpaces = validBuildingSpaces;
    }

    public void process(NetworkHandler nh, Client client) throws IOException {
        // SELECTION OF BUILDING SPACE
        int[] spaceAndDoubleBuilding = buildingSpaceSelection(validBuildingSpaces);
        nh.submit(spaceAndDoubleBuilding);
    }

    private int[] buildingSpaceSelection(List<SpaceCopy> validBuildingSpaces) {

        int[] spaceAndDoubleBuilding = new int[2];

        Scanner scanner = new Scanner(System.in);

        System.out.println(validBuildingSpaces.toString());
        System.out.println(playerName + ": Choose building space");
        int chosenBuildingSpace = scanner.nextInt();
        while (!(validBuildingSpaces.stream().map(SpaceCopy::getNumber).collect(Collectors.toList())).
                contains(chosenBuildingSpace)) {
            System.out.println(chosenBuildingSpace + " is not in the valid building spaces list");
            chosenBuildingSpace = scanner.nextInt();
        }

        SpaceCopy space = null;

        int x = chosenBuildingSpace % 5;
        int y = chosenBuildingSpace / 5;
        for (SpaceCopy spaceCopy : validBuildingSpaces) {
            if (spaceCopy.getX() == x && spaceCopy.getY() == y)
                space = spaceCopy;
        }

        // space.getTowerHeight() < 2 perchè l'altezza non viene davvero incrementata tra i 2 step
        if (space.getTowerHeight() < 2) { // can't build a dome
            // Choice to build another block
            System.out.println("Do you want to build an additional block in " + space.toString() + " ?" + " y/n");
            String answer = scanner.next();
            while (!(answer.equals("y") || answer.equals("n"))) {
                System.out.println("Answer is not valid! y = yes, n = no!");
                answer = scanner.next();
            }

            if (answer.equals("y")) {
                spaceAndDoubleBuilding[1] = 2;
            } else if (answer.equals("n")) {
                spaceAndDoubleBuilding[1] = 1;
            }
        } else {
            spaceAndDoubleBuilding[1] = 1;
        }

        spaceAndDoubleBuilding[0] = chosenBuildingSpace;
        return spaceAndDoubleBuilding;
    }

}
