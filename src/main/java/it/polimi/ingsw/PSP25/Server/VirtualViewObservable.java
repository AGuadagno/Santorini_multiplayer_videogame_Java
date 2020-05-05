package it.polimi.ingsw.PSP25.Server;

import java.util.List;

public interface VirtualViewObservable {
    void subscribe(VirtualViewObserver controller);

    List<Integer> updateAllGodPowers() throws DisconnectionException;

    int[] updateAskWorkerMovement() throws DisconnectionException;
}
