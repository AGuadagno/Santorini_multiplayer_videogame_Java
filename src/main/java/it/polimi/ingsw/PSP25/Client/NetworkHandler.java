package it.polimi.ingsw.PSP25.Client;

import it.polimi.ingsw.PSP25.Utility.Messages.Message;
import it.polimi.ingsw.PSP25.Utility.Messages.PingMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
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
            server.setSoTimeout(20000);
            startPingSender();
            handleServerConnection();
        } catch (IOException e) {
            stop();
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
        for (ServerObserver observer : observers) {
            observer.didReceiveServerMessage(null);
        }
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
        Message receivedMessage = null;
        try {
            receivedMessage = (Message) inputStream.readObject();
        }catch (SocketTimeoutException e){
            System.out.println("Network handler: socket timeout excpetion");
            throw new SocketTimeoutException();
        }
        for (ServerObserver observer : observers) {
            observer.didReceiveServerMessage(receivedMessage);
        }

    }

    public synchronized void submit(Object response) throws IOException {
        outputStream.writeObject(response);
    }

    public void startPingSender(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        outputStream.writeObject(new PingMessage());
                    } catch (IOException e) {
                        return;
                    }
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
               }
            }
        }).start();
    }
}
