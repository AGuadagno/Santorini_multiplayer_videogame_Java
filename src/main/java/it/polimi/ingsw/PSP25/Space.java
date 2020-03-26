package it.polimi.ingsw.PSP25;

import java.util.ArrayList;
import java.util.List;

public class Space {
    private final int x;
    private final int y;
    private int towerHeight;
    private boolean hasDoom;
    private Worker worker;
    private static Board board;

    public Space(int x, int y) {
        this.towerHeight = 0;
        this.hasDoom = false;
        this.worker = null;
        this.x = x;
        this.y = y;
        this.board = null;
    }

    public void increaseTowerHeight() {
        this.towerHeight = this.towerHeight + 1;
    }

    public void decreaseTowerHeight() {
        this.towerHeight = this.towerHeight - 1;
    }

    public void setTowerHeight(int towerHeight) {
        this.towerHeight = towerHeight;
    }

    public int getTowerHeight() {
        return towerHeight;
    }

    public void addDome() {
        this.hasDoom = true;
    }

    public void removeDome() {
        this.hasDoom = false;
    }

    public boolean hasDome() {
        return hasDoom;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Worker getWorker() {
        return worker;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Space> getAdjacentSpaces() {
        ArrayList<Space> adjacentSpaces = new ArrayList<Space>();

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (0 <= i && i < 5 && 0 <= j && j < 5) {
                    adjacentSpaces.add(board.getSpace(i, j));
                }
            }
        }
        return adjacentSpaces;
    }

    @Override
    public String toString() {
        return "Space " + (5*y + x);
    }

    public void removeWorker() {
        worker = null;
    }

    public boolean hasWorker() {
        if(worker == null)
            return false;
        else
            return true;
    }
}
