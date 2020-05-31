package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.*;
import it.polimi.ingsw.PSP25.Server.DisconnectionException;
import java.util.ArrayList;
import java.util.List;
import static it.polimi.ingsw.PSP25.Utility.Utilities.deepCopySpaceList;

/**
 * God Power class.
 * This class represents the "Strategy" class in a Strategy Pattern in which specific Gods
 * (Athena, Apollo, Artemis etc) are the Concrete Strategies and Player is the Context.
 * Workers can move, build, check the victory by using methods in GodPower
 * (or Overridden methods in the extensions of God Power)
 */
public class GodPower {

    protected ActiveEffects activeEffects;
    protected BroadcastInterface broadcastInterface;
    protected Worker selectedWorker = null;

    /**
     * God Power constructor
     *
     * @param activeEffects array containing opponents god power effects that may influence this turn
     */
    public GodPower(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        this.activeEffects = activeEffects;
        this.broadcastInterface = broadcastInterface;
    }

    /**
     * getValidMovementSpaces it's overridden in subclasses of GodPower to specify special movement behaviours
     *
     * @param worker Worker we want to know Spaces to which he can move
     * @return List of possible Spaces where the Worker passed as argument can move to
     */
    protected List<Space> getValidMovementSpaces(Worker worker) {
        ArrayList<Space> validMovementSpaces = new ArrayList<Space>();
        for (Space space : worker.getSpace().getAdjacentSpaces()) {
            if (space.getWorker() == null && (space.getTowerHeight() - worker.getSpace().getTowerHeight() <= 1)
                    && !space.hasDome() && activeEffects.canMove(worker, space)) {
                validMovementSpaces.add(space);
            }
        }
        return validMovementSpaces;
    }

    /**
     * canMove is overridden in subclasses of GodPower to specify god effects that are active during opponents' turns
     *
     * @param worker the opponent's Worker that we want to know if he can move to 'space'
     * @param space  Space where we want to know if the 'worker' can move
     * @return true if the movement is valid according to the GodPower's effect
     */
    public boolean canMove(Worker worker, Space space) {
        return true;
    }

    /**
     * getValidBuildSpaces is overridden in subclasses of GodPower to specify special building behaviours
     *
     * @param worker Worker we want to know Spaces in which he can build
     * @return List of possible Spaces where the Worker passed as argument can build
     */
    protected List<Space> getValidBuildSpaces(Worker worker) {
        ArrayList<Space> validBuildSpaces = new ArrayList<Space>();
        for (Space space : worker.getSpace().getAdjacentSpaces()) {
            if (space.getWorker() == null && !space.hasDome() && space.getTowerHeight() <= 3
                    && activeEffects.canBuild(worker, space)) {
                validBuildSpaces.add(space);
            }
        }
        return validBuildSpaces;
    }

    /**
     * canBuild it's overridden in subclasses of GodPower to specify gods effect that are active during opponents turn
     *
     * @param worker Worker we want to know if he can build in 'space'
     * @param space  Space where we want to know if the Worker can build
     * @return true if the building action is valid.
     */
    public boolean canBuild(Worker worker, Space space) {
        return true;
    }

    /**
     * canWin it's overridden in subclasses of GodPower to specify gods effect that are active during opponents turn
     *
     * @param worker Worker we want to know if he can win
     * @param space  Space where the Worker as been moved
     * @return true if the winning condition is valid
     */
    public boolean canWin(Worker worker, Space space) {
        return true;
    }

    /**
     * Moves 'worker' to 'space' without restrictions (controls are carried out elsewhere)
     *
     * @param worker Worker that the player wants to move
     * @param space  Space where the player wants to move the Worker
     */
    protected void moveWorker(Worker worker, Space space) {
        worker.moveTo(space);
    }

    /**
     * Builds a Block in 'space' without restrictions (controls are carried out elsewhere) increasing towerHeight or building a dome if the space has towerHeight = 3
     *
     * @param space Space where we the player wants to build a Block
     */
    protected void buildBlock(Space space) {
        if (space.getTowerHeight() == 3) {
            space.addDome();
        } else {
            space.increaseTowerHeight();
        }
    }

    /**
     * Verifies if the movement already done by 'worker' has caused the player to win
     *
     * @param worker Worker whose movement can make his player win
     * @return true if the Player who controls worker wins
     */
    protected boolean verifyWin(Worker worker) {
        if (worker.getHeightBeforeMove() == 2 && worker.getSpace().getTowerHeight() == 3) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if the Player cannot move any of his workers
     *
     * @param spacesW1 List of possible Spaces where Worker 1 can move
     * @param spacesW2 List of possible Spaces where Worker 2 can move
     * @return true if both Worker 1 and Worker 2 can't move and the Player who controls the two Workers has lost
     */
    protected boolean verifyLoseByMovement(List<Space> spacesW1, List<Space> spacesW2) {
        if (spacesW1.size() == 0 && spacesW2.size() == 0) {
            return true;
        } else
            return false;
    }

    /**
     * Returns true if the Player cannot build with his selected Worker
     *
     * @param buildingSpaces list of possible Spaces where the moved Worker can build
     * @return true if the Worker can't build and the Player who controls him has lost
     */
    protected boolean verifyLoseByBuilding(List<Space> buildingSpaces) {
        if (buildingSpaces.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * turnSequence manages the sequence of actions in a turn.
     * The Player is asked to
     * 1) Choose a worker
     * 2) Move the worker in a valid space
     * 3) Build a block in a valid space
     * turnSequence also verify if a player has won/has lost
     * specific godPower overrides this method to change the sequence of actions in a turn
     * (for example, in order to move twice or build twice, to build before the first move etc.)
     *
     * @param player        playing the round
     * @param activeEffects array containing opponents god power effects that may influence this turn
     * @return TurnResult.LOSE if the player has lost during this turn
     * TurnResult.WIN if the player has won during this turn
     * TurnResult.CONTINUE if the player hasn't lost or won during this turn
     */
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

