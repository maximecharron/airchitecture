package ca.ulaval.glo4003.air.domain.notification;

public interface EmailSender {

    void sendEmail(Email email) throws NotificationFailedException;
}
