package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.*;
import it.polimi.ingsw.PSP25.Server.DisconnectionException;

import java.util.List;

public class Zeus extends GodPower {

    /**
     * God Power Constructor
     *
     * @param activeEffects      list of opponent GodPower effect active in our turn that could limit movement,
     *                           building action or winning conditions of our player
     * @param broadcastInterface
     */
    public Zeus(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

    /**
     * Override of "getValidBuildSpaces" according to Zeus's effect:
     * "Your Worker may build a block under itself"
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
     * @return
     * @throws DisconnectionException
     */
    @Override
    public Space askToBuild(Player player, List<Space> validBuildingSpaces) throws DisconnectionException {
        Space selectedSpace = super.askToBuild(player, validBuildingSpaces);
        if (selectedSpace.equals(selectedWorker.getSpace())) {
            selectedWorker.setHeightBeforeMove(selectedSpace.getTowerHeight());
        }
        return selectedSpace;
    }
}
