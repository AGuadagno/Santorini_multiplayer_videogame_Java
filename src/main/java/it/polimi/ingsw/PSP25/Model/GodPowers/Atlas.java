package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.ActiveEffects;
import it.polimi.ingsw.PSP25.Model.BroadcastInterface;
import it.polimi.ingsw.PSP25.Model.Player;
import it.polimi.ingsw.PSP25.Model.Space;
import it.polimi.ingsw.PSP25.Server.DisconnectionException;
import java.util.List;
import static it.polimi.ingsw.PSP25.Utility.Utilities.deepCopySpaceList;

/**
 * Atlas class
 */
public class Atlas extends GodPower {

    /**
     * Atlas constructor
     *
     * @param activeEffects      array containing opponents god power effects that may influence this turn
     * @param broadcastInterface Interface used to share information with all the other players
     */
    public Atlas(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

    /**
     * Override of "buildBlock" according to Atlas' effect:
     * "Your Worker may build a dome at any level."
     *
     * @param space     Space where the player wants to build a Block or a Dome
     * @param buildDome if equals to 1, the player has chosen to build a Dome,
     *                  if equals to 0, the player has chosen to build a Block
     */
    private void buildBlock(Space space, int buildDome) {
        if (space.getTowerHeight() < 3) {
            if (buildDome == 0)
                space.increaseTowerHeight();
            else {
                space.addDome();
            }
        } else {
            space.addDome();
        }
    }


    /**
     * Sends a message to the player who has Atlas as GodPower in order to ask him where he wants to build
     * and if he wants to build a block or a dome.
     *
     * @param player              who has Atlas as GodPower
     * @param validBuildingSpaces List of Spaces where the selected worker can build
     * @return the selected building space
     * @throws DisconnectionException exception thrown in case of disconnection of a client or disconnection of the server
     */
    @Override
    protected Space askToBuild(Player player, List<Space> validBuildingSpaces) throws DisconnectionException {
        Space selectedBuildingSpace = null;
        String playerName = player.getName() + "(" + player.getID() + ")";
        int[] selectedSpaceAndBuildDome = player.getClientHandler().askAtlasBuild(playerName,
                deepCopySpaceList(validBuildingSpaces));
        int x = selectedSpaceAndBuildDome[0] % 5;
        int y = selectedSpaceAndBuildDome[0] / 5;
        for (Space space : validBuildingSpaces) {
            if (space.getX() == x && space.getY() == y)
                selectedBuildingSpace = space;
        }
        buildBlock(selectedBuildingSpace, selectedSpaceAndBuildDome[1]);
        broadcastInterface.broadcastBoard();
        return selectedBuildingSpace;
    }
}