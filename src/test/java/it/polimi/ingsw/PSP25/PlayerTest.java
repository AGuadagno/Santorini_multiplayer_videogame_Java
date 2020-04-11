package it.polimi.ingsw.PSP25;

import it.polimi.ingsw.PSP25.Server.ClientHandler;
import it.polimi.ingsw.PSP25.Server.Lobby;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.*;

public class PlayerTest {

    Player player = null;

    @Before
    public void setup(){
        player = new Player("Demo", 1, new ClientHandler(new Socket(), new Lobby()));
    }

    @After
    public void tearDown(){
        player = null;
    }

    @Test
    public void getName_correctOutput(){
        assertEquals(player.getName(), "Demo");
    }

    @Test
    public void getID_correctOutput(){
        assertEquals(player.getID(), "DE1");
    }

    @Test
    public void initializeWorkers_getWorker_test() {
        Space s1 = new Space(0,0);
        Space s2 = new Space(1,1);
        player.initializeWorkers(s1, s2);
        assertEquals(player.getWorker1().getSpace(), s1);
        assertEquals(player.getWorker2().getSpace(), s2);
        assertEquals(s1.getWorker(), player.getWorker1());
        assertEquals(s2.getWorker(), player.getWorker2());
    }

    // generic GodPower
    @Test
    public void initializeGodPower_getGodPower_Test(){
        ActiveEffects activeEffects = new ActiveEffects(2);
        GodPower godPower = new GodPower(activeEffects);
        player.initializeGodPower(godPower);
        assertEquals(player.getGodPower(), godPower);
    }


    // specific GodPower, es: Athena
    @Test
    public void initializeGodPower_getGodPower_Test2(){
        ActiveEffects activeEffects = new ActiveEffects(2);
        GodPower godPower = new Athena(activeEffects);
        player.initializeGodPower(godPower);
        assertEquals(player.getGodPower(), godPower);
    }
}
