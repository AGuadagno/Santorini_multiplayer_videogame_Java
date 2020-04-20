package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.NetworkHandler;

import java.io.IOException;

/**
 * PingMessage Class.
 * Ping messages are periodically and continuously sent from client to server and from server to client in order to detect
 * possible network problems.
 */
public class PingMessage extends Message {

    @Override
    public void process(NetworkHandler nh) throws IOException {
        //System.out.println("ping ricevuto");
    }
}
