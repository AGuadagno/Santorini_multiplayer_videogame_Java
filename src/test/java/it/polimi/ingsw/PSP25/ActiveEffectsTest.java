/*package it.polimi.ingsw.PSP25;

import it.polimi.ingsw.PSP25.Model.GodPowers.GodPower;
import it.polimi.ingsw.PSP25.Server.ClientHandler;
import it.polimi.ingsw.PSP25.Server.Lobby;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.*;

public class ActiveEffectsTest {

    ActiveEffects activeEffects = null;
    Board b = null;
    Worker w = null;
    Player p = null;

    @Before
    public void setup(){
        activeEffects = new ActiveEffects(2);
        activeEffects.initializeEffects();
        b = new Board();
        b.getSpace(0, 0).setBoard(b);
        p = new Player("Nome", 1, new ClientHandler(new Socket(), new Lobby()));
        w = new Worker(b.getSpace(1, 1), p);
    }

    @After
    public void tearDown(){
        activeEffects = null;
        b = null;
        w = null;
        p = null;
    }

    @Test
    public void canMove_Test() {
        activeEffects.initializeEffects();
        Space highSpace = b.getSpace(2,2);
        highSpace.setTowerHeight(1);
        activeEffects.pushEffect(new Athena(activeEffects, null));
        assertTrue(!activeEffects.canMove(w, highSpace));

        activeEffects.pushEffect(new GodPower(activeEffects, null));
        activeEffects.pushEffect(new GodPower(activeEffects, null));
        assertTrue(activeEffects.canMove(w, highSpace));
    }

}
*/