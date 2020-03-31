package it.polimi.ingsw.PSP25;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GodPowerTest {

    ActiveEffects activeEffects = new ActiveEffects(2);
    GodPower godPower = null;
    ActiveEffects activeEffects2 = new ActiveEffects(2);
    GodPower godPower2 = null;
    Board b = null;
    Space demoSpace = null;
    Player demoPlayer = null;
    Worker demoWorker = null;
    Player demoPlayer2 = null;
    Worker demoWorker2 = null;

    @Before
    public void setup(){
        godPower = new GodPower(activeEffects);
        godPower2 = new GodPower(activeEffects2);
        activeEffects.initializeEffects();
        activeEffects2.initializeEffects();
        b = new Board();
        demoSpace = b.getSpace(3, 3);
        demoSpace.setBoard(b);
        demoPlayer = new Player("Name", "Na1");
        demoWorker = new Worker(demoSpace, demoPlayer);
        demoPlayer2 = new Player("Name", "Na2");
        demoSpace.setWorker(demoWorker);
    }

    @After
    public void tearDown(){
        GodPower godPower2 = null;
        Board b = null;
        Space demoSpace = null;
        Player demoPlayer = null;
        Worker demoWorker = null;
        Player demoPlayer2 = null;
        Worker demoWorker2 = null;
        GodPower godPower = null;
    }

    @Test
    public void getValidMovementSpaces_Test() {
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
    public void getValidBuildingSpaces_Test() {
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
        demoSpace = b.getSpace(3,3);
        demoWorker.moveTo(demoSpace);
        List<Space> buildHighList = new ArrayList<Space>();
        Space buildSpace1 = b.getSpace(3,4 );
        Space buildSpace2 = b.getSpace(4, 4);
        Space buildSpace3 = b.getSpace(2, 3);
        Space domeSpace2 = b.getSpace(2, 2);

        buildSpace1.setTowerHeight(1);
        buildSpace2.setTowerHeight(2);
        buildSpace3.setTowerHeight(3);
        domeSpace2.addDome();

        buildHighList.add(b.getSpace(2,3));
        buildHighList.add(b.getSpace(2,4));
        buildHighList.add(b.getSpace(3,2));
        buildHighList.add(b.getSpace(3,4));
        buildHighList.add(b.getSpace(4,2));
        buildHighList.add(b.getSpace(4,3));
        buildHighList.add(b.getSpace(4,4));

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

        b.getSpace(2,4).setTowerHeight(2);
        b.getSpace(4,2).setTowerHeight(3);
        b.getSpace(3,2).setTowerHeight(1);

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
    public void toString_Test(){
        GodPower GP = new Athena(activeEffects);
        String expectedString = "Athena";
        assertTrue(GP.toString().equals(expectedString));

        GodPower GP2 = new GodPower(activeEffects);
        String expectedString2 = "GodPower";
        assertTrue(GP2.toString().equals(expectedString2));
    }

    @Test
    public void verifyLoseByBuilding_Test(){
        // Not blocked Worker
        assertFalse(godPower.verifyLoseByBuilding(godPower.getValidBuildSpaces(demoWorker)));

        // Blocked Worker
        demoWorker.moveTo(b.getSpace(0,0));
        b.getSpace(1,0).addDome();
        b.getSpace(0,1).addDome();
        b.getSpace(1,1).addDome();
        assertTrue(godPower.verifyLoseByBuilding(godPower.getValidBuildSpaces(demoWorker)));
    }

}

