package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;


import java.io.IOException;

public class SendBoard extends Message {
    SpaceCopy[][] board;

    public SendBoard(SpaceCopy[][] board) {
        this.board = board;
    }

    public void process(NetworkHandler nh) throws IOException {
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
                rowLines[2].append("|   H:" + board[i][j].getTowerHeight() +
                        (board[i][j].hasDome() ? " D " : "   "));
                rowLines[3].append("|   ");
                rowLines[4].append("|   ");
                SpaceCopy currSpace = board[i][j];
                if (currSpace.hasWorker()) {
                    rowLines[3].append(currSpace.getID() + "   ");
                    rowLines[4].append("W ");
                    if (currSpace.getWorkerNumber() == 1)
                        rowLines[4].append("1   ");
                    else if (currSpace.getWorkerNumber() == 2)
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

