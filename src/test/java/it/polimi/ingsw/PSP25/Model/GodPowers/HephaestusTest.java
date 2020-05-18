package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.*;
import it.polimi.ingsw.PSP25.Server.ClientHandlerMock;
import it.polimi.ingsw.PSP25.Server.DisconnectionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HephaestusTest {

    Board b = null;
    Worker w = null;
    Space s1 = null;
    Space s2 = null;
    ClientHandlerMock clientHandlerMock = new ClientHandlerMock();
    Player p1 = new Player("P1", 1, clientHandlerMock);
    Hephaestus gp = null;
    ActiveEffects a = null;

    @Before
    public void setUp() {
        b = new Board();
        b.setBoardForAllSpaces();
        s1 = b.getSpace(3, 4);
        s2 = b.getSpace(2, 1);

        a = new ActiveEffects(2);
        gp = new Hephaestus(a, new GameLogic(null));
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
    public void askToBuildSingleBlockTest() {
        int[] workerAndSpace = new int[]{1, 17};
        clientHandlerMock.setAskWorkerMovement(workerAndSpace);
        int[] spaceAndDoubleBuilding = new int[]{16, 1}; // Single Block
        clientHandlerMock.setHephaestusBuild(spaceAndDoubleBuilding);

        try {
            gp.turnSequence(p1, a);
        } catch (DisconnectionException e) {
            e.printStackTrace();
        }

        assertEquals(1, b.getSpace(1, 3).getTowerHeight());
    }

    @Test
    public void askToBuildDoubleBlockTest() {
        int[] workerAndSpace = new int[]{2, 13};
        clientHandlerMock.setAskWorkerMovement(workerAndSpace);
        int[] spaceAndDoubleBuilding = new int[]{14, 2}; // Double Block
        clientHandlerMock.setHephaestusBuild(spaceAndDoubleBuilding);

        try {
            gp.turnSequence(p1, a);
        } catch (DisconnectionException e) {
            e.printStackTrace();
        }

        assertEquals(2, b.getSpace(4, 2).getTowerHeight());
    }

    @Test
    public void askToBuildNoDomeTest() {
        b.getSpace(1, 1).setTowerHeight(2);
        int[] workerAndSpace = new int[]{2, 11};
        clientHandlerMock.setAskWorkerMovement(workerAndSpace);
        int[] spaceAndDoubleBuilding = new int[]{6, 1};
        clientHandlerMock.setHephaestusBuild(spaceAndDoubleBuilding);

        try {
            gp.turnSequence(p1, a);
        } catch (DisconnectionException e) {
            e.printStackTrace();
        }

        assertEquals(3, b.getSpace(1, 1).getTowerHeight());
        assertEquals(false, b.getSpace(1, 1).hasDome());
    }
}