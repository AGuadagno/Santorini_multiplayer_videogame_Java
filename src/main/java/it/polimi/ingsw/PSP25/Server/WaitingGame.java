package it.polimi.ingsw.PSP25.Server;

import it.polimi.ingsw.PSP25.Model.GameLogic;

import java.util.ArrayList;
import java.util.List;

public class WaitingGame {

    private List<ClientHandler> clientList;
    private int numOfParticipants;

    public WaitingGame(int numberOfPlayers, ClientHandler c) {
        this.numOfParticipants=numberOfPlayers;
        clientList = new ArrayList<>();
        clientList.add(c);
    }


    public int getNumOfParticipants(){
        return numOfParticipants;
    }

    public boolean addClient(ClientHandler c) {
        clientList.add(c);
        if(clientList.size()==numOfParticipants){
            GameLogic game = new GameLogic(clientList);
            for(int i=0; i<numOfParticipants; i++){
                clientList.get(i).setGameLogic(game, (i == 0));
            }
            return true;
        }
        else{
            return false;
        }
    }

    public boolean contains(ClientHandler c){
        return clientList.contains(c);
    }

    public void removeClient(ClientHandler c){
        clientList.remove(c);
    }
}
