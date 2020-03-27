package it.polimi.ingsw.PSP25;

import java.util.Scanner;

public class Hephaestus extends GodPower {

    @Override
    protected void buildBlock(Space space) {
        super.buildBlock(space);

        if (space.getTowerHeight() != 3) { // Non può costruire una cupola
            // Scelta se vuole costruire un nuovo blocco
            Scanner scanner = new Scanner(System.in);
            System.out.println("Vuoi costruire un blocco aggiuntivo in " + space.toString() + " ?" + "yes / no");
            String answer = scanner.next();
            if (answer.equals("yes"))
                super.buildBlock(space);
        }
    }

}
