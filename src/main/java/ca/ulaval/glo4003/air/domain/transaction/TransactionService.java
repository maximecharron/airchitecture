package ca.ulaval.glo4003.air.domain.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.TransactionDto;
import ca.ulaval.glo4003.air.domain.notification.TransactionNotifier;

import java.util.logging.Logger;

public class TransactionService {

    private Logger logger = Logger.getLogger(TransactionService.class.getName());

    private final TransactionRepository transactionRepository;
    private final TransactionNotifier transactionNotifier;

    public TransactionService(TransactionRepository transactionRepository, TransactionNotifier transactionNotifier) {
        this.transactionRepository = transactionRepository;
        this.transactionNotifier = transactionNotifier;
    }

    public void buyTickets(Transaction transaction) {
        logTransaction(transaction);

        transactionRepository.save(transaction);
        transactionNotifier.notifyOnNewCompletedTransaction(transaction);
    }

    private void logTransaction(Transaction transaction) {
        String transactionInfo = "User " + transaction.getEmailAddress() + " has bought tickets.";
        logger.info(transactionInfo);
    }
}
