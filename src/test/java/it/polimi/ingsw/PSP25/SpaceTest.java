package it.polimi.ingsw.PSP25;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SpaceTest {
    Space space = null;
    Board b = null;

    @Before
    public void setup(){
        b = new Board();
        space = b.getSpace(0,0);
    }

    @Test
    public void increaseTowerHeight_CorrectOutput(){
        int initialHeight = space.getTowerHeight();
        space.increaseTowerHeight();
        assertEquals(space.getTowerHeight(), initialHeight + 1);
    }

    @Test
    public void decreaseTowerHeight_CorrectOutput(){
        int initialHeight = space.getTowerHeight();
        space.decreaseTowerHeight();
        assertEquals(space.getTowerHeight(), initialHeight - 1);
    }

    @Test
    public void setTowerHeight_CorrectOutput(){
        space.setTowerHeight(2);
        assertEquals(space.getTowerHeight(), 2);
    }

    @Test
    public void getTowerHeight_CorrectOutput(){
        assertEquals(space.getTowerHeight(), 0);
    }

    @Test
    public void addDome_CorrectOutput(){
        space.addDome();
        assertTrue(space.hasDome());
    }

    @Test
    public void removeDome_CorrectOutput(){
        space.removeDome();
        assertTrue(!space.hasDome());
    }

    @Test
    public void setWorker_getWorker_Test(){
        Worker w = new Worker(space, new Player("Nome", "No1"));
        space.setWorker(w);
        assertEquals(space.getWorker(), w);
    }

    @Test
    public void removeWorker_Test() {
        space.removeWorker();
        assertTrue(space.hasWorker() == false);
    }

    @Test
    public void getX_Test(){
          assertEquals(space.getX(), 0);
    }

    @Test
    public void getY_Test(){
        assertEquals(space.getY(), 0);
    }

    @Test
    public void setBoard_Test(){
        space.setBoard(b);
        assertEquals(b.getSpace(space.getX(),space.getY()), space);
    }

    @Test
    public void hasWorker_Test1(){
        assertTrue(space.hasWorker() == false);
    }

    @Test
    public void getAdjacentSpaces_Test(){
        List<Space> l = new ArrayList<>();
        space.setBoard(b);
        l.add(b.getSpace(1,0));
        l.add(b.getSpace(0,1));
        l.add(b.getSpace(1,1));
        assertTrue(space.getAdjacentSpaces().containsAll(l) && l.size() == 3);

        Space space2 = b.getSpace(0,2);
        List<Space> l2 = new ArrayList<>();
        l2.add((b.getSpace(0,1)));
        l2.add((b.getSpace(1,1)));
        l2.add((b.getSpace(1,2)));
        l2.add((b.getSpace(1,3)));
        l2.add((b.getSpace(0,3)));
        assertTrue(space2.getAdjacentSpaces().containsAll(l2) && l2.size() == 5);

        Space space3 = b.getSpace(2,2);
        List<Space> l3 = new ArrayList<>();
        l3.add((b.getSpace(1,1)));
        l3.add((b.getSpace(2,1)));
        l3.add((b.getSpace(3,1)));
        l3.add((b.getSpace(1,2)));
        l3.add((b.getSpace(3,2)));
        l3.add((b.getSpace(1,3)));
        l3.add((b.getSpace(2,3)));
        l3.add((b.getSpace(3,3)));
        assertTrue(space3.getAdjacentSpaces().containsAll(l3) && l3.size() == 8);



    }



    @Test
    public void hasWorker_Test2(){
        Worker w = new Worker(space, new Player("Nome", "No1"));
        space.setWorker(w);
        assertTrue(space.hasWorker());
    }

    @Test
    public void getNumber_Test(){
        assertEquals(space.getNumber(), 0);
    }

    @Test
    public void toString_Test(){
        assertEquals(space.toString(), "Space 0");
    }

}
