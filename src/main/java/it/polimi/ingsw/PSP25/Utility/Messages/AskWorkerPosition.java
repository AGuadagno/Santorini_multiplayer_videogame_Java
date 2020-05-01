package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;

import java.io.IOException;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);

        System.out.println(playerName + " it's your turn! Choose the position of your "
                + ((this.workerNumber == 1) ? "first" : "second") + " worker");
        int pos = scanner.nextInt();
        int x = pos % 5;
        int y = pos / 5;
        while (pos < 0 || pos > 24 || pos == previousPos || board[x][y].hasWorker()) {
            System.out.println("Position not valid. Choose the position of your "
                    + ((this.workerNumber == 1) ? "first" : "second") + " worker, between 0 and 24 " +
                    "and not already occupied by other workers");
            pos = scanner.nextInt();
            x = pos % 5;
            y = pos / 5;
        }

        nh.submit(pos);
    }
}
