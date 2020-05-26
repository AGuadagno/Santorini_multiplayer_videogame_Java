package it.polimi.ingsw.PSP25.Client;

import it.polimi.ingsw.PSP25.Utility.Messages.Message;
import it.polimi.ingsw.PSP25.Server.Server;
import it.polimi.ingsw.PSP25.Utility.SpaceCopy;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Client implements Runnable, ServerObserver, ViewObserver {

    private Message receivedMessage = null;
    private boolean cliIsChosen;
    private ViewObservable view;
    private Scanner scanner;
    private final Object Lock = "";
    private String ip = null;
    private Integer numOfPlayers = null;
    private String name = null;
    private List<Integer> allGodPowerIndexes = null;
    private Integer godPowerIndex = null;
    private Integer workerPosition = null;
    private int[] workerAndSpace = null;
    private Integer chosenBuildingSpace = null;
    private int[] selectedSpaceAndDome = null;
    private int[] workerAndBuildBeforeMove = null;
    private Integer chosenMovementSpace = null;
    private Integer artemisSecondMoveSpace = null;
    private int[] spaceAndDoubleBuildingHephaestus = null;

    public Client(ViewObservable view, boolean cliIsChosen) {
        this.view = view;
        this.cliIsChosen = cliIsChosen;
    }

    @Override
    public void run() {
        scanner = new Scanner(System.in);
        view.askIPAddress();

        synchronized (Lock) {
            while (ip == null) {
                try {
                    Lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        //---
        Socket server;
        try {
            server = new Socket(ip, Server.SOCKET_PORT);
        } catch (IOException e) {
            view.setConnectionMessage("Server unreachable");
            //System.out.println("server unreachable");
            return;
        }
        view.setConnectionMessage("Connected to the server");
        //System.out.println("Connected");

        // CREATION OF NETWORK HANDLER
        NetworkHandler networkHandler = new NetworkHandler(server);
        networkHandler.addObserver(this);
        Thread networkHandlerThread = new Thread(networkHandler);
        networkHandlerThread.start();

        // RECEIVING OF MESSAGES FROM SERVER
        do {
            synchronized (this) {

                networkHandler.receiveCommand();
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (receivedMessage != null) {
                    try {
                        receivedMessage.process(networkHandler, this);
                    } catch (IOException e) {
                        //manageServerDisconnection();
                    }
                }
            }
        } while (receivedMessage != null);

        if (cliIsChosen) {
            System.out.println("\nDo you want to play again? (y = yes, n = no)");
            String answer = scanner.next();
            while (!(answer.equals("y") || answer.equals("n"))) {
                System.out.println("Your Choice is not valid. insert 'y' to play again, 'n' to close");
                answer = scanner.next();
            }

            if (answer.equals("y"))
                run();
        }
    }

    @Override
    public void playAgain(boolean b) {
        if (b) {
            Thread clientThread = new Thread(this);
            clientThread.start();
        }
    }

    @Override
    public synchronized void didReceiveServerMessage(Message message) {
        this.receivedMessage = message;
        notifyAll();
    }

    @Override
    public void manageServerDisconnection() {
        view.manageServerDisconnection();
    }

    @Override
    public void updateIPAddress(String ip) {
        this.ip = ip;
        synchronized (Lock) {
            Lock.notifyAll();
        }
    }

    public int askNumOfPlayers(String question) {
        view.askNumOfPlayers(question);
        while (numOfPlayers == null) {
            try {
                synchronized (Lock) {
                    Lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int numPl = this.numOfPlayers;
        this.numOfPlayers = null;
        return numPl;
    }

    @Override
    public void updateNumOfPlayers(int numOfPlayers) {
        if (this.numOfPlayers == null) {
            this.numOfPlayers = numOfPlayers;
            synchronized (Lock) {
                Lock.notifyAll();
            }
        }
    }

    public String askName(String question) {
        view.askName(question);
        while (name == null) {
            try {
                synchronized (Lock) {
                    Lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String name = this.name;
        this.name = null;
        return name;
    }

    @Override
    public void updateName(String name) {
        if (this.name == null) {
            this.name = name;
            synchronized (Lock) {
                Lock.notifyAll();
            }
        }
    }

    public List<Integer> askAllGodPowers(String playerName, int numOfPlayers, List<String> godPowerNames) {
        view.askAllGodPowers(playerName, numOfPlayers, godPowerNames);

        while (allGodPowerIndexes == null) {
            try {
                synchronized (Lock) {
                    Lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<Integer> allGodPowerIndexes = new ArrayList<>();
        allGodPowerIndexes.addAll(this.allGodPowerIndexes);
        this.allGodPowerIndexes = null;
        return allGodPowerIndexes;
    }

    @Override
    public void updateAllGodPower(List<Integer> selectedIndexes) {
        if (this.allGodPowerIndexes == null) {
            this.allGodPowerIndexes = selectedIndexes;
            synchronized (Lock) {
                Lock.notifyAll();
            }
        }
    }

    public int askGodPower(String playerName, List<String> godPowerNames) {
        view.askGodPower(playerName, godPowerNames);

        while (godPowerIndex == null) {
            try {
                synchronized (Lock) {
                    Lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Integer godPowerIndex = this.godPowerIndex;
        this.godPowerIndex = null;
        return godPowerIndex;
    }


    @Override
    public void updateGodPower(int selectedIndex) {
        if (this.godPowerIndex == null) {
            this.godPowerIndex = selectedIndex;
            synchronized (Lock) {
                Lock.notifyAll();
            }
        }
    }

    public void tellAssignedGodPower(String playerName, List<String> godPowerName) {
        view.tellAssignedGodPower(playerName, godPowerName);
    }

    public void showPlayersGodPowers(List<String> playerNames, List<String> godPowerNames) {
        view.showPlayersGodPowers(playerNames, godPowerNames);
    }

    public void showBoard(SpaceCopy[][] board) {
        view.showBoard(board);
    }

    public int askWorkerPosition(String playerName, int workerNumber, int previousPos, SpaceCopy[][] board) {
        view.askWorkerPosition(playerName, workerNumber, previousPos, board);
        while (workerPosition == null) {
            try {
                synchronized (Lock) {
                    Lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int workerPosition = this.workerPosition;
        this.workerPosition = null;
        return workerPosition;
    }

    @Override
    public void updateWorkerPosition(int pos) {
        if (this.workerPosition == null) {
            this.workerPosition = pos;
            synchronized (Lock) {
                Lock.notifyAll();
            }
        }
    }


    public int[] askWorkerMovement(String playerName, List<SpaceCopy> validMovementSpacesW1, List<SpaceCopy> validMovementSpacesW2) {
        view.askWorkerMovement(playerName, validMovementSpacesW1, validMovementSpacesW2);
        while (workerAndSpace == null) {
            try {
                synchronized (Lock) {
                    Lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int[] workerAndSpace = this.workerAndSpace;
        this.workerAndSpace = null;
        return workerAndSpace;
    }

    @Override
    public void updateWorkerMovement(int[] workerAndSpace) {
        if (this.workerAndSpace == null) {
            this.workerAndSpace = workerAndSpace;
            synchronized (Lock) {
                Lock.notifyAll();
            }
        }
    }


    public int askBuildingSpace(String playerName, List<SpaceCopy> validBuildingSpaces) {
        view.askBuildingSpace(playerName, validBuildingSpaces);
        while (chosenBuildingSpace == null) {
            try {
                synchronized (Lock) {
                    Lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int chosenBuildingSpace = this.chosenBuildingSpace;
        this.chosenBuildingSpace = null;
        return chosenBuildingSpace;
    }


    @Override
    public void updateBuildingSpace(int chosenBuildingSpace) {
        if (this.chosenBuildingSpace == null) {
            this.chosenBuildingSpace = chosenBuildingSpace;
            synchronized (Lock) {
                Lock.notifyAll();
            }
        }
    }

    @Override
    public void updateAtlasBuild(int[] selectedSpaceAndBuildDome) {
        if (this.selectedSpaceAndDome == null) {
            this.selectedSpaceAndDome = selectedSpaceAndBuildDome;
            synchronized (Lock) {
                Lock.notifyAll();
            }
        }
    }


    public int[] askAtlasBuild(String playerName, List<SpaceCopy> validBuildingSpaces) {
        view.askAtlasBuild(playerName, validBuildingSpaces);
        while (selectedSpaceAndDome == null) {
            try {
                synchronized (Lock) {
                    Lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int[] selectedSpaceAndDome = this.selectedSpaceAndDome;
        this.selectedSpaceAndDome = null;
        return selectedSpaceAndDome;
    }

    public int[] askBuildBeforeMovePrometheus(String playerName, boolean w1CanMove, boolean w1CanBuild, boolean w2CanMove, boolean w2CanBuild) {
        view.askBuildBeforeMovePrometheus(playerName, w1CanMove, w1CanBuild, w2CanMove, w2CanBuild);
        while (workerAndBuildBeforeMove == null) {
            try {
                synchronized (Lock) {
                    Lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int[] workerAndBuildBeforeMove = this.workerAndBuildBeforeMove;
        this.workerAndBuildBeforeMove = null;
        return workerAndBuildBeforeMove;
    }

    @Override
    public void updateBuildBeforeMovePrometheus(int[] workerAndBuildBeforeMove) {
        if (this.workerAndBuildBeforeMove == null) {
            this.workerAndBuildBeforeMove = workerAndBuildBeforeMove;
            synchronized (Lock) {
                Lock.notifyAll();
            }
        }
    }


    public int askWorkerMovementPrometheus(String playerName, List<SpaceCopy> validMovementSpaces) {
        view.askWorkerMovementPrometheus(playerName, validMovementSpaces);
        while (chosenMovementSpace == null) {
            try {
                synchronized (Lock) {
                    Lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int chosenMovementSpace = this.chosenMovementSpace;
        this.chosenMovementSpace = null;
        return chosenMovementSpace;

    }

    @Override
    public void updateWorkerMovementPrometheus(int chosenMovementSpace) {
        if (this.chosenMovementSpace == null) {
            this.chosenMovementSpace = chosenMovementSpace;
            synchronized (Lock) {
                Lock.notifyAll();
            }
        }
    }

    public int askArtemisSecondMove(String playerName, List<SpaceCopy> validSecondMovementSpaces) {
        view.askArtemisSecondMove(playerName, validSecondMovementSpaces);
        while (artemisSecondMoveSpace == null) {
            try {
                synchronized (Lock) {
                    Lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int artemisSecondMoveSpace = this.artemisSecondMoveSpace;
        this.artemisSecondMoveSpace = null;
        return artemisSecondMoveSpace;
    }

    @Override
    public void updateArtemisSecondMove(int artemisSecondMoveSpace) {
        if (this.artemisSecondMoveSpace == null) {
            this.artemisSecondMoveSpace = artemisSecondMoveSpace;
            synchronized (Lock) {
                Lock.notifyAll();
            }
        }
    }

    public int askDemeterSecondBuilding(String playerName, List<SpaceCopy> validBuildingSpaces) {
        view.askDemeterSecondBuilding(playerName, validBuildingSpaces);

        while (chosenBuildingSpace == null) {
            try {
                synchronized (Lock) {
                    Lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int chosenBuildingSpace = this.chosenBuildingSpace;
        this.chosenBuildingSpace = null;
        return chosenBuildingSpace;
    }

    public int[] askHephaestusBuild(String playerName, List<SpaceCopy> validBuildingSpaces) {
        view.askHephaestusBuild(playerName, validBuildingSpaces);

        while (spaceAndDoubleBuildingHephaestus == null) {
            try {
                synchronized (Lock) {
                    Lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int[] spaceAndDoubleBuildingHephaestus = this.spaceAndDoubleBuildingHephaestus;
        this.spaceAndDoubleBuildingHephaestus = null;
        return spaceAndDoubleBuildingHephaestus;
    }

    @Override
    public void updateHephaestusBuild(int[] spaceAndDoubleBuildingHephaestus) {
        if (this.spaceAndDoubleBuildingHephaestus == null) {
            this.spaceAndDoubleBuildingHephaestus = spaceAndDoubleBuildingHephaestus;
            synchronized (Lock) {
                Lock.notifyAll();
            }
        }
    }

    public void announceVictory(String playerName) {
        view.announceVictory(playerName);
    }

    public void announceLose(String playerName) {
        view.announceLose(playerName);
    }

    public int askRemoveBlockAres(String playerName, List<SpaceCopy> validRemoveSpaces, int nonSelectedWorkerNumber) {
        view.askRemoveBlockAres(playerName, validRemoveSpaces, nonSelectedWorkerNumber);

        while (chosenBuildingSpace == null) {
            try {
                synchronized (Lock) {
                    Lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int selectedRemoveSpace = this.chosenBuildingSpace;
        this.chosenBuildingSpace = null;
        return selectedRemoveSpace;
    }
}
