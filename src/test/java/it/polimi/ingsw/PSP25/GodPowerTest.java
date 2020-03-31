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
    Board b = null;
    Space demoSpace = null;
    Player demoPlayer = null;
    Worker demoWorker = null;

    @Before
    public void setup(){
        godPower = new GodPower(activeEffects);
        activeEffects.initializeEffects();
        b = new Board();
        demoSpace = b.getSpace(3, 3);
        demoSpace.setBoard(b);
        demoSpace.setWorker(demoWorker);
        demoPlayer = new Player("Name", "Na1");
        demoWorker = new Worker(demoSpace, demoPlayer);
    }

    @After
    public void tearDown(){
        activeEffects = null;
        godPower = null;
    }

    /*  @Test
    // Work in progress ...
    public void getValidMovementSpaces_Test(){
        List<Space> demoList = new ArrayList<Space>();
        demoList.add(b.getSpace(2,2));
        demoList.add(b.getSpace(2,3));
        demoList.add(b.getSpace(2,4));
        demoList.add(b.getSpace(3,2));
        demoList.add(b.getSpace(3,4));
        demoList.add(b.getSpace(4,2));
        demoList.add(b.getSpace(4,3));
        demoList.add(b.getSpace(4,4));

        assertEquals(demoList.size(), godPower.getValidBuildSpaces(demoWorker).size());
    } */

}
