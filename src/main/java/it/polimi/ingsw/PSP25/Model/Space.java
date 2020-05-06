package it.polimi.ingsw.PSP25.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Space Class.
 */
public class Space {
    /**
     * Description of Attributes:
     * x: column number of the space
     * y: row number of the space
     * towerHeight: height of the tower built on the Space
     * hasDome: is true if the tower built on the Space has a dome
     * worker: Worker positioned on the Space. null if the Space is not occupied by a Worker
     * board: Board to which the Space belongs
     */
    private final int x;
    private final int y;
    private int towerHeight;
    private boolean hasDome;
    private Worker worker;
    private static Board board;

    /**
     * Space constructor
     * @param x column number of the space
     * @param y row number of the space
     */
    public Space(int x, int y) {
        this.towerHeight = 0;
        this.hasDome = false;
        this.worker = null;
        this.x = x;
        this.y = y;
        this.board = null;
    }

    /**
     * Increases towerHeight by 1.
     */
    public void increaseTowerHeight() {
        this.towerHeight = this.towerHeight + 1;
    }

    /**
     * Decreases towerHeight by 1.
     */
    public void decreaseTowerHeight() {
        this.towerHeight = this.towerHeight - 1;
    }

    /**
     * Sets the new value of towerHeight.
     *
     * @param towerHeight the new value of towerHeight
     */
    public void setTowerHeight(int towerHeight) {
        this.towerHeight = towerHeight;
    }

    /**
     * @return the value of the tower height built on the space
     */
    public int getTowerHeight() {
        return towerHeight;
    }

    /**
     * Adds dome to the tower.
     */
    public void addDome() {
        this.hasDome = true;
    }

    /**
     * Removes dome to the tower.
     */
    public void removeDome() {
        this.hasDome = false;
    }

    /**
     * @return true only if the tower has a dome
     */
    public boolean hasDome() {
        return hasDome;
    }

    /**
     * Modifies the Attribute "worker" in Space,
     * used to link a Space to a Worker.
     *
     * @param worker Worker positioned on the Space
     */
    public void setWorker(Worker worker) { ;
        worker.setSpace(this);
        this.worker = worker;
    }

    /**
     * @return the Worker positioned on the Space
     */
    public Worker getWorker() {
        return worker;
    }

    /**
     * @return the column number of the Space
     */
    public int getX() {
        return x;
    }

    /**
     * @return the row number of the Space
     */
    public int getY() {
        return y;
    }

    /**
     * Links the Space to the Board
     * @param board Board to which the Space is linked
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * @return the 8 spaces directly connected to the Space, the 8 spaces surrounding it
     */
    public List<Space> getAdjacentSpaces() {
        ArrayList<Space> adjacentSpaces = new ArrayList<Space>();
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (0 <= i && i < 5 && 0 <= j && j < 5 && !(i==x && j==y)) {
                    adjacentSpaces.add(board.getSpace(i, j));
                }
            }
        }
        return adjacentSpaces;
    }

    /**
     * @return a String containing "Space xxx".
     * xxx is the Space number
     */
    @Override
    public String toString() {
        return "Space " + getNumber();
    }

    /**
     * @return the space number
     * The Space at the top left of the board is Space number 0,
     * the Space at the bottom right of the board is Space number 24.
     */
    public int getNumber() {
        return 5 * y + x;
    }

    /**
     * Removes the Worker which is positioned on the Space setting the attribute "worker" to null
     */
    public void removeWorker() {
        worker = null;
    }

    /**
     * @return true if the Space is occupied by a Worker
     */
    public boolean hasWorker() {
        if (worker == null)
            return false;
        else
            return true;
    }
}