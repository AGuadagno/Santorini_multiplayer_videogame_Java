package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;
import java.io.IOException;


/**
 * AskWorkerPosition Message Class.
 * This message is sent to all the players at the beginning of the game to ask them to position their workers in the board.
 */
public class AskWorkerPosition extends Message {
    private String playerName;
    private int workerNumber;
    private int previousPos;
    private SpaceCopy[][] board;

    public AskWorkerPosition(String playerName, int workerNumber, int previousPos, SpaceCopy[][] board) {
        this.playerName = playerName;
        this.workerNumber = workerNumber;
        this.previousPos = previousPos;
        this.board = board;
    }

    public void process(NetworkHandler nh, Client client) throws IOException {
        int pos = client.askWorkerPosition(playerName, workerNumber, previousPos, board);
        nh.submit(pos);
    }
}
