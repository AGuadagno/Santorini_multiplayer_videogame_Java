package it.polimi.ingsw.PSP25.Server.Messages;

import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import it.polimi.ingsw.PSP25.GodPower;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AskAllGodPowers extends Message {
    private String playerName;
    private int numOfPlayers;
    private List<String> godPowerNames;

    public AskAllGodPowers(String playerName, int numOfPlayers, List<String> godPowerNames) {
        this.playerName = playerName;
        this.numOfPlayers = numOfPlayers;
        this.godPowerNames = godPowerNames;
    }

    public void process(NetworkHandler nh) throws IOException {
        Scanner scanner = new Scanner(System.in);

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

        nh.submit(selectedIndexes);
    }
}
