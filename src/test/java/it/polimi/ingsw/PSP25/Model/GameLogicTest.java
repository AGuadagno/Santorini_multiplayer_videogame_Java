package it.polimi.ingsw.PSP25.Model;

import it.polimi.ingsw.PSP25.Server.ClientHandlerMock;
import it.polimi.ingsw.PSP25.Server.DisconnectionException;
import it.polimi.ingsw.PSP25.Server.VirtualView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class GameLogicTest {
    GameLogic gameLogic;
    ClientHandlerMock clientHandler1, clientHandler2, clientHandler3;
    List<VirtualView> clientHandlerMockList;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        gameLogic = null;
        clientHandler1 = null;
        clientHandler2 = null;
        clientHandler3 = null;
        clientHandlerMockList = null;
    }

    @Test
    public void startGame2PlayersTest() {
        clientHandler1 = new ClientHandlerMock();
        clientHandler2 = new ClientHandlerMock();
        clientHandlerMockList = new ArrayList(Arrays.asList(clientHandler1, clientHandler2));
        gameLogic = new GameLogic(clientHandlerMockList);

        clientHandler1.setAskName("nome1");
        clientHandler2.setAskName("nome2");

        //Game with Demeter and Pan
        clientHandler1.setAskAllGodPowers(new ArrayList(Arrays.asList(4, 7)));
        clientHandler2.setAskAllGodPowers(new ArrayList(Arrays.asList(399, 499)));

        //Player2 chooses Pan
        clientHandler1.setAskGodPower(999);
        clientHandler2.setAskGodPower(2);

        clientHandler1.setAskWorkerPosition(new int[]{6, 8});
        clientHandler2.setAskWorkerPosition(new int[]{7, 11});

        clientHandler1.setAskWorkerMovement(new int[][]{new int[]{1, 5}, new int[]{2, 9}, new int[]{2, 4}});
        clientHandler2.setAskWorkerMovement(new int[][]{new int[]{1, 6}, new int[]{1, 10}, new int[]{1, 15}});

        clientHandler1.setAskToBuild(new int[]{6, 4, 3});
        clientHandler1.setDemeterSecondBuilding(new int[]{10, -1, -1});
        clientHandler2.setAskToBuild(new int[]{10, 6});

        try {
            gameLogic.startGame();
        } catch (DisconnectionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void broadcastBoard() {
    }

    @Test
    public void broadcastGodPowers() {
    }

    @Test
    public void stopGame() {
    }
}