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

        /*Scanner scanner = new Scanner(System.in);
        String answer = null;

        // SELECTION OF WORKER
        if (!w1CanMove) {
            System.out.println("Worker 1 can't move! Worker 2 is automatically selected");
            workerChoice = 2;
        } else if (!w2CanMove) {
            System.out.println("Worker 2 can't move! Worker 1 is automatically selected");
            workerChoice = 1;
        } else {
            System.out.println(playerName + ": Choose a worker");
            workerChoice = scanner.nextInt();
            while (workerChoice < 1 || workerChoice > 2) {
                System.out.println("Worker number is not valid. Choose 1 or 2");
                workerChoice = scanner.nextInt();
            }
        }

        if (workerChoice == 1 ? (w1CanBuild) : (w2CanBuild)) {
            System.out.println("Do you want to build before move? y|n");
            answer = scanner.next();
            while (!(answer.equals("y") || answer.equals("n"))) {
                System.out.println("Not valid choice. Do you want to build before move? y|n");
                answer = scanner.next();
            }
        }

        int[] workerAndBuildBeforeMove = new int[2];
        workerAndBuildBeforeMove[0] = workerChoice;

        if (answer.equals("y")) {
            workerAndBuildBeforeMove[1] = 1;
        } else {
            workerAndBuildBeforeMove[1] = 0;
        }*/

        int[] workerAndBuildBeforeMove = client.askBuildBeforeMovePrometheus(playerName, w1CanMove, w1CanBuild, w2CanMove, w2CanBuild);

        nh.submit(workerAndBuildBeforeMove);
    }
}
