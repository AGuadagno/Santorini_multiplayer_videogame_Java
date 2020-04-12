package it.polimi.ingsw.PSP25.Utility;

import it.polimi.ingsw.PSP25.Space;
import it.polimi.ingsw.PSP25.Worker;

import java.io.Serializable;

public class SpaceCopy implements Serializable {
    private final int x;
    private final int y;
    private int towerHeight;
    private boolean hasDome;
    private String playerID;
    private int workerNumber;

    public SpaceCopy(Space s) {
        this.x = s.getX();
        this.y = s.getY();
        this.towerHeight = s.getTowerHeight();
        this.hasDome = s.hasDome();

        if (s.hasWorker()) {
            Worker worker = s.getWorker();
            this.playerID = worker.getPlayer().getID();
            if (worker.equals(worker.getPlayer().getWorker1()))
                this.workerNumber = 1;
            else if (worker.equals(worker.getPlayer().getWorker2()))
                this.workerNumber = 2;
        } else {
            this.playerID = null;
            this.workerNumber = 0;
        }
    }

    public int getTowerHeight() {
        return towerHeight;
    }

    public boolean hasDome() {
        return hasDome;
    }

    public boolean hasWorker() {
        return this.playerID != null;
    }

    public String getID() {
        return this.playerID;
    }

    public int getWorkerNumber() {
        return this.workerNumber;
    }


}
