package ca.ulaval.glo4003.air.domain.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.TransactionDto;
import ca.ulaval.glo4003.air.domain.notification.EmailTransactionNotifier;
import ca.ulaval.glo4003.air.transfer.transaction.TransactionAssembler;

import java.util.logging.Logger;

public class TransactionService {

    private Logger logger = Logger.getLogger(TransactionService.class.getName());

    private final TransactionRepository transactionRepository;
    private final EmailTransactionNotifier transactionNotifier;
    private final TransactionAssembler transactionAssembler;

    public TransactionService(TransactionRepository transactionRepository, EmailTransactionNotifier transactionNotifier, TransactionAssembler transactionAssembler) {
        this.transactionRepository = transactionRepository;
        this.transactionNotifier = transactionNotifier;
        this.transactionAssembler = transactionAssembler;
    }

    public void buyTickets(TransactionDto transactionDto) {
        logTransaction(transactionDto);

        Transaction transaction = transactionAssembler.create(transactionDto);

        transactionRepository.save(transaction);
        transactionNotifier.notifyOnNewCompletedTransaction(transaction);
    }

    private void logTransaction(TransactionDto transactionDto) {
        String transactionInfo = "User " + transactionDto.emailAddress + " has bought tickets.";
        logger.info(transactionInfo);
    }
}
