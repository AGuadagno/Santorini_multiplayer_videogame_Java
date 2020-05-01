package it.polimi.ingsw.PSP25.Client;

public interface ViewObserver {
    void updateIPAddress(String ip);

    void updateNumOfPlayers(int numOfPlayers);

    void updateName(String name);
}
