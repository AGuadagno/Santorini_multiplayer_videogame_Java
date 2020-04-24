package it.polimi.ingsw.PSP25.Client;

import it.polimi.ingsw.PSP25.Utility.Messages.Message;
import it.polimi.ingsw.PSP25.Server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable, ServerObserver {

    private Message receivedMessage = null;

    @Override
    public void run() {

        Scanner scanner = new Scanner(System.in);

        // CONNECTION TO SERVER
        System.out.println("IP address of server?");
        String ip = scanner.nextLine();

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

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    @Override
    public synchronized void didReceiveServerMessage(Message message) {
        this.receivedMessage = message;
        notifyAll();
    }

}
