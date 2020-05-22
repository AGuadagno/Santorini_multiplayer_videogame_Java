package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.*;
import it.polimi.ingsw.PSP25.Server.ClientHandlerMock;
import it.polimi.ingsw.PSP25.Server.DisconnectionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AresTest {

    Board b = null;
    Worker w = null;
    Space s1 = null;
    Space s2 = null;
    ClientHandlerMock clientHandlerMock = new ClientHandlerMock();
    Player p1 = new Player("P1", 1, clientHandlerMock);
    Ares gp = null;
    ActiveEffects a = null;

    @Before
    public void setUp() throws Exception {
        b = new Board();
        b.setBoardForAllSpaces();
        s1 = b.getSpace(0, 0);
        //s1.setBoard(b);
        s2 = b.getSpace(4, 4);
        //s2.setBoard(b);

        a = new ActiveEffects(2);
        gp = new Ares(a, new GameLogic(null));
        a.initializeEffects();
        p1.initializeGodPower(gp);
        p1.initializeWorkers(s1, s2);
        p1.getWorker1().moveTo(s1);
        p1.getWorker2().moveTo(s2);
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
    public void turnSequenceTest() {
        //Removing a block with W1

        b.getSpace(4, 3).setTowerHeight(3);
        b.getSpace(3, 3).setTowerHeight(3);
        b.getSpace(3, 4).setTowerHeight(3);

        int[] workerAndSpace = new int[]{1, 1};
        clientHandlerMock.setAskWorkerMovement(workerAndSpace);
        int selectedSpace = 0;
        clientHandlerMock.setAskToBuild(selectedSpace);
        int aresDemolition = 23;
        clientHandlerMock.setAresDemolition(aresDemolition);

        try {
            assertTrue(gp.turnSequence(p1, a) == TurnResult.CONTINUE);
        } catch (DisconnectionException e) {
            e.printStackTrace();
        }
        assertTrue(b.getSpace(3, 4).getTowerHeight() == 2);
    }

    @Test
    public void turnSequenceTest2() {
        //Not removing a block with W1

        b.getSpace(4, 3).setTowerHeight(3);
        b.getSpace(3, 3).setTowerHeight(3);
        b.getSpace(3, 4).setTowerHeight(3);

        int[] workerAndSpace = new int[]{1, 1};
        clientHandlerMock.setAskWorkerMovement(workerAndSpace);
        int selectedSpace = 0;
        clientHandlerMock.setAskToBuild(selectedSpace);
        int aresDemolition = -1;
        clientHandlerMock.setAresDemolition(aresDemolition);

        try {
            assertTrue(gp.turnSequence(p1, a) == TurnResult.CONTINUE);
        } catch (DisconnectionException e) {
            e.printStackTrace();
        }
        assertTrue(b.getSpace(3, 4).getTowerHeight() == 3);
    }

    @Test
    public void turnSequenceTest3() {
        //Removing a block with W2

        b.getSpace(1, 0).setTowerHeight(3);
        b.getSpace(1, 1).setTowerHeight(3);
        b.getSpace(0, 1).setTowerHeight(3);

        int[] workerAndSpace = new int[]{2, 23};
        clientHandlerMock.setAskWorkerMovement(workerAndSpace);
        int selectedSpace = 24;
        clientHandlerMock.setAskToBuild(selectedSpace);
        int aresDemolition = 1;
        clientHandlerMock.setAresDemolition(aresDemolition);

        try {
            assertTrue(gp.turnSequence(p1, a) == TurnResult.CONTINUE);
        } catch (DisconnectionException e) {
            e.printStackTrace();
        }
        assertTrue(b.getSpace(1, 0).getTowerHeight() == 2);
    }

    @Test
    public void turnSequenceTest4() {
        //Not removing a block with W2

        b.getSpace(1, 0).setTowerHeight(3);
        b.getSpace(1, 1).setTowerHeight(3);
        b.getSpace(0, 1).setTowerHeight(3);

        int[] workerAndSpace = new int[]{2, 23};
        clientHandlerMock.setAskWorkerMovement(workerAndSpace);
        int selectedSpace = 24;
        clientHandlerMock.setAskToBuild(selectedSpace);
        int aresDemolition = -1;
        clientHandlerMock.setAresDemolition(aresDemolition);

        try {
            assertTrue(gp.turnSequence(p1, a) == TurnResult.CONTINUE);
        } catch (DisconnectionException e) {
            e.printStackTrace();
        }
        assertTrue(b.getSpace(1, 0).getTowerHeight() == 3);
    }

    @Test
    public void turnSequenceWinTest() {

        b.getSpace(0, 0).setTowerHeight(2);
        b.getSpace(1, 0).setTowerHeight(3);

        int[] workerAndSpace = new int[]{1, 1};
        clientHandlerMock.setAskWorkerMovement(workerAndSpace);
        int selectedSpace = 1;
        clientHandlerMock.setAskToBuild(selectedSpace);
        int secondBuildingSpace = 2;
        clientHandlerMock.setDemeterSecondBuilding(secondBuildingSpace);

        try {
            assertEquals(gp.turnSequence(p1, a), TurnResult.WIN);
        } catch (DisconnectionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void turnSequenceLoseTest() {
        b.getSpace(0, 1).setTowerHeight(2);
        b.getSpace(1, 0).setTowerHeight(2);
        b.getSpace(1, 1).setTowerHeight(2);

        b.getSpace(4, 3).setTowerHeight(2);
        b.getSpace(3, 4).setTowerHeight(2);
        b.getSpace(3, 3).setTowerHeight(2);

        try {
            assertEquals(gp.turnSequence(p1, a), TurnResult.LOSE);
        } catch (DisconnectionException e) {
            e.printStackTrace();
        }
    }

}