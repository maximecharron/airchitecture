package ca.ulaval.glo4003.air.domain.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.TransactionDto;
import ca.ulaval.glo4003.air.domain.transaction.notification.EmailSender;

import java.util.logging.Logger;

public class TransactionService {

    private Logger logger = Logger.getLogger(TransactionService.class.getName());

    private final TransactionRepository transactionRepository;
    private final EmailSender emailSender;
    private final TransactionFactory transactionFactory;

    public TransactionService(TransactionRepository transactionRepository, EmailSender emailSender, TransactionFactory transactionFactory) {
        this.transactionRepository = transactionRepository;
        this.emailSender = emailSender;
        this.transactionFactory = transactionFactory;
    }

    public void buyTickets(TransactionDto transactionDto) {
        logTransaction(transactionDto);

        Transaction transaction = transactionFactory.create(transactionDto);

        transactionRepository.save(transaction);
        emailSender.sendTransactionDetails(transaction);
    }

    private void logTransaction(TransactionDto transactionDto) {
        String transactionInfo = "User " + transactionDto.emailAddress + " has bought tickets.";
        logger.info(transactionInfo);
    }
}