        // If the player can move at least one of his workers, he's ask to move a worker and then win by movement is verified.
        if (askToMoveWorker(player, validMovementSpacesW1, validMovementSpacesW2) == true) {
            return TurnResult.WIN;
        }

        validBuildSpaces = getValidBuildSpaces(selectedWorker);
        // Verify if selected worker can build
        if (verifyLoseByBuilding(validBuildSpaces)) {
            return TurnResult.LOSE;
        }

        // If selected worker can build, the player his asked to chose a building space and then a block (or a dome) is built in the selected space.
        askToBuild(player, validBuildSpaces);

        addActiveEffects(activeEffects, player.getWorker1(), player.getWorker2(), selectedWorker);
        return TurnResult.CONTINUE;
    }

    /**
     * The Player is asked to select a Worker and to move the selected Worker in a valid movement Space.
     *
     * @param player                who is playing the turn
     * @param validMovementSpacesW1 List of valid Spaces where Worker 1 can move
     * @param validMovementSpacesW2 List of valid Spaces where Worker 2 can move
     * @return true if the Player has won the game
     * @throws DisconnectionException
     */
    protected boolean askToMoveWorker(Player player, List<Space> validMovementSpacesW1, List<Space> validMovementSpacesW2) throws DisconnectionException {
        Space selectedMovementSpace = null;
        String playerName = player.getName() + "(" + player.getID() + ")";

        int[] workerAndSpace = player.getClientHandler().askWorkerMovement(playerName, deepCopySpaceList(validMovementSpacesW1), deepCopySpaceList(validMovementSpacesW2));
        int x = workerAndSpace[1] % 5;
        int y = workerAndSpace[1] / 5;
        for (Space space : ((workerAndSpace[0] == 1) ? validMovementSpacesW1 : validMovementSpacesW2)) {
            if (space.getX() == x && space.getY() == y)
                selectedMovementSpace = space;
        }
        if (workerAndSpace[0] == 1) {
            selectedWorker = player.getWorker1();
        } else {
            selectedWorker = player.getWorker2();
        }
        moveWorker(selectedWorker, selectedMovementSpace);
        broadcastInterface.broadcastBoard();
        if (activeEffects.canWin(selectedWorker, selectedMovementSpace) && verifyWin(selectedWorker) == true) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * The Player is asked to chose the Space where the selected Worker has to build
     *
     * @param player              who is playing the turn
     * @param validBuildingSpaces List of valid Spaces where the selected Worker can build
     * @return the Spaces selected for the building action
     * @throws DisconnectionException
     */
    protected Space askToBuild(Player player, List<Space> validBuildingSpaces) throws DisconnectionException {
        Space selectedBuildingSpace = null;
        String playerName = player.getName() + "(" + player.getID() + ")";

        int selectedSpace = player.getClientHandler().askBuildingSpace(playerName, deepCopySpaceList(validBuildingSpaces));
        int x = selectedSpace % 5;
        int y = selectedSpace / 5;
        for (Space space : validBuildingSpaces) {
            if (space.getX() == x && space.getY() == y)
                selectedBuildingSpace = space;
        }
        buildBlock(selectedBuildingSpace);
        broadcastInterface.broadcastBoard();
        return selectedBuildingSpace;
    }

    /**
     * Add the god power effect of the current player to the list of god power effects that can influence opponents actions
     * during their turn. If the god power effect of the current player does not influence opponents turn,
     * a not relevant GodPower object is added to the list.
     *
     * @param activeEffects  array containing opponents god power effects that may influence this turn
     * @param worker1        worker 1 controlled by the current player
     * @param worker2        worker 2 controlled by the current player
     * @param selectedWorker the worker selected for the movement and the building action during this turn
     */
    protected void addActiveEffects(ActiveEffects activeEffects, Worker worker1, Worker worker2, Worker selectedWorker) {
        activeEffects.pushEffect(this);
    }

    /**
     * First positioning of workers in the board
     *
     * @param player  who controls is playing the turn
     * @param spaceW1 Space where the Player wants to position his first Worker
     * @param spaceW2 Space where the Player wants to position his second Worker
     */
    public void initializeWorkers(Player player, Space spaceW1, Space spaceW2) {
        player.initializeWorkers(spaceW1, spaceW2);
        addActiveEffects(activeEffects, null, null, null);
    }

    /**
     * @return the name of the GodPower
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
