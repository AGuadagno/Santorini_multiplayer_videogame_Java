package it.polimi.ingsw.PSP25.Client;

import java.util.Scanner;

public class CLI extends View {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public String askIPAddress() {
        System.out.println("IP address of server?");
        String ip = scanner.nextLine();
        return ip;
    }
}
