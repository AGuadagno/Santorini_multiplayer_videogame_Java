package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.ActiveEffects;
import it.polimi.ingsw.PSP25.Model.BroadcastInterface;
import it.polimi.ingsw.PSP25.Model.Space;
import it.polimi.ingsw.PSP25.Model.Worker;

public class Hypnus extends GodPower {
    /**
     * God Power Constructor
     *
     * @param activeEffects      list of opponent GodPower effect active in our turn that could limit movement,
     *                           building action or winning conditions of our player
     * @param broadcastInterface
     */
    public Hypnus(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

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
