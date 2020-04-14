package it.polimi.ingsw.PSP25;

import it.polimi.ingsw.PSP25.Model.ActiveEffects;
import it.polimi.ingsw.PSP25.Model.GodPowers.*;
import it.polimi.ingsw.PSP25.Model.BroadcastInterface;
import it.polimi.ingsw.PSP25.Server.ClientHandler;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.PSP25.Utility.Utilities.deepCopyBoard;
import static it.polimi.ingsw.PSP25.Utility.Utilities.deepCopyGodPowerNames;

public class GameLogic implements BroadcastInterface {
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
        System.out.println("The game begins.");
        int numOfPlayers = clientHandlerList.size();

        for (int i = 1; i <= numOfPlayers; i++) {
            String name = clientHandlerList.get(i - 1).askName(i);

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
        }


        for (int i = 0; i < numOfPlayers - 1; i++) {
            Player currPlayer = playerList.get(i + 1);

            playerName = currPlayer.getName() + "(" + currPlayer.getID() + ")";
            int selectedGodPowerIndex = currPlayer.getClientHandler().
                    askGodPower(playerName, deepCopyGodPowerNames(selectedGodPowers));

            currPlayer.initializeGodPower(selectedGodPowers.get(selectedGodPowerIndex - 1));
            selectedGodPowers.remove(selectedGodPowerIndex - 1);
        }

        playerName = playerList.get(0).getName() + "(" + playerList.get(0).getID() + ")";
        playerList.get(0).getClientHandler().tellAssignedGodPower(playerName, deepCopyGodPowerNames(selectedGodPowers));

        playerList.get(0).initializeGodPower(selectedGodPowers.get(0));
    }

    private List<GodPower> getGodPowerList(ActiveEffects activeEffects) {
        List<GodPower> godPowers = new ArrayList<GodPower>();
        godPowers.add(new Apollo(activeEffects, this));
        godPowers.add(new Artemis(activeEffects, this));
        godPowers.add(new Athena(activeEffects, this));
        godPowers.add(new Atlas(activeEffects, this));
        godPowers.add(new Demeter(activeEffects, this));
        godPowers.add(new Hephaestus(activeEffects, this));
        godPowers.add(new Minotaur(activeEffects, this));
        godPowers.add(new Pan(activeEffects, this));
        godPowers.add(new Prometheus(activeEffects, this));
        return godPowers;
    }

    private void boardSetup() {
        int pos1, pos2;

        broadcastBoard();

        for (int i = 0; i < playerList.size(); i++) {
            Player currPlayer = playerList.get(i);

            String playerName = currPlayer.getName() + "(" + currPlayer.getID() + ")";
            pos1 = currPlayer.getClientHandler()
                    .askWorkerPosition(playerName, 1, -1, deepCopyBoard(board));

            pos2 = currPlayer.getClientHandler()
                    .askWorkerPosition(playerName, 2, pos1, deepCopyBoard(board));

            currPlayer.initializeWorkers(board.getSpace(pos1 % 5, pos1 / 5),
                    board.getSpace(pos2 % 5, pos2 / 5));

            broadcastBoard();
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

    @Override
    public void broadcastBoard() {
        for (Player p : playerList) {
            p.getClientHandler().sendBoard(deepCopyBoard(board));
        }
    }
}

