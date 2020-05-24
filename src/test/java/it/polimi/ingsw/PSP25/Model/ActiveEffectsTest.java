package it.polimi.ingsw.PSP25.Model;

import it.polimi.ingsw.PSP25.Model.GodPowers.Apollo;
import it.polimi.ingsw.PSP25.Model.GodPowers.Athena;
import it.polimi.ingsw.PSP25.Model.GodPowers.GodPower;
import it.polimi.ingsw.PSP25.Model.GodPowers.Limus;
import it.polimi.ingsw.PSP25.Server.ClientHandler;
import it.polimi.ingsw.PSP25.Server.Lobby;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ActiveEffectsTest {

    ActiveEffects activeEffects = null;
    Board b = null;
    Worker w = null;
    Player p = null;

    @Before
    public void setup() {
        activeEffects = new ActiveEffects(2);
        activeEffects.initializeEffects();
        b = new Board();
        b.setBoardForAllSpaces();
        b.getSpace(0, 0).setBoard(b);
        p = new Player("Nome", 1, new ClientHandler(new Socket(), 1, new Lobby()));
        w = new Worker(b.getSpace(1, 1), p);
    }

    @After
    public void tearDown() {
        activeEffects = null;
        b = null;
        w = null;
        p = null;
    }

    @Test
    public void canMove_Test() {
        activeEffects.initializeEffects();
        Space highSpace = b.getSpace(2, 2);
        highSpace.setTowerHeight(1);
        activeEffects.pushEffect(new Athena(activeEffects, null));
        assertTrue(!activeEffects.canMove(w, highSpace));

        activeEffects.pushEffect(new GodPower(activeEffects, null));
        activeEffects.pushEffect(new GodPower(activeEffects, null));
        assertTrue(activeEffects.canMove(w, highSpace));
    }

    @Test
    public void adaptEffectsAfterPlayerLose() {
        ActiveEffects activeEffects3 = new ActiveEffects(3);
        activeEffects3.initializeEffects();
        activeEffects3.pushEffect(new Athena(activeEffects3, null));
        activeEffects3.pushEffect(new Apollo(activeEffects3, null));

        Space highSpace = b.getSpace(2, 2);
        highSpace.setTowerHeight(1);
        assertTrue(!activeEffects3.canMove(w, highSpace));

        activeEffects3.adaptEffectsAfterPlayerLose();

        assertTrue(activeEffects3.canMove(w, highSpace));

    }

    @Test
    //Line 90 can not be tested because there isn't any GodPower that limits victory conditions.
    //We added this functionality for future extension (Era)
    public void canWin() {
        ActiveEffects activeEffects3 = new ActiveEffects(3);
        activeEffects3.initializeEffects();
        activeEffects3.pushEffect(new Athena(activeEffects3, null));
        activeEffects3.pushEffect(new Apollo(activeEffects3, null));

        Space highSpace2 = b.getSpace(2, 2);
        highSpace2.setTowerHeight(2);
        Space highSpace3 = b.getSpace(2, 3);
        highSpace3.setTowerHeight(3);
        w = new Worker(highSpace2, p);
        w.moveTo(highSpace3);
        assertTrue(activeEffects3.canWin(w, highSpace3));
    }

    @Test
    public void canBuildPositiveTest() {
        ActiveEffects activeEffects3 = new ActiveEffects(3);
        activeEffects3.initializeEffects();
        activeEffects3.pushEffect(new Athena(activeEffects3, null));
        activeEffects3.pushEffect(new Apollo(activeEffects3, null));

        Space highSpace2 = b.getSpace(2, 2);

        assertTrue(activeEffects3.canBuild(w, highSpace2));
    }

    @Test
    public void canBuildNegativeTest() {
        ActiveEffects activeEffects2 = new ActiveEffects(2);
        activeEffects2.initializeEffects();
        GodPower limus = new Limus(activeEffects2, null);
        limus.initializeWorkers(new Player("nome2", 2, new ClientHandler(new Socket(), 2, new Lobby())),
                b.getSpace(4, 4), b.getSpace(3, 4));
        activeEffects2.pushEffect(limus);

        Space spaceNearLimus = b.getSpace(3, 3);

        assertFalse(activeEffects2.canBuild(w, spaceNearLimus));
    }

}
