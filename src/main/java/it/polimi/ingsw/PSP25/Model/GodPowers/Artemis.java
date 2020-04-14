package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.ActiveEffects;
import it.polimi.ingsw.PSP25.Model.BroadcastInterface;
import it.polimi.ingsw.PSP25.Player;
import it.polimi.ingsw.PSP25.Space;
import it.polimi.ingsw.PSP25.TurnResult;

import java.util.List;

import static it.polimi.ingsw.PSP25.Utility.Utilities.deepCopySpaceList;

/**
 * Artemis class
 */
public class Artemis extends GodPower {

    /**
     * Artemis constructor
     *
     * @param activeEffects list of opponent GodPower effects active in our turn that could limit movement,
     *                      building actions or winning conditions of our player
     */
    public Artemis(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

    /**
     * Override of "turnSequence" according to Artemis' effect:
     * "Your Worker may move one additional time, but not back to its initial space.".
     * We ask to the player if he wants to move a second time,
     * if the answer is yes, we call "getValidMovementSpaces" for a second time.
     * The original space where the worker was positioned is not included in the second valid spaces list.
     *
     * @param player        playing the turn
     * @param activeEffects array containing opponents' god powers' effects that may influence this turn
     * @return TurnResult.LOSE if the player has lost during this turn
     * TurnResult.WIN if the player has won during this turn
     * TurnResult.CONTINUE if the player hasn't lost or won during this turn
     */
    @Override
    public TurnResult turnSequence(Player player, ActiveEffects activeEffects) {
        List<Space> validMovementSpacesW1;
        List<Space> validMovementSpacesW2;
        List<Space> validBuildSpaces;
        Space originalSpaceW1 = player.getWorker1().getSpace();
        Space originalSpaceW2 = player.getWorker2().getSpace();
        Space originalSpace = null;

        validMovementSpacesW1 = getValidMovementSpaces(player.getWorker1());
        validMovementSpacesW2 = getValidMovementSpaces(player.getWorker2());

        if (verifyLoseByMovement(validMovementSpacesW1, validMovementSpacesW2)) {
            return TurnResult.LOSE;
        }


        if (askToMoveWorker(player, validMovementSpacesW1, validMovementSpacesW2) == true) {
            return TurnResult.WIN;
        }

        if (selectedWorker.equals(player.getWorker1()))
            originalSpace = originalSpaceW1;
        else if (selectedWorker.equals(player.getWorker2()))
            originalSpace = originalSpaceW2;

        List<Space> validSecondMovementSpaces = getValidMovementSpaces(selectedWorker);
        validSecondMovementSpaces.remove(originalSpace);


        if (askSecondMovement(player, validSecondMovementSpaces) == true) {
            return TurnResult.WIN;
        }

        validBuildSpaces = getValidBuildSpaces(selectedWorker);

        // VERIFICA SE PUO' COSTRUIRE (LOSEBYBUILDING)
        if (verifyLoseByBuilding(validBuildSpaces)) {
            return TurnResult.LOSE;
        }

        // CHIEDI DOVE VUOLE COSTRUIRE
        askToBuild(player, validBuildSpaces);

        addActiveEffects(activeEffects, player.getWorker1(), player.getWorker2(), selectedWorker);
        return TurnResult.CONTINUE;
    }

    private boolean askSecondMovement(Player player, List<Space> validSecondMovementSpaces) {
        Space selectedMovementSpace = null;

        if (validSecondMovementSpaces.size() > 0) {
            String playerName = player.getName() + "(" + player.getID() + ")";
            int chosenMovementSpace = player.getClientHandler().askArtemisSecondMove(playerName,
                    deepCopySpaceList(validSecondMovementSpaces));

            if (chosenMovementSpace == -1)
                return false;

            int x = chosenMovementSpace % 5;
            int y = chosenMovementSpace / 5;
            for (Space space : validSecondMovementSpaces) {
                if (space.getX() == x && space.getY() == y)
                    selectedMovementSpace = space;
            }

            moveWorker(selectedWorker, selectedMovementSpace);

            broadcastInterface.broadcastBoard();
            if (activeEffects.canWin(selectedWorker, selectedMovementSpace) && verifyWin(selectedWorker) == true) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }
}
