package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.*;
import it.polimi.ingsw.PSP25.Server.ClientHandler;
import it.polimi.ingsw.PSP25.Server.ClientHandlerMock;
import it.polimi.ingsw.PSP25.Server.DisconnectionException;
import it.polimi.ingsw.PSP25.Server.Lobby;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.*;

public class ArtemisTest {

    Board b = null;
    Worker w = null;
    Space s1 = null;
    Space s2 = null;
    ClientHandlerMock clientHandlerMock = new ClientHandlerMock();
    Player p1 = new Player("P1", 1, clientHandlerMock);
    Artemis gp = null;
    ActiveEffects a = null;


    @Before
    public void setup() {
        b = new Board();
        b.setBoardForAllSpaces();
        s1 = b.getSpace(0, 0);
        //s1.setBoard(b);
        s2 = b.getSpace(4, 4);
        //s2.setBoard(b);

        a = new ActiveEffects(2);
        gp = new Artemis(a, new GameLogic(null));
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
    public void turnSequenceBaseTest() {
        int[] workerAndSpace = new int[]{1, 1};
        clientHandlerMock.setAskWorkerMovement(workerAndSpace);
        int artemisChosenMovementSpace = 2;
        clientHandlerMock.setArtemisSecondMove(artemisChosenMovementSpace);
        int selectedSpace = 1;
        clientHandlerMock.setAskToBuild(selectedSpace);

        try {
            assertEquals(gp.turnSequence(p1, a), TurnResult.CONTINUE);
            assertTrue(b.getSpace(2, 0).hasWorker());
            assertEquals(b.getSpace(2, 0).getWorker(), p1.getWorker1());
            assertFalse(b.getSpace(1, 0).hasWorker());
            assertEquals(b.getSpace(1, 0).getTowerHeight(), 1);
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

    @Test
    public void turnSequenceWinTest1() {
        // Winning with second movement

        b.getSpace(0, 0).setTowerHeight(1);
        b.getSpace(1, 0).setTowerHeight(2);
        b.getSpace(2, 0).setTowerHeight(3);

        int[] workerAndSpace = new int[]{1, 1};
        clientHandlerMock.setAskWorkerMovement(workerAndSpace);
        int artemisChosenMovementSpace = 2;
        clientHandlerMock.setArtemisSecondMove(artemisChosenMovementSpace);
        int selectedSpace = 1;
        clientHandlerMock.setAskToBuild(selectedSpace);

        try {
            assertEquals(gp.turnSequence(p1, a), TurnResult.WIN);
        } catch (DisconnectionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void turnSequenceWinTest2() {
        // Winning with first movement

        b.getSpace(0, 0).setTowerHeight(2);
        b.getSpace(1, 0).setTowerHeight(3);

        int[] workerAndSpace = new int[]{1, 1};
        clientHandlerMock.setAskWorkerMovement(workerAndSpace);
        int artemisChosenMovementSpace = 2;
        clientHandlerMock.setArtemisSecondMove(artemisChosenMovementSpace);
        int selectedSpace = 1;
        clientHandlerMock.setAskToBuild(selectedSpace);

        try {
            assertEquals(gp.turnSequence(p1, a), TurnResult.WIN);
        } catch (DisconnectionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void turnSequenceBaseTest2() {
        // No second movement

        int[] workerAndSpace = new int[]{1, 1};
        clientHandlerMock.setAskWorkerMovement(workerAndSpace);
        int artemisChosenMovementSpace = -1;
        clientHandlerMock.setArtemisSecondMove(artemisChosenMovementSpace);
        int selectedSpace = 0;
        clientHandlerMock.setAskToBuild(selectedSpace);

        try {
            assertEquals(gp.turnSequence(p1, a), TurnResult.CONTINUE);
            assertTrue(b.getSpace(1, 0).hasWorker());
            assertEquals(b.getSpace(1, 0).getWorker(), p1.getWorker1());
            assertFalse(b.getSpace(0, 0).hasWorker());
            assertEquals(b.getSpace(0, 0).getTowerHeight(), 1);
        } catch (DisconnectionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void turnSequenceBaseTest3() {
        // With Worker 2
        int[] workerAndSpace = new int[]{2, 23};
        clientHandlerMock.setAskWorkerMovement(workerAndSpace);
        int artemisChosenMovementSpace = 22;
        clientHandlerMock.setArtemisSecondMove(artemisChosenMovementSpace);
        int selectedSpace = 23;
        clientHandlerMock.setAskToBuild(selectedSpace);

        try {
            assertEquals(gp.turnSequence(p1, a), TurnResult.CONTINUE);
            assertTrue(b.getSpace(2, 4).hasWorker());
            assertEquals(b.getSpace(2, 4).getWorker(), p1.getWorker2());
            assertFalse(b.getSpace(3, 4).hasWorker());
            assertEquals(b.getSpace(3, 4).getTowerHeight(), 1);
        } catch (DisconnectionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void turnSequenceBaseTest4() {

        b.getSpace(0, 0).setTowerHeight(2);
        b.getSpace(2, 0).setTowerHeight(2);
        b.getSpace(0, 1).setTowerHeight(2);
        b.getSpace(1, 1).setTowerHeight(2);
        b.getSpace(2, 1).setTowerHeight(2);

        int[] workerAndSpace = new int[]{1, 1};
        clientHandlerMock.setAskWorkerMovement(workerAndSpace);
        int artemisChosenMovementSpace = 2;
        clientHandlerMock.setArtemisSecondMove(artemisChosenMovementSpace);
        int selectedSpace = 0;
        clientHandlerMock.setAskToBuild(selectedSpace);

        try {
            assertEquals(gp.turnSequence(p1, a), TurnResult.CONTINUE);
            assertTrue(b.getSpace(1, 0).hasWorker());
            assertEquals(b.getSpace(1, 0).getWorker(), p1.getWorker1());
            assertFalse(b.getSpace(0, 0).hasWorker());
            assertEquals(b.getSpace(0, 0).getTowerHeight(), 3);
        } catch (DisconnectionException e) {
            e.printStackTrace();
        }
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
    public void turnSequenceLoseByBuildingTest() throws DisconnectionException {
        ClientHandlerMock clientHandlerMock = new ClientHandlerMock();
        Player demoPlayer = new Player("Name", 1, clientHandlerMock);
        b.getSpace(0, 0).removeWorker();
        demoPlayer.initializeWorkers(b.getSpace(0, 1), b.getSpace(4, 4));
        GodPower limus = new Limus(a, null);
        limus.initializeWorkers(new Player("nome2", 2, new ClientHandler(new Socket(), 2, new Lobby())),
                b.getSpace(1, 1), b.getSpace(3, 4));
        a.pushEffect(limus);

        clientHandlerMock.setAskWorkerMovement(new int[]{1, 0});
        int artemisChosenMovementSpace = -1;
        clientHandlerMock.setArtemisSecondMove(artemisChosenMovementSpace);
        assertEquals(TurnResult.LOSE, gp.turnSequence(demoPlayer, a));
    }
}