package ca.ulaval.glo4003.air.domain.notification;

public class NotificationFailedException extends RuntimeException {

    public NotificationFailedException(final Throwable cause) {
        super(cause);
    }
}
