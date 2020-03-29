package it.polimi.ingsw.PSP25;

public class Pan extends GodPower {

    public Pan(ActiveEffects activeEffects) {
        super(activeEffects);
    }

    @Override
    protected boolean verifyWin(Worker worker) {
        if (super.verifyWin(worker) || worker.getHeightBeforeMove() - worker.getSpace().getTowerHeight() >= 2)
            return true;
        else
            return false;
    }
}
