package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.*;
import it.polimi.ingsw.PSP25.Model.ActiveEffects;
import it.polimi.ingsw.PSP25.Model.BroadcastInterface;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.PSP25.Utility.Utilities.deepCopySpaceList;

public class GodPower{

    protected ActiveEffects activeEffects;
    protected BroadcastInterface broadcastInterface;
    // Rendiamo selectedWorker un attributo generale così da poterlo utilizzare per definire l'array "validBuildingSpaces"
    protected Worker selectedWorker = null;

    public GodPower(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        this.activeEffects=activeEffects;
        this.broadcastInterface=broadcastInterface;
    }

    protected List<Space> getValidMovementSpaces(Worker worker) {
        ArrayList<Space> validMovementSpaces = new ArrayList<Space>();
        for (Space space : worker.getSpace().getAdjacentSpaces()) {
            if (space.getWorker() == null && (space.getTowerHeight() - worker.getSpace().getTowerHeight() <= 1)
                    && !space.hasDome() && activeEffects.canMove(worker, space)) {
                validMovementSpaces.add(space);
            }
        }
        return validMovementSpaces;
    }

    public boolean canMove(Worker worker, Space space) {
        return true;
    }


    public List<Space> getValidBuildSpaces(Worker worker) {
        ArrayList<Space> validBuildSpaces = new ArrayList<Space>();
        for (Space space : worker.getSpace().getAdjacentSpaces()) {

            if (space.getWorker() == null && !space.hasDome() && space.getTowerHeight() <= 3
                    && activeEffects.canBuild(worker, space)) {
                validBuildSpaces.add(space);
            }
        }
        return validBuildSpaces;
    }

    public boolean canBuild(Worker worker, Space space) {
        return true;
    }

    public boolean canWin(Worker worker, Space space) {
        return true;
    }

    protected void moveWorker(Worker worker, Space space) {
        worker.moveTo(space);
    }

    protected void buildBlock(Space space) {
        if (space.getTowerHeight() == 3) {
            space.addDome();
        } else {
            space.increaseTowerHeight();
        }
    }

    protected boolean verifyWin(Worker worker) {
        if (worker.getHeightBeforeMove() == 2 && worker.getSpace().getTowerHeight() == 3) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean verifyLoseByMovement(List<Space> spacesW1, List<Space> spacesW2){
        if(spacesW1.size()==0 && spacesW2.size()==0) {
            return true;
        }
        else
            return false;
    }

    protected boolean verifyLoseByBuilding(List<Space> buildingSpaces){
        if(buildingSpaces.size()==0){
            return true;
        }
        else{
            return false;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // TURN SEQUENCE E METODI AUSILIARI

    public TurnResult turnSequence(Player player, ActiveEffects activeEffects) throws SocketTimeoutException, IOException {

        List<Space> validMovementSpacesW1;
        List<Space> validMovementSpacesW2;
        List<Space> validBuildSpaces;

        // VERIFICA SE SI PUO' MUOVERE (LOSEBYMOVEMENT)
        validMovementSpacesW1 = getValidMovementSpaces(player.getWorker1());
        validMovementSpacesW2 = getValidMovementSpaces(player.getWorker2());
        if (verifyLoseByMovement(validMovementSpacesW1, validMovementSpacesW2)) {
            return TurnResult.LOSE;
        }

        // SE PUO' MUOVERSI, CHIEDI DOVE VUOLE MUOVERSI E VERIFICA VITTORIA PER MOVIMENTO
        if(askToMoveWorker(player, validMovementSpacesW1, validMovementSpacesW2) == true){
            return TurnResult.WIN;
        }

        validBuildSpaces = getValidBuildSpaces(selectedWorker);

        // VERIFICA SE PUO' COSTRUIRE (LOSEBYBUILDING)
        if(verifyLoseByBuilding(validBuildSpaces)){
            return TurnResult.LOSE;
        }

        // CHIEDI DOVE VUOLE COSTRUIRE
        askToBuild(player, validBuildSpaces);

        addActiveEffects(activeEffects, player.getWorker1(), player.getWorker2(), selectedWorker);
        return TurnResult.CONTINUE;
    }

    protected boolean askToMoveWorker(Player player, List<Space> validMovementSpacesW1,
                                      List<Space> validMovementSpacesW2) throws IOException {

        Space selectedMovementSpace = null;

        String playerName = player.getName() + "(" + player.getID() + ")";
        int[] workerAndSpace = player.getClientHandler().askWorkerMovement(playerName,
                deepCopySpaceList(validMovementSpacesW1), deepCopySpaceList(validMovementSpacesW2));

        int x = workerAndSpace[1] % 5;
        int y = workerAndSpace[1] / 5;
        for (Space space : ((workerAndSpace[0] == 1) ? validMovementSpacesW1 : validMovementSpacesW2)) {
            if (space.getX() == x && space.getY() == y)
                selectedMovementSpace = space;
        }

        if(workerAndSpace[0]==1){
            selectedWorker = player.getWorker1();
        }
        else{
            selectedWorker = player.getWorker2();
        }

        moveWorker(selectedWorker, selectedMovementSpace);
        broadcastInterface.broadcastBoard();

        if (activeEffects.canWin(selectedWorker, selectedMovementSpace) && verifyWin(selectedWorker) == true) {
            return true;
        }
        else{
            return false;
        }
    }

    public Space askToBuild(Player player, List<Space> validBuildingSpaces) throws SocketTimeoutException, IOException {

        Space selectedBuildingSpace = null;
        String playerName = player.getName() + "(" + player.getID() + ")";

        int selectedSpace = player.getClientHandler().askBuildingSpace(playerName, deepCopySpaceList(validBuildingSpaces));

        int x = selectedSpace % 5;
        int y = selectedSpace / 5;
        for (Space space : validBuildingSpaces) {
            if (space.getX() == x && space.getY() == y)
                selectedBuildingSpace = space;
        }

        buildBlock(selectedBuildingSpace);
        broadcastInterface.broadcastBoard();

        return selectedBuildingSpace;
    }

    protected void addActiveEffects(ActiveEffects activeEffects, Worker worker1, Worker worker2, Worker selectedWorker) {
        activeEffects.pushEffect(this);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
