package it.polimi.ingsw.PSP25.Server;

import it.polimi.ingsw.PSP25.Utility.SpaceCopy;

import java.net.InetAddress;
import java.util.List;

import static org.junit.Assert.*;

public class ClientHandlerMock implements VirtualView {

    //private int[] workerAndSpace;
    private int[][] workerAndSpace;
    private int workerAndSpaceCurrIndex;
    private int artemisChosenMovementSpace;
    private int selectedSpace[];
    private int selectedSpaceCurrIndex;
    private int[] atlasSpaceAndDome;
    private int[] demeterSecondBuilding;
    private int demeterSecondBuildingCurrIndex;
    private String name;
    private List<Integer> allSelectedGodPowers;
    private int selectedGodPowerIndex;
    private int[] setupWorkerPosition;
    private int setupWorkerPositionCurrIndex;
    private int[] hephaestusBuild;
    private int[] BuildBeforeMovePrometheus;
    private int prometheusMovement;
    private int askToBuildNumber = 0;

    public void setAskName(String name) {
        this.name = name;
    }

    @Override
    public String askName(int i) throws DisconnectionException {
        return name;
    }

    public void setAskAllGodPowers(List<Integer> allSelectedGodPowers) {
        this.allSelectedGodPowers = allSelectedGodPowers;
    }

    @Override
    public List<Integer> askAllGodPowers(String playerName, int numOfPlayers, List<String> deepCopyGodPowerNames) throws DisconnectionException {
        return allSelectedGodPowers;
    }

    public void setAskGodPower(int selectedGodPowerIndex) {
        this.selectedGodPowerIndex = selectedGodPowerIndex;
    }

    @Override
    public int askGodPower(String playerName, List<String> deepCopyGodPowerNames) throws DisconnectionException {
        return selectedGodPowerIndex;
    }

    @Override
    public void tellAssignedGodPower(String playerName, List<String> deepCopyGodPowerNames) throws DisconnectionException {

    }

    public void setAskWorkerPosition(int setupWorkerPosition) {
        this.setupWorkerPosition = new int[1];
        this.setupWorkerPosition[0] = setupWorkerPosition;
    }

    public void setAskWorkerPosition(int setupWorkerPosition[]) {
        this.setupWorkerPosition = setupWorkerPosition;
        this.setupWorkerPositionCurrIndex = 0;
    }

    @Override
    public int askWorkerPosition(String playerName, int i, int i1, SpaceCopy[][] deepCopyBoard) throws DisconnectionException {
        if (setupWorkerPosition.length == 1)
            return setupWorkerPosition[0];
        if (setupWorkerPositionCurrIndex < setupWorkerPosition.length) {
            setupWorkerPositionCurrIndex++;
            return setupWorkerPosition[setupWorkerPositionCurrIndex - 1];
        }

        System.out.println("Index out of bounds askWorkerPosition");
        return -1000;
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
        this.workerAndSpace = new int[1][2];
        this.workerAndSpace[0] = workerAndSpace;
    }

    public void setAskWorkerMovement(int[][] workerAndSpace) {
        this.workerAndSpace = workerAndSpace;
        this.workerAndSpaceCurrIndex = 0;
    }

    @Override
    public int[] askWorkerMovement(String playerName, List<SpaceCopy> deepCopySpaceList, List<SpaceCopy> deepCopySpaceList1) throws DisconnectionException {
        if (workerAndSpace.length == 1)
            return workerAndSpace[0];
        if (workerAndSpaceCurrIndex < workerAndSpace.length) {
            workerAndSpaceCurrIndex++;
            return workerAndSpace[workerAndSpaceCurrIndex - 1];
        }

        System.out.println("Index out of bounds askWorkerMovement");
        return new int[]{-1000, -1000};
    }

    public void setAskToBuild(int selectedSpace) {
        //this.selectedSpace = selectedSpace;
        this.selectedSpace = new int[1];
        this.selectedSpace[0] = selectedSpace;
    }

    public void setAskToBuild(int[] selectedSpace) {
        this.selectedSpace = selectedSpace;
        this.selectedSpaceCurrIndex = 0;
    }

    @Override
    public int askBuildingSpace(String playerName, List<SpaceCopy> deepCopySpaceList) throws DisconnectionException {
        //return this.selectedSpace;
        if (selectedSpace.length == 1)
            return selectedSpace[0];
        if (selectedSpaceCurrIndex < selectedSpace.length) {
            selectedSpaceCurrIndex++;
            return selectedSpace[selectedSpaceCurrIndex - 1];
        }

        System.out.println("Index out of bounds askBuildingSpace");
        return -1000;
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
        //this.demeterSecondBuilding = demeterSecondBuilding;
        this.demeterSecondBuilding = new int[1];
        this.demeterSecondBuilding[0] = demeterSecondBuilding;
    }

    public void setDemeterSecondBuilding(int[] demeterSecondBuilding) {
        this.demeterSecondBuilding = demeterSecondBuilding;
        this.demeterSecondBuildingCurrIndex = 0;
    }

    @Override
    public int askDemeterSecondBuilding(String playerName, List<SpaceCopy> deepCopySpaceList) throws DisconnectionException {
        //return demeterSecondBuilding;
        if (demeterSecondBuilding.length == 1)
            return demeterSecondBuilding[0];
        if (demeterSecondBuildingCurrIndex < demeterSecondBuilding.length) {
            demeterSecondBuildingCurrIndex++;
            return demeterSecondBuilding[demeterSecondBuildingCurrIndex - 1];
        }

        System.out.println("Index out of bounds askDemeterSecondBuilding");
        return -1000;
    }
}