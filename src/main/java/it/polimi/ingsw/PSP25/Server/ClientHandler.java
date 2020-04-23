package it.polimi.ingsw.PSP25.Server;

import it.polimi.ingsw.PSP25.Model.GameLogic;
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

    private final Socket client;
    private final int clientNumber;
    private Lobby lobby;
    private int numOfParticipants;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean endGame = false;
    // NEW
    private GameLogic game;

    public ClientHandler(Socket client, int clientNumber, Lobby lobby) {
        this.client = client;
        this.clientNumber = clientNumber;
        this.lobby = lobby;
        // NEW
        game = null;
    }

    @Override
    public void run() {
        try {
            handleClientConnection();
        } catch (DisconnectionException e) {
            try {
                System.out.println("Client " + e.getClientHandler().getClientNumber() + " DisconnectionException: stopping game");
                // NEW
                if (game != null) {
                    game.stopGame(e.getClientHandler(), e.getClientHandler().getClientAddress());
                }
                lobby.removeClient(this);
            } catch (DisconnectionException stopException) {
                //System.out.println("Can't send stopMessage to client: client already disconnected");
                System.out.println("Lobby.stopGame() DisconnectionException");
                //stopException.printStackTrace();
            } finally {
                endGame = true;
            }
        }
    }

    private void handleClientConnection() throws DisconnectionException {
        try {
            outputStream = new ObjectOutputStream(client.getOutputStream());
            inputStream = new ObjectInputStream(client.getInputStream());
            client.setSoTimeout(20000);
        } catch (IOException e) {
            throw new DisconnectionException(this);
        }

        startPingSender();

        System.out.println("Connected to " + client.getInetAddress());

        //NEW
        while (game == null && !lobby.isFirstClient(this)) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
            }
        }

        if (lobby.isFirstClient(this)) {
            sendMessage(new AskNumberOfPlayers());
            numOfParticipants = (int) receiveMessage();
            lobby.startGame(numOfParticipants);
        }

    }

    public int getClientNumber() {
        return clientNumber;
    }

    public InetAddress getClientAddress() {
        return client.getInetAddress();
    }

    public void setGameLogic(GameLogic g) {
        this.game = g;
        synchronized (this) {
            notifyAll();
        }
    }

    public String askName(int playerNumber) throws DisconnectionException {
        /*try {
            outputStream.writeObject(new AskName(playerNumber));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        sendMessage(new AskName(playerNumber));

        String name = (String) receiveMessage();
        return name;
    }

    public List<Integer> askAllGodPowers(String playerName, int numOfPlayers, List<String> godPowerNames) throws DisconnectionException {
        /*try {
            outputStream.writeObject(new AskAllGodPowers(playerName, numOfPlayers, godPowerNames));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        sendMessage(new AskAllGodPowers(playerName, numOfPlayers, godPowerNames));

        List<Integer> selectedIndexes = (List<Integer>) receiveMessage();
        return selectedIndexes;
    }

    public int askGodPower(String playerName, List<String> godPowerNames) throws DisconnectionException {
        /*try {
            outputStream.writeObject(new AskGodPower(playerName, godPowerNames));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        sendMessage(new AskGodPower(playerName, godPowerNames));

        int index = (int) receiveMessage();

        return index;
    }

    public void tellAssignedGodPower(String playerName, List<String> godPowerName) throws DisconnectionException {
        /*try {
            outputStream.writeObject(new TellAssignedGodPower(playerName, godPowerName));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        sendMessage(new TellAssignedGodPower(playerName, godPowerName));
    }

    public void sendBoard(SpaceCopy[][] boardCopy) throws DisconnectionException {
        /*try {
            outputStream.writeObject(new SendBoard(boardCopy));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        sendMessage(new SendBoard(boardCopy));
    }

    public int askWorkerPosition(String playerName, int workerNumber, int previousPos,
                                 SpaceCopy[][] boardCopy) throws DisconnectionException {
        /*try {
            outputStream.writeObject(new AskWorkerPosition(playerName, workerNumber, previousPos, boardCopy));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        sendMessage(new AskWorkerPosition(playerName, workerNumber, previousPos, boardCopy));

        int pos = (int) receiveMessage();
        return pos;
    }

    public int[] askWorkerMovement(String playerName, List<SpaceCopy> validMovementSpacesW1,
                                   List<SpaceCopy> validMovementSpacesW2) throws DisconnectionException {
        /*try {
            outputStream.writeObject(new AskWorkerMovement(playerName, validMovementSpacesW1, validMovementSpacesW2));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        sendMessage(new AskWorkerMovement(playerName, validMovementSpacesW1, validMovementSpacesW2));

        int[] workerAndSpace = (int[]) receiveMessage();
        return workerAndSpace;
    }

    public int askBuildingSpace(String playerName, List<SpaceCopy> validBuildingSpaces) throws DisconnectionException {
        /*try {
            outputStream.writeObject(new AskBuildingSpace(playerName, validBuildingSpaces));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        sendMessage(new AskBuildingSpace(playerName, validBuildingSpaces));

        int selectedBuildingSpace = (int) receiveMessage();
        return selectedBuildingSpace;
    }

    public int askArtemisSecondMove(String playerName, List<SpaceCopy> deepCopySpaceList) throws DisconnectionException {
        /*try {
            outputStream.writeObject(new AskArtemisSecondMove(playerName, deepCopySpaceList));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        sendMessage(new AskArtemisSecondMove(playerName, deepCopySpaceList));

        int selectedMovementSpace = (int) receiveMessage();
        return selectedMovementSpace;
    }

    public int[] askAtlasBuild(String playerName, List<SpaceCopy> deepCopySpaceList) throws DisconnectionException {
        /*try {
            outputStream.writeObject(new AskAtlasBuild(playerName, deepCopySpaceList));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        sendMessage(new AskAtlasBuild(playerName, deepCopySpaceList));

        int[] selectedSpaceAndBuildDome = (int[]) receiveMessage();
        return selectedSpaceAndBuildDome;
    }

    public int askDemeterSecondBuilding(String playerName, List<SpaceCopy> deepCopySpaceList) throws DisconnectionException {
        /*try {
            outputStream.writeObject(new AskDemeterSecondBuilding(playerName, deepCopySpaceList));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        sendMessage(new AskDemeterSecondBuilding(playerName, deepCopySpaceList));

        int selectedBuildingSpace = (int) receiveMessage();
        return selectedBuildingSpace;
    }

    public int[] askHephaestusBuild(String playerName, List<SpaceCopy> deepCopySpaceList) throws DisconnectionException {
        /*try {
            outputStream.writeObject(new AskHephaestusBuild(playerName, deepCopySpaceList));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        sendMessage(new AskHephaestusBuild(playerName, deepCopySpaceList));

        int[] spaceAndDoubleBuilding = (int[]) receiveMessage();
        return spaceAndDoubleBuilding;
    }

    public int[] askBuildBeforeMovePrometheus(String playerName, boolean w1CanMove, boolean w2CanMove,
                                              boolean w1CanBuild, boolean w2CanBuild) throws DisconnectionException {
       /* try {
            outputStream.writeObject(new AskBuildBeforeMovePrometheus(playerName, w1CanMove, w2CanMove, w1CanBuild, w2CanBuild));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        sendMessage(new AskBuildBeforeMovePrometheus(playerName, w1CanMove, w2CanMove, w1CanBuild, w2CanBuild));

        int[] workerAndBuildBeforeMove = (int[]) receiveMessage();
        return workerAndBuildBeforeMove;
    }

    public int askWorkerMovementPrometheus(String playerName, List<SpaceCopy> validMovementSpaces) throws DisconnectionException {
        /*try {
            outputStream.writeObject(new AskWorkerMovementPrometheus(playerName, validMovementSpaces));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        sendMessage(new AskWorkerMovementPrometheus(playerName, validMovementSpaces));

        int selectedSpace = (int) receiveMessage();
        return selectedSpace;
    }

    private Object receiveMessage() throws DisconnectionException {
        Object message = null;

        try {
            do {
                message = inputStream.readObject();
            } while (message instanceof PingMessage);
        } catch (SocketTimeoutException e) {
            throw new DisconnectionException(this);
        } catch (IOException e) {
            throw new DisconnectionException(this);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return message;
    }

    private void sendMessage(Message message) throws DisconnectionException {
        synchronized (outputStream) {
            try {
                outputStream.writeObject(message);
            } catch (IOException e) {
                //DEBUG
                e.printStackTrace();
                System.out.println("IOException dall'outputStream di clientHandler " + this.clientNumber + " lancio una DisconnectionException");
                throw new DisconnectionException(this);
            }
        }
    }

    public void sendStop(InetAddress disconnectedAddress) throws DisconnectionException {
        /*try {
            outputStream.writeObject(new SendStop(disconnectedAddress));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        System.out.println("Sending stop message to client " + this.clientNumber + " with address " + client.getInetAddress());
        sendMessage(new SendStop(disconnectedAddress));
    }

    public void startPingSender() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!endGame) {
                    /*try {
                        synchronized (outputStream) {
                            outputStream.writeObject(new PingMessage());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                    try {
                        sendMessage(new PingMessage());
                    } catch (DisconnectionException e) {
                        System.out.println("DisconnectionException: fermo il pingSender del server");
                        lobby.removeClient(ClientHandler.this);
                        return;
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
        lobby.removeClient(this);
        try {
            client.close();
        } catch (IOException e) {
            System.out.println("stopGame(): Socket client.close() IOException, socket già chiuso");
            //e.printStackTrace();
        }
    }

    public void manageVictory(String playerName) throws DisconnectionException {
        sendMessage(new AnnounceVictory(playerName));
    }

    public void manageLose(String playerName) throws DisconnectionException {
        sendMessage(new AnnounceLose(playerName));
    }

    public void sendOpponentsGodPowers(List<String> playerNames, List<String> godPowerNames) throws DisconnectionException {
        sendMessage(new SendOpponentsGodPower(playerNames, godPowerNames));
    }
}