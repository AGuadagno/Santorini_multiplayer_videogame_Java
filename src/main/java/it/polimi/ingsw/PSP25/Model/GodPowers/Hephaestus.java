package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.ActiveEffects;
import it.polimi.ingsw.PSP25.Model.BroadcastInterface;
import it.polimi.ingsw.PSP25.Model.Player;
import it.polimi.ingsw.PSP25.Model.Space;

import java.io.IOException;
import java.util.List;

import static it.polimi.ingsw.PSP25.Utility.Utilities.deepCopySpaceList;

/**
 * Hephaestus class
 */
public class Hephaestus extends GodPower {

    /**
     * Hephaestus constructor
     *
     * @param activeEffects list of opponent GodPower effects active in our turn that could limit movement,
     *                      building action or winning conditions of our player
     */
    public Hephaestus(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

    @Override
    public Space askToBuild(Player player, List<Space> validBuildingSpaces) throws IOException {
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