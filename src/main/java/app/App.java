package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class App extends Application {
    private final String USER_DIR = System.getProperty("user.dir");
    private static Client client;
    private Stage stage;
    private Controller controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("app.fxml"));
        AnchorPane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            System.err.println("Failed to read fxml file.");
        }

        controller = loader.getController();

        assert pane != null;
        stage.setScene(new Scene(pane));
        setParameters();
        stage.show();
        clientStart();

    }

    private void setParameters() {
        stage.setTitle("Form");
        stage.setWidth(400);
        stage.setHeight(400);
        stage.setResizable(false);
        try {
            stage.getIcons().add(new Image(new FileInputStream(USER_DIR + "\\icon.png")));
        } catch (FileNotFoundException e) {
            System.err.println("Failed to read icon file.");
        }
    }

    private void setFields() {
        controller.setFields(client);
    }

    private void clientStart() {
        String login = controller.reqForm();
        client = new Client();
        client.setLogin(login);
        client.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setFields();
    }
}


