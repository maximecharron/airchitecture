package ca.ulaval.glo4003.air.domain.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.TransactionDto;

import java.util.logging.Logger;

public class TransactionService {

    private Logger logger = Logger.getLogger(TransactionService.class.getName());

    private final TransactionRepository transactionRepository;
    private final EmailSender emailSender;
    private final TransactionAssembler transactionAssembler;

    public TransactionService(TransactionRepository transactionRepository, EmailSender emailSender, TransactionAssembler transactionAssembler) {
        this.transactionRepository = transactionRepository;
        this.emailSender = emailSender;
        this.transactionAssembler = transactionAssembler;
    }

    public void buyTickets(TransactionDto transactionDto) {
        logTransaction(transactionDto);

        Transaction transaction = transactionAssembler.create(transactionDto);

        transactionRepository.save(transaction);
        emailSender.sendTransactionDetails(transaction);
    }

    private void logTransaction(TransactionDto transactionDto) {
        String transactionInfo = "User " + transactionDto.emailAddress + " has bought tickets.";
        logger.info(transactionInfo);
    }
}
