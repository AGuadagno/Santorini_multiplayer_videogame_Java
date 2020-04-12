package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import java.io.IOException;
import java.util.Scanner;

public class AskName extends Message {
    private int playerNumber;
    private String question;

    public AskName(int playerNumber) {
        this.playerNumber = playerNumber;
        question = "Player " + playerNumber + " enter your name (2 Characters or more): ";
    }

    public void process(NetworkHandler nh) throws IOException {
        System.out.println(question);
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();
        while (name.length() < 2) {
            System.out.println("Player " + playerNumber +
                    " your name it's too short. Enter another name (2 Characters or more): ");
            name = scanner.next();
        }

        nh.submit(name);
    }
}

