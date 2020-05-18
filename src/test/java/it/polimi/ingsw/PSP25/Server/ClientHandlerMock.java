package it.polimi.ingsw.PSP25.Server;

import it.polimi.ingsw.PSP25.Utility.SpaceCopy;

import java.net.InetAddress;
import java.util.List;

import static org.junit.Assert.*;

public class ClientHandlerMock implements VirtualView {

    private int[] workerAndSpace;
    private int artemisChosenMovementSpace;
    private int selectedSpace;
    private int selectedSpace2;
    private int[] atlasSpaceAndDome;
    private int demeterSecondBuilding;
    private int[] hephaestusBuild;
    private int[] BuildBeforeMovePrometheus;
    private int prometheusMovement;
    private int askToBuildNumber = 0;

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

    public void setAskToBuild2(int selectedSpace2) {
        this.selectedSpace2 = selectedSpace2;
    }

    @Override
    public int askBuildingSpace(String playerName, List<SpaceCopy> deepCopySpaceList) throws DisconnectionException {
        if (askToBuildNumber == 0) {
            askToBuildNumber++;
            return this.selectedSpace;
        } else {
            return this.selectedSpace2;
        }
    }

    @Override
    public int[] askBuildBeforeMovePrometheus(String playerName, boolean b, boolean b1, boolean b2, boolean b3) throws DisconnectionException {
        return BuildBeforeMovePrometheus;
    }

    public void setBuildBeforeMovePrometheus(int[] BuildBeforeMovePrometheus) {
        this.BuildBeforeMovePrometheus = BuildBeforeMovePrometheus;
    }

    @Override
    public int askWorkerMovementPrometheus(String playerName, List<SpaceCopy> deepCopySpaceList) throws DisconnectionException {
        return prometheusMovement;
    }

    public void setPrometheusMovement(int prometheusMovement) {
        this.prometheusMovement = prometheusMovement;
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