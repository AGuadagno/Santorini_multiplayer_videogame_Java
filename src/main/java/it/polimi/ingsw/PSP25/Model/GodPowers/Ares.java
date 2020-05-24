package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.*;
import it.polimi.ingsw.PSP25.Server.DisconnectionException;

import java.util.List;

import static it.polimi.ingsw.PSP25.Utility.Utilities.deepCopySpaceList;

public class Ares extends GodPower {

    /**
     * God Power Constructor
     *
     * @param activeEffects      list of opponent GodPower effect active in our turn that could limit movement,
     *                           building action or winning conditions of our player
     * @param broadcastInterface Interface used to share information with all the other players
     */
    public Ares(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

    /**
     * Override of "turnSequence" according to Ares' effect:
     * "You may remove an unoccupied block (not dome) neighboring your unmoved Worker. You also remove any Tokens on the block".
     * The player is asked if he wants to remove a block.
     *
     *  @param player        playing the turn
     *  @param activeEffects array containing opponents' god powers' effects that may influence this turn
     *  @return TurnResult.LOSE if the player has lost during this turn
     *  TurnResult.WIN if the player has won during this turn
     *  TurnResult.CONTINUE if the player hasn't lost or won during this turn
     */
    @Override
    public TurnResult turnSequence(Player player, ActiveEffects activeEffects) throws DisconnectionException {

        List<Space> validMovementSpacesW1;
        List<Space> validMovementSpacesW2;
        List<Space> validBuildSpaces;

        // Verify if the player can move
        validMovementSpacesW1 = getValidMovementSpaces(player.getWorker1());
        validMovementSpacesW2 = getValidMovementSpaces(player.getWorker2());
        if (verifyLoseByMovement(validMovementSpacesW1, validMovementSpacesW2)) {
            return TurnResult.LOSE;
        }

        // If the player can move at least one of his workers, he's ask to move a worker
        // and then win by movement is verified.
        if (askToMoveWorker(player, validMovementSpacesW1, validMovementSpacesW2) == true) {
            return TurnResult.WIN;
        }

        validBuildSpaces = getValidBuildSpaces(selectedWorker);

        // Verify if selected worker can build
        if (verifyLoseByBuilding(validBuildSpaces)) {
            return TurnResult.LOSE;
        }

        // If selected worker can build, the player his asked to chose a building space and then
        // a block (or a dome) is built in the selected space.
        askToBuild(player, validBuildSpaces);

        askToRemoveBlock(player, selectedWorker);

        addActiveEffects(activeEffects, player.getWorker1(), player.getWorker2(), selectedWorker);

        return TurnResult.CONTINUE;
    }

    /**
     * The worker is asked if he wants to remove a block (not dome) neighboring your unmoved Worker.
     *
     * @param player         playing the turn
     * @param selectedWorker moved worker
     * @throws DisconnectionException
     */
    private void askToRemoveBlock(Player player, Worker selectedWorker) throws DisconnectionException {
        Worker nonSelectedWorker;
        int nonSelectedWorkerNumber;
        if (selectedWorker == player.getWorker1()) {
            nonSelectedWorker = player.getWorker2();
            nonSelectedWorkerNumber = 2;
        } else {
            nonSelectedWorker = player.getWorker1();
            nonSelectedWorkerNumber = 1;
        }
        List<Space> validRemoveSpaces = nonSelectedWorker.getSpace().getAdjacentSpaces();
        validRemoveSpaces.removeIf(s -> s.hasDome() || s.hasWorker() || s.getTowerHeight() == 0);

        Space removeSpace = null;
        int removeSpaceNumber = -1;
        String playerName = player.getName() + "(" + player.getID() + ")";
        if (validRemoveSpaces.size() > 0)
            removeSpaceNumber = player.getClientHandler().askToRemoveBlockAres(playerName,
                    deepCopySpaceList(validRemoveSpaces), nonSelectedWorkerNumber);
        // -1 = don't want to remove a block
        if (removeSpaceNumber != -1) {
            int x = removeSpaceNumber % 5;
            int y = removeSpaceNumber / 5;
            for (Space space : validRemoveSpaces) {
                if (space.getX() == x && space.getY() == y)
                    removeSpace = space;
            }
            removeSpace.decreaseTowerHeight();
            broadcastInterface.broadcastBoard();
        }
    }
}
