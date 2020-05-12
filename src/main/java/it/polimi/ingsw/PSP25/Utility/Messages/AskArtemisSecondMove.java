package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * AskArtemisSecondMove Message Class.
 * This message is sent to the player who controls Artemis to ask him if he wants to move for a second time.
 * If the player wants to move for a second time, he's asked to chose a valid movement space,
 * then the worker is moved to that space.
 */
public class AskArtemisSecondMove extends Message {
    List<SpaceCopy> validSecondMovementSpaces;
    String playerName;

    public AskArtemisSecondMove(String playerName, List<SpaceCopy> validSecondMovementSpaces) {
        this.validSecondMovementSpaces = validSecondMovementSpaces;
        this.playerName = playerName;
    }

    @Override
    public void process(NetworkHandler nh, Client client) throws IOException {
        /*Scanner scanner = new Scanner(System.in);
        String answer;

        System.out.println("Do you want to move your Worker for the second time? y/n");
        answer = scanner.next();
        while (!answer.equals("y") && !answer.equals("n")) {
            System.out.println("Your choice is not valid! Choose again!");
            answer = scanner.next();
        }

        int chosenMovementSpace = -1;
        if (answer.equals("y")) {
            System.out.println(validSecondMovementSpaces.toString());
            System.out.println(playerName + ": Choose movement space");
            chosenMovementSpace = scanner.nextInt();
            while (!(validSecondMovementSpaces.stream().map(SpaceCopy::getNumber).collect(Collectors.toList())).
                    contains(chosenMovementSpace)) {
                System.out.println(chosenMovementSpace + " is not in the valid movement spaces list");
                chosenMovementSpace = scanner.nextInt();
            }
        }*/

        int chosenMovementSpace = client.askArtemisSecondMove(playerName, validSecondMovementSpaces);

        nh.submit(chosenMovementSpace);

    }
}
