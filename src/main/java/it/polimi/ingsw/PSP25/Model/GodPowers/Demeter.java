package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.ActiveEffects;
import it.polimi.ingsw.PSP25.Model.BroadcastInterface;
import it.polimi.ingsw.PSP25.Model.GodPowers.GodPower;
import it.polimi.ingsw.PSP25.Player;
import it.polimi.ingsw.PSP25.Space;
import it.polimi.ingsw.PSP25.TurnResult;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static it.polimi.ingsw.PSP25.Utility.Utilities.deepCopySpaceList;

/**
 * Demeter class
 */
public class Demeter extends GodPower {

    /**
     * Demeter constructor
     *
     * @param activeEffects list of opponent GodPower effects active in our turn that could limit movement,
     *                      building action or winning conditions of our player
     */
    public Demeter(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

    /**
     * Override of "turnSequence" according to Demeter's effect:
     * "Your Worker may build one additional time, but not on the same space".
     * We ask to the player if he wants to build for a second time.
     * If the answer is yes, we call methods for construction a second time.
     *
     * @param player        playing the turn
     * @param activeEffects array containing opponent god power effects that may influence this turn
     * @return TurnResult.LOSE if the player has lost during this turn
     * TurnResult.WIN if the player has won during this turn
     * TurnResult.CONTINUE if the player hasn't lost or won during this turn
     */
    @Override
    public TurnResult turnSequence(Player player, ActiveEffects activeEffects) {
        List<Space> validMovementSpacesW1;
        List<Space> validMovementSpacesW2;
        List<Space> validBuildSpaces;
        Space selectedBuildingSpace = null;

        validMovementSpacesW1 = getValidMovementSpaces(player.getWorker1());
        validMovementSpacesW2 = getValidMovementSpaces(player.getWorker2());


        if (verifyLoseByMovement(validMovementSpacesW1, validMovementSpacesW2)) {
            return TurnResult.LOSE;
        }

        // SE PUO' MUOVERSI, CHIEDI DOVE VUOLE MUOVERSI E VERIFICA VITTORIA PER MOVIMENTO
        if (askToMoveWorker(player, validMovementSpacesW1, validMovementSpacesW2) == true) {
            return TurnResult.WIN;
        }

        validBuildSpaces = getValidBuildSpaces(selectedWorker);
        if (verifyLoseByBuilding(validBuildSpaces)) {
            return TurnResult.LOSE;
        }

        // CHIEDI DOVE VUOLE COSTRUIRE
        selectedBuildingSpace = askToBuild(player, validBuildSpaces);
        validBuildSpaces.remove(selectedBuildingSpace);

        askSecondBuilding(player, validBuildSpaces);

        addActiveEffects(activeEffects, player.getWorker1(), player.getWorker2(), selectedWorker);

        return TurnResult.CONTINUE;
    }

    private void askSecondBuilding(Player player, List<Space> validBuildingSpaces) {
        Space selectedBuildingSpace = null;
        String playerName = player.getName() + "(" + player.getID() + ")";

        int selectedSpace = player.getClientHandler().askDemeterSecondBuilding(playerName, deepCopySpaceList(validBuildingSpaces));

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