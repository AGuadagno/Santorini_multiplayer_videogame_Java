/*package it.polimi.ingsw.PSP25;

import it.polimi.ingsw.PSP25.Model.GodPowers.GodPower;
import it.polimi.ingsw.PSP25.Server.ClientHandler;
import it.polimi.ingsw.PSP25.Server.Lobby;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MinotaurTest {
    GodPower gp = null;
    ActiveEffects activeEffects = null;
    Board board = null;
    Player player1 = null;
    Worker workerP1 = null;
    Space space1 = null;

    @Before
    public void setup(){
        activeEffects = new ActiveEffects(2);
        activeEffects.initializeEffects();
        player1 = new Player("Name1", 1, new ClientHandler(new Socket(), new Lobby()));
        gp = new Minotaur(activeEffects, null);
        board = new Board();
    }

    @After
    public void tearDown(){
        activeEffects = null;
        player1 = null;
        gp = null;
        board = null;
    }

    @Test
    public void getValidMovementSpaces_TestOpponentWorker() {
        space1 = board.getSpace(0,0);
        space1.setBoard(board);
        workerP1 = new Worker(space1, player1);
        space1.setWorker(workerP1);

        Player player2 = new Player("Name2", 2, new ClientHandler(new Socket(), new Lobby()));
        Space space2 = board.getSpace(1, 1);
        space2.setBoard(board);
        Worker workerP2 = new Worker(space2,player2);
        space2.setWorker(workerP2);

        List<Space> spaces = new ArrayList<>();
        spaces.add(board.getSpace(0,1));
        spaces.add(board.getSpace(1,1));
        spaces.add(board.getSpace(1,0));

        List<Space> validMovementSpaces = gp.getValidMovementSpaces(workerP1);
        assertTrue(validMovementSpaces.containsAll(spaces) && spaces.size() == validMovementSpaces.size());
    }

    @Test
    public void getValidMovementSpaces_TestOwnWorker() {
        space1 = board.getSpace(0,0);
        space1.setBoard(board);
        workerP1 = new Worker(space1, player1);
        space1.setWorker(workerP1);

        Player player2 = new Player("Name2", 2, new ClientHandler(new Socket(), new Lobby()));
        Space space2 = board.getSpace(1, 1);
        space2.setBoard(board);
        Worker workerP2 = new Worker(space2,player1);
        space2.setWorker(workerP2);

        List<Space> spaces = new ArrayList<>();
        spaces.add(board.getSpace(0,1));
        spaces.add(board.getSpace(1,0));

        List<Space> validMovementSpaces = gp.getValidMovementSpaces(workerP1);
        assertTrue(validMovementSpaces.containsAll(spaces) && spaces.size() == validMovementSpaces.size());
    }

    @Test
    public void moveWorker_TestOpponentPlayer() {
        space1 = board.getSpace(0,0);
        space1.setBoard(board);
        workerP1 = new Worker(space1, player1);
        space1.setWorker(workerP1);

        Player player2 = new Player("Name2", 2, new ClientHandler(new Socket(), new Lobby()));
        Space space2 = board.getSpace(1, 1);
        space2.setBoard(board);
        Worker workerP2 = new Worker(space2,player2);
        space2.setWorker(workerP2);

        gp.moveWorker(workerP1, space2);

        assertEquals(space2.getWorker(), workerP1);
        assertEquals(workerP1.getSpace(), space2);
        assertEquals(board.getSpace(2,2).getWorker(), workerP2);
        assertEquals(workerP2.getSpace(), board.getSpace(2,2));
    }



}*/