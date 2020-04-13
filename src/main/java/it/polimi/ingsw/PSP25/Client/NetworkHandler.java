package it.polimi.ingsw.PSP25.Client;

import it.polimi.ingsw.PSP25.Utility.Messages.Message;

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

    private boolean isWaiting = false;

    public NetworkHandler(Socket server) {
        this.server = server;
    }

    public void addObserver(ServerObserver observer) {
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


    public synchronized void receiveCommand() {
        while (isWaiting == false) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        nextCommand = Commands.RECEIVE;
        notifyAll();
    }

    public synchronized void stop()
    {
        nextCommand = Commands.STOP;
        notifyAll();
    }

    private synchronized void handleServerConnection() throws IOException, ClassNotFoundException {
        /* wait for commands */
        while (true) {

            nextCommand = null;

            try {
                isWaiting = true;
                notifyAll();
                wait();
                isWaiting = false;

            } catch (InterruptedException e) {
            }

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
        receivedMessage = (Message) inputStream.readObject();
        for (ServerObserver observer : observers) {
            observer.didReceiveServerMessage(receivedMessage);
        }
    }

    public synchronized void submit(Object response) throws IOException {
        outputStream.writeObject(response);
    }
}
