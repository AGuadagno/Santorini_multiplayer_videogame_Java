package it.polimi.ingsw.PSP25.Server;

import it.polimi.ingsw.PSP25.Server.Model.Player;

import java.util.List;

public class Controller implements VirtualViewObserver, ControllerObservable {
    private ControllerObserver godPower;
    private VirtualViewObservable clientHandler;

    public Controller(Player player, VirtualViewObservable clientHandler) {
        this.godPower = player.getGodPower();
        this.clientHandler = clientHandler;
        clientHandler.subscribe(this);
    }

    @Override
    public List<Integer> updateAllGodPowers() throws DisconnectionException {
        return clientHandler.updateAllGodPowers();
    }

    @Override
    public int[] updateAskWorkerMovement() throws DisconnectionException {
        return clientHandler.updateAskWorkerMovement();
    }
}
