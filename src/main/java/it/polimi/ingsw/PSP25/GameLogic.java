package it.polimi.ingsw.PSP25;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameLogic {
    private ActiveEffects activeEffects;
    private Board board;
    private List<Player> playerList;
    private Player nowPlaying;

    public void playerInitialization() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("The game begins.");

        System.out.println("Choose the number of players (2 or 3): ");
        int numOfPlayers = scanner.nextInt();

        for (int i = 1; i <= numOfPlayers; i++) {
            System.out.println("Player " + i + " enter your name: ");
            String name = scanner.next();
            playerList.add(new Player(name));
        }
        activeEffects = new ActiveEffects(playerList.size());
        nowPlaying = playerList.get(0);

        List<GodPower> allGodPowers = getGodPowerList();

        System.out.println(playerList.get(0).getName() + " choose " + numOfPlayers + " god powers from the list: " + allGodPowers);
        List<GodPower> selectedGodPowers = new ArrayList<GodPower>();
        for (int i = 1; i <= numOfPlayers; i++) {
            selectedGodPowers.add(allGodPowers.get(scanner.nextInt()));
        }

        for (int i = 1; i < numOfPlayers; i++) {
            Player currPlayer = playerList.get((i+1)%numOfPlayers);
            System.out.println(currPlayer.getName() + " choose your god power from the list: " + selectedGodPowers);
            int selectedGodPowerIndex = scanner.nextInt();
            currPlayer.initializeGodPower(selectedGodPowers.get(selectedGodPowerIndex));
            selectedGodPowers.remove(selectedGodPowerIndex);
        }
        System.out.println(playerList.get(0).getName() + " you got: " + selectedGodPowers);
        playerList.get(0).initializeGodPower(selectedGodPowers.get(0));
    }

    private List<GodPower> getGodPowerList() {
        List<GodPower> godPowers = new ArrayList<GodPower>();
        godPowers.add(new Apollo());
        godPowers.add(new Artemis());
        godPowers.add(new Athena());
        godPowers.add(new Atlas());
        godPowers.add(new Demeter());
        godPowers.add(new Hephaestus());
        godPowers.add(new Minotaur());
        godPowers.add(new Pan());
        return godPowers;
    }

    public GameLogic(){
        Board board = new Board();
        board.getSpace(0, 0).setBoard(board);
        playerList = new ArrayList<Player>();
    }

    public void boardSetup() {
        Scanner scanner = new Scanner(System.in);
        int x1, y1, x2, y2, pos1, pos2;

        board.print();

        for(int i = 0; i < playerList.size(); i++){
            Player currPlayer = playerList.get(i);
            System.out.println(currPlayer.getName() + " it's your turn! Choose the position of your first worker");
            pos1 = scanner.nextInt();
            x1 = pos1%5;
            y1 = pos1/5;
            System.out.println("And now choose the position of your second worker");
            pos2 = scanner.nextInt();
            x2 = pos2%5;
            y2 = pos2/5;
            currPlayer.initializeWorkers(board.getSpace(x1, y1), board.getSpace(x2, y2));
            System.out.println();
            board.print();
        }
    }

    public boolean gameLoop() {
        boolean endGame = false;

        endGame = nowPlaying.getGodPower().turnSequence(nowPlaying);
        System.out.println();
        board.print();
        System.out.println();
        nowPlaying = playerList.get((playerList.indexOf(nowPlaying)+1)%playerList.size());
        return endGame;
    }
}
