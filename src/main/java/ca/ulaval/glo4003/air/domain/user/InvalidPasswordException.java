package ca.ulaval.glo4003.air.domain.user;

public class InvalidPasswordException extends Exception {

    public InvalidPasswordException(String message) {
        super(message);
    }
}
