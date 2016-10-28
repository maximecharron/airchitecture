package ca.ulaval.glo4003.air.domain.transaction.notification;

import ca.ulaval.glo4003.air.domain.transaction.Transaction;

public interface EmailSender {

    void sendTransactionDetails(Transaction transaction);
}
