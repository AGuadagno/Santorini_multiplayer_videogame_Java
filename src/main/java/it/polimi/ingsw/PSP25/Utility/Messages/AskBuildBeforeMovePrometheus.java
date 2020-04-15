package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

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

    public void process(NetworkHandler nh) throws IOException {

        Scanner scanner = new Scanner(System.in);
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
        }

        nh.submit(workerAndBuildBeforeMove);
    }
}
