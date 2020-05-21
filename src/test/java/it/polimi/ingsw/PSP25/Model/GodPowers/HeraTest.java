package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.*;
import it.polimi.ingsw.PSP25.Server.ClientHandlerMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HeraTest {

    Board b = null;
    Worker w = null;
    Space s1 = null;
    Space s2 = null;
    ClientHandlerMock clientHandlerMock = new ClientHandlerMock();
    Player p1 = new Player("P1", 1, clientHandlerMock);
    Hera gp = null;
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
        gp = new Hera(a, new GameLogic(null));
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
    public void canWin() {
        a.pushEffect(new Hera(a, null));

        ClientHandlerMock clientHandlerMock2 = new ClientHandlerMock();
        Player p2 = new Player("P2", 2, clientHandlerMock2);
        Hephaestus gp2 = new Hephaestus(a, new GameLogic(null));
        p2.initializeGodPower(gp2);
        Space s3 = b.getSpace(0, 3);
        Space s4 = b.getSpace(2, 2);
        Space s5 = b.getSpace(0, 4);
        Space s6 = b.getSpace(1, 3);
        p2.initializeWorkers(s3, s4);
        p2.getWorker1().moveTo(s3);
        p2.getWorker2().moveTo(s4);

        s3.setTowerHeight(2);
        s5.setTowerHeight(3);
        s6.setTowerHeight(3);

        p2.getWorker1().moveTo(s5);

        assertFalse(a.canWin(p2.getWorker1(), s5));

    }

    @Test
    public void canWin2() {
        a.pushEffect(new Hera(a, null));

        ClientHandlerMock clientHandlerMock2 = new ClientHandlerMock();
        Player p2 = new Player("P2", 2, clientHandlerMock2);
        Hephaestus gp2 = new Hephaestus(a, new GameLogic(null));
        p2.initializeGodPower(gp2);
        Space s3 = b.getSpace(0, 3);
        Space s4 = b.getSpace(2, 2);
        Space s5 = b.getSpace(0, 4);
        Space s6 = b.getSpace(1, 3);
        p2.initializeWorkers(s3, s4);
        p2.getWorker1().moveTo(s3);
        p2.getWorker2().moveTo(s4);

        s3.setTowerHeight(2);
        s5.setTowerHeight(3);
        s6.setTowerHeight(3);

        p2.getWorker1().moveTo(s6);

        assertTrue(a.canWin(p2.getWorker1(), s6));

    }
}