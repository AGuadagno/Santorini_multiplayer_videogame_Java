package it.polimi.ingsw.PSP25.Client;

import java.util.List;

public interface ViewObservable {
    void subscribe(ViewObserver o);

    void askIPAddress();

    void setConnectionMessage(String s);

    void askNumOfPlayers(String question);

    void askName(String question);

    void askAllGodPowers(String playerName, int numOfPlayers, List<String> godPowerNames);

    void askGodPower(String playerName, List<String> godPowerNames);

    void showPlayersGodPowers(List<String> playerNames, List<String> godPowerNames);
}
