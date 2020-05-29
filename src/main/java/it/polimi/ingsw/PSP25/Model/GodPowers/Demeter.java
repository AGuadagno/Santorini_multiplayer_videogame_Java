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
 * Demeter class
 */
public class Demeter extends GodPower {

    /**
     * Demeter constructor
     *
     * @param activeEffects      array containing opponents god power effects that may influence this turn
     * @param broadcastInterface Interface used to share information with all the other players
     */
    public Demeter(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

    /**
     * Override of "turnSequence" according to Demeter's effect:
     * "Your Worker may build one additional time, but not on the same space".
     * The player is asked if the wants to build twice.
     *
     * @param player        playing the turn
     * @param activeEffects array containing opponents god power effects that may influence this turn
     * @return TurnResult.LOSE if the player has lost during this turn
     * TurnResult.WIN if the player has won during this turn
     * TurnResult.CONTINUE if the player hasn't lost or won during this turn
     */
    @Override
    public TurnResult turnSequence(Player player, ActiveEffects activeEffects) throws DisconnectionException {
        List<Space> validMovementSpacesW1;
        List<Space> validMovementSpacesW2;
        List<Space> validBuildSpaces;
        Space selectedBuildingSpace = null;

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

        validBuildSpaces = getValidBuildSpaces(selectedWorker);
        // Verify if selected worker can build
        if (verifyLoseByBuilding(validBuildSpaces)) {
            return TurnResult.LOSE;
        }

        selectedBuildingSpace = askToBuild(player, validBuildSpaces);
        validBuildSpaces.remove(selectedBuildingSpace);

        // Demeter Effect: The player is asked if he wants to build twice
        askSecondBuilding(player, validBuildSpaces);

        addActiveEffects(activeEffects, player.getWorker1(), player.getWorker2(), selectedWorker);
        return TurnResult.CONTINUE;
    }

    /**
     * According to Demeter effect, we ask to the player if he wants to build for a second time.
     * If the answer is yes, the player can chose the second building space.
     * The second building space can not be the same space chosen for the first building action.
     *
     * @param player              playing the turn
     * @param validBuildingSpaces List of valid building spaces for the second building of the selected worker
     * @throws DisconnectionException
     */
    private void askSecondBuilding(Player player, List<Space> validBuildingSpaces) throws DisconnectionException {
        Space selectedBuildingSpace = null;
        String playerName = player.getName() + "(" + player.getID() + ")";
        int selectedSpace = player.getClientHandler().askDemeterSecondBuilding(playerName, deepCopySpaceList(validBuildingSpaces));

        // -1 = the player doesn't want to build twice
        if (selectedSpace == -1)
            return;

        int x = selectedSpace % 5;
        int y = selectedSpace / 5;
        for (Space space : validBuildingSpaces) {
            if (space.getX() == x && space.getY() == y)
                selectedBuildingSpace = space;
        }
        buildBlock(selectedBuildingSpace);
        broadcastInterface.broadcastBoard();
    }
}