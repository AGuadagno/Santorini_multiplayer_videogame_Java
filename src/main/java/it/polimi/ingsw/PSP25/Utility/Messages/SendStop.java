package it.polimi.ingsw.PSP25.Utility.Messages;

import it.polimi.ingsw.PSP25.Client.NetworkHandler;

import java.io.IOException;
import java.net.InetAddress;

public class SendStop extends Message {
    private InetAddress disconnectedAddress;

    public SendStop(InetAddress disconnectedAddress) {
        this.disconnectedAddress = disconnectedAddress;
    }

    @Override
    public void process(NetworkHandler nh) throws IOException {
        System.out.println("The player with address: " + disconnectedAddress + " disconnected. Game ends");
        nh.stop();
    }
}
