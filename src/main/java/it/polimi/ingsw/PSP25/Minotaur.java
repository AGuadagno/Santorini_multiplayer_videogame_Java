package it.polimi.ingsw.PSP25;

import java.util.ArrayList;
import java.util.List;

public class Minotaur extends GodPower {

    @Override
    protected List<Space> getValidMovementSpaces(Worker worker) {
        ArrayList<Space> validMovementSpaces = new ArrayList<Space>();

        for (Space space : worker.getSpace().getAdjacentSpaces()) {

            Space spaceSameDir = spaceSameDir(space, worker.getSpace());
            if ((space.getWorker() == null || (spaceSameDir != null && spaceSameDir.getWorker() == null && !spaceSameDir.hasDome()))
                    && space.getTowerHeight() - worker.getSpace().getTowerHeight() <= 1 && !space.hasDome()) {
                validMovementSpaces.add(space);
            }
        }
        return validMovementSpaces;
    }

    @Override
    protected void moveWorker(Worker worker, Space space) {
        if (space.hasWorker()) {
            Space spaceSameDir = spaceSameDir(space, worker.getSpace());
            space.getWorker().moveTo(spaceSameDir);
        }
        worker.moveTo(space);
    }

    private Space spaceSameDir(Space space1, Space space2) {

        int dirX = space1.getX() - space2.getX();
        int dirY = space1.getY() - space2.getY();
        List<Space> adiacentSpaces = space1.getAdjacentSpaces();
        Space spaceSameDir = null;
        for (Space space : adiacentSpaces) {
            if (space.getX() == space1.getX() + dirX && space.getY() == space1.getY() + dirY) {
                spaceSameDir = space2;
                break;
            }
        }
        return spaceSameDir;
    }

}
