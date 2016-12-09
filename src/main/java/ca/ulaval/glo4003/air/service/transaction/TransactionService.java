package ca.ulaval.glo4003.air.service.transaction;

import ca.ulaval.glo4003.air.transfer.transaction.dto.TransactionDto;
import ca.ulaval.glo4003.air.domain.notification.TransactionNotifier;
import ca.ulaval.glo4003.air.domain.transaction.Transaction;
import ca.ulaval.glo4003.air.domain.transaction.TransactionRepository;
import ca.ulaval.glo4003.air.transfer.transaction.TransactionAssembler;

import java.util.logging.Logger;

public class TransactionService {

    private Logger logger = Logger.getLogger(TransactionService.class.getName());

    private final TransactionRepository transactionRepository;
    private final TransactionNotifier transactionNotifier;
    private final TransactionAssembler transactionAssembler;

    public TransactionService(TransactionRepository transactionRepository, TransactionNotifier transactionNotifier, TransactionAssembler transactionAssembler) {
        this.transactionRepository = transactionRepository;
        this.transactionNotifier = transactionNotifier;
        this.transactionAssembler = transactionAssembler;
    }

    public void buyTickets(TransactionDto transactionDto) {
        Transaction transaction = transactionAssembler.create(transactionDto);
        logTransaction(transaction);

        transactionRepository.save(transaction);
        transactionNotifier.notifyOnNewCompletedTransaction(transaction);
    }

    private void logTransaction(Transaction transaction) {
        String transactionInfo = "User " + transaction.getEmailAddress() + " has bought tickets.";
        logger.info(transactionInfo);
    }
}
