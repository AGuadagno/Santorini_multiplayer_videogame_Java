package it.polimi.ingsw.PSP25;

import it.polimi.ingsw.PSP25.Server.ClientHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.PSP25.Utility.Utilities.deepCopyBoard;
import static it.polimi.ingsw.PSP25.Utility.Utilities.deepCopyGodPowerNames;

public class GameLogic {
    private ActiveEffects activeEffects;
    private Board board;
    private List<Player> playerList;
    private Player nowPlaying;
    private List<ClientHandler> clientHandlerList;

    public GameLogic(List<ClientHandler> clientHandlerList) {
        board = new Board();
        board.getSpace(0, 0).setBoard(board);
        playerList = new ArrayList<Player>();
        this.clientHandlerList = clientHandlerList;
    }

    private void playerInitialization() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("The game begins.");
        int numOfPlayers = clientHandlerList.size();

        //DEBUG
        System.out.println("GameLogic: numOfPlayers = " + numOfPlayers);

        for (int i = 1; i <= numOfPlayers; i++) {
            String name = clientHandlerList.get(i - 1).askName(i);

            //DEBUG
            System.out.println("GameLogic: player " + i + " name = " + name);

            playerList.add(new Player(name, i, clientHandlerList.get(i - 1)));
        }

        activeEffects = new ActiveEffects(playerList.size());
        activeEffects.initializeEffects();
        nowPlaying = playerList.get(0);

        List<GodPower> allGodPowers = getGodPowerList(activeEffects);


        String playerName = playerList.get(0).getName() + "(" + playerList.get(0).getID() + ")";
        List<Integer> selectedIndexes = playerList.get(0).getClientHandler().
                askAllGodPowers(playerName, numOfPlayers, deepCopyGodPowerNames(allGodPowers));
        List<GodPower> selectedGodPowers = new ArrayList<>();
        for (Integer i : selectedIndexes) {
            selectedGodPowers.add(allGodPowers.get(i));

            //DEBUG
            System.out.println("GameLogic: selectedGodPowers " + i + " name = " + allGodPowers.get(i).toString());
        }


        for (int i = 0; i < numOfPlayers - 1; i++) {
            Player currPlayer = playerList.get(i + 1);

            playerName = currPlayer.getName() + "(" + playerList.get(0).getID() + ")";
            int selectedGodPowerIndex = currPlayer.getClientHandler().
                    askGodPower(playerName, deepCopyGodPowerNames(selectedGodPowers));

            /*System.out.print(currPlayer.getName() + "(" + currPlayer.getID() + ")" + " choose your god power from the list: [");
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
            }*/
            currPlayer.initializeGodPower(selectedGodPowers.get(selectedGodPowerIndex - 1));
            selectedGodPowers.remove(selectedGodPowerIndex - 1);
        }

        playerName = playerList.get(0).getName() + "(" + playerList.get(0).getID() + ")";
        playerList.get(0).getClientHandler().tellAssignedGodPower(playerName, deepCopyGodPowerNames(selectedGodPowers));

        //System.out.println(playerList.get(0).getName() + "(" + playerList.get(0).getID() + ")" + " you got: " + selectedGodPowers);
        playerList.get(0).initializeGodPower(selectedGodPowers.get(0));
        System.out.println(" DEBUG :D");
    }

    private List<GodPower> getGodPowerList(ActiveEffects activeEffects) {
        List<GodPower> godPowers = new ArrayList<GodPower>();
        godPowers.add(new Apollo(activeEffects));
        godPowers.add(new Artemis(activeEffects));
        godPowers.add(new Athena(activeEffects));
        godPowers.add(new Atlas(activeEffects));
        godPowers.add(new Demeter(activeEffects));
        godPowers.add(new Hephaestus(activeEffects));
        godPowers.add(new Minotaur(activeEffects));
        godPowers.add(new Pan(activeEffects));
        godPowers.add(new Prometheus(activeEffects));
        return godPowers;
    }

    private void boardSetup() {
        Scanner scanner = new Scanner(System.in);
        int x1, y1, x2, y2, pos1, pos2;

        for (Player p : playerList) {
            p.getClientHandler().sendBoard(deepCopyBoard(board));
        }

        //board.print();

        for (int i = 0; i < playerList.size(); i++) {
            Player currPlayer = playerList.get(i);

            String playerName = currPlayer.getName() + "(" + currPlayer.getID() + ")";
            pos1 = currPlayer.getClientHandler()
                    .askWorkerPosition(playerName, 1, -1, deepCopyBoard(board));
            /*System.out.println(currPlayer.getName() + "(" + currPlayer.getID() + ")" + " it's your turn! Choose the position of your first worker");
            pos1 = scanner.nextInt();
            x1 = pos1 % 5;
            y1 = pos1 / 5;
            while (pos1 < 0 || pos1 > 24 || board.getSpace(x1, y1).hasWorker()) {
                System.out.println("Position not valid. Choose the position of your first worker, between 0 and 24 " +
                        "and not already occupied by other workers");
                pos1 = scanner.nextInt();
                x1 = pos1 % 5;
                y1 = pos1 / 5;
            }*/

            pos2 = currPlayer.getClientHandler()
                    .askWorkerPosition(playerName, 2, pos1, deepCopyBoard(board));
            /*System.out.println("And now choose the position of your second worker");
            pos2 = scanner.nextInt();
            x2 = pos2 % 5;
            y2 = pos2 / 5;
            while (pos2 < 0 || pos2 > 24 || pos2 == pos1 || board.getSpace(x2, y2).hasWorker()) {
                System.out.println("Position not valid. Choose the position of your second worker, " +
                        "between 0 and 24 and different from " + pos1 + "and not already occupied by other workers");
                pos2 = scanner.nextInt();
                x2 = pos2 % 5;
                y2 = pos2 / 5;
            }*/

            currPlayer.initializeWorkers(board.getSpace(pos1 % 5, pos1 / 5),
                    board.getSpace(pos2 % 5, pos2 / 5));

            for (Player p : playerList) {
                p.getClientHandler().sendBoard(deepCopyBoard(board));
            }

        }
    }

    private boolean gameLoop() {
        boolean endGame = false;
        TurnResult turnResult = nowPlaying.getGodPower().turnSequence(nowPlaying, activeEffects);
        Player otherPlayer = playerList.get((playerList.indexOf(nowPlaying) + 1) % playerList.size());

        if (turnResult.equals(TurnResult.WIN)) {
            endGame = true;
            manageVictory(nowPlaying);
        } else if (turnResult.equals(TurnResult.LOSE)) {
            if (playerList.size() == 2) {
                manageVictory(otherPlayer);
                endGame = true;
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