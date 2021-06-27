package app;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;


public class Controller {
    private final String USER_DIR = System.getProperty("user.dir");
    private Client client;

    public Controller() {
    }

    public String reqForm() {
        TextInputDialog dialog = new TextInputDialog("");

        dialog.setTitle("Form");
        dialog.setHeaderText("Enter login");
        dialog.setContentText("Login:");

        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()) {
            return result.get();
        } else {
            Platform.exit();
        }
        return "";
    }

    public void setFields(Client client) {
        this.client = client;
        String[] array = client.getArray();
        if (array != null) {
            course.setText(array[0]);
            trainer.setText(array[1]);
            firstName.setText(array[2]);
            lastName.setText(array[3]);
            date.setText(array[4]);
        }
    }

    @FXML
    private TextField course;

    @FXML
    private TextField trainer;

    @FXML
    private TextField date;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField phone;

    @FXML
    private TextField email;

    @FXML
    private void exit() {
        Platform.exit();
    }

    @FXML
    private void info() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("About");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        try {
            stage.getIcons().add(new Image(new FileInputStream(USER_DIR + "\\icon.png")));
        } catch (FileNotFoundException e) {
            System.err.println("Failed to read icon file.");
        }
        dialog.setHeaderText("About this program");
        dialog.setContentText("This is program for practice");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.show();
    }

    @FXML
    private void sendForm() {
        String[] arr = new String[4];
        arr[0] = firstName.getText();
        arr[1] = lastName.getText();
        arr[2] = phone.getText();
        arr[3] = email.getText();
        client.sendForm(arr);
        Client.close();
        exit();
    }
}