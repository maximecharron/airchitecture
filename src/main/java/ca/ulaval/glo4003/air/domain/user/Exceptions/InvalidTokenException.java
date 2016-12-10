package ca.ulaval.glo4003.air.domain.user.Exceptions;

public class InvalidTokenException extends Exception {

    public InvalidTokenException() {
        super();
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
