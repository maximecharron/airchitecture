package ca.ulaval.glo4003.air.service.flight;

public class InvalidParameterException extends RuntimeException {

    public InvalidParameterException(String message) {
        super(message);
    }
}
