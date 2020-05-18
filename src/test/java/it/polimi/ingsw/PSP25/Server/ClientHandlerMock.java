package it.polimi.ingsw.PSP25.Server;

import it.polimi.ingsw.PSP25.Utility.SpaceCopy;

import java.net.InetAddress;
import java.util.List;

import static org.junit.Assert.*;

public class ClientHandlerMock implements VirtualView {

    private int[] workerAndSpace;
    private int artemisChosenMovementSpace;
    private int selectedSpace;
    private int[] atlasSpaceAndDome;
    private int demeterSecondBuilding;
    private int[] hephaestusBuild;

    @Override
    public String askName(int i) throws DisconnectionException {
        return null;
    }

    @Override
    public List<Integer> askAllGodPowers(String playerName, int numOfPlayers, List<String> deepCopyGodPowerNames) throws DisconnectionException {
        return null;
    }

    @Override
    public int askGodPower(String playerName, List<String> deepCopyGodPowerNames) throws DisconnectionException {
        return 0;
    }

    @Override
    public void tellAssignedGodPower(String playerName, List<String> deepCopyGodPowerNames) throws DisconnectionException {

    }

    @Override
    public int askWorkerPosition(String playerName, int i, int i1, SpaceCopy[][] deepCopyBoard) throws DisconnectionException {
        return 0;
    }

    @Override
    public void manageVictory(String playerName) throws DisconnectionException {

    }

    @Override
    public void stopGame() {

    }

    @Override
    public void manageLose(String playerName) throws DisconnectionException {

    }

    @Override
    public void sendBoard(SpaceCopy[][] deepCopyBoard) throws DisconnectionException {

    }

    @Override
    public void sendPlayersGodPowers(List<String> playerNames, List<String> godPowerNames) throws DisconnectionException {

    }

    @Override
    public int getClientNumber() {
        return 0;
    }

    @Override
    public void sendStop(InetAddress disconnectedAddress) throws DisconnectionException {

    }


    public void setAskWorkerMovement(int[] workerAndSpace) {
        this.workerAndSpace = workerAndSpace;
    }

    @Override
    public int[] askWorkerMovement(String playerName, List<SpaceCopy> deepCopySpaceList, List<SpaceCopy> deepCopySpaceList1) throws DisconnectionException {
        return this.workerAndSpace;
    }

    public void setAskToBuild(int selectedSpace) {
        this.selectedSpace = selectedSpace;
    }

    @Override
    public int askBuildingSpace(String playerName, List<SpaceCopy> deepCopySpaceList) throws DisconnectionException {
        return this.selectedSpace;
    }

    @Override
    public int[] askBuildBeforeMovePrometheus(String playerName, boolean b, boolean b1, boolean b2, boolean b3) throws DisconnectionException {
        return new int[0];
    }

    @Override
    public int askWorkerMovementPrometheus(String playerName, List<SpaceCopy> deepCopySpaceList) throws DisconnectionException {
        return 0;
    }

    public void setHephaestusBuild(int[] hephaestusBuild) {
        this.hephaestusBuild = hephaestusBuild;
    }

    @Override
    public int[] askHephaestusBuild(String playerName, List<SpaceCopy> deepCopySpaceList) throws DisconnectionException {
        return this.hephaestusBuild;
    }

    public void setArtemisSecondMove(int chosenMovementSpace) {
        this.artemisChosenMovementSpace = chosenMovementSpace;
    }

    @Override
    public int askArtemisSecondMove(String playerName, List<SpaceCopy> deepCopySpaceList) throws DisconnectionException {
        return this.artemisChosenMovementSpace;
    }

    public void setAtlasBuild(int[] atlasSpaceAndDome) {
        this.atlasSpaceAndDome = atlasSpaceAndDome;
    }

    @Override
    public int[] askAtlasBuild(String playerName, List<SpaceCopy> deepCopySpaceList) throws DisconnectionException {
        return atlasSpaceAndDome;
    }

    public void setDemeterSecondBuilding(int demeterSecondBuilding) {
        this.demeterSecondBuilding = demeterSecondBuilding;
    }

    @Override
    public int askDemeterSecondBuilding(String playerName, List<SpaceCopy> deepCopySpaceList) throws DisconnectionException {
        return demeterSecondBuilding;
    }
}