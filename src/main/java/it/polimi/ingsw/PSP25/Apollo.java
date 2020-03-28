package it.polimi.ingsw.PSP25;

import java.util.ArrayList;
import java.util.List;

public class Apollo extends GodPower {

    @Override
    protected List<Space> getValidMovementSpaces(Worker worker) {
        ArrayList<Space> validMovementSpaces = new ArrayList<Space>();
        for (Space space : worker.getSpace().getAdjacentSpaces()) {
            if (space.getTowerHeight() - worker.getSpace().getTowerHeight() <= 1 && !space.hasDome() && !space.equals(worker.getSpace())) {
                validMovementSpaces.add(space);
            }
        }
        return validMovementSpaces;
    }

    @Override
    protected void moveWorker(Worker myWorker, Space targetSpace) {
        if (targetSpace.hasWorker()) {
            Space myPreviousSpace = myWorker.getSpace();
            Worker opponentWorker = targetSpace.getWorker();
            targetSpace.setWorker(myWorker);
            myPreviousSpace.setWorker(opponentWorker);
            opponentWorker.setHeightBeforeMove(myPreviousSpace.getTowerHeight());
        }
        else{
            myWorker.moveTo(targetSpace);
        }
    }
}
