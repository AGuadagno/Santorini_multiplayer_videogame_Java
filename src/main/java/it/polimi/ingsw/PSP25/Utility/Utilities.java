package it.polimi.ingsw.PSP25.Utility;

import it.polimi.ingsw.PSP25.Board;
import it.polimi.ingsw.PSP25.GodPower;
import it.polimi.ingsw.PSP25.Space;

import java.util.ArrayList;
import java.util.List;

public class Utilities {
    public static List<String> deepCopyGodPowerNames(List<GodPower> original) {
        List<String> copied = new ArrayList<>();
        for (GodPower g : original) {
            copied.add(g.toString());
        }
        return copied;
    }

    public static SpaceCopy[][] deepCopyBoard(Board board) {
        SpaceCopy[][] boardCopy = new SpaceCopy[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boardCopy[i][j] = new SpaceCopy(board.getSpace(j, i));
            }
        }

        return boardCopy;
    }
}
