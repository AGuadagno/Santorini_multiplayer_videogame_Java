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


    public synchronized void receiveCommand() {
        while (isWaiting == false) {
            try {
                //DEBUG
                System.out.println("IsWaiting = false, receiveCommand va in wait");

                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //DEBUG
        System.out.println(" receiveCommand setta nextCommand = RECEIVE");

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
            //DEBUG
            System.out.println("Sono dentro handleServerConnection, ma prima della wait! :D");

            nextCommand = null;

            try {
                isWaiting = true;
                notifyAll();
                //DEBUG
                System.out.println("IsWaiting = true, notifyAll");

                wait();

                //DEBUG
                System.out.println("IsWaiting = false");

                isWaiting = false;

            } catch (InterruptedException e) {
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

        receivedMessage = (Message) inputStream.readObject();

        //DEBUG
        System.out.println("Ho ricevuto il messaggio, notifico l'observer");

        for (ServerObserver observer : observers) {
            observer.didReceiveServerMessage(receivedMessage);
        }
    }

    public synchronized void submit(Object response) throws IOException {
        outputStream.writeObject(response);
    }
}
