package it.polimi.ingsw.PSP25.Server;

import it.polimi.ingsw.PSP25.Utility.Messages.*;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;

public class ClientHandler implements Runnable {

    private Socket client;
    private Lobby lobby;
    private int numOfParticipants;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean endGame = false;

    public ClientHandler(Socket client, Lobby lobby) {
        this.client = client;
        this.lobby = lobby;
    }

    @Override
    public void run() {
        try {
            handleClientConnection();
        }catch(IOException e){
            lobby.stopGame(this, client.getInetAddress());
            endGame = true;
            return;
        }
    }

    private void handleClientConnection() throws IOException {
        outputStream = new ObjectOutputStream(client.getOutputStream());
        inputStream = new ObjectInputStream(client.getInputStream());
        client.setSoTimeout(20000);

        startPingSender();

        System.out.println("Connected to " + client.getInetAddress());

        if (lobby.isFirstClient(this)) {
            synchronized (outputStream) {
                outputStream.writeObject(new AskNumberOfPlayers());
            }
            numOfParticipants = (int) receiveMessage();
            lobby.startGame(numOfParticipants);
        }
    }

    public String askName(int playerNumber) throws IOException {
        try {
            outputStream.writeObject(new AskName(playerNumber));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String name = (String) receiveMessage();
        return name;
    }

    public List<Integer> askAllGodPowers(String playerName, int numOfPlayers, List<String> godPowerNames) throws IOException {
        try {
            outputStream.writeObject(new AskAllGodPowers(playerName, numOfPlayers, godPowerNames));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Integer> selectedIndexes = (List<Integer>) receiveMessage();
        return selectedIndexes;
    }

    public int askGodPower(String playerName, List<String> godPowerNames) throws IOException {
        try {
            outputStream.writeObject(new AskGodPower(playerName, godPowerNames));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int index = (int) receiveMessage();

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

    public int askWorkerPosition(String playerName, int workerNumber, int previousPos,
                                 SpaceCopy[][] boardCopy) throws IOException {
        try {
            outputStream.writeObject(new AskWorkerPosition(playerName, workerNumber, previousPos, boardCopy));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int pos = (int) receiveMessage();
        return pos;
    }

    public int[] askWorkerMovement(String playerName, List<SpaceCopy> validMovementSpacesW1,
                                   List<SpaceCopy> validMovementSpacesW2) throws IOException {
        try {
            outputStream.writeObject(new AskWorkerMovement(playerName, validMovementSpacesW1, validMovementSpacesW2));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] workerAndSpace = (int[]) receiveMessage();
        return workerAndSpace;
    }

    public int askBuildingSpace(String playerName, List<SpaceCopy> validBuildingSpaces) throws IOException {
        try {
            outputStream.writeObject(new AskBuildingSpace(playerName, validBuildingSpaces));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int selectedBuildingSpace = (int) receiveMessage();
        return selectedBuildingSpace;
    }

    public int askArtemisSecondMove(String playerName, List<SpaceCopy> deepCopySpaceList) throws IOException {
        try {
            outputStream.writeObject(new AskArtemisSecondMove(playerName, deepCopySpaceList));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int selectedMovementSpace =  (int) receiveMessage();
        return selectedMovementSpace;
    }

    public int[] askAtlasBuild(String playerName, List<SpaceCopy> deepCopySpaceList) throws IOException {
        try {
            outputStream.writeObject(new AskAtlasBuild(playerName, deepCopySpaceList));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] selectedSpaceAndBuildDome = (int[]) receiveMessage();
        return selectedSpaceAndBuildDome;
    }

    public int askDemeterSecondBuilding(String playerName, List<SpaceCopy> deepCopySpaceList) throws IOException {
        try {
            outputStream.writeObject(new AskDemeterSecondBuilding(playerName, deepCopySpaceList));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int selectedBuildingSpace = (int) receiveMessage();
        return selectedBuildingSpace;
    }

    public int[] askHephaestusBuild(String playerName, List<SpaceCopy> deepCopySpaceList) throws IOException {
        try {
            outputStream.writeObject(new AskHephaestusBuild(playerName, deepCopySpaceList));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] spaceAndDoubleBuilding = (int[]) receiveMessage();
        return spaceAndDoubleBuilding;
    }

    public int[] askBuildBeforeMovePrometheus(String playerName, boolean w1CanMove, boolean w2CanMove,
                                              boolean w1CanBuild, boolean w2CanBuild) throws IOException {
        try {
            outputStream.writeObject(new AskBuildBeforeMovePrometheus(playerName, w1CanMove, w2CanMove, w1CanBuild, w2CanBuild));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] workerAndBuildBeforeMove = (int[]) receiveMessage();
        return workerAndBuildBeforeMove;
    }

    public int askWorkerMovementPrometheus(String playerName, List<SpaceCopy> validMovementSpaces) throws IOException {
        try {
            outputStream.writeObject(new AskWorkerMovementPrometheus(playerName, validMovementSpaces));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int selectedSpace = (int) receiveMessage();
        return selectedSpace;
    }

    private Object receiveMessage() throws IOException {
        Object message = null;

        try {
            do {
                message = inputStream.readObject();
            } while (message instanceof PingMessage);
        }catch (SocketTimeoutException e){
            throw new SocketTimeoutException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return message;
    }

    private void sendMessage(Message message) {
        try {
            synchronized (outputStream) {
                outputStream.writeObject(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendStop(InetAddress disconnectedAddress) {
        try {
            outputStream.writeObject(new SendStop(disconnectedAddress));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startPingSender() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!endGame){
                    try {
                        synchronized (outputStream) {
                            outputStream.writeObject(new PingMessage());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void stopGame() {
        endGame = true;
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void manageVictory(String playerName) {

        sendMessage(new AnnounceVictory(playerName));

    }

    public void manageLose(String playerName) {

        sendMessage(new AnnounceLose(playerName));

    }


}

