package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.ActiveEffects;
import it.polimi.ingsw.PSP25.Model.BroadcastInterface;
import it.polimi.ingsw.PSP25.Model.Space;
import it.polimi.ingsw.PSP25.Model.Worker;
import java.util.ArrayList;
import java.util.List;

/**
 * Apollo class
 */
public class Apollo extends GodPower {

    /**
     * Apollo constructor
     *
     * @param activeEffects      array containing opponents god power effects that may influence this turn
     * @param broadcastInterface Interface used to share information with all the other players
     */
    public Apollo(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

    /**
     * Override of "getValidMovementSpaces" according to Apollo's effect:
     * "Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated."
     * Adjacent spaces occupied by opponent workers are considered valid movement spaces.
     *
     * @param worker Worker that the player wants to know Spaces valid for the movement
     * @return List of possible Spaces where the Worker passed as argument can move to
     */
    @Override
    protected List<Space> getValidMovementSpaces(Worker worker) {
        ArrayList<Space> validMovementSpaces = new ArrayList<Space>();
        for (Space space : worker.getSpace().getAdjacentSpaces()) {
            if ((!space.hasWorker() || !space.getWorker().getPlayer().equals(worker.getPlayer())) &&
                    space.getTowerHeight() - worker.getSpace().getTowerHeight() <= 1 && !space.hasDome()
                    && activeEffects.canMove(worker, space)) {
                validMovementSpaces.add(space);
            }
        }
        return validMovementSpaces;
    }

    /**
     * Override of "moveWorker" according to Apollo's effect:
     * "Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated."
     * If the space where the player wants to move his worker is occupied by an opponent worker,
     * the two worker positions are switched.
     *
     * @param myWorker    Worker moved by the player who has Apollo as GodPower
     * @param targetSpace Space where the player who has Apollo as GodPower wants to move his worker
     */
    @Override
    protected void moveWorker(Worker myWorker, Space targetSpace) {
        if (targetSpace.hasWorker()) {
            Space myPreviousSpace = myWorker.getSpace();
            Worker opponentWorker = targetSpace.getWorker();
            targetSpace.setWorker(myWorker);
            myPreviousSpace.setWorker(opponentWorker);
            myWorker.setHeightBeforeMove(myPreviousSpace.getTowerHeight());
            opponentWorker.setHeightBeforeMove(myPreviousSpace.getTowerHeight());
        } else {
            myWorker.moveTo(targetSpace);
        }
    }
}