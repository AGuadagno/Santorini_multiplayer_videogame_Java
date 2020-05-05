package it.polimi.ingsw.PSP25.Utility;

import it.polimi.ingsw.PSP25.Server.Model.Board;
import it.polimi.ingsw.PSP25.Server.Model.GodPowers.GodPower;
import it.polimi.ingsw.PSP25.Server.Model.Space;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilities Class.
 * This class contains methods useful to other classes and other methods.
 */
public class Utilities {
    /**
     * Creates a copy of a God Power Name.
     *
     * @param original List of original God Powers.
     * @return
     */
    public static List<String> deepCopyGodPowerNames(List<GodPower> original) {
        List<String> copied = new ArrayList<>();
        for (GodPower g : original) {
            copied.add(g.toString());
        }
        return copied;
    }

    /**
     * Creates a copy of the board in order to share it with players.
     *
     * @param board Original board
     * @return
     */
    public static SpaceCopy[][] deepCopyBoard(Board board) {
        SpaceCopy[][] boardCopy = new SpaceCopy[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boardCopy[i][j] = new SpaceCopy(board.getSpace(i, j));
            }
        }

        return boardCopy;
    }

    /**
     * Creates a copy of a list of Spaces.
     * Useful for valid movement spaces and valid building spaces, sent to players
     * during their turn.
     */
    public static List<SpaceCopy> deepCopySpaceList(List<Space> spaceList) {

        List<SpaceCopy> copied = new ArrayList<>();
        for (Space s : spaceList) {
            copied.add(new SpaceCopy(s));
        }
        return copied;
    }
}

