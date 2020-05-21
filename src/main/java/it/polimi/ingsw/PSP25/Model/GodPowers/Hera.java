package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.ActiveEffects;
import it.polimi.ingsw.PSP25.Model.BroadcastInterface;
import it.polimi.ingsw.PSP25.Model.Space;
import it.polimi.ingsw.PSP25.Model.Worker;

public class Hera extends GodPower {
    /**
     * God Power Constructor
     *
     * @param activeEffects      list of opponent GodPower effect active in our turn that could limit movement,
     *                           building action or winning conditions of our player
     * @param broadcastInterface
     */
    public Hera(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

    /**
     * Override of "canWin" according to Hera's effect:
     * "An opponent cannot win by moving into a perimeter space."
     *
     * @param worker worker we want to know if he can win
     * @param space  Space where the Worker as been moved
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
