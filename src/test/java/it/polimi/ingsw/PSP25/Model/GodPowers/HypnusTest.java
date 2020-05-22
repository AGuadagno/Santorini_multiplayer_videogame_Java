package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.*;
import it.polimi.ingsw.PSP25.Server.ClientHandler;
import it.polimi.ingsw.PSP25.Server.Lobby;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.*;

public class HypnusTest {

    Board board = null;
    Space space = null;
    Worker worker = null;
    Player player1 = null;
    Player player2 = null;
    ActiveEffects activeEffects = null;
    GodPower gp = null;
    Worker worker1Player2 = null;
    Worker worker2Player2 = null;

    @Before
    public void setup() {
        board = new Board();
        board.setBoardForAllSpaces();
        space = board.getSpace(0, 0);
        player1 = new Player("Name1", 1, new ClientHandler(new Socket(), 1, new Lobby()));
        worker = new Worker(space, player1);
        space.setWorker(worker);
        activeEffects = new ActiveEffects(2);
        activeEffects.initializeEffects();
        gp = new Hypnus(activeEffects, null);
        player1.initializeGodPower(gp);

        player2 = new Player("Name2", 2, new ClientHandler(new Socket(), 2, new Lobby()));
        //worker1Player2 = new Worker(board.getSpace(3,4), player2);
        // = new Worker(board.getSpace(1,3), player2);
        player2.initializeWorkers(board.getSpace(3, 4), board.getSpace(1, 3));
        player2.initializeGodPower(new Prometheus(activeEffects, null));
    }


    @After
    public void tearDown() {
        board = null;
        space = null;
        worker = null;
        player1 = null;
        player2 = null;
        activeEffects = null;
        worker1Player2 = null;
        worker2Player2 = null;
        gp = null;
    }

    @Test
    public void canMoveTest() {
        board.getSpace(3, 4).setTowerHeight(2);
        board.getSpace(1, 3).setTowerHeight(1);

        activeEffects.pushEffect(new Hypnus(activeEffects, null));

        for (Space adjacentSpace : player2.getWorker1().getSpace().getAdjacentSpaces())
            assertTrue(!gp.canMove(player2.getWorker1(), adjacentSpace));

        for (Space adjacentSpace : player2.getWorker2().getSpace().getAdjacentSpaces())
            assertTrue(gp.canMove(player2.getWorker2(), adjacentSpace));
    }

    @Test
    public void canMoveTest2() {
        board.getSpace(3, 4).setTowerHeight(1);
        board.getSpace(1, 3).setTowerHeight(1);

        activeEffects.pushEffect(new Hypnus(activeEffects, null));

        for (Space adjacentSpace : player2.getWorker1().getSpace().getAdjacentSpaces())
            assertTrue(gp.canMove(player2.getWorker1(), adjacentSpace));

        for (Space adjacentSpace : player2.getWorker2().getSpace().getAdjacentSpaces())
            assertTrue(gp.canMove(player2.getWorker2(), adjacentSpace));
    }

}