package it.polimi.ingsw.PSP25.Model;

/**
 * Class Board. Board is the playground and it's made of 25 spaces organized in a 5x5 matrix.
 */
public class Board {
    private Space[][] spaceMatrix = new Space[5][5];

    /**
     * Board Constructor.
     */
    public Board() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                spaceMatrix[i][j] = new Space(j, i);
            }
        }
    }

    /**
     * Set the belonging bord for all the 25 spaces.
     * (In case of multiple matches, there are different boards whit 25 different spaces each one)
     */
    public void setBoardForAllSpaces() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                spaceMatrix[i][j].setBoard(this);
            }
        }
    }

    /**
     * @param x Space column number
     * @param y Space row number
     * @return the Space in column x and row y
     */
    public Space getSpace(int x, int y) {
        return spaceMatrix[y][x];
    }

}