package it.polimi.ingsw.PSP25;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * God Power class. This class represents the class "Strategy" in a Strategy Pattern in which specific Gods (Athena, Apollo, Artemis etc)
 * are the Concrete Strategies and Player is the Context.
 * Workers can move, build, check the victory by using the methods in GodPower
 * (or Overridden methods in the extensions of God Power)
 */
public class GodPower {

    /**
     * Description of Attributes:
     * activeEffects: this array can contain GodPower Objects or objects belonging to GodPower sub-classes.
     * activeEffects it's use to memorize GodPower effects that influence the opponent players' actions during their turn.
     * For Example, if one of the workers of the player who has Athena as God Power moves up, we put an Object "Athena"
     * in activeEffects to prevent Workers controlled by opponent players from moving up during their turn.
     */
    protected ActiveEffects activeEffects;

    /**
     * God Power Constructor
     * @param activeEffects list of opponent GodPower effect active in our turn that could limit movement,
     *                      building action or winning conditions of our player
     */
    public GodPower(ActiveEffects activeEffects) {
        this.activeEffects = activeEffects;
    }

    /**
     * It's overridden in subclasses to specify special movement behaviours
     *
     * @param worker the Worker we want to know Spaces to which he can move
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
     * @param worker the opponent's Worker that we want to know if he can move to Space
     * @param space  Space where we want to know if the worker can move
     * @return true if the movement is valid according to the GodPower's effect
     */
    public boolean canMove(Worker worker, Space space) {
        return true;
    }

