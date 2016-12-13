package ca.ulaval.glo4003.air.domain.user;

public class UnauthorizedException extends Exception {

    public UnauthorizedException(String message) {
        super(message);
    }
}
