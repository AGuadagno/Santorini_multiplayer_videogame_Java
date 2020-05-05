package it.polimi.ingsw.PSP25.Server.Model.GodPowers;

import it.polimi.ingsw.PSP25.Server.Model.*;
import it.polimi.ingsw.PSP25.Server.ClientHandler;
import it.polimi.ingsw.PSP25.Server.Lobby;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApolloTest {
    Board b = null;
    Worker w = null;
    Space s = null;
    Player p1 = new Player("P1", 1, new ClientHandler(new Socket(), 1, new Lobby()));
    Apollo gp = null;
    ActiveEffects a = null;


    @Before
    public void setup() {
        b = new Board();
        s = b.getSpace(0, 0);
        s.setBoard(b);
        a = new ActiveEffects(2);
        a.initializeEffects();
        gp = new Apollo(a, null);
        p1.initializeGodPower(gp);
        w = new Worker(s, p1);
        w.moveTo(s);
    }

    @After
    public void tearDown() {
        b = null;
        w = null;
        s = null;
        p1 = null;
        gp = null;
        a = null;
    }

    @Test
    public void getValidMovementSpaces_TestOpponentWorker() {
        Player p2 = new Player("P2", 2, new ClientHandler(new Socket(), 2, new Lobby()));
        Worker w2 = new Worker(b.getSpace(0, 1), p2);
        b.getSpace(0, 1).setWorker(w2);

        List<Space> spaces = new ArrayList<>();
        spaces.add(b.getSpace(0, 1));
        spaces.add(b.getSpace(1, 1));
        spaces.add(b.getSpace(1, 0));

        List<Space> validMovementSpaces = gp.getValidMovementSpaces(w);
        assertTrue(validMovementSpaces.containsAll(spaces) && validMovementSpaces.size() == spaces.size());
    }

    @Test
    public void getValidMovementSpaces_TestOwnWorker() {
        Worker w2 = new Worker(b.getSpace(0, 1), p1);
        b.getSpace(0, 1).setWorker(w2);
        List<Space> spaces = new ArrayList<>();

        spaces.add(b.getSpace(1, 1));
        spaces.add(b.getSpace(1, 0));

        List<Space> validMovementSpaces = gp.getValidMovementSpaces(w);
        assertTrue(validMovementSpaces.containsAll(spaces) && validMovementSpaces.size() == spaces.size());

    }

    @Test
    public void moveWorker_TestWithExchangeOfPosition() {
        Player p2 = new Player("P2", 2, new ClientHandler(new Socket(), 2, new Lobby()));
        Worker w2 = new Worker(b.getSpace(0, 1), p2);
        b.getSpace(0, 1).setWorker(w2);

        gp.moveWorker(w, b.getSpace(0, 1));
        assertEquals(b.getSpace(0, 1).getWorker(), w);
        assertEquals(w.getSpace(), b.getSpace(0, 1));
        assertEquals(b.getSpace(0, 0).getWorker(), w2);
        assertEquals(w2.getSpace(), b.getSpace(0, 0));
    }

    @Test
    public void moveWorker_TestEmptySpace() {
        gp.moveWorker(w, b.getSpace(0, 1));
        assertEquals(b.getSpace(0, 1).getWorker(), w);
        assertEquals(w.getSpace(), b.getSpace(0, 1));
    }
}
