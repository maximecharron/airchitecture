package ca.ulaval.glo4003.ws.domain.user;

public class NoSuchUserException extends RuntimeException {

    public NoSuchUserException(String message) {
        super(message);
    }
}
