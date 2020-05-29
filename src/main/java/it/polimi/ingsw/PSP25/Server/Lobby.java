package it.polimi.ingsw.PSP25.Server;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    private List<WaitingGame> gameList;

    public Lobby(){
        gameList = new ArrayList<>();
    }

    public synchronized void setGame (ClientHandler c, int numberOfPlayers) throws DisconnectionException {
        boolean gameFound = false;
        WaitingGame fullGame=null;
        for(WaitingGame waitingGame : gameList){
            if(numberOfPlayers == waitingGame.getNumOfParticipants()){
                // if the game is complete we remove it from the list of waiting game because it has started
                if(waitingGame.addClient(c)){
                    fullGame=waitingGame;
                }
                gameFound = true;
            }
        }
        gameList.remove(fullGame);
        if(gameFound==false){
            gameList.add(new WaitingGame(numberOfPlayers, c));
        }
    }

    public synchronized void removeClient(ClientHandler clientHandler) {
        for(WaitingGame waitingGame : gameList){
            if(waitingGame.contains(clientHandler)){
                waitingGame.removeClient(clientHandler);
                return;
            }
        }
    }

}
