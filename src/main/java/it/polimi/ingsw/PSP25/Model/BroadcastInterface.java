package it.polimi.ingsw.PSP25.Model;

import it.polimi.ingsw.PSP25.Server.DisconnectionException;

/**
 * Broadcastinterface class.
 * This interface is used to share information among all the players
 * (for example: the board, the god power chosen by each player ecc).
 */
public interface BroadcastInterface {
    void broadcastBoard() throws DisconnectionException;
}
