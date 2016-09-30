package ca.ulaval.glo4003.ws.domain.user;

public class InvalidPasswordException extends Exception {

    public InvalidPasswordException(String message) {
        super(message);
    }
}
