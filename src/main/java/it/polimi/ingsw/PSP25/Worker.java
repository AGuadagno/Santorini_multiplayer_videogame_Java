package it.polimi.ingsw.PSP25;

/**
 * Worker Class. Each Player controls 2 Workers.
 */
public class Worker {

    /**
     * Description of Attributes:
     * space = contains the Space where the Worker is positioned in the Board
     * heightBeforeMove = contains the height of the tower of the Space where the Worker was previously positioned
     * heightBeforeMove is used to verify win conditions
     * player = contains the Player who controls the Worker
     */
    private Space space;
    private int heightBeforeMove;
    private Player player;

    /**
     * Worker Class Constructor
     * @param space Space where the Worker will be positioned at the beginning of the game
     * @param player Player who controls the Worker
     */
    public Worker(Space space, Player player) {
        this.space = space;
        this.heightBeforeMove = 0;
        this.player = player;
    }

    /**
     * Modifies the Attribute "space" in Worker.
     * Used to link a Worker to a Space
     * @param space Space where the Workers will be positioned
     */
    public void setSpace(Space space){
        this.space=space;
    }

    /**
     * Moves the Worker in a given Space
     * Updates heightBeforeMove and Worker reference in Space
     * @param space Space where the Worker will be positioned
     */
    public void moveTo(Space space) {
        this.space.removeWorker();
        this.heightBeforeMove = this.space.getTowerHeight();
        this.space = space;
        this.space.setWorker(this);
    }

    /**
     * Sets "heightBeforeMove" attribute
     * @param height new value of "heightBeforeMove" attribute
     */
    public void setHeightBeforeMove(int height) {
        this.heightBeforeMove = height;
    }

    /**
     * @return the Space where the Worker is positioned
     */
    public Space getSpace() {
        return space;
    }

    /**
     * @return the Player who controls the Worker
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return the value of "heightBeforeMove"
     */
    public int getHeightBeforeMove() {
        return heightBeforeMove;
    }
}
