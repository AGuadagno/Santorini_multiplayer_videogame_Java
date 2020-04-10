package it.polimi.ingsw.PSP25.Server;

import java.util.ArrayList;
import java.util.List;


// Contains the list of connections of users who want to join the game

public class Lobby {

    private List<ClientHandler> clientList;

    public Lobby(){
        clientList = new ArrayList<>();
    }

    public synchronized void addClient(ClientHandler c){
        clientList.add(c);
        notifyAll();
    }

    // Used to identify the client who created the game in order to ask him the number of players
    public boolean isFirstClient(ClientHandler c){
        if(c==clientList.get(0)){
            return true;
        }
        else{
            return false;
        }
    }

}


