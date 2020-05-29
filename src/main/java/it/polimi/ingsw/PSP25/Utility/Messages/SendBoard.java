package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;
import java.io.IOException;

/**
 * SendBoard Message.
 * This message is sent to the players in order to show them a copy of the board.
 */
public class SendBoard extends Message {
    private SpaceCopy[][] board;
    public SendBoard(SpaceCopy[][] board) {
        this.board = board;
    }

    public void process(NetworkHandler nh, Client client) throws IOException {
        client.showBoard(board);
    }
}

