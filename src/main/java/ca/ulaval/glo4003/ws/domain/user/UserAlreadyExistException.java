package ca.ulaval.glo4003.ws.domain.user;

public class UserAlreadyExistException extends Exception {

    public UserAlreadyExistException(String message) {
        super(message);
    }
}