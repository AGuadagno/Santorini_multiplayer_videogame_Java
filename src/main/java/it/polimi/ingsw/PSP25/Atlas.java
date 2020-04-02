package it.polimi.ingsw.PSP25;

import java.util.Scanner;

/**
 * Atlas Class.
 */
public class Atlas extends GodPower {

    /**
     * Atlas constructor
     * @param activeEffects list of opponent GodPower effect active in our turn that could limit movement,
     *                      building action or winning conditions of our player
     */
    public Atlas(ActiveEffects activeEffects) {
        super(activeEffects);
    }

    /**
     * Override of "buildBlock" according to Atlas effect:
     * "Your Worker may build a dome at any level."
     * We ask to the player if he want to build a block or a dome. If he choose 'b',
     * we build a block, if the choose 'd' we build a dome
     * @param space Space where the player wants to build a Block or a Dome
     */
    @Override
    protected void buildBlock(Space space) {
        if (space.getTowerHeight() < 3) {
            System.out.println("Do you want to build a dome or a block? (b = block , d = dome)");
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.next();
            while (!(answer.equals("d") || answer.equals("b"))) {
                System.out.println("Your Choice is not valid. insert 'b' to build a block, 'd' to build a dome");
                answer = scanner.next();
            }
            if (answer.equals("b"))
                space.increaseTowerHeight();
            else{
                space.addDome();
            }
        }
        else {
            space.addDome();
        }
    }
}
