package ca.ulaval.glo4003.air.domain.transaction;

public interface EmailSender {

    void sendTransactionDetails(Transaction transaction);
}
