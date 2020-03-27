package it.polimi.ingsw.PSP25;

import java.util.Scanner;

public class Atlas extends GodPower {

    @Override
    protected void buildBlock(Space space) {
        if (space.getTowerHeight() < 3) {
            System.out.println("Do you want to build a dome or a block");
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.next();
            if (answer.equals("block"))
                space.increaseTowerHeight();
        }
        space.addDome();
    }
}
