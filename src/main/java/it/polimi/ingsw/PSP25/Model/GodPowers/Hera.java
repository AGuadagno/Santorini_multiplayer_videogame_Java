package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.ActiveEffects;
import it.polimi.ingsw.PSP25.Model.BroadcastInterface;
import it.polimi.ingsw.PSP25.Model.Space;
import it.polimi.ingsw.PSP25.Model.Worker;

/**
 * Hera class.
 */
public class Hera extends GodPower {
    /**
     * Hera constructor
     *
     * @param activeEffects      array containing opponents god power effects that may influence this turn
     * @param broadcastInterface Interface used to share information with all the other players
     */
    public Hera(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

    /**
     * Override of "canWin" according to Hera's effect:
     * "An opponent cannot win by moving into a perimeter space."
     *
     * @param worker we want to know if he can win
     * @param space  where the Worker has been moved
     * @return true if 'worker' can win
     */
    @Override
    public boolean canWin(Worker worker, Space space) {
        if (space.getX() == 0 || space.getX() == 4 || space.getY() == 0 || space.getY() == 4) {
            return false;
        } else {
            return true;
        }
    }
}
