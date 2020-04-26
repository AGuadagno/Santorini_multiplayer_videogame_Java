package it.polimi.ingsw.PSP25.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class TurnResultTest {

    @Test
    public void turnResultTest() {
        TurnResult w = TurnResult.WIN;
        assertEquals(TurnResult.valueOf("WIN"), w);
        w = TurnResult.LOSE;
        assertEquals(TurnResult.valueOf("LOSE"), w);
        w = TurnResult.CONTINUE;
        assertEquals(TurnResult.valueOf("CONTINUE"), w);
    }

}