package it.polimi.ingsw.PSP25.Server.Messages;

import it.polimi.ingsw.PSP25.Client.NetworkHandler;

import java.io.IOException;
import java.io.Serializable;

public abstract class Message implements Serializable {

    public void process(NetworkHandler nh) throws IOException {
    }

}
