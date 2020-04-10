package it.polimi.ingsw.PSP25;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AthenaTest {
    Board board = null;
    Space space = null;
    Worker worker = null;
    Player player = null;
    ActiveEffects activeEffects = null;
    GodPower gp = null;

    @Before
    public void setup(){
        board = new Board();
        space = board.getSpace(2,2);
        player = new Player("Name", 1);
        worker = new Worker(space, player);
        space.setWorker(worker);
        activeEffects = new ActiveEffects(2);
        activeEffects.initializeEffects();
        gp = new Athena(activeEffects);
    }

    @After
    public void tearDown(){
        board = null;
        space = null;
        worker = null;
        player = null;
        activeEffects = null;
        gp = null;
    }

    @Test
    public void canMove() {
        board.getSpace(1,2).setTowerHeight(1);
        board.getSpace(3,2).setTowerHeight(1);

        activeEffects.pushEffect(new Athena(activeEffects));

        assertTrue(!gp.canMove(worker, board.getSpace(1,2)));
        assertTrue(gp.canMove(worker, board.getSpace(1,3)));
        assertTrue(!gp.canMove(worker, board.getSpace(3,2)));
        assertTrue(gp.canMove(worker, board.getSpace(2,1)));
        assertTrue(gp.canMove(worker, board.getSpace(2,3)));
        assertTrue(gp.canMove(worker, board.getSpace(3,3)));
        assertTrue(gp.canMove(worker, board.getSpace(3,1)));
        assertTrue(gp.canMove(worker, board.getSpace(1,3)));
    }

    @Test
    public void addActiveEffects() {
        worker.moveTo(board.getSpace(1,1));
        gp.addActiveEffects(activeEffects, null, null, worker);


    }
}