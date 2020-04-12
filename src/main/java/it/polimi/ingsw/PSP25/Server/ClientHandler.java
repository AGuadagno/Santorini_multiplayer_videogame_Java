package it.polimi.ingsw.PSP25.Server;

import it.polimi.ingsw.PSP25.Utility.Messages.*;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;

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

    public int askGodPower(String playerName, List<String> godPowerNames) {
        try {
            outputStream.writeObject(new AskGodPower(playerName, godPowerNames));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int index = -1;
        try {
            index = (int) inputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return index;
    }

    public void tellAssignedGodPower(String playerName, List<String> godPowerName) {
        try {
            outputStream.writeObject(new TellAssignedGodPower(playerName, godPowerName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendBoard(SpaceCopy[][] boardCopy) {
        try {
            outputStream.writeObject(new SendBoard(boardCopy));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int askWorkerPosition(String playerName, int workerNumber, int previousPos, SpaceCopy[][] boardCopy) {
        try {
            outputStream.writeObject(new AskWorkerPosition(playerName, workerNumber, previousPos, boardCopy));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int pos = -1;
        try {
            pos = (int) inputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return pos;
    }

}
