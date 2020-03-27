package it.polimi.ingsw.PSP25;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GodPower {

    public GodPower() {
    }

    protected List<Space> getValidMovementSpaces(Worker worker) {
        ArrayList<Space> validMovementSpaces = new ArrayList<Space>();

        for (Space space : worker.getSpace().getAdjacentSpaces()) {

            if (space.getWorker() == null && space.getTowerHeight() - worker.getSpace().getTowerHeight() <= 1
                    && !space.hasDome()) {
                validMovementSpaces.add(space);
            }
        }
        return validMovementSpaces;
    }

    public List<Space> getValidBuildSpaces(Worker worker) {
        ArrayList<Space> validBuildSpaces = new ArrayList<Space>();

        for (Space space : worker.getSpace().getAdjacentSpaces()) {

            if (space.getWorker() == null && !space.hasDome() && space.getTowerHeight() <= 3) {
                validBuildSpaces.add(space);
            }
        }
        return validBuildSpaces;
    }

    protected void moveWorker(Worker worker, Space space) {
        worker.moveTo(space);
    }

    protected void buildBlock(Space space) {
        if (space.getTowerHeight() == 3) {
            space.addDome();
        } else {
            space.increaseTowerHeight();
        }
    }

    protected boolean verifyWin(Worker worker) {

        //TEMP
        if (worker.getHeightBeforeMove() == 2 && worker.getSpace().getTowerHeight() == 3) {
            return true;
        } else {
            return false;
        }
        //END TEMP

    }

    // Return true is someone wins
    public boolean turnSequence(Player player) {
        List<Space> validMovementSpacesW1;
        List<Space> validMovementSpacesW2;
        List<Space> validBuildSpaces;
        Worker selectedWorker;
        Space selectedMovementSpace = null;
        Space selectedBuildingSpace = null;

        validMovementSpacesW1 = getValidMovementSpaces(player.getWorker1());
        validMovementSpacesW2 = getValidMovementSpaces(player.getWorker2());

        // Player selects a Worker
        // selectedWorker = ...
        // Player moves selected Worker in a valid space
        // TEMP
        System.out.println(player.getName() + ": Choose a worker");
        Scanner scanner = new Scanner(System.in);
        String workerchoice = scanner.next();
        if (workerchoice.equals("1")) {
            selectedWorker = player.getWorker1();
            System.out.println(validMovementSpacesW1.toString());
            System.out.println(player.getName() + ": Choose movement space");
            int chosenmovementspace = scanner.nextInt();
            int x = chosenmovementspace % 5;
            int y = chosenmovementspace / 5;
            for (Space space: validMovementSpacesW1) {
                if(space.getX() == x && space.getY() == y)
                    selectedMovementSpace = space;
            }
        } else {
            selectedWorker = player.getWorker2();
            System.out.println(validMovementSpacesW2.toString());
            System.out.println(player.getName() + ": Choose movement space");
            int chosenmovementspace = scanner.nextInt();
            int x = chosenmovementspace % 5;
            int y = chosenmovementspace / 5;
            for (Space space: validMovementSpacesW2) {
                if(space.getX() == x && space.getY() == y)
                    selectedMovementSpace = space;
            }
        }
        // END TEMP

        moveWorker(selectedWorker, selectedMovementSpace);

        if (verifyWin(selectedWorker) == true) {
            return true;
        }

        validBuildSpaces = getValidBuildSpaces(selectedWorker);
        // Player builds in a valid space
        // TEMP
        System.out.println(validBuildSpaces.toString());
        System.out.println(player.getName() + ": Choose building space");
        int chosenbuildingspace = scanner.nextInt();
        int x = chosenbuildingspace % 5;
        int y = chosenbuildingspace / 5;
        for (Space space: validBuildSpaces) {
            if(space.getX() == x && space.getY() == y)
                selectedBuildingSpace = space;
        }
        // END TEMP
        buildBlock(selectedBuildingSpace);

        return false;

    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}
