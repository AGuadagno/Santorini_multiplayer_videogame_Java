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