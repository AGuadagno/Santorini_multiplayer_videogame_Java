package it.polimi.ingsw.PSP25;

import java.util.ArrayList;
import java.util.List;

public class ActiveEffects {
    private List<GodPower> effectsList;
    private int numOfEffects;

    public ActiveEffects(int numOfPlayers) {
        numOfEffects = numOfPlayers - 1;
        effectsList = new ArrayList<>(numOfEffects);
    }

    public void initializeEffects() {
        for (int i = 0; i < numOfEffects; i++) {
            effectsList.add(new GodPower(this));
        }
    }

    public void pushEffect(GodPower effect) {
        effectsList.add(0, effect);
        effectsList.remove(numOfEffects);
    }

    public boolean canBuild(Worker worker, Space space) {
        for (int i = 0; i < numOfEffects; i++) {
            if (effectsList.get(i).canBuild(worker, space) == false)
                return false;
        }
        return true;
    }

    public boolean canMove(Worker worker, Space space) {
        for (int i = 0; i < numOfEffects; i++) {
            if (effectsList.get(i).canMove(worker, space) == false)
                return false;
        }
        return true;
    }

    public boolean canWin(Worker worker, Space space) {
        for (int i = 0; i < numOfEffects; i++) {
            if (effectsList.get(i).canWin(worker, space) == false)
                return false;
        }
        return true;
    }

    public void debugPrint() {
        System.out.println(effectsList);
    }
}
