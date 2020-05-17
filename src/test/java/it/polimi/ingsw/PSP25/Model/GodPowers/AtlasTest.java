package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.*;
import it.polimi.ingsw.PSP25.Server.ClientHandlerMock;
import it.polimi.ingsw.PSP25.Server.DisconnectionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtlasTest {

    Board b = null;
    Worker w = null;
    Space s1 = null;
    Space s2 = null;
    ClientHandlerMock clientHandlerMock = new ClientHandlerMock();
    Player p1 = new Player("P1", 1, clientHandlerMock);
    Atlas gp = null;
    ActiveEffects a = null;

    @Before
    public void setUp() {
        b = new Board();
        b.setBoardForAllSpaces();
        s1 = b.getSpace(0, 0);
        //s1.setBoard(b);
        s2 = b.getSpace(4, 4);
        //s2.setBoard(b);

        a = new ActiveEffects(2);
        gp = new Atlas(a, new GameLogic(null));
        a.initializeEffects();
        p1.initializeGodPower(gp);
        p1.initializeWorkers(s1, s2);
        p1.getWorker1().moveTo(s1);
        p1.getWorker2().moveTo(s2);
    }

    @After
    public void tearDown() {
        b = null;
        w = null;
        s1 = null;
        s2 = null;
        p1 = null;
        gp = null;
        a = null;
    }

    @Test
    public void askToBuildTestNoDome() {
        // Building of a normal block

        int[] workerAndSpace = new int[]{1, 1};
        clientHandlerMock.setAskWorkerMovement(workerAndSpace);
        int[] atlasSpaceAndDome = new int[]{2, 0};
        clientHandlerMock.setAtlasBuild(atlasSpaceAndDome);

        try {
            gp.turnSequence(p1, a);
        } catch (DisconnectionException e) {
            e.printStackTrace();
        }

        assertEquals(1, b.getSpace(2, 0).getTowerHeight());
        assertFalse(b.getSpace(2, 0).hasDome());
    }

    @Test
    public void askToBuildTestYesDome() {
        // Building of a Dome

        int[] workerAndSpace = new int[]{1, 1};
        clientHandlerMock.setAskWorkerMovement(workerAndSpace);
        int[] atlasSpaceAndDome = new int[]{2, 1};
        clientHandlerMock.setAtlasBuild(atlasSpaceAndDome);

        try {
            gp.turnSequence(p1, a);
        } catch (DisconnectionException e) {
            e.printStackTrace();
        }

        assertEquals(0, b.getSpace(2, 0).getTowerHeight());
        assertTrue(b.getSpace(2, 0).hasDome());
    }

    @Test
    public void askToBuildTestNoDome2() {
        // Building of a dome due to building on tower height = 3

        b.getSpace(2, 0).setTowerHeight(3);

        int[] workerAndSpace = new int[]{1, 1};
        clientHandlerMock.setAskWorkerMovement(workerAndSpace);
        int[] atlasSpaceAndDome = new int[]{2, 0};
        clientHandlerMock.setAtlasBuild(atlasSpaceAndDome);

        try {
            gp.turnSequence(p1, a);
        } catch (DisconnectionException e) {
            e.printStackTrace();
        }

        assertEquals(3, b.getSpace(2, 0).getTowerHeight());
        assertTrue(b.getSpace(2, 0).hasDome());
    }
}