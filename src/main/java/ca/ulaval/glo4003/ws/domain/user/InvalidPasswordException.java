package ca.ulaval.glo4003.ws.domain.user;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(String message) {
        super(message);
    }
}
