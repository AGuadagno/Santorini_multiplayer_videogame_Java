package it.polimi.ingsw.PSP25;

import it.polimi.ingsw.PSP25.Model.Player;
import it.polimi.ingsw.PSP25.Model.Space;
import it.polimi.ingsw.PSP25.Model.Worker;
import it.polimi.ingsw.PSP25.Server.ClientHandler;
import it.polimi.ingsw.PSP25.Server.Lobby;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.*;

public class WorkerTest {
    private Worker worker = null;
    private Space space = null;
    private Space space2 = null;
    private Player player = null;

    @Before
    public void setUp() throws Exception {
        space = new Space(0, 0);
        space2 = new Space(1, 1);
        player = new Player("Jerry", 1, new ClientHandler(new Socket(), new Lobby()));
        worker = new Worker(space, player);
    }

    @Test
    public void getSpace_test() {
        assertEquals(worker.getSpace(), space);
    }

    @Test
    public void setSpace_test() {
        worker.setSpace(space2);
        assertEquals(worker.getSpace(), space2);
        worker.setSpace(null);
        assertEquals(worker.getSpace(), null);
    }

    @Test
    public void moveTo_test() {
        worker.moveTo(space2);
        assertEquals(worker.getSpace(), space2);
        assertEquals(space2.getWorker(), worker);
        assertEquals(worker.getHeightBeforeMove(), space.getTowerHeight());
        assertEquals(space.getWorker(), null);
    }

    @Test
    public void getPlayer_test() {
        assertEquals(worker.getPlayer(), player);
    }

    @Test
    public void setHeightBeforeMove_getHeightBeforeMove_test() {
        int h1 = 3;
        int h2 = 0;
        worker.setHeightBeforeMove(h1);
        assertEquals(worker.getHeightBeforeMove(), h1);
        worker.setHeightBeforeMove(h2);
        assertEquals(worker.getHeightBeforeMove(), h2);
    }
}