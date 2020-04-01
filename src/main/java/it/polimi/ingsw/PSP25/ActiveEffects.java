package it.polimi.ingsw.PSP25;

import java.util.ArrayList;
import java.util.List;

/**
 * ActiveEffects Class.
 * ActiveEffects manages "effectList", an array containing players GodPower which effect influences opponents turn.
 * For Example, if one of the workers of the player who has Athena as God Power moves up, we put an Object "Athena"
 * in effectList to prevent the move up of Workers controlled by opponent players during their turn.
 * effectList size is (numofplayer-1) so that GodPower which effect influences opponents turn are active only during
 * opponent turn and only for 1 turn for each opponent player.
 * effectList is used as a stack. During each players turn, an effect is added in position 0 and the effect in position
 * (numofplayer-1) is removed. If the player GodPower does not influence opponent turn, a generic object belonging to the class
 * GodPower is added to effectList.
 */
public class ActiveEffects {
    private List<GodPower> effectsList;
    private int numOfEffects;

    /**
     * Creates effectsList, a (numOfPlayers-1) size array containing GodPower objects
     * @param numOfPlayers number of player playing the game
     */
    public ActiveEffects(int numOfPlayers) {
        numOfEffects = numOfPlayers - 1;
        effectsList = new ArrayList<>(numOfEffects);
    }

    /**
     * Initialize effectsList adding generic GodPower Objects which does not influence players turn.
     * GodPower objects are added to avoid null pointer exceptions
     */
    public void initializeEffects() {
        for (int i = 0; i < numOfEffects; i++) {
            effectsList.add(new GodPower(this));
        }
    }

    /**
     * inserts an effect in position 0 in effectsList and remove the last effect in the sack
     * which effects is no longer valid
     * @param effect we have to add in position 0 of effectsList
     */
    public void pushEffect(GodPower effect) {
        effectsList.add(0, effect);
        effectsList.remove(numOfEffects);
    }

    /**
     * Check if opponents GodPower in effectsList limits building action of a Worker
     * @param worker who wants to build
     * @param space where the worker wants to build
     * @return
     */
    public boolean canBuild(Worker worker, Space space) {
        for (int i = 0; i < numOfEffects; i++) {
            if (effectsList.get(i).canBuild(worker, space) == false)
                return false;
        }
        return true;
    }

    /**
     * Check if opponents GodPower in effectsList limits the movement of a Worker
     * @param worker we want to move
     * @param space where we want to move the worker
     * @return
     */
    public boolean canMove(Worker worker, Space space) {
        for (int i = 0; i < numOfEffects; i++) {
            if (effectsList.get(i).canMove(worker, space) == false)
                return false;
        }
        return true;
    }

    /**
     * @param worker tha player has moved
     * @param space where the worker has been moved
     * @return true if the win condition is valid considering opponent GodPower effects
     */
    public boolean canWin(Worker worker, Space space) {
        for (int i = 0; i < numOfEffects; i++) {
            if (effectsList.get(i).canWin(worker, space) == false)
                return false;
        }
        return true;
    }

    // DEBUG
    public void debugPrint() {
        System.out.println(effectsList);
    }
}
