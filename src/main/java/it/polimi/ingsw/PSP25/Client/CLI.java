package it.polimi.ingsw.PSP25.Client;

import java.util.Scanner;

public class CLI implements ViewObservable {
    private Scanner scanner = new Scanner(System.in);
    private ViewObserver client;

    @Override
    public void askIPAddress() {
        System.out.println("IP address of server?");
        String ip = scanner.nextLine();

        client.updateIPAddress(ip);
    }

    @Override
    public void setConnectionMessage(String s) {
        System.out.println(s);
    }

    @Override
    public void askNumOfPlayers(String question) {
        int numOfPlayers;
        do {
            System.out.println(question);
            numOfPlayers = scanner.nextInt();
        } while (numOfPlayers < 2 || numOfPlayers > 3);
        client.updateNumOfPlayers(numOfPlayers);
    }

    @Override
    public void subscribe(ViewObserver client) {
        this.client = client;
    }

}
