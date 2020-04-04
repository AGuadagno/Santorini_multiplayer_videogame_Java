package it.polimi.ingsw.PSP25;

import java.util.Scanner;

/**
 * Hephaestus class
 */
public class Hephaestus extends GodPower {

    /**
     * Hephaestus constructor
     *
     * @param activeEffects list of opponent GodPower effects active in our turn that could limit movement,
     *                      building action or winning conditions of our player
     */
    public Hephaestus(ActiveEffects activeEffects) {
        super(activeEffects);
    }

    /**
     * Override of "buildBlock" according to Hephaestus' effect:
     * "Your Worker may build one additional block (not dome) on top of your first block".
     * If the answer is 'yes', the methods that increase the tower height are called twice.
     * @param space Space where the player wants to build a Block
     */
    @Override
    protected void buildBlock(Space space) {
        super.buildBlock(space);

        if (space.getTowerHeight() != 3) { // can't build a dome
            // Choice to build another block
            Scanner scanner = new Scanner(System.in);
            System.out.println("Do you want ot build an additional block in " + space.toString() + " ?" + " y/n");
            String answer = scanner.next();
            if (answer.equals("y"))
                space.increaseTowerHeight();
        }
    }
}