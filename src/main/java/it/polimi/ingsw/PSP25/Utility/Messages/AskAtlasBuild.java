package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * AskAtlasBuild Message Class.
 * This Message is sent to the player who controls Atlas to ask him if he wants to build a block or a Dome.
 */
public class AskAtlasBuild extends Message {
    private String playerName;
    private List<SpaceCopy> validBuildingSpaces;

    public AskAtlasBuild(String playerName, List<SpaceCopy> validBuildingSpaces) {
        this.playerName = playerName;
        this.validBuildingSpaces = validBuildingSpaces;
    }

    @Override
    public void process(NetworkHandler nh, Client client) throws IOException {
        int selectedSpace = buildingSpaceSelection(validBuildingSpaces);
        SpaceCopy chosenBuildingSpace = null;
        String answer = null;
        int[] selectedSpaceAndBuildDome = new int[2];

        int x = selectedSpace % 5;
        int y = selectedSpace / 5;
        for (SpaceCopy space : validBuildingSpaces) {
            if (space.getX() == x && space.getY() == y)
                chosenBuildingSpace = space;
        }


        if (chosenBuildingSpace.getTowerHeight() < 3) {
            System.out.println("Do you want to build a dome or a block? (b = block , d = dome)");
            Scanner scanner = new Scanner(System.in);
            answer = scanner.next();
            while (!(answer.equals("d") || answer.equals("b"))) {
                System.out.println("Your Choice is not valid. insert 'b' to build a block, 'd' to build a dome");
                answer = scanner.next();
            }
        } else {
            answer = "d";
        }

        selectedSpaceAndBuildDome[0] = selectedSpace;
        if (answer.equals("b"))
            selectedSpaceAndBuildDome[1] = 0;
        else if (answer.equals("d"))
            selectedSpaceAndBuildDome[1] = 1;

        nh.submit(selectedSpaceAndBuildDome);
    }

    private int buildingSpaceSelection(List<SpaceCopy> validBuildingSpaces) {

        Scanner scanner = new Scanner(System.in);

        System.out.println(validBuildingSpaces.toString());
        System.out.println(playerName + ": Choose building space");
        int chosenBuildingSpace = scanner.nextInt();
        while (!(validBuildingSpaces.stream().map(SpaceCopy::getNumber).collect(Collectors.toList())).
                contains(chosenBuildingSpace)) {
            System.out.println(chosenBuildingSpace + " is not in the valid building spaces list");
            chosenBuildingSpace = scanner.nextInt();
        }

        return chosenBuildingSpace;
    }
}