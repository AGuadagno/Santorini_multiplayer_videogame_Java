package it.polimi.ingsw.PSP25.Client;

import it.polimi.ingsw.PSP25.Utility.Messages.Message;
import it.polimi.ingsw.PSP25.Server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client implements Runnable, ServerObserver, ViewObserver {

    private Message receivedMessage = null;
    private ViewObservable view;
    private Scanner scanner;
    private final Object Lock = "";
    private String ip = "";
    private Integer numOfPlayers = 0;
    private String name = "";
    private List<Integer> allGodPowerIndexes = null;
    private Integer godPowerIndex = null;

    public Client(ViewObservable view) {
        this.view = view;
    }

    @Override
    public void run() {
        scanner = new Scanner(System.in);
        view.askIPAddress();

        while (ip.equals("")) {
            try {
                synchronized (Lock) {
                    Lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //---
        Socket server;
        try {
            server = new Socket(ip, Server.SOCKET_PORT);
        } catch (IOException e) {
            view.setConnectionMessage("server unreachable");
            //System.out.println("server unreachable");
            return;
        }
        view.setConnectionMessage("Connected");
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
                        System.out.println("Disconnected from server");
                        //e.printStackTrace();
                    }
                }
            }
        } while (receivedMessage != null);

        System.out.println("\nDo you want to play again? (y = yes, n = no)");
        String answer = scanner.next();
        while (!(answer.equals("y") || answer.equals("n"))) {
            System.out.println("Your Choice is not valid. insert 'y' to play again, 'n' to close");
            answer = scanner.next();
        }

        if (answer.equals("y"))
            run();
    }

    @Override
    public synchronized void didReceiveServerMessage(Message message) {
        this.receivedMessage = message;
        notifyAll();
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
        while (numOfPlayers == 0) {
            try {
                synchronized (Lock) {
                    Lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return numOfPlayers;
    }

    @Override
    public void updateNumOfPlayers(int numOfPlayers) {
        if (this.numOfPlayers == 0) {
            this.numOfPlayers = numOfPlayers;
            synchronized (Lock) {
                Lock.notifyAll();
            }
        }
    }

    public String askName(String question) {
        view.askName(question);
        while (name.equals("")) {
            try {
                synchronized (Lock) {
                    Lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return name;
    }

    @Override
    public void updateName(String name) {
        if (this.name.equals("")) {
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

    public void showPlayersGodPowers(List<String> playerNames, List<String> godPowerNames) {
        view.showPlayersGodPowers(playerNames, godPowerNames);
    }
}
