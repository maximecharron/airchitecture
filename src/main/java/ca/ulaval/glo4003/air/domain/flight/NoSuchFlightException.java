package ca.ulaval.glo4003.air.domain.flight;

public class NoSuchFlightException extends Exception {

    public NoSuchFlightException(String message) {
        super(message);
    }
}
