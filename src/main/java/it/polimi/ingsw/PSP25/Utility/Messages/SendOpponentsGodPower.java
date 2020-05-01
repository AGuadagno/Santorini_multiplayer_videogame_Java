package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.NetworkHandler;

import java.io.IOException;
import java.util.List;

public class SendOpponentsGodPower extends Message {
    List<String> playerNames;
    List<String> godPowerNames;

    public SendOpponentsGodPower(List<String> playerNames, List<String> godPowerNames) {
        this.playerNames = playerNames;
        this.godPowerNames = godPowerNames;
    }

    @Override
    public void process(NetworkHandler nh, Client client) throws IOException {
        String s = "";
        for (int i = 0; i < playerNames.size(); i++) {
            s = s + playerNames.get(i) + " has " + godPowerNames.get(i) + "\n";
        }

        System.out.println(s);
    }
}
