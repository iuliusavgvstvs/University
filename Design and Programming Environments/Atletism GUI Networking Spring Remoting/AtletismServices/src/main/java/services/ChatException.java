package services;

import java.io.Serializable;

public class ChatException extends Exception implements Serializable {
    public ChatException() {
    }

    public ChatException(String message) {
        super(message);
    }

    public ChatException(String message, Throwable cause) {
        super(message, cause);
    }
}
