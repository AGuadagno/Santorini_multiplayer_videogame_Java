package it.polimi.ingsw.PSP25.Client;

import it.polimi.ingsw.PSP25.Client.GUI.GUI;
import it.polimi.ingsw.PSP25.Utility.Messages.Message;
import it.polimi.ingsw.PSP25.Server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable, ServerObserver, ViewObserver {

    private Message receivedMessage = null;
    private ViewObservable view;
    private Scanner scanner;

    public Client(ViewObservable view) {

        this.view = view;

    }

    @Override
    public void run() {
        scanner = new Scanner(System.in);
        view.askIPAddress();
    }

    @Override
    public synchronized void didReceiveServerMessage(Message message) {
        this.receivedMessage = message;
        notifyAll();
    }

    @Override
    public void updateIPAddress(String ip) {
        Socket server;
        try {
            server = new Socket(ip, Server.SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }
        System.out.println("Connected");

        // CREATION OF NETWORK HANDLER
        NetworkHandler networkHandler = new NetworkHandler(server);
        networkHandler.addObserver(this);
        Thread networkHandlerThread = new Thread(networkHandler);
        networkHandlerThread.start();

        // RECEIVING OF MESSAGES FROM SERVER
        do {
            synchronized (this) {

                networkHandler.receiveCommand();
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (receivedMessage != null) {
                    try {
                        receivedMessage.process(networkHandler);
                    } catch (IOException e) {
                        System.out.println("Disconnected from server");
                        //e.printStackTrace();
                    }
                }
            }
        } while (receivedMessage != null);

        System.out.println("\nDo you want to play again? (y = yes, n = no)");
        String answer = scanner.next();
        while (!(answer.equals("y") || answer.equals("n"))) {
            System.out.println("Your Choice is not valid. insert 'y' to play again, 'n' to close");
            answer = scanner.next();
        }

        if (answer.equals("y"))
            run();
    }
}
