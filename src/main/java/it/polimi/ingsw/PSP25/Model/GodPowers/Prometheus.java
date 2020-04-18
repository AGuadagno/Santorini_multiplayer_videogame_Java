package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.ActiveEffects;
import it.polimi.ingsw.PSP25.Model.BroadcastInterface;
import it.polimi.ingsw.PSP25.Model.GodPowers.GodPower;
import it.polimi.ingsw.PSP25.Player;
import it.polimi.ingsw.PSP25.Space;
import it.polimi.ingsw.PSP25.TurnResult;
import it.polimi.ingsw.PSP25.Worker;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static it.polimi.ingsw.PSP25.Utility.Utilities.deepCopySpaceList;

/**
 * Prometheus class
 */
public class Prometheus extends GodPower {

    /**
     * Prometheus constructor
     *
     * @param activeEffects list of opponent GodPower effects active in our turn that could limit movement,
     *                      building action or winning conditions of our player
     */
    public Prometheus(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

    /**
     * Override of "turnSequence" according to Prometheus' effect:
     * "If your Worker does not move up, it may build both before and after moving",
     * the turn sequence is modified and the player is asked if he wants to build before moving.
     * @param player playing the round
     * @param activeEffects array containing opponent god power effects that may influence this turn
     * @return TurnResult.LOSE if the player has lost during this turn
     *         TurnResult.WIN if the player has won during this turn
     *         TurnResult.CONTINUE if the player hasn't lost or won during this turn
     */
    @Override
    public TurnResult turnSequence(Player player, ActiveEffects activeEffects) throws IOException {

        List<Space> validMovementSpacesW1;
        List<Space> validMovementSpacesW2;
        List<Space> validBuildingSpacesW1;
        List<Space> validBuildingSpacesW2;
        List<Space> validBuildSpaces;
        List<Space> validMoveSpaces;

        /* Worker selectedWorker;
        int workerchoice;
        Space selectedMovementSpace = null;
        Space selectedBuildingSpace = null;
        boolean cantMoveUp = false; */

        validMovementSpacesW1 = getValidMovementSpaces(player.getWorker1());
        validMovementSpacesW2 = getValidMovementSpaces(player.getWorker2());

        // VERIFICA SE SI PUO' MUOVERE (LOSEBYMOVEMENT)
        if (verifyLoseByMovement(validMovementSpacesW1, validMovementSpacesW2)) {
            return TurnResult.LOSE;
        }

        validBuildingSpacesW1 = getValidBuildSpaces(player.getWorker1());
        validBuildingSpacesW2 = getValidBuildSpaces(player.getWorker2());

        boolean buildBeforeMove = askWorkerAndBuildBeforeMove(player, validMovementSpacesW1, validMovementSpacesW2,
                validBuildingSpacesW1, validBuildingSpacesW2);

        if (buildBeforeMove) {
            // TRUE
            if (selectedWorker.equals(player.getWorker1())) {
                askToBuild(player, validBuildingSpacesW1);
            } else {
                askToBuild(player, validBuildingSpacesW2);
            }
        }

        validMoveSpaces = getValidMovementSpaces(selectedWorker, buildBeforeMove);
        if (validMoveSpaces.size() == 0) {
            return TurnResult.LOSE;
        }

        if (askToMoveWorkerPrometheus(player, validMoveSpaces)) {
            return TurnResult.WIN;
        }

        validBuildSpaces = getValidBuildSpaces(selectedWorker);

        if (verifyLoseByBuilding(validBuildSpaces)) {
            return TurnResult.LOSE;
        }

        askToBuild(player, validBuildSpaces);

        addActiveEffects(activeEffects, player.getWorker1(), player.getWorker2(), selectedWorker);

        return TurnResult.CONTINUE;
    }

    private List<Space> getValidMovementSpaces(Worker worker, boolean cantMoveUp) {
        if(!cantMoveUp)
            return super.getValidBuildSpaces(worker);
        else{
            ArrayList<Space> validMovementSpaces = new ArrayList<Space>();

            for (Space space : worker.getSpace().getAdjacentSpaces()) {

                if (space.getWorker() == null && (space.getTowerHeight() <= worker.getSpace().getTowerHeight())
                        && !space.hasDome() && activeEffects.canMove(worker, space)) {
                    validMovementSpaces.add(space);
                }
            }
            return validMovementSpaces;
        }
    }

    private boolean askWorkerAndBuildBeforeMove(Player player, List<Space> validMovementSpacesW1, List<Space> validMovementSpacesW2,
                                                List<Space> validBuildingSpacesW1, List<Space> validBuildingSpacesW2) throws IOException {

        String playerName = player.getName() + "(" + player.getID() + ")";
        // Ritorna in pos 0 il worker, in pos 1 ritorna 0 se non vuole costruire prima di muvoere, 1 altrimenti
        int[] workerAndBuildBeforeMove = player.getClientHandler().askBuildBeforeMovePrometheus(playerName,
                (validMovementSpacesW1.size() > 0), (validMovementSpacesW2.size() > 0),
                (validBuildingSpacesW1.size() > 0), (validBuildingSpacesW2.size() > 0));

        if (workerAndBuildBeforeMove[0] == 1) {
            selectedWorker = player.getWorker1();
        } else {
            selectedWorker = player.getWorker2();
        }

        if (workerAndBuildBeforeMove[1] == 0) {
            return false;
        } else {
            return true;
        }

    }

    private boolean askToMoveWorkerPrometheus(Player player, List<Space> validMovementSpaces) throws IOException {

        Space selectedMovementSpace = null;

        String playerName = player.getName() + "(" + player.getID() + ")";
        int selectedSpace = player.getClientHandler().askWorkerMovementPrometheus(playerName, deepCopySpaceList(validMovementSpaces));

        int x = selectedSpace % 5;
        int y = selectedSpace / 5;
        for (Space space : validMovementSpaces) {
            if (space.getX() == x && space.getY() == y)
                selectedMovementSpace = space;
        }

        moveWorker(selectedWorker, selectedMovementSpace);
        broadcastInterface.broadcastBoard();

        if (activeEffects.canWin(selectedWorker, selectedMovementSpace) && verifyWin(selectedWorker) == true) {
            return true;
        } else {
            return false;
        }
    }
}