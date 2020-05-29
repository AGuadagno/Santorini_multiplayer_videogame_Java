package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.ActiveEffects;
import it.polimi.ingsw.PSP25.Model.BroadcastInterface;
import it.polimi.ingsw.PSP25.Model.Player;
import it.polimi.ingsw.PSP25.Model.Space;
import it.polimi.ingsw.PSP25.Model.TurnResult;
import it.polimi.ingsw.PSP25.Model.Worker;
import it.polimi.ingsw.PSP25.Server.DisconnectionException;
import java.util.ArrayList;
import java.util.List;
import static it.polimi.ingsw.PSP25.Utility.Utilities.deepCopySpaceList;

/**
 * Prometheus class
 */
public class Prometheus extends GodPower {

    /**
     * Prometheus constructor
     *
     * @param activeEffects      array containing opponents god power effects that may influence this turn
     * @param broadcastInterface Interface used to share information with all the other players
     */
    public Prometheus(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

    /**
     * Override of "turnSequence" according to Prometheus' effect:
     * "If your Worker does not move up, it may build both before and after moving."
     * The players is asked if he wants to build before move.
     *
     * @param player        playing the round
     * @param activeEffects array containing opponents god power effects that may influence this turn
     * @return TurnResult.LOSE if the player has lost during this turn
     * TurnResult.WIN if the player has won during this turn
     * TurnResult.CONTINUE if the player hasn't lost or won during this turn
     */
    @Override
    public TurnResult turnSequence(Player player, ActiveEffects activeEffects) throws DisconnectionException {

        List<Space> validMovementSpacesW1;
        List<Space> validMovementSpacesW2;
        List<Space> validBuildingSpacesW1;
        List<Space> validBuildingSpacesW2;
        List<Space> validBuildSpaces;
        List<Space> validMoveSpaces;

        validMovementSpacesW1 = getValidMovementSpaces(player.getWorker1());
        validMovementSpacesW2 = getValidMovementSpaces(player.getWorker2());

        // Verify if the player can move
        if (verifyLoseByMovement(validMovementSpacesW1, validMovementSpacesW2)) {
            return TurnResult.LOSE;
        }

        validBuildingSpacesW1 = getValidBuildSpaces(player.getWorker1());
        validBuildingSpacesW2 = getValidBuildSpaces(player.getWorker2());

        // We ask to the player if he wants to build before move
        boolean buildBeforeMove = askWorkerAndBuildBeforeMove(player, validMovementSpacesW1, validMovementSpacesW2,
                validBuildingSpacesW1, validBuildingSpacesW2);

        // If the players wants to build before move
        if (buildBeforeMove) {
            if (selectedWorker.equals(player.getWorker1())) {
                askToBuild(player, validBuildingSpacesW1);
            } else {
                askToBuild(player, validBuildingSpacesW2);
            }
        }

        // Player movement
        validMoveSpaces = getValidMovementSpaces(selectedWorker, buildBeforeMove);
        if (validMoveSpaces.size() == 0) {
            return TurnResult.LOSE;
        }
        if (askToMoveWorkerPrometheus(player, validMoveSpaces)) {
            return TurnResult.WIN;
        }

        validBuildSpaces = getValidBuildSpaces(selectedWorker);
        // Verify if selected worker can build
        if (verifyLoseByBuilding(validBuildSpaces)) {
            return TurnResult.LOSE;
        }

        askToBuild(player, validBuildSpaces);
        addActiveEffects(activeEffects, player.getWorker1(), player.getWorker2(), selectedWorker);
        return TurnResult.CONTINUE;
    }

    /**
     * Return the list of valid movement spaces considering that, if the player decided to build before moving,
     * the selected worker can't move in a space which high is greater then the worker's actual space high.
     *
     * @param worker     selected by the player
     * @param cantMoveUp is true if the player decided to build before moving.
     *                   is false if the player decided to not build before moving.
     * @return the list of valid movement spaces
     */

    private List<Space> getValidMovementSpaces(Worker worker, boolean cantMoveUp) {
        if (!cantMoveUp)
            return super.getValidMovementSpaces(worker);
        else {
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

    /**
     * The player is asked to select a worker and to decide if he wants to build before move.
     *
     * @param player                who is playing the turn
     * @param validMovementSpacesW1 list of valid spaces where worker 1 can move
     * @param validMovementSpacesW2 list of valid spaces where worker 2 can move
     * @param validBuildingSpacesW1 list of valid spaces where worker 1 can build
     * @param validBuildingSpacesW2 list of valid spaces where worker 2 can build
     * @return true if the player wants to build before moving, false otherwise
     * @throws DisconnectionException
     */
    private boolean askWorkerAndBuildBeforeMove(Player player, List<Space> validMovementSpacesW1, List<Space> validMovementSpacesW2,
                                                List<Space> validBuildingSpacesW1, List<Space> validBuildingSpacesW2) throws DisconnectionException {

        String playerName = player.getName() + "(" + player.getID() + ")";
        // Return the selected worker in pos 0
        // Return, in pos 1, 0 if the player don't want to build before move, 1 otherwise
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

    /**
     * Ask to the player the Space where he wants to move the selected Worker.
     * (Does not ask to selected a Worker. The selection of the Worker has already been done).
     *
     * @param player              who is playing the turn
     * @param validMovementSpaces List of valid spaces where the selected worker can move
     * @return True if the Player wins the game, false otherwise
     * @throws DisconnectionException
     */
    private boolean askToMoveWorkerPrometheus(Player player, List<Space> validMovementSpaces) throws DisconnectionException {
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