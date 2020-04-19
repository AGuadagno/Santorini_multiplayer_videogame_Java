package it.polimi.ingsw.PSP25.Server;

import it.polimi.ingsw.PSP25.Model.GameLogic;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

// Contains the list of connections of users who want to join the game

public class Lobby {

    private List<ClientHandler> clientList;
    private GameLogic gameLogic = null;

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

    public synchronized void startGame(int numOfPlayers) throws IOException {
        while (clientList.size() < numOfPlayers) {

            //DEBUG
            System.out.println("Non ci sono abbastanza giocatori");

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        gameLogic = new GameLogic(clientList.subList(0, numOfPlayers));
        gameLogic.startGame();
    }

    public void stopGame(ClientHandler timeOutClient, InetAddress disconnectedAddress){
        for(ClientHandler clientHandler: clientList){
            if(clientHandler != timeOutClient){
                clientHandler.sendStop(disconnectedAddress);
            }
            clientHandler.stopGame();
        }
    }

}


