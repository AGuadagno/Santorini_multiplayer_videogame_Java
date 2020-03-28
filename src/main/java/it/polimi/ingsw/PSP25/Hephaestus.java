package it.polimi.ingsw.PSP25;

import java.util.Scanner;

public class Hephaestus extends GodPower {

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
