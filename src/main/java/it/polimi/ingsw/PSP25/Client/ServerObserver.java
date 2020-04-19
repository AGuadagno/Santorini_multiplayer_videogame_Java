package it.polimi.ingsw.PSP25.Client;

import it.polimi.ingsw.PSP25.Utility.Messages.Message;

public interface ServerObserver {
    void didReceiveServerMessage(Message message);
}
