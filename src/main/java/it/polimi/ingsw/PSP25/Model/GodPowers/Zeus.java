package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.ActiveEffects;
import it.polimi.ingsw.PSP25.Model.BroadcastInterface;
import it.polimi.ingsw.PSP25.Model.Space;
import it.polimi.ingsw.PSP25.Model.Worker;

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

    @Override
    protected List<Space> getValidBuildSpaces(Worker worker) {
        List<Space> validBuildingSpaces = super.getValidBuildSpaces(worker);
        if (worker.getSpace().getTowerHeight() != 3)
            validBuildingSpaces.add(worker.getSpace());
        return validBuildingSpaces;
    }

}
