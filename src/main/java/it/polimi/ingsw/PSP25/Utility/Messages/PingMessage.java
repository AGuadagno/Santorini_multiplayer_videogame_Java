package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.NetworkHandler;

import java.io.IOException;

public class PingMessage extends Message {

    @Override
    public void process(NetworkHandler nh) throws IOException {
        //System.out.println("ping ricevuto");
    }
}
