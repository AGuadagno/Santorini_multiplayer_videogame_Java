package it.polimi.ingsw.PSP25;

import java.util.ArrayList;
import java.util.List;


/**
 * Minotaus Class.
 */
public class Minotaur extends GodPower {

    /**
     * Minotaur constructor
     * @param activeEffects list of opponent GodPower effect active in our turn that could limit movement,
     *                      building action or winning conditions of our player
     */
    public Minotaur(ActiveEffects activeEffects) {
        super(activeEffects);
    }

    /**
     * Override of "getValidMovementSpaces" according to Minotaur effect:
     * "Your Worker may move into an opponent Worker’s space, if their Worker
     * can be forced one space straight backwards to an unoccupied space at any level"
     * So, spaces occupied by opponents worker could be added to the list of valid movement spaces
     * if the previous condition is respected.
     * @param worker the Worker we want to know Spaces to which he can move
     * @return List of possible Spaces where the Worker passed as argument can move to
     */
    @Override
    protected List<Space> getValidMovementSpaces(Worker worker) {
        ArrayList<Space> validMovementSpaces = new ArrayList<Space>();

        for (Space space : worker.getSpace().getAdjacentSpaces()) {

            Space spaceSameDir = spaceSameDir(space, worker.getSpace());
            if ((space.getWorker() == null || (space.getWorker().getPlayer() != worker.getPlayer()
                    && spaceSameDir != null && spaceSameDir.getWorker() == null && !spaceSameDir.hasDome()))
                    && space.getTowerHeight() - worker.getSpace().getTowerHeight() <= 1 && !space.hasDome()
                    && activeEffects.canMove(worker, space)) {
                validMovementSpaces.add(space);
            }
        }
        return validMovementSpaces;
    }


    @Override
    /**
     * Override of "moveWorker" according to Minotaur effect:
     * "Your Worker may move into an opponent Worker’s space, if their Worker
     * can be forced one space straight backwards to an unoccupied space at any level"
     * This method moves player worker and also opponent worker
     */
    protected void moveWorker(Worker worker, Space space) {
        if (space.hasWorker()) {
            Space spaceSameDir = spaceSameDir(space, worker.getSpace());
            space.getWorker().moveTo(spaceSameDir);
        }
        worker.moveTo(space);
    }

    /**
     *
     */
    private Space spaceSameDir(Space space1, Space space2) {

        int dirX = space1.getX() - space2.getX();
        int dirY = space1.getY() - space2.getY();
        List<Space> adiacentSpaces = space1.getAdjacentSpaces();
        Space spaceSameDir = null;
        for (Space space : adiacentSpaces) {
            if (space.getX() == space1.getX() + dirX && space.getY() == space1.getY() + dirY) {
                spaceSameDir = space;
                break;
            }
        }
        return spaceSameDir;
    }

}
