package it.polimi.ingsw.PSP25;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Artemis extends GodPower {
    public Artemis(ActiveEffects activeEffects) {
        super(activeEffects);
    }

    // OVERVIEW: I tuoi lavoratori possono muoversi una volta in più, ma non tornare nello spazio iniziale

    @Override
    public TurnResult turnSequence(Player player, ActiveEffects activeEffects) {
        List<Space> validMovementSpacesW1;
        List<Space> validMovementSpacesW2;
        List<Space> validBuildSpaces;
        Worker selectedWorker;
        Scanner scanner = new Scanner(System.in);
        int workerchoice;
        Space selectedMovementSpace = null;
        Space selectedBuildingSpace = null;

        validMovementSpacesW1 = getValidMovementSpaces(player.getWorker1());
        validMovementSpacesW2 = getValidMovementSpaces(player.getWorker2());

        //DEBUG
        activeEffects.debugPrint();
        //END DEBUG

        if (verifyLoseByMovement(validMovementSpacesW1, validMovementSpacesW2)) {
            return TurnResult.LOSE;
        }

        // Player selects a Worker
        // selectedWorker = ...
        // Player moves selected Worker in a valid space
        // TEMP
        if (validMovementSpacesW1.size() == 0) {
            System.out.println("Worker 1 can't move! Worker 2 is automatically selected");
            workerchoice = 2;
        } else if (validMovementSpacesW2.size() == 0) {
            System.out.println("Worker 2 can't move! Worker 1 is automatically selected");
            workerchoice = 1;
        } else {
            System.out.println(player.getName() + "(" + player.getID() + ")" + ": Choose a worker");
            workerchoice = scanner.nextInt();
            while (workerchoice < 1 || workerchoice > 2) {
                System.out.println("Worker number is not valid. Choose 1 or 2");
                workerchoice = scanner.nextInt();
            }
        }
        if (workerchoice == 1) {
            selectedWorker = player.getWorker1();
            System.out.println(validMovementSpacesW1.toString());
            System.out.println(player.getName() + "(" + player.getID() + ")" + ": Choose movement space");
            int chosenMovementSpace = scanner.nextInt();
            while (!(validMovementSpacesW1.stream().map(Space::getNumber).collect(Collectors.toList())).
                    contains(chosenMovementSpace)) {
                System.out.println(chosenMovementSpace + " is not in the valid movement spaces list");
                chosenMovementSpace = scanner.nextInt();
            }
            int x = chosenMovementSpace % 5;
            int y = chosenMovementSpace / 5;
            for (Space space : validMovementSpacesW1) {
                if (space.getX() == x && space.getY() == y)
                    selectedMovementSpace = space;
            }
        } else {
            selectedWorker = player.getWorker2();
            System.out.println(validMovementSpacesW2.toString());
            System.out.println(player.getName() + "(" + player.getID() + ")" + ": Choose movement space");
            int chosenmovementspace = scanner.nextInt();
            while (!(validMovementSpacesW2.stream().map(Space::getNumber).collect(Collectors.toList())).
                    contains(chosenmovementspace)) {
                System.out.println(chosenmovementspace + " is not in the valid movement spaces list");
                chosenmovementspace = scanner.nextInt();
            }
            int x = chosenmovementspace % 5;
            int y = chosenmovementspace / 5;
            for (Space space : validMovementSpacesW2) {
                if (space.getX() == x && space.getY() == y)
                    selectedMovementSpace = space;
            }
        }
        // END TEMP

        moveWorker(selectedWorker, selectedMovementSpace);
        return null;
    }
}


