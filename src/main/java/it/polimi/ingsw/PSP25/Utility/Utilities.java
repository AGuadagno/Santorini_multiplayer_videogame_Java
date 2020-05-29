package it.polimi.ingsw.PSP25.Utility;

import it.polimi.ingsw.PSP25.Model.Board;
import it.polimi.ingsw.PSP25.Model.GodPowers.GodPower;
import it.polimi.ingsw.PSP25.Model.Space;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Utilities Class.
 * This class contains methods useful to other classes and other methods.
 */
public class Utilities {
    /**
     * Creates a list containing copies of  God Power Names
     *
     * @param original List of original God Powers.
     * @return list containing copies of  God Power Names
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

    public static int readIntegerInput(Scanner scanner) {
        boolean validInput;
        int input = -1;
        do {
            validInput = true;
            try {
                input = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Not valid input");
                scanner.nextLine();
                validInput = false;
            }
        } while (!validInput);
        return input;
    }
}