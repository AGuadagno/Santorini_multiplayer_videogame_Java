package it.polimi.ingsw.PSP25;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameLogic {
    private ActiveEffects activeEffects;
    private Board board;
    private List<Player> playerList;
    private Player nowPlaying;

    public GameLogic() {
        board = new Board();
        board.getSpace(0, 0).setBoard(board);
        playerList = new ArrayList<Player>();
    }

    private void playerInitialization() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("The game begins.");
        int numOfPlayers;

        do {
            System.out.println("Choose the number of players (2 or 3): ");
            numOfPlayers = scanner.nextInt();
        } while (numOfPlayers < 2 || numOfPlayers > 3);

        for (int i = 1; i <= numOfPlayers; i++) {
            String name;
            System.out.println("Player " + i + " enter your name (2 Characters or more): ");
            name = scanner.next();
            while(name.length() < 2){
                System.out.println("Player " + i + " your name it's too short. Enter another name (2 Characters or more): ");
                name = scanner.next();
            }
            playerList.add(new Player(name, name.substring(0, 2).toUpperCase()+i));
        }
        activeEffects = new ActiveEffects(playerList.size());
        nowPlaying = playerList.get(0);

        List<GodPower> allGodPowers = getGodPowerList();

        System.out.print(playerList.get(0).getName() + "(" + playerList.get(0).getID() + ")" + " choose " + numOfPlayers + " god powers from the list: [");
        System.out.print("1 - " + allGodPowers.get(0));
        for (int i = 1; i < allGodPowers.size(); i++) {
            System.out.print(", " + (i + 1) + " - " + allGodPowers.get(i));
        }
        System.out.println("]");
        List<GodPower> selectedGodPowers = new ArrayList<GodPower>();
        List<Integer> selectedIndexes = new ArrayList<>(numOfPlayers);
        for (int i = 1; i <= numOfPlayers; i++) {
            int index = scanner.nextInt();
            while (index <= 0 || index > allGodPowers.size() || selectedIndexes.contains(index - 1)) {
                System.out.println("God power index is not valid. Choose an index between 1 and " +
                        allGodPowers.size() + " and different from other chosen indexes");
                index = scanner.nextInt();
            }
            selectedGodPowers.add(allGodPowers.get(index - 1));
            selectedIndexes.add(index - 1);
        }

        for (int i = 0; i < numOfPlayers - 1; i++) {
            Player currPlayer = playerList.get(i + 1);
            System.out.print(currPlayer.getName() + "(" + currPlayer.getID() + ")" + " choose your god power from the list: [");
            System.out.print("1 - " + selectedGodPowers.get(0));
            for (int k = 1; k < selectedGodPowers.size(); k++) {
                System.out.print(", " + (k + 1) + " - " + selectedGodPowers.get(k));
            }
            System.out.println("]");
            int selectedGodPowerIndex = scanner.nextInt();
            while (selectedGodPowerIndex - 1 >= selectedGodPowers.size() || selectedGodPowerIndex - 1 < 0) {
                System.out.println("God power index is not valid. Choose an index between 1 and " +
                        selectedGodPowers.size());
                selectedGodPowerIndex = scanner.nextInt();
            }
            currPlayer.initializeGodPower(selectedGodPowers.get(selectedGodPowerIndex - 1));
            selectedGodPowers.remove(selectedGodPowerIndex - 1);
        }
        System.out.println(playerList.get(0).getName() + "(" + playerList.get(0).getID() + ")" + " you got: " + selectedGodPowers);
        playerList.get(0).initializeGodPower(selectedGodPowers.get(0));
    }

    private List<GodPower> getGodPowerList() {
        List<GodPower> godPowers = new ArrayList<GodPower>();
        godPowers.add(new Apollo());
        godPowers.add(new Artemis());
        godPowers.add(new Athena());
        godPowers.add(new Atlas());
        godPowers.add(new Demeter());
        godPowers.add(new Hephaestus());
        godPowers.add(new Minotaur());
        godPowers.add(new Pan());
        godPowers.add(new Prometheus());
        return godPowers;
    }

    private void boardSetup() {
        Scanner scanner = new Scanner(System.in);
        int x1, y1, x2, y2, pos1, pos2;

        board.print();

        for (int i = 0; i < playerList.size(); i++) {
            Player currPlayer = playerList.get(i);
            System.out.println(currPlayer.getName() + "(" + currPlayer.getID() + ")" + " it's your turn! Choose the position of your first worker");
            pos1 = scanner.nextInt();
            x1 = pos1 % 5;
            y1 = pos1 / 5;
            while (pos1 < 0 || pos1 > 24 || board.getSpace(x1, y1).hasWorker()) {
                System.out.println("Position not valid. Choose the position of your first worker, between 0 and 24 " +
                        "and not already occupied by other workers");
                pos1 = scanner.nextInt();
                x1 = pos1 % 5;
                y1 = pos1 / 5;
            }

            System.out.println("And now choose the position of your second worker");
            pos2 = scanner.nextInt();
            x2 = pos2 % 5;
            y2 = pos2 / 5;
            while (pos2 < 0 || pos2 > 24 || pos2 == pos1 || board.getSpace(x2, y2).hasWorker()) {
                System.out.println("Position not valid. Choose the position of your second worker, " +
                        "between 0 and 24 and different from " + pos1 + "and not already occupied by other workers");
                pos2 = scanner.nextInt();
                x2 = pos2 % 5;
                y2 = pos2 / 5;
            }

            currPlayer.initializeWorkers(board.getSpace(x1, y1), board.getSpace(x2, y2));
            System.out.println();
            board.print();
        }
    }

    private boolean gameLoop() {
        boolean endGame = false;
        TurnResult turnResult = nowPlaying.getGodPower().turnSequence(nowPlaying);
        Player otherPlayer=playerList.get((playerList.indexOf(nowPlaying) + 1) % playerList.size());

        if(turnResult.equals(TurnResult.WIN)){
            endGame = true;
            manageVictory(nowPlaying);
        }
        else if(turnResult.equals(TurnResult.LOSE)){
            if(playerList.size()==2){
                manageVictory(otherPlayer);
                endGame=true;
            }
            else{
                manageLose(nowPlaying);
                nowPlaying.getWorker1().getSpace().removeWorker();
                nowPlaying.getWorker2().getSpace().removeWorker();
                playerList.remove(nowPlaying);
            }
        }

        System.out.println();
        board.print();
        System.out.println();
        nowPlaying = otherPlayer;
        return endGame;
    }

    public void startGame() {
        playerInitialization();
        boardSetup();
        boolean endGame = false;
        while (!endGame) {
            endGame = gameLoop();
        }
    }

    private void manageVictory(Player player) {
        System.out.println(player.getName() + " (" + player.getID() + ") won the Game! Congratulations!");
        System.out.println("Game ends.");
    }

    private void manageLose(Player player){
        System.out.println(player.getName() + " (" + player.getID() + ") you Lost! You can't Move or Build.");
    }
}
