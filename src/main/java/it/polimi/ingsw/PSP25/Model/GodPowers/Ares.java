package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.*;
import it.polimi.ingsw.PSP25.Server.DisconnectionException;

import java.util.List;

import static it.polimi.ingsw.PSP25.Utility.Utilities.deepCopySpaceList;

/*Fine del Tuo Turno:Puoi rimuovere
        un blocco non occupato (non una
        cupola) intorno al tuo Lavoratore
        che non si è mosso.*/
public class Ares extends GodPower {

    /**
     * God Power Constructor
     *
     * @param activeEffects      list of opponent GodPower effect active in our turn that could limit movement,
     *                           building action or winning conditions of our player
     * @param broadcastInterface
     */
    public Ares(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

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
