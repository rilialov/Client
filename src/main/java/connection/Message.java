package connection;

import java.io.Serializable;

public class Message implements Serializable {
    private final MessageType type;
    private final String[] array;
    private final String login;

    public Message(MessageType type) {
        this.type = type;
        array = null;
        login = null;
    }

    public Message(MessageType type, String[] array) {
        this.type = type;
        this.array = array;
        login = null;
    }

    public Message(MessageType type, String login) {
        this.type = type;
        this.login = login;
        array = null;
    }

    public MessageType getType() {
        return type;
    }

    public String[] getArray() {
        return array;
    }

    public String getLogin() {
        return login;
    }
}