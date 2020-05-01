package it.polimi.ingsw.PSP25.Client;

public interface ViewObservable {
    void subscribe(ViewObserver o);

    void askIPAddress();

    void setConnectionMessage(String s);

    void askNumOfPlayers(String question);
}
