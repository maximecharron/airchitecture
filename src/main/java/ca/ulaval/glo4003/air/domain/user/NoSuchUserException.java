package ca.ulaval.glo4003.air.domain.user;

public class NoSuchUserException extends Exception {

    public NoSuchUserException(String message) {
        super(message);
    }
}
