package it.polimi.ingsw.PSP25.Model;

import it.polimi.ingsw.PSP25.Model.GodPowers.*;
import it.polimi.ingsw.PSP25.Server.ClientHandler;
import it.polimi.ingsw.PSP25.Server.DisconnectionException;
import it.polimi.ingsw.PSP25.Server.VirtualView;

import static it.polimi.ingsw.PSP25.Utility.Utilities.deepCopyBoard;
import static it.polimi.ingsw.PSP25.Utility.Utilities.deepCopyGodPowerNames;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Game Logic class.
 * This class contains the status and supervise the entire game
 */
public class GameLogic implements BroadcastInterface {
    private ActiveEffects activeEffects;
    private Board board;
    private List<Player> playerList;
    private Player nowPlaying;
    private List<VirtualView> clientHandlerList;

    /**
     * Game Logic constructor.
     *
     * @param clientHandlerList List of players' client handler.
     */
    public GameLogic(List<VirtualView> clientHandlerList) {
        board = new Board();
        board.setBoardForAllSpaces();
        playerList = new ArrayList<>();
        this.clientHandlerList = clientHandlerList;
    }

    /**
     * This method manage the beginning of the game.
     * This is the list of actions performed by this method:
     * 1) the request of the names of the players
     * 2) the request to the player who create the game of the God Powers selected for the game
     * 3) the request to chose a God Power from the selected God Powers list to all the players
     *
     * @throws DisconnectionException
     */
    private void playerInitialization() throws DisconnectionException {
        System.out.println("The game begins.");
        int numOfPlayers = clientHandlerList.size();

        // 1) the request of the names of the players
        for (int i = 1; i <= numOfPlayers; i++) {
            String name = clientHandlerList.get(i - 1).askName(i);
            playerList.add(new Player(name, i, clientHandlerList.get(i - 1)));
        }

        activeEffects = new ActiveEffects(playerList.size());
        activeEffects.initializeEffects();
        nowPlaying = playerList.get(0);

        List<GodPower> allGodPowers = getGodPowerList(activeEffects);

        // 2) the request to the player who create the game of the God Powers selected for the game
        String playerName = playerList.get(0).getName() + "(" + playerList.get(0).getID() + ")";
        List<Integer> selectedIndexes = playerList.get(0).getClientHandler().
                askAllGodPowers(playerName, numOfPlayers, deepCopyGodPowerNames(allGodPowers));
        List<GodPower> selectedGodPowers = new ArrayList<>();
        for (Integer i : selectedIndexes) {
            selectedGodPowers.add(allGodPowers.get(i));
        }

        // 3) the request to chose a God Power from the selected God Powers list to all the players
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

    /**
     * @param activeEffects list where the of opponent GodPower effects active in the current turn that could limit movement,
     *                      building action or winning conditions of workers will be included
     * @return The list of all the God Power that can be chosen for the game
     */
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

    /**
     * Asks to all players to position their workers at the beginning of the game
     *
     * @throws DisconnectionException
     */
    private void boardSetup() throws DisconnectionException {
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

    /**
     * Manages the succession of turns
     *
     * @return true if one of the players won the game.
     * @throws DisconnectionException
     */
    private boolean gameLoop() throws DisconnectionException {
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
            } else {
                manageLose(nowPlaying);
            }
        }

        nowPlaying = otherPlayer;
        return endGame;
    }

    /**
     * Begins the game.
     *
     * @throws DisconnectionException
     */
    public void startGame() throws DisconnectionException {
        playerInitialization();
        broadcastGodPowers();
        boardSetup();
        boolean endGame = false;
        while (!endGame) {
            endGame = gameLoop();
        }
    }

    /**
     * Manages the victory of the game by a player
     *
     * @param player who won the game.
     */
    private void manageVictory(Player player) throws DisconnectionException {
        String playerName = player.getName() + "(" + player.getID() + ")";
        for (Player p : playerList) {
            p.getClientHandler().manageVictory(playerName);
            p.getClientHandler().stopGame();
        }


    }

    /**
     * Manages the lose of a player
     *
     * @param player who has lost.
     */
    private void manageLose(Player player) throws DisconnectionException {
        String playerName = player.getName() + "(" + player.getID() + ")";

        for (Player p : playerList) {
            p.getClientHandler().manageLose(playerName);
        }
        nowPlaying.getWorker1().getSpace().removeWorker();
        nowPlaying.getWorker2().getSpace().removeWorker();

        nowPlaying.getClientHandler().stopGame();
        activeEffects.adaptEffectsAfterPlayerLose();

        playerList.remove(nowPlaying);
        broadcastBoard();
    }

    /**
     * Sends the board to all the players
     */
    @Override
    public void broadcastBoard() throws DisconnectionException {
        for (Player p : playerList) {
            p.getClientHandler().sendBoard(deepCopyBoard(board));
        }
    }

    public void broadcastGodPowers() throws DisconnectionException {
        List<String> playerNames = playerList.stream().map(p -> p.getName() + "(" + p.getID() + ")").collect(Collectors.toList());
        List<String> godPowerNames = playerList.stream().map(p -> p.getGodPower().toString()).collect(Collectors.toList());

        for (Player p : playerList) {
            p.getClientHandler().sendPlayersGodPowers(playerNames, godPowerNames);
        }
    }

    // NEW
    public void stopGame(VirtualView timeOutClient, InetAddress disconnectedAddress) throws DisconnectionException {
        int disconnectedClientIndex = timeOutClient.getClientNumber();

        System.out.println("Client " + disconnectedClientIndex + " with address " + disconnectedAddress + " disconnected.");
        for (int i = 0; i < clientHandlerList.size(); i++) {
            if (clientHandlerList.get(i) != timeOutClient) {
                clientHandlerList.get(i).sendStop(disconnectedAddress);
            }
            clientHandlerList.get(i).stopGame();
        }
    }
}

