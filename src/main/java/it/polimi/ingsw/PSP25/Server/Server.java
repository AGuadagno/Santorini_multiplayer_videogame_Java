package it.polimi.ingsw.PSP25.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server
{
    public final static int SOCKET_PORT = 7777;


    public static void main(String[] args)
    {

        Lobby lobby = new Lobby();

        ServerSocket socket;
        try {
            socket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }

        //DEBUG
        System.out.println("Server OK!");

        int clientCounter = 0;
        while (true) {
            try {
                /* accepts connections; for every connection we accept, create a new Thread executing a ClientHandler */
                Socket client = socket.accept();
                ClientHandler clientHandler = new ClientHandler(client, clientCounter, lobby);
                Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
                clientCounter++;
                // The new Client Handler is added to the lobby waiting for joining the game
                lobby.addClient(clientHandler);
                thread.start();

            } catch (IOException e) {
                System.out.println("connection dropped");
            }
        }
    }
}
