package it.polimi.ingsw.PSP25.Server;

import it.polimi.ingsw.PSP25.Model.GameLogic;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

// Contains the list of connections of users who want to join the game

public class Lobby {

    private List<ClientHandler> clientList;
    private GameLogic gameLogic = null;
    private int numOfParticipants;

    public Lobby(){
        clientList = new ArrayList<>();
    }

    public synchronized void addClient(ClientHandler c){
        clientList.add(c);
        notifyAll();
    }

    // Used to identify the client who created the game in order to ask him the number of players
    public boolean isFirstClient(ClientHandler c) {
        if (c == clientList.get(0)) {
            return true;
        } else {
            return false;
        }
    }

    public void startGame(int numOfPlayers) throws DisconnectionException {
        this.numOfParticipants = numOfPlayers;
        while (clientList.size() < numOfPlayers) {

            //DEBUG
            System.out.println("Non ci sono abbastanza giocatori");

            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        List<ClientHandler> l = new ArrayList<>();
        for (int i = 0; i < numOfPlayers; i++)
            l.add(clientList.get(i));

        gameLogic = new GameLogic(l);
        gameLogic.startGame();
    }

    public void stopGame(ClientHandler timeOutClient, InetAddress disconnectedAddress) throws DisconnectionException {
        int disconnectedClientIndex = timeOutClient.getClientNumber();

        System.out.println("Client " + disconnectedClientIndex + " with address " + disconnectedAddress + " disconnected.");
        for (int i = 0; i < numOfParticipants; i++) {
            if (clientList.get(i) != timeOutClient) {
                clientList.get(i).sendStop(disconnectedAddress);
            }
            clientList.get(i).stopGame();
        }
    }


}


