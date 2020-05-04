package it.polimi.ingsw.PSP25.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI implements ViewObservable {
    private Scanner scanner = new Scanner(System.in);
    private ViewObserver client;

    @Override
    public void askIPAddress() {
        System.out.println("IP address of server?");
        String ip = scanner.nextLine();

        client.updateIPAddress(ip);
    }

    @Override
    public void setConnectionMessage(String s) {
        System.out.println(s);
    }

    @Override
    public void askNumOfPlayers(String question) {
        int numOfPlayers;
        do {
            System.out.println(question);
            numOfPlayers = scanner.nextInt();
        } while (numOfPlayers < 2 || numOfPlayers > 3);
        client.updateNumOfPlayers(numOfPlayers);
    }

    @Override
    public void askName(String question) {
        System.out.println(question);
        String name = scanner.next();
        while (name.length() < 2) {
            System.out.println("Your name it's too short. Enter another name (2 Characters or more): ");
            name = scanner.next();
        }
        client.updateName(name);
    }

    @Override
    public void askAllGodPowers(String playerName, int numOfPlayers, List<String> godPowerNames) {

        System.out.print(playerName + " choose " + numOfPlayers + " god powers from the list: [");
        System.out.print("1 - " + godPowerNames.get(0));
        for (int i = 1; i < godPowerNames.size(); i++) {
            System.out.print(", " + (i + 1) + " - " + godPowerNames.get(i));
        }
        System.out.println("]");
        List<Integer> selectedIndexes = new ArrayList<>(numOfPlayers);
        for (int i = 1; i <= numOfPlayers; i++) {
            int index = scanner.nextInt();
            while (index <= 0 || index > godPowerNames.size() || selectedIndexes.contains(index - 1)) {
                System.out.println("God power index is not valid. Choose an index between 1 and " +
                        godPowerNames.size() + " and different from other chosen indexes");
                index = scanner.nextInt();
            }
            selectedIndexes.add(index - 1);
        }

        String printedChoice = "You have chosen: ";
        for (int i = 0; i < selectedIndexes.size(); i++) {
            printedChoice += godPowerNames.get(selectedIndexes.get(i));
            if (i < selectedIndexes.size() - 1)
                printedChoice += ", ";
        }
        System.out.println(printedChoice);

        client.updateAllGodPower(selectedIndexes);
    }

    @Override
    public void askGodPower(String playerName, List<String> godPowerNames) {
        System.out.print(playerName + " choose your god power from the list: [");
        System.out.print("1 - " + godPowerNames.get(0));
        for (int i = 1; i < godPowerNames.size(); i++) {
            System.out.print(", " + (i + 1) + " - " + godPowerNames.get(i));
        }
        System.out.println("]");

        // TODO: verificare eccezione nel caso in cui inserisca una stringa
        int selectedIndex = scanner.nextInt();
        while (selectedIndex - 1 >= godPowerNames.size() || selectedIndex - 1 < 0) {
            System.out.println("God power index is not valid. Choose an index between 1 and " +
                    godPowerNames.size());
            selectedIndex = scanner.nextInt();
        }

        client.updateGodPower(selectedIndex);
    }

    @Override
    public void showPlayersGodPowers(List<String> playerNames, List<String> godPowerNames) {
        String s = "";
        for (int i = 0; i < playerNames.size(); i++) {
            s = s + playerNames.get(i) + " has " + godPowerNames.get(i) + "\n";
        }

        System.out.println(s);
    }

    @Override
    public void subscribe(ViewObserver client) {
        this.client = client;
    }

}
