package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.ActiveEffects;
import it.polimi.ingsw.PSP25.Model.BroadcastInterface;
import it.polimi.ingsw.PSP25.Model.Player;
import it.polimi.ingsw.PSP25.Model.Space;
import it.polimi.ingsw.PSP25.Model.TurnResult;
import it.polimi.ingsw.PSP25.Server.DisconnectionException;

import java.util.List;

import static it.polimi.ingsw.PSP25.Utility.Utilities.deepCopySpaceList;

/**
 * Artemis class
 */
public class Artemis extends GodPower {

    /**
     * Artemis constructor
     *
     * @param activeEffects      array containing opponents god power effects that may influence this turn
     * @param broadcastInterface Interface used to share information with all the other players
     */
    public Artemis(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

    /**
     * Override of "turnSequence" according to Artemis' effect:
     * "Your Worker may move one additional time, but not back to its initial space."
     * The player is asked if the wants to move twice.
     *
     * @param player        playing the turn
     * @param activeEffects array containing opponents god power effects that may influence this turn
     * @return TurnResult.LOSE if the player has lost during this turn
     * TurnResult.WIN if the player has won during this turn
     * TurnResult.CONTINUE if the player hasn't lost or won during this turn
     * @throws DisconnectionException
     */
    @Override
    public TurnResult turnSequence(Player player, ActiveEffects activeEffects) throws DisconnectionException {
        List<Space> validMovementSpacesW1;
        List<Space> validMovementSpacesW2;
        List<Space> validBuildSpaces;
        Space originalSpaceW1 = player.getWorker1().getSpace();
        Space originalSpaceW2 = player.getWorker2().getSpace();
        Space originalSpace = null;

        // Verify if the player can move
        validMovementSpacesW1 = getValidMovementSpaces(player.getWorker1());
        validMovementSpacesW2 = getValidMovementSpaces(player.getWorker2());
        if (verifyLoseByMovement(validMovementSpacesW1, validMovementSpacesW2)) {
            return TurnResult.LOSE;
        }

        // If the player can move at least one of his workers, he is asked to move a worker and then win by movement is verified.
        if (askToMoveWorker(player, validMovementSpacesW1, validMovementSpacesW2)) {
            return TurnResult.WIN;
        }

        // We memorize the space occupied by the selected worker before his first move
        if (selectedWorker.equals(player.getWorker1()))
            originalSpace = originalSpaceW1;
        else if (selectedWorker.equals(player.getWorker2()))
            originalSpace = originalSpaceW2;

        List<Space> validSecondMovementSpaces = getValidMovementSpaces(selectedWorker);
        validSecondMovementSpaces.remove(originalSpace);

        // Artemis Effect: Artemis second sovement
        if (askSecondMovement(player, validSecondMovementSpaces)) {
            return TurnResult.WIN;
        }

        validBuildSpaces = getValidBuildSpaces(selectedWorker);
        // Verify if selected worker can build
        if (verifyLoseByBuilding(validBuildSpaces)) {
            return TurnResult.LOSE;
        }

        // If selected worker can build, the player is asked to choose a building space and then a block (or a dome) is built in the selected space.
        askToBuild(player, validBuildSpaces);

        addActiveEffects(activeEffects, player.getWorker1(), player.getWorker2(), selectedWorker);
        return TurnResult.CONTINUE;
    }

    /**
     * According to Artemis effect, we ask to the player if he wants to move his selected worker for a second time.
     * If the answer is yes, the player can chose the movement space and the selected worker is moved for the second time.
     *
     * @param player                    playing the turn
     * @param validSecondMovementSpaces List of valid movement spaces for the second movement of the selected worker
     * @return true if the player has win, false if the player hasn't win
     * @throws DisconnectionException
     */
    private boolean askSecondMovement(Player player, List<Space> validSecondMovementSpaces) throws DisconnectionException {
        Space selectedMovementSpace = null;

        if (validSecondMovementSpaces.size() > 0) {
            String playerName = player.getName() + "(" + player.getID() + ")";
            // We ask to the player if the wants to move the selected worker for a second time
            int chosenMovementSpace = player.getClientHandler().askArtemisSecondMove(playerName,
                    deepCopySpaceList(validSecondMovementSpaces));
            // -1 = no second movement
            if (chosenMovementSpace == -1)
                return false;
            int x = chosenMovementSpace % 5;
            int y = chosenMovementSpace / 5;
            for (Space space : validSecondMovementSpaces) {
                if (space.getX() == x && space.getY() == y)
                    selectedMovementSpace = space;
            }

            // Artemis second movement
            moveWorker(selectedWorker, selectedMovementSpace);

            broadcastInterface.broadcastBoard();
            if (activeEffects.canWin(selectedWorker, selectedMovementSpace) && verifyWin(selectedWorker)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
