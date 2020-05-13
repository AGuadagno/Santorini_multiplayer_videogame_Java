package it.polimi.ingsw.PSP25.Client;

import java.util.List;

public interface ViewObserver {

    void updateIPAddress(String ip);

    void updateNumOfPlayers(int numOfPlayers);

    void updateName(String name);

    void updateAllGodPower(List<Integer> selectedIndexes);

    void updateGodPower(int selectedIndex);

    void updateWorkerPosition(int pos);

    void updateWorkerMovement(int[] workerAndSpace);

    void updateBuildingSpace(int chosenBuildingSpace);

    void updateAtlasBuild(int[] selectedSpaceAndBuildDome);

    void updateBuildBeforeMovePrometheus(int[] workerAndBuildBeforeMove);

    void updateWorkerMovementPrometheus(int chosenMovementSpace);

    void updateArtemisSecondMove(int artemisSecondMoveSpace);

    void updateHephaestusBuild(int[] spaceAndDoubleBuilding);

}
