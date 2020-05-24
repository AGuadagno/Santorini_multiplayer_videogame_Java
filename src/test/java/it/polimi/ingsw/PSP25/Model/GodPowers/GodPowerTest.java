package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.*;
import it.polimi.ingsw.PSP25.Server.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class GodPowerTest {

    ActiveEffects activeEffects;
    ActiveEffects activeEffects2;
    GodPower godPower = null;
    GodPower godPower2 = null;
    Board b = null;
    Space demoSpace = null;
    Player demoPlayer = null;
    Player demoPlayer2 = null;
    Worker demoWorker = null;
    Worker demoWorker2 = null;

    @Before
    public void setup() {
        activeEffects = new ActiveEffects(2);
        activeEffects2 = new ActiveEffects(2);
        List<VirtualView> clientList = new ArrayList<>();
        clientList.add(new ClientHandlerMock());
        clientList.add(new ClientHandlerMock());

        godPower = new GodPower(activeEffects, new GameLogic(clientList));
        godPower2 = new GodPower(activeEffects2, null);
        activeEffects.initializeEffects();
        activeEffects2.initializeEffects();
        b = new Board();
        b.setBoardForAllSpaces();
        demoSpace = b.getSpace(3, 3);
        demoSpace.setBoard(b);
        demoPlayer = new Player("Name", 1, new ClientHandler(new Socket(), 1, new Lobby()));
        demoWorker = new Worker(demoSpace, demoPlayer);
        demoPlayer2 = new Player("Name", 2, new ClientHandler(new Socket(), 2, new Lobby()));
        demoSpace.setWorker(demoWorker);
    }

    @After
    public void tearDown() {
        activeEffects = null;
        activeEffects2 = null;
        godPower2 = null;
        b = null;
        demoSpace = null;
        demoPlayer = null;
        demoWorker = null;
        demoPlayer2 = null;
        demoWorker2 = null;
        godPower = null;
    }

    @Test
    public void getValidMovementSpaces_test() {
        List<Space> demoList = new ArrayList<Space>();
        demoList.add(b.getSpace(2, 2));
        demoList.add(b.getSpace(2, 3));
        demoList.add(b.getSpace(2, 4));
        demoList.add(b.getSpace(3, 2));
        demoList.add(b.getSpace(3, 4));
        demoList.add(b.getSpace(4, 2));
        demoList.add(b.getSpace(4, 3));
        demoList.add(b.getSpace(4, 4));

        assertEquals(demoList.size(), godPower.getValidMovementSpaces(demoWorker).size());
        assertTrue(godPower.getValidMovementSpaces(demoWorker).containsAll(demoList));

        // Corner Case
        Space cornerSpace = b.getSpace(0, 0);
        demoWorker.moveTo(cornerSpace);
        List<Space> cornerList = new ArrayList<Space>();
        cornerList.add(b.getSpace(1, 0));
        cornerList.add(b.getSpace(0, 1));
        cornerList.add(b.getSpace(1, 1));

        assertEquals(cornerList.size(), godPower.getValidMovementSpaces(demoWorker).size());
        assertTrue(godPower.getValidMovementSpaces(demoWorker).containsAll(cornerList));

        // Border Case
        Space borderSpace = b.getSpace(0, 2);
        demoWorker.moveTo(borderSpace);
        List<Space> borderList = new ArrayList<Space>();
        borderList.add(b.getSpace(1, 2));
        borderList.add(b.getSpace(0, 1));
        borderList.add(b.getSpace(1, 1));
        borderList.add(b.getSpace(1, 3));
        borderList.add(b.getSpace(0, 3));

        assertEquals(borderList.size(), godPower.getValidMovementSpaces(demoWorker).size());
        assertTrue(godPower.getValidMovementSpaces(demoWorker).containsAll(borderList));

        // Height >= 2 & Dome
        demoWorker.moveTo(demoSpace);
        List<Space> highList = new ArrayList<Space>();
        Space highSpace1 = b.getSpace(3, 4);
        Space highSpace2 = b.getSpace(4, 4);
        Space highSpace3 = b.getSpace(2, 3);
        Space domeSpace = b.getSpace(2, 2);

        highSpace1.setTowerHeight(1);
        highSpace2.setTowerHeight(2);
        highSpace3.setTowerHeight(3);
        domeSpace.addDome();

        highList.add(b.getSpace(2, 4));
        highList.add(b.getSpace(3, 2));
        highList.add(b.getSpace(3, 4));
        highList.add(b.getSpace(4, 2));
        highList.add(b.getSpace(4, 3));

        assertEquals(highList.size(), godPower.getValidMovementSpaces(demoWorker).size());
        assertTrue(godPower.getValidMovementSpaces(demoWorker).containsAll(highList));

        highSpace1.setTowerHeight(0);
        highSpace2.setTowerHeight(0);
        highSpace3.setTowerHeight(0);
        domeSpace.removeDome();

        //Occupied Spaces
        List<Space> occupiedMovementList = new ArrayList<Space>();
        Space occupiedSpace1 = b.getSpace(3, 4);
        Space occupiedSpace2 = b.getSpace(2, 2);
        Worker demoWorker2 = new Worker(occupiedSpace1, demoPlayer);
        Worker demoWorker3 = new Worker(occupiedSpace2, demoPlayer2);
        occupiedSpace1.setWorker(demoWorker2);
        occupiedSpace2.setWorker(demoWorker3);

        occupiedMovementList.add(b.getSpace(2, 3));
        occupiedMovementList.add(b.getSpace(2, 4));
        occupiedMovementList.add(b.getSpace(3, 2));
        occupiedMovementList.add(b.getSpace(4, 2));
        occupiedMovementList.add(b.getSpace(4, 3));
        occupiedMovementList.add(b.getSpace(4, 4));

        assertEquals(occupiedMovementList.size(), godPower.getValidMovementSpaces(demoWorker).size());
        assertTrue(godPower.getValidMovementSpaces(demoWorker).containsAll(occupiedMovementList));

        occupiedSpace1.removeWorker();
        occupiedSpace2.removeWorker();

    }

    @Test
    public void getValidBuildingSpaces_test() {
        List<Space> demoList2 = new ArrayList<Space>();
        demoList2.add(b.getSpace(2, 2));
        demoList2.add(b.getSpace(2, 3));
        demoList2.add(b.getSpace(2, 4));
        demoList2.add(b.getSpace(3, 2));
        demoList2.add(b.getSpace(3, 4));
        demoList2.add(b.getSpace(4, 2));
        demoList2.add(b.getSpace(4, 3));
        demoList2.add(b.getSpace(4, 4));

        assertEquals(demoList2.size(), godPower.getValidBuildSpaces(demoWorker).size());
        assertTrue(godPower.getValidBuildSpaces(demoWorker).containsAll(demoList2));

        // Corner Case
        Space cornerSpace = b.getSpace(0, 0);
        demoWorker.moveTo(cornerSpace);
        List<Space> cornerList = new ArrayList<Space>();
        cornerList.add(b.getSpace(1, 0));
        cornerList.add(b.getSpace(0, 1));
        cornerList.add(b.getSpace(1, 1));

        assertEquals(cornerList.size(), godPower.getValidBuildSpaces(demoWorker).size());
        assertTrue(godPower.getValidBuildSpaces(demoWorker).containsAll(cornerList));

        // Border Case
        Space borderSpace = b.getSpace(0, 2);
        demoWorker.moveTo(borderSpace);
        List<Space> borderList = new ArrayList<Space>();
        borderList.add(b.getSpace(1, 2));
        borderList.add(b.getSpace(0, 1));
        borderList.add(b.getSpace(1, 1));
        borderList.add(b.getSpace(1, 3));
        borderList.add(b.getSpace(0, 3));

        assertEquals(borderList.size(), godPower.getValidBuildSpaces(demoWorker).size());
        assertTrue(godPower.getValidBuildSpaces(demoWorker).containsAll(borderList));

        // Height >= 2 & Dome
        demoSpace = b.getSpace(3, 3);
        demoWorker.moveTo(demoSpace);
        List<Space> buildHighList = new ArrayList<Space>();
        Space buildSpace1 = b.getSpace(3, 4);
        Space buildSpace2 = b.getSpace(4, 4);
        Space buildSpace3 = b.getSpace(2, 3);
        Space domeSpace2 = b.getSpace(2, 2);

        buildSpace1.setTowerHeight(1);
        buildSpace2.setTowerHeight(2);
        buildSpace3.setTowerHeight(3);
        domeSpace2.addDome();

        buildHighList.add(b.getSpace(2, 3));
        buildHighList.add(b.getSpace(2, 4));
        buildHighList.add(b.getSpace(3, 2));
        buildHighList.add(b.getSpace(3, 4));
        buildHighList.add(b.getSpace(4, 2));
        buildHighList.add(b.getSpace(4, 3));
        buildHighList.add(b.getSpace(4, 4));

        assertEquals(buildHighList.size(), godPower.getValidBuildSpaces(demoWorker).size());
        assertTrue(godPower.getValidBuildSpaces(demoWorker).containsAll(buildHighList));

        //Occupied Spaces
        List<Space> occupiedBuildList = new ArrayList<Space>();
        Space occupiedSpace1 = b.getSpace(3, 4);
        Space occupiedSpace2 = b.getSpace(2, 2);
        Worker demoWorker2 = new Worker(occupiedSpace1, demoPlayer);
        Worker demoWorker3 = new Worker(occupiedSpace2, demoPlayer2);
        occupiedSpace1.setWorker(demoWorker2);
        occupiedSpace2.setWorker(demoWorker3);

        b.getSpace(2, 4).setTowerHeight(2);
        b.getSpace(4, 2).setTowerHeight(3);
        b.getSpace(3, 2).setTowerHeight(1);

        occupiedBuildList.add(b.getSpace(2, 3));
        occupiedBuildList.add(b.getSpace(2, 4));
        occupiedBuildList.add(b.getSpace(3, 2));
        occupiedBuildList.add(b.getSpace(4, 2));
        occupiedBuildList.add(b.getSpace(4, 3));
        occupiedBuildList.add(b.getSpace(4, 4));

        assertEquals(occupiedBuildList.size(), godPower.getValidBuildSpaces(demoWorker).size());
        assertTrue(godPower.getValidBuildSpaces(demoWorker).containsAll(occupiedBuildList));

        occupiedSpace1.removeWorker();
        occupiedSpace2.removeWorker();

    }

    @Test
    public void canMove_test() {
        assertTrue(godPower.canMove(demoWorker, demoSpace));
    }

    @Test
    public void canBuild_test() {
        assertTrue(godPower.canMove(demoWorker, demoSpace));
    }

    @Test
    public void canWin_test() {
        assertTrue(godPower.canWin(demoWorker, demoSpace));
    }

    @Test
    public void moveWorker_test() {
        Space demoSpace2 = b.getSpace(4, 4);
        demoSpace2.setTowerHeight(3);

        godPower.moveWorker(demoWorker, demoSpace2);

        assertEquals(demoWorker.getSpace(), demoSpace2);
        assertEquals(demoSpace2.getWorker(), demoWorker);
        assertEquals(demoWorker.getHeightBeforeMove(), demoSpace.getTowerHeight());
        assertEquals(demoSpace.getWorker(), null);
    }

    @Test
    public void buildBlock_test() {
        Space space1 = b.getSpace(3, 4);
        Space space2 = b.getSpace(3, 0);
        Space space3 = b.getSpace(3, 1);
        Space space4 = b.getSpace(3, 2);
        space2.setTowerHeight(1);
        space3.setTowerHeight(2);
        space4.setTowerHeight(3);

        //Build on space with towerHeight 0,1,2
        space1.setTowerHeight(0);
        godPower.buildBlock(space1);
        assertEquals(space1.getTowerHeight(), 1);
        assertFalse(space1.hasDome());

        space2.setTowerHeight(1);
        godPower.buildBlock(space2);
        assertEquals(space2.getTowerHeight(), 2);
        assertFalse(space2.hasDome());

        space3.setTowerHeight(2);
        godPower.buildBlock(space3);
        assertEquals(space3.getTowerHeight(), 3);
        assertFalse(space3.hasDome());

        //Build on space with towerHeight 3
        space4.setTowerHeight(3);
        godPower.buildBlock(space4);
        assertEquals(space4.getTowerHeight(), 3);
        assertTrue(space4.hasDome());
    }

    @Test
    public void verifyWin_test() {
        //Positive test
        Space space = b.getSpace(1, 4);
        Space space2 = b.getSpace(3, 2);
        space.setTowerHeight(2);
        space2.setTowerHeight(3);
        demoWorker.moveTo(space);
        demoWorker.moveTo(space2);
        assertTrue(godPower.verifyWin(demoWorker));

        //Negative test
        Space space3 = b.getSpace(0, 0);
        Space space4 = b.getSpace(4, 4);
        space3.setTowerHeight(3);
        demoWorker.moveTo(space3);
        assertFalse(godPower.verifyWin(demoWorker));
        demoWorker.moveTo(space4);
        assertFalse(godPower.verifyWin(demoWorker));
    }

    @Test
    public void verifyLoseByMovement_test() {
        //Negative test
        Space space2 = b.getSpace(4, 4);
        demoWorker2 = new Worker(space2, demoPlayer);
        space2.setWorker(demoWorker2);
        assertFalse(godPower.verifyLoseByMovement(godPower.getValidMovementSpaces(demoWorker),
                godPower.getValidMovementSpaces(demoWorker2)));

        //Positive test
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                b.getSpace(i, j).addDome();
            }
        }
        assertTrue(godPower.verifyLoseByMovement(godPower.getValidMovementSpaces(demoWorker),
                godPower.getValidMovementSpaces(demoWorker2)));

    }

    @Test
    public void verifyLoseByBuilding_test() {
        // Not blocked Worker
        assertFalse(godPower.verifyLoseByBuilding(godPower.getValidBuildSpaces(demoWorker)));

        // Blocked Worker
        demoWorker.moveTo(b.getSpace(0, 0));
        b.getSpace(1, 0).addDome();
        b.getSpace(0, 1).addDome();
        b.getSpace(1, 1).addDome();
        assertTrue(godPower.verifyLoseByBuilding(godPower.getValidBuildSpaces(demoWorker)));
    }

    @Test
    public void turnSequence_test_LoseByMovement() throws DisconnectionException {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                b.getSpace(i, j).addDome();
            }
        }
        demoPlayer.initializeWorkers(b.getSpace(0, 0), b.getSpace(4, 4));
        assertEquals(TurnResult.LOSE, godPower.turnSequence(demoPlayer, activeEffects));
    }

    @Test
    public void turnSequence_test_LoseByBuilding() throws DisconnectionException {
        ClientHandlerMock clientHandlerMock = new ClientHandlerMock();
        Player demoPlayer3 = new Player("Name", 1, clientHandlerMock);
        demoPlayer3.initializeWorkers(b.getSpace(0, 1), b.getSpace(4, 4));
        GodPower limus = new Limus(activeEffects, null);
        limus.initializeWorkers(new Player("nome2", 2, new ClientHandler(new Socket(), 2, new Lobby())),
                b.getSpace(1, 1), b.getSpace(3, 4));
        activeEffects.pushEffect(limus);

        clientHandlerMock.setAskWorkerMovement(new int[]{1, 0});
        assertEquals(TurnResult.LOSE, godPower.turnSequence(demoPlayer3, activeEffects));
    }

    @Test
    public void addActiveEffects_test() {
        godPower.addActiveEffects(activeEffects, demoWorker, demoWorker2, demoWorker);
        demoWorker.moveTo(b.getSpace(4, 4));
        Space highSpace = b.getSpace(0, 0);
        highSpace.setTowerHeight(1);
        assertTrue(activeEffects.canMove(demoWorker, highSpace));
    }

    @Test
    public void toString_test() {
        GodPower GP = new Athena(activeEffects, null);
        String expectedString = "Athena";
        assertEquals(GP.toString(), expectedString);

        GodPower GP2 = new GodPower(activeEffects, null);
        String expectedString2 = "GodPower";
        assertEquals(GP2.toString(), expectedString2);
    }

}