package it.polimi.ingsw.PSP25.Server;

import it.polimi.ingsw.PSP25.Utility.SpaceCopy;

import java.util.List;

public interface ModelObserver {
    void askWorkerMovement(String playerName, List<SpaceCopy> validMovementSpacesW1,
                           List<SpaceCopy> validMovementSpacesW2) throws DisconnectionException;

    void askAllGodPowers(String playerName, int numOfPlayers, List<String> deepCopyGodPowerNames) throws DisconnectionException;

    int askGodPower(String playerName, List<String> deepCopyGodPowerNames) throws DisconnectionException;

    void tellAssignedGodPower(String playerName, List<String> deepCopyGodPowerNames) throws DisconnectionException;

    int askWorkerPosition(String playerName, int i, int i1, SpaceCopy[][] deepCopyBoard) throws DisconnectionException;

    void manageVictory(String playerName) throws DisconnectionException;

    void stopGame();

    void manageLose(String playerName) throws DisconnectionException;

    void sendBoard(SpaceCopy[][] deepCopyBoard) throws DisconnectionException;

    void sendPlayersGodPowers(List<String> playerNames, List<String> godPowerNames) throws DisconnectionException;

    int askBuildingSpace(String playerName, List<SpaceCopy> deepCopySpaceList) throws DisconnectionException;

    int[] askAtlasBuild(String playerName, List<SpaceCopy> deepCopySpaceList) throws DisconnectionException;

    int askDemeterSecondBuilding(String playerName, List<SpaceCopy> deepCopySpaceList) throws DisconnectionException;

    int[] askBuildBeforeMovePrometheus(String playerName, boolean b, boolean b1, boolean b2, boolean b3) throws DisconnectionException;

    int askWorkerMovementPrometheus(String playerName, List<SpaceCopy> deepCopySpaceList) throws DisconnectionException;

    int askArtemisSecondMove(String playerName, List<SpaceCopy> deepCopySpaceList) throws DisconnectionException;

    int[] askHephaestusBuild(String playerName, List<SpaceCopy> deepCopySpaceList) throws DisconnectionException;
}
