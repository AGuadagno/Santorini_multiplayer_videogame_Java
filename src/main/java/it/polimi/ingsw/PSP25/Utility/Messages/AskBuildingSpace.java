package it.polimi.ingsw.PSP25.Utility.Messages;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import it.polimi.ingsw.PSP25.Space;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AskBuildingSpace extends Message {

    List<SpaceCopy> validBuildingSpaces;
    String playerName;

    public AskBuildingSpace(String playerName, List<SpaceCopy> validBuildingSpaces){
        this.playerName = playerName;
        this.validBuildingSpaces=validBuildingSpaces;
    }

    public void process(NetworkHandler nh) throws IOException{
    // SELECTION OF BUILDING SPACE
    int chosenBuildingSpace = buildingSpaceSelection(validBuildingSpaces);
    nh.submit(chosenBuildingSpace);
    }

    private int buildingSpaceSelection(List<SpaceCopy> validBuildingSpaces){

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
