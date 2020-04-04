package it.polimi.ingsw.PSP25;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PanTest {
    Board b = null;
    Space s = null;
    Worker w = null;
    Player p = null;
    Pan gp = null;
    ActiveEffects a = null;

    @Before
    public void setup(){
        b = new Board();
        s = b.getSpace(1,0);
        s.setTowerHeight(3);
        p = new Player("Name1", 1);
        w = new Worker(s,p);
        a = new ActiveEffects(2);
        gp = new Pan(a);
    }

    @After
    public void tearDown(){
        b = null;
        s = null;
        w = null;
        p = null;
        gp = null;
        a = null;
    }

    @Test
    public void verifyWin() {
        Space s2 = b.getSpace(2,0);
        s2.setTowerHeight(1);

        // Worker moves down 2 levels
        w.moveTo(s);
        w.moveTo(s2);
        assertTrue(gp.verifyWin(w));

        // Worker moves down 1 level
        s2 = b.getSpace(1,1);
        s2.setTowerHeight(0);
        w.moveTo(s2);
        assertTrue(!gp.verifyWin(w));

        // Worker moves down 3 levels
        w.moveTo(s);
        w.moveTo(s2);
        assertTrue(gp.verifyWin(w));

        // Worker moves up to 3rd level
        s.setTowerHeight(3);
        s2.setTowerHeight(2);
        w.moveTo(s2);
        w.moveTo(s);
        assertTrue(gp.verifyWin(w));

    }
}