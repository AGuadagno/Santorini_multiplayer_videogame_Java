package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.ActiveEffects;
import it.polimi.ingsw.PSP25.Model.BroadcastInterface;
import it.polimi.ingsw.PSP25.Model.Player;
import it.polimi.ingsw.PSP25.Model.Space;
import it.polimi.ingsw.PSP25.Server.DisconnectionException;
import java.util.List;
import static it.polimi.ingsw.PSP25.Utility.Utilities.deepCopySpaceList;

/**
 * Hephaestus class
 */
public class Hephaestus extends GodPower {

    /**
     * Hephaestus constructor
     *
     * @param activeEffects      array containing opponents god power effects that may influence this turn
     * @param broadcastInterface Interface used to share information with all the other players
     */
    public Hephaestus(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

    /**
     * According to Hephaestus effect, we ask to the player if he wants to build an additional block in the selected building space.
     *
     * @param player              playing the turn
     * @param validBuildingSpaces List of valid building spaces
     * @return the selected building space
     * @throws DisconnectionException exception thrown in case of disconnection of a client or disconnection of the server
     */
    @Override
    public Space askToBuild(Player player, List<Space> validBuildingSpaces) throws DisconnectionException {
        Space selectedBuildingSpace = null;
        String playerName = player.getName() + "(" + player.getID() + ")";
        int[] spaceAndDoubleBuilding = player.getClientHandler().askHephaestusBuild(playerName, deepCopySpaceList(validBuildingSpaces));
        int x = spaceAndDoubleBuilding[0] % 5;
        int y = spaceAndDoubleBuilding[0] / 5;
        for (Space space : validBuildingSpaces) {
            if (space.getX() == x && space.getY() == y)
                selectedBuildingSpace = space;
        }
        buildBlock(selectedBuildingSpace);
        if (spaceAndDoubleBuilding[1] == 2) {
            selectedBuildingSpace.increaseTowerHeight();
        }
        broadcastInterface.broadcastBoard();
        return selectedBuildingSpace;
    }
}