package it.polimi.ingsw.PSP25;

public class Worker {
    private Space space;
    private int heightBeforeMove;
    private Player player;

    public Worker(Space space, Player player) {
        this.space = space;
        this.heightBeforeMove = 0;
        this.player = player;
    }

    public void setSpace(Space space){
        this.space=space;
    }

    public void moveTo(Space space) {
        this.space.removeWorker();
        this.heightBeforeMove = this.space.getTowerHeight();
        this.space = space;
        this.space.setWorker(this);
    }

    public void setHeightBeforeMove(int height) {
        this.heightBeforeMove = height;
    }

    public Space getSpace() {
        return space;
    }

    public Player getPlayer() {
        return player;
    }

    public int getHeightBeforeMove() {
        return heightBeforeMove;
    }
}
