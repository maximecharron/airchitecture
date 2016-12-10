package ca.ulaval.glo4003.air.domain.user.Exceptions;

public class InvalidPasswordException extends Exception {

    public InvalidPasswordException(String message) {
        super(message);
    }
}
