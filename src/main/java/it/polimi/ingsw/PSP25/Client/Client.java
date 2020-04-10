package it.polimi.ingsw.PSP25.Client;

import it.polimi.ingsw.PSP25.Server.Messages.Message;
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

        /* open a connection to the server */
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

        do {
            synchronized (this) {

                //DEBUG
                System.out.println("Sono dentro al ciclo while! :D");
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //END DEBUG

                networkHandler.receiveCommand();
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (receivedMessage != null) {
                    receivedMessage.process();
                }
            }
        } while (receivedMessage != null);
        //DEBUG
        System.out.println("Sono fuori dal ciclo while :(");
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    @Override
    public synchronized void didReceiveServerMessage(Message message) {
        this.receivedMessage = message;
        //DEBUG
        System.out.println("Ho ricevuto il messaggio in client! :D");
        notifyAll();
    }
}
