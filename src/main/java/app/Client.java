package app;

import connection.Comm;
import connection.Message;
import connection.MessageType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

public class Client extends Thread {
    private static FileReader FR;
    private static String serverAddress;
    private static int serverPort;

    private static Comm connection;
    private String[] array;
    private String login;
    private boolean connected;

    public void run() {
        try {
            loadProperties();
            connection = new Comm(new Socket(serverAddress, serverPort));
            clientHandshake();
            clientMainLoop();
        } catch (ClassNotFoundException | IOException e) {
           e.printStackTrace();
        }
    }

    private void clientHandshake() throws IOException, ClassNotFoundException {
        Message message = connection.receive();
        if (message.getType() == MessageType.CONNECTED) {
            System.out.println("Connected to server");
            connected = true;
        } else connected = false;
    }

    private void clientMainLoop() throws IOException, ClassNotFoundException {
        if (connected) {
            connection.send(new Message(MessageType.FORM_REQUEST, login));
            Message message = connection.receive();
            if (message.getType() == MessageType.ACCEPTED) {
                array = message.getArray();
            } else {
                throw new IOException("Wrong message type");
            }
        }
    }

    private static void loadProperties() {
        String USER_DIR = System.getProperty("user.dir");
        File file = new File(USER_DIR + "\\connection.properties");
        Properties properties = new Properties();
        try {
            FR = new FileReader(file);
            properties.load(FR);
        } catch (IOException e) {
            System.err.println("Failed to read properties");
        }
        serverAddress = properties.getProperty("serverAddress");
        serverPort = Integer.parseInt(properties.getProperty("serverPort"));
    }

    public static void close() {
        try {
            connection.close();
            FR.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String[] getArray() {
        return array;
    }

    public void sendForm(String[] arr) {
        try {
            connection.send(new Message(MessageType.FORM_SAVE, arr));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
