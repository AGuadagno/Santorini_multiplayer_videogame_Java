package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import it.polimi.ingsw.PSP25.Client.ViewObservable;

import java.io.IOException;
import java.util.Scanner;

/**
 * AskNumberOfPlayers Message Class.
 * This Message is sent to the player who crates the game to ask him the number of players he wants for the game.
 */

public class AskNumberOfPlayers extends Message {

    private String question = "Select the number of players: ";

    /*public void process(NetworkHandler nh, Client client) throws IOException {
        int numOfPlayers;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println(question);
            numOfPlayers = scanner.nextInt();
        } while (numOfPlayers < 2 || numOfPlayers > 3);
        nh.submit(numOfPlayers);
    }*/

    public void process(NetworkHandler nh, Client client) throws IOException {
        int numOfPlayers = client.selectNumOfPlayers(question);
        nh.submit(numOfPlayers);
    }

}
