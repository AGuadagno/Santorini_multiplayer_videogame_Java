package it.polimi.ingsw.PSP25.Model;

import java.lang.*;

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
     * @param x Space column number
     * @param y Space row number
     * @return the Space in column x and row y
     */
    public Space getSpace(int x, int y) {
        return spaceMatrix[y][x];
    }

    /**
     * Prints the Board. In each space this method specifies the number of the Space,
     * the tower height (eventually with the dome) and, if the Space is occupied by a Worker,
     * the ID of the player who controls the Worker and the worker number (worker 1 or worker 2).
     */
    public void print() {
        for (int i = 0; i < 5; i++) {
            StringBuilder[] rowLines = new StringBuilder[5];
            for (int k = 0; k < 5; k++) {
                rowLines[k] = new StringBuilder("");
                rowLines[0].append("+ - - - - ");    //first line: cell separator
            }
            rowLines[0].append("+"); //last '+'

            for (int j = 0; j < 5; j++) {
                int cellNum = (5 * i) + j;

                rowLines[1].append("|" + cellNum + (cellNum < 10 ? "        " : "       "));
                rowLines[2].append("|   H:" + spaceMatrix[i][j].getTowerHeight() +
                        (spaceMatrix[i][j].hasDome() ? " D " : "   "));
                rowLines[3].append("|   ");
                rowLines[4].append("|   ");
                if (spaceMatrix[i][j].hasWorker()) {
                    Worker worker = spaceMatrix[i][j].getWorker();
                    rowLines[3].append(worker.getPlayer().getID() + "   ");
                    rowLines[4].append("W ");
                    if (worker.equals(worker.getPlayer().getWorker1()))
                        rowLines[4].append("1   ");
                    else if (worker.equals(worker.getPlayer().getWorker2()))
                        rowLines[4].append("2   ");
                    else {
                        try {
                            throw new Exception();
                        } catch (Exception e) {
                            System.out.println("Worker in cell " + cellNum + "doesn't correspond to his Player");
                            e.printStackTrace();
                        }
                    }
                } else {
                    rowLines[3].append("      ");
                    rowLines[4].append("      ");
                }
            }
            for (int k = 1; k < 5; k++) {
                rowLines[k].append("|"); //last '|'
            }
            for (int l = 0; l < 5; l++) {
                System.out.println(rowLines[l]);
            }
        }
        for (int k = 0; k < 5; k++) {
            System.out.print("+ - - - - ");    //last line: cell separator
        }
        System.out.print("+"); //last '+'
        System.out.println();
    }
}