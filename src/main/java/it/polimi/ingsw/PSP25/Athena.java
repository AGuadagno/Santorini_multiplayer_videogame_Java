package it.polimi.ingsw.PSP25;

/**
 * Athena Class.
 */
public class Athena extends GodPower {

    /**
     * Athena constructor
     * @param activeEffects list of opponent GodPower effect active in our turn that could limit movement,
     *                      building action or winning conditions of our player
     */
    public Athena(ActiveEffects activeEffects) {
        super(activeEffects);
    }

    /**
     * Override of "canMove" according to Athena effect:
     * "If one of your Workers moved up on your last turn, opponent workers cannot move up this turn"
     * @param worker the opponent's Worker that we want to know if he can move to Space
     * @param space  Space where we want to know if the worker can move
     * @return true if the opponent worker is not moving up (so if the movement is considered valid)
     *         false if the opponent worker is moving up (so if the movement is not considered valid)
     */
    @Override
    public boolean canMove(Worker worker, Space space) {
        //DEBUG
        /*System.out.println("AthenaEffect.canMove() WorkerPos: " + worker.getSpace().getNumber() + " TargetSpace: " +
                space.getNumber());*/
        //END DEBUG
        if (worker.getSpace().getTowerHeight() < space.getTowerHeight())
            return false;
        else
            return true;
    }

    /**
     * Override of "addActiveEffects" according to Athena effect:
     * "If one of your Workers moved up on your last turn, opponent workers cannot move up this turn"
     * If one of the workers controlled by the player who has "Athena" god power moves up, we put "Athena effects"
     * in the list of effects valid during opponent turn.
     * Athena effect prevents the moving up of opponent workers.
     * @param activeEffects list of opponent GodPower effect active in our turn that could limit movement,
     *                      building action or winning conditions of our player
     * @param worker1 controlled by the player who has "Athena" god power
     * @param worker2 controlled by the player who has "Athena" god power
     * @param selectedWorker controlled by the player who has "Athena" god power
     */
    @Override
    protected void addActiveEffects(ActiveEffects activeEffects, Worker worker1, Worker worker2, Worker selectedWorker) {
        if (selectedWorker.getHeightBeforeMove() < selectedWorker.getSpace().getTowerHeight())
            activeEffects.pushEffect(this);
        else
            activeEffects.pushEffect(new GodPower(activeEffects));
    }
}
