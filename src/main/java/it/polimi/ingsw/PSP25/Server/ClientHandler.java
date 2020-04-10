package it.polimi.ingsw.PSP25.Server;

import it.polimi.ingsw.PSP25.Server.Messages.AskName;
import it.polimi.ingsw.PSP25.Server.Messages.AskNumberOfPlayers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket client;
    private Lobby lobby;
    private int numOfParticipants;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    ClientHandler(Socket client, Lobby lobby)
    {
        this.client = client;
        this.lobby=lobby;
    }

    @Override
    public void run() {
        try {
            handleClientConnection();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("client " + client.getInetAddress() + " connection dropped");
        }
    }

    private void handleClientConnection() throws IOException, ClassNotFoundException {
        outputStream = new ObjectOutputStream(client.getOutputStream());
        inputStream = new ObjectInputStream(client.getInputStream());

        System.out.println("Connected to " + client.getInetAddress());

        if(lobby.isFirstClient(this)){
            outputStream.writeObject(new AskNumberOfPlayers());
            numOfParticipants = Integer.parseInt((String)(inputStream.readObject()));
        }

    }

}
