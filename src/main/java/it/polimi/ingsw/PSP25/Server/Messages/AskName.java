package it.polimi.ingsw.PSP25.Server.Messages;

import it.polimi.ingsw.PSP25.Client.NetworkHandler;
import java.io.IOException;

public class AskName extends Message{

    public String question = "Select your name: ";

    public void process(NetworkHandler nh) throws IOException {
        System.out.println(question);
        nh.scanAndSubmit();
    }

}

