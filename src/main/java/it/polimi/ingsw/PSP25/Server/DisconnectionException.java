package it.polimi.ingsw.PSP25.Server;

import java.io.IOException;

public class DisconnectionException extends IOException {
    private ClientHandler clientHandler;

    public DisconnectionException(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }
}
