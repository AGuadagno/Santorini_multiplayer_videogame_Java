package it.polimi.ingsw.PSP25;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoardTest {

    Board board = null;

    @Before
    public void setup(){
        board = new Board();
    }

    @After
    public void tearDown(){
        board = null;
    }

    @Test
    public void getSpace_correctOut() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(board.getSpace(i,j).getX(), i);
                assertEquals(board.getSpace(i,j).getY(), j);
                assertEquals(board.getSpace(i,j).getNumber(), 5*j + i);
            }
        }
    }

    @Test
    public void print() {
    }
}
