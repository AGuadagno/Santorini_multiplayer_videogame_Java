package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.ActiveEffects;
import it.polimi.ingsw.PSP25.Model.BroadcastInterface;
import it.polimi.ingsw.PSP25.Model.Space;
import it.polimi.ingsw.PSP25.Model.Worker;

/**
 * Hypnus class.
 */
public class Hypnus extends GodPower {
    /**
     * Hypnus constructor
     *
     * @param activeEffects      array containing opponents god power effects that may influence this turn
     * @param broadcastInterface Interface used to share information with all the other players
     */
    public Hypnus(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

    /**
     * Override of "canMove" according tu Hyonus' effect:
     * "If one of your opponent’s Workers is higher than all of their others, it cannot move"
     *
     * @param worker the opponent's Worker that we want to know if he can move to Space
     * @param space  Space where we want to know if the worker can move
     * @return true if the worker can move in the selected space, false otherwise
     */
    @Override
    public boolean canMove(Worker worker, Space space) {
        Worker otherWorker = null;
        if (worker.getPlayer().getWorker1() == worker) {
            otherWorker = worker.getPlayer().getWorker2();
        } else if (worker.getPlayer().getWorker2() == worker) {
            otherWorker = worker.getPlayer().getWorker1();
        }
        if (otherWorker != null && worker.getSpace().getTowerHeight() > otherWorker.getSpace().getTowerHeight()) {
            return false;
        } else {
            return true;
        }
    }
}
