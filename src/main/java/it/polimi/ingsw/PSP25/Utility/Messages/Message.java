package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import java.io.IOException;
import java.io.Serializable;

/**
 * Message class.
 * Spacific messages extends this class.
 */
public abstract class Message implements Serializable {

    /**
     * Method that the client who receive the message has to perform.
     *
     * @param nh network handler of the client who is the recipient of the message.
     * @throws IOException
     */
    public void process(NetworkHandler nh, Client client) throws IOException {
    }

}
