package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.*;
import it.polimi.ingsw.PSP25.Server.DisconnectionException;
import java.util.List;

/**
 * Zeus class
 */
public class Zeus extends GodPower {

    /**
     * Zeus constructor
     *
     * @param activeEffects      array containing opponents god power effects that may influence this turn
     * @param broadcastInterface Interface used to share information with all the other players
     */
    public Zeus(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

    /**
     * Override of "getValidBuildSpaces" according to Zeus's effect:
     * "Your Worker may build a block under itself."
     * The space where the Worker is located is added to the valid build spaces list.
     *
     * @param worker we want to know Spaces in which he can build
     * @return List of possible Spaces where the Worker passed as argument can build
     */
    @Override
    protected List<Space> getValidBuildSpaces(Worker worker) {
        List<Space> validBuildingSpaces = super.getValidBuildSpaces(worker);
        if (worker.getSpace().getTowerHeight() != 3)
            validBuildingSpaces.add(worker.getSpace());
        return validBuildingSpaces;
    }

    /**
     * Override of "askToBuild" in which the "HeightBeforeMove" of the worker is updated if the player decides to build
     * in the space where the worker itself is located.
     *
     * @param player              who is playing the turn
     * @param validBuildingSpaces List of valid spaces where the selected worker can build
     * @return Space where we want to build
     * @throws DisconnectionException exception thrown in case of disconnection of a client or disconnection of the server
     */
    @Override
    protected Space askToBuild(Player player, List<Space> validBuildingSpaces) throws DisconnectionException {
        Space selectedSpace = super.askToBuild(player, validBuildingSpaces);
        if (selectedSpace.equals(selectedWorker.getSpace())) {
            selectedWorker.setHeightBeforeMove(selectedSpace.getTowerHeight());
        }
        return selectedSpace;
    }
}
