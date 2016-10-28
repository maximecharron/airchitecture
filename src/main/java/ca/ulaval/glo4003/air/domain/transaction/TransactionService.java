package ca.ulaval.glo4003.air.domain.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.TransactionDto;
import ca.ulaval.glo4003.air.domain.notification.TransactionNotifier;

import java.util.logging.Logger;

public class TransactionService {

    private Logger logger = Logger.getLogger(TransactionService.class.getName());

    private final TransactionRepository transactionRepository;
    private final TransactionNotifier transactionNotifier;
    private final TransactionFactory transactionFactory;

    public TransactionService(TransactionRepository transactionRepository, TransactionNotifier transactionNotifier, TransactionFactory transactionFactory) {
        this.transactionRepository = transactionRepository;
        this.transactionNotifier = transactionNotifier;
        this.transactionFactory = transactionFactory;
    }

    public void buyTickets(TransactionDto transactionDto) {
        logTransaction(transactionDto);

        Transaction transaction = transactionFactory.create(transactionDto);

        transactionRepository.save(transaction);
        transactionNotifier.notifyOnNewCompletedTransaction(transaction);
    }

    private void logTransaction(TransactionDto transactionDto) {
        String transactionInfo = "User " + transactionDto.emailAddress + " has bought tickets.";
        logger.info(transactionInfo);
    }
}
