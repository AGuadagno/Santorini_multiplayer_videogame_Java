package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.ActiveEffects;
import it.polimi.ingsw.PSP25.Model.BroadcastInterface;
import it.polimi.ingsw.PSP25.Model.GodPowers.GodPower;
import it.polimi.ingsw.PSP25.Player;
import it.polimi.ingsw.PSP25.Space;

import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.PSP25.Utility.Utilities.deepCopySpaceList;

/**
 * Atlas class
 */
public class Atlas extends GodPower {

    /**
     * Atlas constructor
     *
     * @param activeEffects list of opponent GodPower effects active in our turn that could limit movement,
     *                      building action or winning conditions of our player
     */
    public Atlas(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

    /**
     * Override of "buildBlock" according to Atlas' effect:
     * "Your Worker may build a dome at any level."
     * We ask to the player if he want to build a block or a dome. If he chooses 'b',
     * we build a block, if the chooses 'd', we build a dome
     *
     * @param space Space where the player wants to build a Block or a Dome
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

    @Override
    public Space askToBuild(Player player, List<Space> validBuildingSpaces) {
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