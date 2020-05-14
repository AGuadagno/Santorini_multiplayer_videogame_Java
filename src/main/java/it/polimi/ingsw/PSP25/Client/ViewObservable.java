package it.polimi.ingsw.PSP25.Client;

import it.polimi.ingsw.PSP25.Utility.SpaceCopy;

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

    void showBoard(SpaceCopy[][] board);

    void askWorkerPosition(String playerName, int workerNumber, int previousPos, SpaceCopy[][] board);

    void askWorkerMovement(String playerName, List<SpaceCopy> validMovementSpacesW1, List<SpaceCopy> validMovementSpacesW2);

    void askBuildingSpace(String playerName, List<SpaceCopy> validBuildingSpaces);

    void askAtlasBuild(String playerName, List<SpaceCopy> validBuildingSpaces);

    void askBuildBeforeMovePrometheus(String playerName, boolean w1CanMove, boolean w1CanBuild, boolean w2CanMove, boolean w2CanBuild);

    void askWorkerMovementPrometheus(String playerName, List<SpaceCopy> validMovementSpaces);

    void askArtemisSecondMove(String playerName, List<SpaceCopy> validSecondMovementSpaces);

    void askDemeterSecondBuilding(String playerName, List<SpaceCopy> validBuildingSpaces);

    void askHephaestusBuild(String playerName, List<SpaceCopy> validBuildingSpaces);

    void announceVictory(String playerName);

    void announceLose(String playerName);
}
