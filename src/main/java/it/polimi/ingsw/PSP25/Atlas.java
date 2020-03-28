package it.polimi.ingsw.PSP25;

import java.util.Scanner;

public class Atlas extends GodPower {

    @Override
    protected void buildBlock(Space space) {
        if (space.getTowerHeight() < 3) {
            System.out.println("Do you want to build a dome or a block? (b = block , d = dome)");
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.next();
            while(!(answer.equals("d") || answer.equals("b"))){
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
