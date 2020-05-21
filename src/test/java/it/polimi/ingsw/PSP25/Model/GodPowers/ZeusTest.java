package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.*;
import it.polimi.ingsw.PSP25.Server.ClientHandlerMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ZeusTest {

    Board b = null;
    Worker w = null;
    Space s1 = null;
    Space s2 = null;
    ClientHandlerMock clientHandlerMock = new ClientHandlerMock();
    Player p1 = new Player("P1", 1, clientHandlerMock);
    Zeus gp = null;
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
        gp = new Zeus(a, new GameLogic(null));
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
    public void getValidBuildSpaces() {
        List<Space> spaces = new ArrayList<>();
        spaces.add(b.getSpace(0, 0));
        spaces.add(b.getSpace(0, 1));
        spaces.add(b.getSpace(1, 1));
        spaces.add(b.getSpace(1, 0));

        List<Space> validBuildingSpaces = gp.getValidBuildSpaces(p1.getWorker1());
        assertTrue(validBuildingSpaces.containsAll(spaces) && spaces.size() == validBuildingSpaces.size());
    }

    @Test
    public void getValidBuildSpaces2() {
        // Present space tower height = 3
        List<Space> spaces = new ArrayList<>();
        b.getSpace(0, 0).setTowerHeight(3);
        spaces.add(b.getSpace(0, 1));
        spaces.add(b.getSpace(1, 1));
        spaces.add(b.getSpace(1, 0));

        List<Space> validBuildingSpaces = gp.getValidBuildSpaces(p1.getWorker1());
        assertTrue(validBuildingSpaces.containsAll(spaces) && spaces.size() == validBuildingSpaces.size());
    }
}