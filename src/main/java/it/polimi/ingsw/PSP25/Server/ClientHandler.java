package it.polimi.ingsw.PSP25.Server;

import it.polimi.ingsw.PSP25.GodPower;
import it.polimi.ingsw.PSP25.Server.Messages.AskAllGodPowers;
import it.polimi.ingsw.PSP25.Server.Messages.AskName;
import it.polimi.ingsw.PSP25.Server.Messages.AskNumberOfPlayers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {

    private Socket client;
    private Lobby lobby;
    private int numOfParticipants;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public ClientHandler(Socket client, Lobby lobby) {
        this.client = client;
        this.lobby = lobby;
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

        //DEBUG
        System.out.println("ClientHandler: lobby.isFirstClient = " + lobby.isFirstClient(this));

        if (lobby.isFirstClient(this)) {
            outputStream.writeObject(new AskNumberOfPlayers());

            //DEBUG
            System.out.println("ClientHandler: writeObject eseguito");

            numOfParticipants = (int) inputStream.readObject();

            //DEBUG
            System.out.println("ClientHandler: numOfParticipants ricevuto = " + numOfParticipants);

            lobby.startGame(numOfParticipants);
        }
    }

    public String askName(int playerNumber) {
        //DEBUG
        System.out.println("ClientHandler askName(" + playerNumber + ") invia messaggio");

        try {
            outputStream.writeObject(new AskName(playerNumber));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //DEBUG
        System.out.println("ClientHandler askName(" + playerNumber + ") messaggio inviato");

        String name = null;
        try {
            name = (String) inputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //DEBUG
        System.out.println("ClientHandler askName(" + playerNumber + ") messaggio ricevuto: " + name);

        return name;
    }

    public List<Integer> askAllGodPowers(String playerName, int numOfPlayers, List<String> godPowerNames) {
        try {
            outputStream.writeObject(new AskAllGodPowers(playerName, numOfPlayers, godPowerNames));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Integer> selectedIndexes = new ArrayList<>();
        try {
            selectedIndexes = (List<Integer>) inputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return selectedIndexes;
    }
}
