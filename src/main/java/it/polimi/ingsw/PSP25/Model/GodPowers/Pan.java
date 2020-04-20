package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.ActiveEffects;
import it.polimi.ingsw.PSP25.Model.BroadcastInterface;
import it.polimi.ingsw.PSP25.Model.Worker;

/**
 * Pan class
 */
public class Pan extends GodPower {

    /**
     * Pan constructor
     *
     * @param activeEffects      list of opponent GodPower effects active in the current turn that could limit movement,
     *                           building action or winning conditions of workers
     * @param broadcastInterface used to send the modified board to all the players
     */
    public Pan(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

    /**
     * Override of "verifyWin" according to Pan's effect:
     * "You also win if your Worker moves down two or more levels"
     * Controls if the moved worker moves down two or more levels,
     * super.verifyWin(worker) it's used because normal winning conditions are also valid.
     *
     * @param worker Worker whose movement can make his player win
     * @return true if the winning condition is verified
     */
    @Override
    protected boolean verifyWin(Worker worker) {
        if (super.verifyWin(worker) || worker.getHeightBeforeMove() - worker.getSpace().getTowerHeight() >= 2)
            return true;
        else
            return false;
    }
}