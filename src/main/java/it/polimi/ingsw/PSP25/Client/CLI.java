package it.polimi.ingsw.PSP25.Client;

import it.polimi.ingsw.PSP25.Utility.SpaceCopy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CLI implements ViewObservable {
    private Scanner scanner = new Scanner(System.in);
    private ViewObserver client;

    @Override
    public void askIPAddress() {
        System.out.println("IP address of server?");
        String ip = scanner.nextLine();

        client.updateIPAddress(ip);
    }

    @Override
    public void setConnectionMessage(String s) {
        System.out.println(s);
    }

    @Override
    public void askNumOfPlayers(String question) {
        int numOfPlayers;
        do {
            System.out.println(question);
            numOfPlayers = scanner.nextInt();
        } while (numOfPlayers < 2 || numOfPlayers > 3);
        client.updateNumOfPlayers(numOfPlayers);
    }

    @Override
    public void askName(String question) {
        System.out.println(question);
        String name = scanner.next();
        while (name.length() < 2) {
            System.out.println("Your name it's too short. Enter another name (2 Characters or more): ");
            name = scanner.next();
        }
        client.updateName(name);
    }

    @Override
    public void askAllGodPowers(String playerName, int numOfPlayers, List<String> godPowerNames) {

        System.out.print(playerName + " choose " + numOfPlayers + " god powers from the list: [");
        System.out.print("1 - " + godPowerNames.get(0));
        for (int i = 1; i < godPowerNames.size(); i++) {
            System.out.print(", " + (i + 1) + " - " + godPowerNames.get(i));
        }
        System.out.println("]");
        List<Integer> selectedIndexes = new ArrayList<>(numOfPlayers);
        for (int i = 1; i <= numOfPlayers; i++) {
            int index = scanner.nextInt();
            while (index <= 0 || index > godPowerNames.size() || selectedIndexes.contains(index - 1)) {
                System.out.println("God power index is not valid. Choose an index between 1 and " +
                        godPowerNames.size() + " and different from other chosen indexes");
                index = scanner.nextInt();
            }
            selectedIndexes.add(index - 1);
        }

        String printedChoice = "You have chosen: ";
        for (int i = 0; i < selectedIndexes.size(); i++) {
            printedChoice += godPowerNames.get(selectedIndexes.get(i));
            if (i < selectedIndexes.size() - 1)
                printedChoice += ", ";
        }
        System.out.println(printedChoice);

        client.updateAllGodPower(selectedIndexes);
    }

    @Override
    public void askGodPower(String playerName, List<String> godPowerNames) {
        System.out.print(playerName + " choose your god power from the list: [");
        System.out.print("1 - " + godPowerNames.get(0));
        for (int i = 1; i < godPowerNames.size(); i++) {
            System.out.print(", " + (i + 1) + " - " + godPowerNames.get(i));
        }
        System.out.println("]");

        // TODO: verificare eccezione nel caso in cui inserisca una stringa
        int selectedIndex = scanner.nextInt();
        while (selectedIndex - 1 >= godPowerNames.size() || selectedIndex - 1 < 0) {
            System.out.println("God power index is not valid. Choose an index between 1 and " +
                    godPowerNames.size());
            selectedIndex = scanner.nextInt();
        }

        client.updateGodPower(selectedIndex);
    }

    @Override
    public void showPlayersGodPowers(List<String> playerNames, List<String> godPowerNames) {
        String s = "";
        for (int i = 0; i < playerNames.size(); i++) {
            s = s + playerNames.get(i) + " has " + godPowerNames.get(i) + "\n";
        }

        System.out.println(s);
    }

    @Override
    public void showBoard(SpaceCopy[][] board) {
        for (int j = 0; j < 5; j++) {
            StringBuilder[] rowLines = new StringBuilder[5];
            for (int k = 0; k < 5; k++) {
                rowLines[k] = new StringBuilder("");
                rowLines[0].append("+ - - - - ");    //first line: cell separator
            }
            rowLines[0].append("+"); //last '+'

            for (int i = 0; i < 5; i++) {
                int cellNum = (5 * j) + i;

                rowLines[1].append("|" + cellNum + (cellNum < 10 ? "        " : "       "));
                rowLines[2].append("|   H:" + board[i][j].getTowerHeight() +
                        (board[i][j].hasDome() ? " D " : "   "));
                rowLines[3].append("|   ");
                rowLines[4].append("|   ");
                SpaceCopy currSpace = board[i][j];
                if (currSpace.hasWorker()) {
                    rowLines[3].append(currSpace.getID() + "   ");
                    rowLines[4].append("W ");
                    if (currSpace.getWorkerNumber() == 1)
                        rowLines[4].append("1   ");
                    else if (currSpace.getWorkerNumber() == 2)
                        rowLines[4].append("2   ");
                    else {
                        try {
                            throw new Exception();
                        } catch (Exception e) {
                            System.out.println("Worker in cell " + cellNum + "doesn't correspond to his Player");
                            e.printStackTrace();
                        }
                    }
                } else {
                    rowLines[3].append("      ");
                    rowLines[4].append("      ");
                }
            }
            for (int k = 1; k < 5; k++) {
                rowLines[k].append("|"); //last '|'
            }
            for (int l = 0; l < 5; l++) {
                System.out.println(rowLines[l]);
            }
        }
        for (int k = 0; k < 5; k++) {
            System.out.print("+ - - - - ");    //last line: cell separator
        }
        System.out.print("+"); //last '+'
        System.out.println();
    }

    @Override
    public void askWorkerPosition(String playerName, int workerNumber, int previousPos, SpaceCopy[][] board) {

        System.out.println(playerName + " it's your turn! Choose the position of your "
                + ((workerNumber == 1) ? "first" : "second") + " worker");
        int pos = scanner.nextInt();
        int x = pos % 5;
        int y = pos / 5;
        while (pos < 0 || pos > 24 || pos == previousPos || board[x][y].hasWorker()) {
            System.out.println("Position not valid. Choose the position of your "
                    + ((workerNumber == 1) ? "first" : "second") + " worker, between 0 and 24 " +
                    "and not already occupied by other workers");
            pos = scanner.nextInt();
            x = pos % 5;
            y = pos / 5;
        }

        client.updateWorkerPosition(pos);
    }

    @Override
    public void askWorkerMovement(String playerName, List<SpaceCopy> validMovementSpacesW1, List<SpaceCopy> validMovementSpacesW2) {
        int workerChoice = -1;

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
        int chosenMovementSpace = workerMovementSelection(playerName, workerChoice == 1 ?
                validMovementSpacesW1 : validMovementSpacesW2);

        int[] workerAndSpace = new int[2];

        workerAndSpace[0] = workerChoice;
        workerAndSpace[1] = chosenMovementSpace;

        client.updateWorkerMovement(workerAndSpace);
    }


    private int workerMovementSelection(String playerName, List<SpaceCopy> validMovementSpacesW) {

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

    @Override
    public void askBuildingSpace(String playerName, List<SpaceCopy> validBuildingSpaces) {
        System.out.println(validBuildingSpaces.toString());
        System.out.println(playerName + ": Choose building space");
        int chosenBuildingSpace = scanner.nextInt();
        while (!(validBuildingSpaces.stream().map(SpaceCopy::getNumber).collect(Collectors.toList())).
                contains(chosenBuildingSpace)) {
            System.out.println(chosenBuildingSpace + " is not in the valid building spaces list");
            chosenBuildingSpace = scanner.nextInt();
        }

        client.updateBuildingSpace(chosenBuildingSpace);
    }

    @Override
    public void askAtlasBuild(String playerName, List<SpaceCopy> validBuildingSpaces) {
        int selectedSpace = buildingSpaceSelection(playerName, validBuildingSpaces);
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

        client.updateAtlasBuild(selectedSpaceAndBuildDome);
    }


    private int buildingSpaceSelection(String playerName, List<SpaceCopy> validBuildingSpaces) {
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

    @Override
    public void askBuildBeforeMovePrometheus(String playerName, boolean w1CanMove, boolean w1CanBuild, boolean w2CanMove, boolean w2CanBuild) {
        String answer = null;
        int workerChoice = -1;

        // SELECTION OF WORKER
        if (!w1CanMove) {
            System.out.println("Worker 1 can't move! Worker 2 is automatically selected");
            workerChoice = 2;
        } else if (!w2CanMove) {
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

        if (workerChoice == 1 ? (w1CanBuild) : (w2CanBuild)) {
            System.out.println("Do you want to build before move? y|n");
            answer = scanner.next();
            while (!(answer.equals("y") || answer.equals("n"))) {
                System.out.println("Not valid choice. Do you want to build before move? y|n");
                answer = scanner.next();
            }
        }

        int[] workerAndBuildBeforeMove = new int[2];
        workerAndBuildBeforeMove[0] = workerChoice;

        if (answer.equals("y")) {
            workerAndBuildBeforeMove[1] = 1;
        } else {
            workerAndBuildBeforeMove[1] = 0;
        }

        client.updateBuildBeforeMovePrometheus(workerAndBuildBeforeMove);
    }

    @Override
    public void askWorkerMovementPrometheus(String playerName, List<SpaceCopy> validMovementSpaces) {
        System.out.println(validMovementSpaces.toString());
        System.out.println(playerName + ": Choose movement space");
        int chosenMovementSpace = scanner.nextInt();

        while (!(validMovementSpaces.stream().map(SpaceCopy::getNumber).collect(Collectors.toList())).
                contains(chosenMovementSpace)) {
            System.out.println(chosenMovementSpace + " is not in the valid movement spaces list");
            chosenMovementSpace = scanner.nextInt();
        }

        client.updateWorkerMovementPrometheus(chosenMovementSpace);
    }

    @Override
    public void subscribe(ViewObserver client) {
        this.client = client;
    }

    @Override
    public void askArtemisSecondMove(String playerName, List<SpaceCopy> validSecondMovementSpaces) {
        String answer;

        System.out.println("Do you want to move your Worker for the second time? y/n");
        answer = scanner.next();
        while (!answer.equals("y") && !answer.equals("n")) {
            System.out.println("Your choice is not valid! Choose again!");
            answer = scanner.next();
        }

        int chosenMovementSpace = -1;
        if (answer.equals("y")) {
            System.out.println(validSecondMovementSpaces.toString());
            System.out.println(playerName + ": Choose movement space");
            chosenMovementSpace = scanner.nextInt();
            while (!(validSecondMovementSpaces.stream().map(SpaceCopy::getNumber).collect(Collectors.toList())).
                    contains(chosenMovementSpace)) {
                System.out.println(chosenMovementSpace + " is not in the valid movement spaces list");
                chosenMovementSpace = scanner.nextInt();
            }
        }

        client.updateArtemisSecondMove(chosenMovementSpace);
    }

    @Override
    public void askDemeterSecondBuilding(String playerName, List<SpaceCopy> validBuildingSpaces) {
        int chosenBuildingSpace = -1;

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

        //client.updateDemeterSecondBuilding(chosenBuildingSpace);
        client.updateBuildingSpace(chosenBuildingSpace);
    }

    @Override
    public void askHephaestusBuild(String playerName, List<SpaceCopy> validBuildingSpaces) {
        int[] spaceAndDoubleBuilding = new int[2];

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

        client.updateHephaestusBuild(spaceAndDoubleBuilding);
    }

    @Override
    public void announceVictory(String playerName) {
        System.out.println(playerName + " won the Game! Congratulations!");
    }

    @Override
    public void announceLose(String playerName) {
        System.out.println(playerName + " lost the Game! Can't move or build!");
    }

    @Override
    public void manageServerDisconnection() {
        System.out.println("Disconnected from server");
    }
}