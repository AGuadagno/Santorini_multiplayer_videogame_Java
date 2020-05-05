package it.polimi.ingsw.PSP25.Server.Model.GodPowers;

import it.polimi.ingsw.PSP25.Server.Model.*;
import it.polimi.ingsw.PSP25.Server.ClientHandler;
import it.polimi.ingsw.PSP25.Server.Lobby;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.assertTrue;

public class AthenaTest {
    Board board = null;
    Space space = null;
    Worker worker = null;
    Player player = null;
    ActiveEffects activeEffects = null;
    GodPower gp = null;

    @Before
    public void setup() {
        board = new Board();
        space = board.getSpace(2, 2);
        player = new Player("Name", 1, new ClientHandler(new Socket(), 1, new Lobby()));
        worker = new Worker(space, player);
        space.setWorker(worker);
        activeEffects = new ActiveEffects(2);
        activeEffects.initializeEffects();
        gp = new Athena(activeEffects, null);
        player.initializeGodPower(gp);
    }

    @After
    public void tearDown() {
        board = null;
        space = null;
        worker = null;
        player = null;
        activeEffects = null;
        gp = null;
    }

    @Test
    public void canMove() {
        board.getSpace(1, 2).setTowerHeight(1);
        board.getSpace(3, 2).setTowerHeight(1);

        activeEffects.pushEffect(new Athena(activeEffects, null));

        assertTrue(!gp.canMove(worker, board.getSpace(1, 2)));
        assertTrue(gp.canMove(worker, board.getSpace(1, 3)));
        assertTrue(!gp.canMove(worker, board.getSpace(3, 2)));
        assertTrue(gp.canMove(worker, board.getSpace(2, 1)));
        assertTrue(gp.canMove(worker, board.getSpace(2, 3)));
        assertTrue(gp.canMove(worker, board.getSpace(3, 3)));
        assertTrue(gp.canMove(worker, board.getSpace(3, 1)));
        assertTrue(gp.canMove(worker, board.getSpace(1, 3)));
    }

    @Test
    public void addActiveEffects() {

        Space spaceh0 = board.getSpace(3, 3);
        Space spaceh3 = board.getSpace(3, 2);
        spaceh0.setTowerHeight(0);
        spaceh3.setTowerHeight(1);
        Space spaceh1 = board.getSpace(4, 4);
        Space spaceh2 = board.getSpace(4, 3);
        spaceh1.setTowerHeight(1);
        spaceh2.setTowerHeight(2);

        spaceh0.setBoard(board);
        spaceh1.setBoard(board);
        spaceh2.setBoard(board);
        spaceh3.setBoard(board);


        Player player2 = new Player("Name2", 2, new ClientHandler(new Socket(), 2, new Lobby()));
        GodPower gp2 = new Apollo(activeEffects, null);
        player2.initializeGodPower(gp2);

        Worker workerP2 = new Worker(spaceh0, player2);
        spaceh0.setWorker(workerP2);

        Worker worker = new Worker(spaceh1, player);
        spaceh1.setWorker(worker);

        worker.moveTo(spaceh2);

        gp.addActiveEffects(activeEffects, worker, worker, worker);

        assertTrue(!gp2.getValidMovementSpaces(workerP2).contains(spaceh3));

    }

    @Test
    public void addActiveEffects2() {

        Space spaceh0 = board.getSpace(3, 3);
        Space spaceh3 = board.getSpace(3, 2);
        spaceh0.setTowerHeight(0);
        spaceh3.setTowerHeight(1);
        Space spaceh1 = board.getSpace(4, 4);
        Space spaceh2 = board.getSpace(4, 3);
        spaceh1.setTowerHeight(2);
        spaceh2.setTowerHeight(2);

        spaceh0.setBoard(board);
        spaceh1.setBoard(board);
        spaceh2.setBoard(board);
        spaceh3.setBoard(board);


        Player player2 = new Player("Name2", 2, new ClientHandler(new Socket(), 2, new Lobby()));
        GodPower gp2 = new Apollo(activeEffects, null);
        player2.initializeGodPower(gp2);

        Worker workerP2 = new Worker(spaceh0, player2);
        spaceh0.setWorker(workerP2);

        Worker worker = new Worker(spaceh1, player);
        spaceh1.setWorker(worker);

        worker.moveTo(spaceh2);

        gp.addActiveEffects(activeEffects, worker, worker, worker);

        assertTrue(gp2.getValidMovementSpaces(workerP2).contains(spaceh3));

    }

}