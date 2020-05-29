package it.polimi.ingsw.PSP25.Utility;

import it.polimi.ingsw.PSP25.Model.Space;
import it.polimi.ingsw.PSP25.Model.Worker;
import java.io.Serializable;

/**
 * SpaceCopy Class.
 * This class is used to create a copy of the Board. A copy of the board is sent to the clients when
 * they need to be informed about other players actions. (It's more safe to not share the original board but a copy of it)
 */
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
        if (this.playerID != null) {
            return true;
        } else {
            return false;
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getID() {
        return this.playerID;
    }

    public int getWorkerNumber() {
        return this.workerNumber;
    }

    public int getNumber() {
        return 5 * y + x;
    }

    public String toString() {
        return "Space " + getNumber();
    }

}
