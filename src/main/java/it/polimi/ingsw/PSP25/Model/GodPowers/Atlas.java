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
     * @param activeEffects      list of opponent GodPower effects active in the current turn that could limit movement,
     *                           building action or winning conditions of workers
     * @param broadcastInterface used to send the modified board to all the players
     *
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
     * @throws DisconnectionException
     */
    @Override
    public Space askToBuild(Player player, List<Space> validBuildingSpaces) throws DisconnectionException {
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