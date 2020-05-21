package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.*;
import it.polimi.ingsw.PSP25.Server.DisconnectionException;

/*Nel Turno Avversario: I Lavoratori
        avversari non possono costruire negli
        spazi circostanti i tuoi Lavoratori,
        tranne costruendo una cupola per creare una
        Torre Completa.*/
public class Limus extends GodPower {
    private Space workerSpace1 = null;
    private Space workerSpace2 = null;

    /**
     * God Power Constructor
     *
     * @param activeEffects      list of opponent GodPower effect active in our turn that could limit movement,
     *                           building action or winning conditions of our player
     * @param broadcastInterface
     */
    public Limus(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

    @Override
    public boolean canBuild(Worker worker, Space space) {
        if (space.getTowerHeight() < 3 && (workerSpace1.getAdjacentSpaces().contains(space) ||
                workerSpace2.getAdjacentSpaces().contains(space))) {
            return false;
        } else
            return true;
    }

    @Override
    public void initializeWorkers(Player player, Space spaceW1, Space spaceW2) {
        super.initializeWorkers(player, spaceW1, spaceW2);
        this.workerSpace1 = spaceW1;
        this.workerSpace2 = spaceW2;
        //addActiveEffects(activeEffects, null, null, null);
    }

    @Override
    public TurnResult turnSequence(Player player, ActiveEffects activeEffects) throws DisconnectionException {
        TurnResult turnResult = super.turnSequence(player, activeEffects);
        workerSpace1 = player.getWorker1().getSpace();
        workerSpace2 = player.getWorker2().getSpace();
        return turnResult;
    }
}