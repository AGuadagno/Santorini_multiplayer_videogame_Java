package it.polimi.ingsw.PSP25.Model;

import it.polimi.ingsw.PSP25.Server.DisconnectionException;

public interface BroadcastInterface {
    void broadcastBoard() throws DisconnectionException;
}
