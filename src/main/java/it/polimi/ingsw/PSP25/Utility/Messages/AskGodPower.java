package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.NetworkHandler;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AskGodPower extends Message {
    private String playerName;
    private List<String> godPowerNames;

    public AskGodPower(String playerName, List<String> godPowerNames) {
        this.playerName = playerName;
        this.godPowerNames = godPowerNames;
    }

    public void process(NetworkHandler nh) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print(playerName + " choose your god power from the list: [");
        System.out.print("1 - " + godPowerNames.get(0));
        for (int i = 1; i < godPowerNames.size(); i++) {
            System.out.print(", " + (i + 1) + " - " + godPowerNames.get(i));
        }
        System.out.println("]");

        // TO DO: verificare eccezione nel caso in cui inserisca una stringa
        int selectedIndex = scanner.nextInt();
        while (selectedIndex - 1 >= godPowerNames.size() || selectedIndex - 1 < 0) {
            System.out.println("God power index is not valid. Choose an index between 1 and " +
                    godPowerNames.size());
            selectedIndex = scanner.nextInt();
        }

        nh.submit(selectedIndex);
    }
}
