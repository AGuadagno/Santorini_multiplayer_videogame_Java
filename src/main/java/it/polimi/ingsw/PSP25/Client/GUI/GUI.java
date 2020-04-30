package it.polimi.ingsw.PSP25.Client.GUI;

import it.polimi.ingsw.PSP25.Client.CLI;
import it.polimi.ingsw.PSP25.Client.Client;
import it.polimi.ingsw.PSP25.Client.ViewObservable;
import it.polimi.ingsw.PSP25.Client.ViewObserver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application implements ViewObservable, ViewObserver {
    private ViewObserver client;

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("CLI")) {
            ViewObservable view = new CLI();
            Client client = new Client(view);
            view.subscribe(client);
            client.run();
        } else {
            launch();
        }
    }

    @Override
    public void subscribe(ViewObserver client) {
        this.client = client;
    }

    @Override
    public void askIPAddress() {
    }

    @Override
    public void start(Stage stage) {
        Client client = new Client(this);
        this.subscribe(client);
        client.run();

        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/Scene1.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene1Controller controller = loader.getController();
        controller.subscribe(this);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void updateIPAddress(String ip) {
        client.updateIPAddress(ip);
    }
}
