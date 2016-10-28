package ca.ulaval.glo4003.air.domain.notification;

import ca.ulaval.glo4003.air.domain.transaction.Transaction;

public interface TransactionNotifier {

    void notifyOnNewCompletedTransaction(final Transaction transaction) throws NotificationFailedException;

}
