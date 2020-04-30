package it.polimi.ingsw.PSP25.Client;

import it.polimi.ingsw.PSP25.Server.Server;

import java.io.IOException;
import java.net.Socket;
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
    public void subscribe(ViewObserver client) {
        this.client = client;
    }
}