    /**
     * It's overridden in subclasses to specify special building behaviours
     * @param worker we want to know Spaces in which he can build
     * @return List of possible Spaces where the Worker passed as argument can build
     */
    public List<Space> getValidBuildSpaces(Worker worker) {
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
     * @param worker worker we want to know if he can build in Space
     * @param space Space where we want to know if the Worker can build
     * @return true if the building action is valid.
     */
    public boolean canBuild(Worker worker, Space space) {
        return true;
    }

    /**
     * canWin it's overridden in subclasses of GodPower to specify gods effect that are active during opponents turn
     * @param worker worker we want to know if he can win
     * @param space Space where the Worker as been moved
     * @return true if the winning condition is valid
     */
    public boolean canWin(Worker worker, Space space) {
        return true;
    }

    /**
     * Moves worker to space without restrictions
     *
     * @param worker Worker that the player wants to move
     * @param space  Space where the player wants to move the Worker
     */
    protected void moveWorker(Worker worker, Space space) {
        worker.moveTo(space);
    }

    /**
     * Builds a Block in Space without restrictions increasing towerHeight or building a dome if the space has towerHeight = 3
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
     * Verifies if the movement already done by the worker has caused the player to win
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
     * @param spacesW1 List of possible spaces where worker 1 can move
     * @param spacesW2 List of possible spaces where worker 2 can move
     * @return true if both worker 1 and worker 2 can't move and the player who controls the two workers has lost
     */

    protected boolean verifyLoseByMovement(List<Space> spacesW1, List<Space> spacesW2){
        if(spacesW1.size()==0 && spacesW2.size()==0) {
            return true;
        }
        else
            return false;
    }

    /**
     * @param buildingSpaces list of possible spaces where the moved worker can build
     * @return true if the worker can't build and the player who controls him has lost
     */
    protected boolean verifyLoseByBuilding(List<Space> buildingSpaces){
        if(buildingSpaces.size()==0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * turnSequence manages the sequence of actions in a turn.
     * The player is asked to
     * 1) Choose a worker
     * 2) Move the worker in a valid space
     * 3) Build a block in a valid space
     * turnSequence also verify if a player has won/has lost
     * specific godPower overrides this method to change the sequence of actions in a turn
     * (for example, in order to move twice or build twice, to build before the first move etc.)
     * @param player playing the round
     * @param activeEffects array containing opponent god power effects that may influence this turn
     * @return TurnResult.LOSE if the player has lost during this turn
     *         TurnResult.WIN if the player has won during this turn
     *         TurnResult.CONTINUE if the player hasn't lost or won during this turn
     */
    public TurnResult turnSequence(Player player, ActiveEffects activeEffects) {
        List<Space> validMovementSpacesW1;
        List<Space> validMovementSpacesW2;
        List<Space> validBuildSpaces;
        int workerchoice;
        Worker selectedWorker;
        Space selectedMovementSpace = null;
        Space selectedBuildingSpace = null;
        // Attributi temporanei
        Scanner scanner = new Scanner(System.in);

        validMovementSpacesW1 = getValidMovementSpaces(player.getWorker1());
        validMovementSpacesW2 = getValidMovementSpaces(player.getWorker2());

        if (verifyLoseByMovement(validMovementSpacesW1, validMovementSpacesW2)) {
            return TurnResult.LOSE;
        }

        // TEMP
        if (validMovementSpacesW1.size() == 0) {
            System.out.println("Worker 1 can't move! Worker 2 is automatically selected");
            workerchoice = 2;
        } else if (validMovementSpacesW2.size() == 0) {
            System.out.println("Worker 2 can't move! Worker 1 is automatically selected");
            workerchoice = 1;
        } else {
            System.out.println(player.getName() + "(" + player.getID() + ")" + ": Choose a worker");
            workerchoice = scanner.nextInt();
            while (workerchoice < 1 || workerchoice > 2) {
                System.out.println("Worker number is not valid. Choose 1 or 2");
                workerchoice = scanner.nextInt();
            }
        }
        if (workerchoice == 1) {
            selectedWorker = player.getWorker1();
            System.out.println(validMovementSpacesW1.toString());
            System.out.println(player.getName() + "(" + player.getID() + ")" + ": Choose movement space");
            int chosenMovementSpace = scanner.nextInt();
            while (!(validMovementSpacesW1.stream().map(Space::getNumber).collect(Collectors.toList())).
                    contains(chosenMovementSpace)) {
                System.out.println(chosenMovementSpace + " is not in the valid movement spaces list");
                chosenMovementSpace = scanner.nextInt();
            }
            int x = chosenMovementSpace % 5;
            int y = chosenMovementSpace / 5;
            for (Space space : validMovementSpacesW1) {
                if (space.getX() == x && space.getY() == y)
                    selectedMovementSpace = space;
            }
        } else {
            selectedWorker = player.getWorker2();
            System.out.println(validMovementSpacesW2.toString());
            System.out.println(player.getName() + "(" + player.getID() + ")" + ": Choose movement space");
            int chosenmovementspace = scanner.nextInt();
            while (!(validMovementSpacesW2.stream().map(Space::getNumber).collect(Collectors.toList())).
                    contains(chosenmovementspace)) {
                System.out.println(chosenmovementspace + " is not in the valid movement spaces list");
                chosenmovementspace = scanner.nextInt();
            }
            int x = chosenmovementspace % 5;
            int y = chosenmovementspace / 5;
            for (Space space : validMovementSpacesW2) {
                if (space.getX() == x && space.getY() == y)
                    selectedMovementSpace = space;
            }
        }
        // END TEMP

        moveWorker(selectedWorker, selectedMovementSpace);

        if (activeEffects.canWin(selectedWorker, selectedMovementSpace) && verifyWin(selectedWorker) == true) {
            return TurnResult.WIN;
        }

        validBuildSpaces = getValidBuildSpaces(selectedWorker);
        // Player builds in a valid space
        // TEMP
        if(verifyLoseByBuilding(validBuildSpaces)){
            return TurnResult.LOSE;
        }
        System.out.println(validBuildSpaces.toString());
        System.out.println(player.getName() + "(" + player.getID() + ")" + ": Choose building space");
        int chosenBuildingSpace = scanner.nextInt();
        while (!(validBuildSpaces.stream().map(Space::getNumber).collect(Collectors.toList())).
                contains(chosenBuildingSpace)) {
            System.out.println(chosenBuildingSpace + " is not in the valid building spaces list");
            chosenBuildingSpace = scanner.nextInt();
        }
        int x = chosenBuildingSpace % 5;
        int y = chosenBuildingSpace / 5;
        for (Space space : validBuildSpaces) {
            if (space.getX() == x && space.getY() == y)
                selectedBuildingSpace = space;
        }
        // END TEMP
        buildBlock(selectedBuildingSpace);

        addActiveEffects(activeEffects, player.getWorker1(), player.getWorker2(), selectedWorker);

        return TurnResult.CONTINUE;
    }

    protected void addActiveEffects(ActiveEffects activeEffects, Worker worker1, Worker worker2, Worker selectedWorker) {
        activeEffects.pushEffect(this);
    }

    /**
     * @return String containing the GodPower name
     * (for example toString can return "Athena", "Artemis", "Apollo" etc. in subclasses)
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}