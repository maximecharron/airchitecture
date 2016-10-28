package ca.ulaval.glo4003.air.infrastructure;

import ca.ulaval.glo4003.air.domain.notification.Message;
import ca.ulaval.glo4003.air.domain.notification.NotificationFailedException;

public interface EmailSender {

    void sendEmail(Message message) throws NotificationFailedException;

}
