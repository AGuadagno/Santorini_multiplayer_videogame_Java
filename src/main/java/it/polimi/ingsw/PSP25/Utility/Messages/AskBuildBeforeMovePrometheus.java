package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;

import java.io.IOException;
import java.util.Scanner;

/**
 * AskBuildBeforeMovePrometheus Message Class.
 * This Message is sent to the player who controls Prometheus to ask him if he wants to build before move.
 * If the answer is yes, the player is asked to chose a worker and a valid space where the selected worker can build.
 */
public class AskBuildBeforeMovePrometheus extends Message {

    boolean w1CanMove;
    boolean w2CanMove;
    boolean w1CanBuild;
    boolean w2CanBuild;
    String playerName;
    int workerChoice;

    public AskBuildBeforeMovePrometheus(String playerName, boolean w1CanMove, boolean w2CanMove, boolean w1CanBuild, boolean w2CanBuild) {
        this.w1CanMove = w1CanMove;
        this.w2CanMove = w2CanMove;
        this.w1CanBuild = w1CanBuild;
        this.w2CanBuild = w2CanBuild;
        this.playerName = playerName;
    }

    public void process(NetworkHandler nh, Client client) throws IOException {

        int[] workerAndBuildBeforeMove = client.askBuildBeforeMovePrometheus(playerName, w1CanMove, w1CanBuild, w2CanMove, w2CanBuild);
        nh.submit(workerAndBuildBeforeMove);
    }
}
