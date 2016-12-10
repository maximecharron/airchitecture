package ca.ulaval.glo4003.air.domain.user.Exceptions;

public class UserAlreadyExistException extends Exception {

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
