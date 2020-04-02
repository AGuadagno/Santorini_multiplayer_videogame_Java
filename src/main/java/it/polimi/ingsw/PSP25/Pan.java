package it.polimi.ingsw.PSP25;

/**
 * Pan Class
 */
public class Pan extends GodPower {

    /**
     * Pan constructor
     * @param activeEffects list of opponent GodPower effect active in our turn that could limit movement,
     *                      building action or winning conditions of our player
     */
    public Pan(ActiveEffects activeEffects) {
        super(activeEffects);
    }

    /**
     * Override of "verifyWin" according to Pan effect:
     * "You also win if your Worker moves down two or more levels"
     * Controls if the moved worker moves down two or more levels.
     * super.verifyWin(worker) it's used because also normal winning conditions are valid.
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
