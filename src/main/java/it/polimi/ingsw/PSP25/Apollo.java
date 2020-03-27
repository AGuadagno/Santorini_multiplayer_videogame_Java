package it.polimi.ingsw.PSP25;

import java.util.ArrayList;
import java.util.List;

public class Apollo extends GodPower {

    @Override
    protected List<Space> getValidMovementSpaces(Worker worker) {
        ArrayList<Space> validMovementSpaces = new ArrayList<Space>();

        for (Space space : worker.getSpace().getAdjacentSpaces()) {

            if (space.getTowerHeight() - worker.getSpace().getTowerHeight() <= 1 && !space.hasDome()) {
                validMovementSpaces.add(space);
            }
        }
        return validMovementSpaces;
    }

    @Override
    protected void moveWorker(Worker worker, Space space) {
        if (space.hasWorker()) {
            space.getWorker().moveTo(worker.getSpace());
        }
        worker.moveTo(space);
    }
}
