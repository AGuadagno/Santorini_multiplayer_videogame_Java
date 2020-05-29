package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.*;
import it.polimi.ingsw.PSP25.Server.DisconnectionException;

/**
 * Limus class.
 */
public class Limus extends GodPower {
    private Space workerSpace1 = null;
    private Space workerSpace2 = null;

    /**
     * Limus constructor
     *
     * @param activeEffects      array containing opponents god power effects that may influence this turn
     * @param broadcastInterface Interface used to share information with all the other players
     */
    public Limus(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

    /**
     * Override of "canBuild" according to Limus' effect:
     * "Opponent Workers cannot build on spaces neighboring your Workers, unless building a dome to create a Complete Tower."
     *
     * @param worker who wants to build
     * @param space  where the worker wants to build
     */
    @Override
    public boolean canBuild(Worker worker, Space space) {
        if (space.getTowerHeight() < 3 && (workerSpace1.getAdjacentSpaces().contains(space) ||
                workerSpace2.getAdjacentSpaces().contains(space))) {
            return false;
        } else
            return true;
    }

    /**
     * First positioning of workers in the board
     *
     * @param player  who controls Limus
     * @param spaceW1 Space where the player wants to position his first worker
     * @param spaceW2 Space where the player wants to position his second worker
     */
    @Override
    public void initializeWorkers(Player player, Space spaceW1, Space spaceW2) {
        super.initializeWorkers(player, spaceW1, spaceW2);
        this.workerSpace1 = spaceW1;
        this.workerSpace2 = spaceW2;
    }

    /**
     * Override of "turnSequence" in which the positions of workers of the player who controls Limus are saved.
     *
     * @param player        playing the round
     * @param activeEffects array containing opponents god power effects that may influence this turn
     * @return TurnResult.LOSE if the player has lost during this turn
     * TurnResult.WIN if the player has won during this turn
     * TurnResult.CONTINUE if the player hasn't lost or won during this turn
     */
    @Override
    public TurnResult turnSequence(Player player, ActiveEffects activeEffects) throws DisconnectionException {
        TurnResult turnResult = super.turnSequence(player, activeEffects);
        workerSpace1 = player.getWorker1().getSpace();
        workerSpace2 = player.getWorker2().getSpace();
        return turnResult;
    }
}