package it.polimi.ingsw.PSP25.Client;

import it.polimi.ingsw.PSP25.Server.Messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NetworkHandler implements Runnable{

    private enum Commands {
        RECEIVE,
        STOP
    }

    private Socket server;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Commands nextCommand;
    Scanner scanner = new Scanner(System.in);
    private List<ServerObserver> observers = new ArrayList<>();

    public NetworkHandler(Socket server)
    {
        this.server = server;
    }

    public void addObserver(ServerObserver observer)
    {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(ServerObserver observer)
    {
        synchronized (observers) {
            observers.remove(observer);
        }
    }


    @Override
    public void run() {
        try {
            outputStream = new ObjectOutputStream(server.getOutputStream());
            inputStream = new ObjectInputStream(server.getInputStream());
            //DEBUG
            System.out.println("Sto per chiamare handleServerConnection! :D");
            handleServerConnection();
        } catch (IOException e) {
            System.out.println("server has died");
        } catch (ClassCastException | ClassNotFoundException e) {
            System.out.println("protocol violation");
        }

        try {
            server.close();
        } catch (IOException e) { }
    }


    public synchronized void receiveCommand()
    {
        nextCommand = Commands.RECEIVE;
        notifyAll();
    }

    public synchronized void stop()
    {
        nextCommand = Commands.STOP;
        notifyAll();
    }

    private void handleServerConnection() throws IOException, ClassNotFoundException
    {
        /* wait for commands */
        while (true) {
            //DEBUG
            System.out.println("Sono dentro handleServerConnection, ma prima della wait! :D");
            nextCommand = null;

            while(nextCommand==null){
                System.out.println("DEMO");
            }

            //DEBUG
            System.out.println("Sono dentro handleServerConnection, dopo la wait! :D");

            if (nextCommand == null)
                continue;


            switch (nextCommand) {
                case RECEIVE:
                    receive();
                    break;
                case STOP:
                    return;
            }
        }
    }

    private synchronized void receive() throws IOException, ClassNotFoundException {
        Message receivedMessage;
        //DEBUG
        System.out.println("Sono dentro la receive per ricevere il messaggio! :D");
        receivedMessage = (Message)inputStream.readObject();
        for (ServerObserver observer: observers) {
            observer.didReceiveServerMessage(receivedMessage);
        }
    }

    public synchronized void scanAndSubmit() throws IOException {
        String response = scanner.nextLine();
        outputStream.writeObject(response);
    }
}
