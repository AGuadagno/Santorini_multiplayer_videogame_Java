package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.*;
import it.polimi.ingsw.PSP25.Server.ClientHandlerMock;
import it.polimi.ingsw.PSP25.Server.DisconnectionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LimusTest {

    Board b = null;
    Worker w = null;
    Space s1 = null;
    Space s2 = null;
    Space s3 = null;
    Space s4 = null;
    ClientHandlerMock clientHandlerMock = new ClientHandlerMock();
    ClientHandlerMock clientHandlerMock2 = new ClientHandlerMock();
    Player p1 = new Player("P1", 1, clientHandlerMock);
    Player p2 = new Player("P2", 2, clientHandlerMock2);
    Limus gp = null;
    Artemis gp2 = null;
    ActiveEffects a = null;

    @Before
    public void setUp() throws Exception {
        b = new Board();
        b.setBoardForAllSpaces();
        s1 = b.getSpace(0, 0);
        s2 = b.getSpace(4, 4);
        s3 = b.getSpace(0, 2);
        s4 = b.getSpace(2, 2);

        a = new ActiveEffects(2);
        GameLogic gameLogic = new GameLogic(null);
        gp = new Limus(a, gameLogic);
        gp2 = new Artemis(a, gameLogic);
        a.initializeEffects();
        p1.initializeGodPower(gp);
        p2.initializeGodPower(gp2);

    }

    @After
    public void tearDown() throws Exception {
        b = null;
        w = null;
        s1 = null;
        s2 = null;
        p1 = null;
        gp = null;
        a = null;
    }

    @Test
    public void canBuild() {

        b.getSpace(1, 1).setTowerHeight(3);

        gp.initializeWorkers(p1, s1, s2);
        p1.getWorker1().moveTo(s1);
        p1.getWorker2().moveTo(s2);
        p2.initializeWorkers(s3, s4);
        p2.getWorker1().moveTo(s3);
        p2.getWorker2().moveTo(s4);

        assertFalse(gp.canBuild(p2.getWorker1(), b.getSpace(0, 1)));
        assertTrue(gp.canBuild(p2.getWorker1(), b.getSpace(1, 1)));
        assertFalse(gp.canBuild(p2.getWorker1(), b.getSpace(3, 3)));
        assertTrue(gp.canBuild(p2.getWorker1(), b.getSpace(0, 3)));
        assertTrue(gp.canBuild(p2.getWorker1(), b.getSpace(1, 2)));
        assertTrue(gp.canBuild(p2.getWorker1(), b.getSpace(1, 3)));

        assertTrue(gp.canBuild(p2.getWorker1(), b.getSpace(2, 3)));
        assertTrue(gp.canBuild(p2.getWorker1(), b.getSpace(3, 2)));
        assertTrue(gp.canBuild(p2.getWorker1(), b.getSpace(1, 2)));
        assertTrue(gp.canBuild(p2.getWorker1(), b.getSpace(1, 1)));
        assertTrue(gp.canBuild(p2.getWorker1(), b.getSpace(1, 3)));
        assertTrue(gp.canBuild(p2.getWorker1(), b.getSpace(2, 1)));

    }

    @Test
    public void initializeWorkers() {
        gp.initializeWorkers(p1, s1, s2);
        assertEquals(b.getSpace(s1.getX(), s1.getY()).getWorker(), p1.getWorker1());
        assertEquals(b.getSpace(s2.getX(), s2.getY()).getWorker(), p1.getWorker2());
    }

    @Test
    public void turnSequence() {

        p1.initializeWorkers(s1, s2);
        p1.getWorker1().moveTo(s1);
        p1.getWorker2().moveTo(s2);

        int[] workerAndSpace = new int[]{1, 1};
        clientHandlerMock.setAskWorkerMovement(workerAndSpace);
        int selectedSpace = 0;
        clientHandlerMock.setAskToBuild(selectedSpace);

        try {
            assertTrue(gp.turnSequence(p1, a) == TurnResult.CONTINUE);
        } catch (DisconnectionException e) {
            e.printStackTrace();
        }
    }
}