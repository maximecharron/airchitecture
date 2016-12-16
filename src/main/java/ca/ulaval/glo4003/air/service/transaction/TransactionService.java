package ca.ulaval.glo4003.air.service.transaction;

import ca.ulaval.glo4003.air.domain.transaction.cart.CartItem;
import ca.ulaval.glo4003.air.domain.user.InvalidTokenException;
import ca.ulaval.glo4003.air.domain.user.UserRepository;
import ca.ulaval.glo4003.air.service.user.UserService;
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
    private final UserService userService;

    public TransactionService(TransactionRepository transactionRepository, TransactionNotifier transactionNotifier, TransactionAssembler transactionAssembler, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.transactionNotifier = transactionNotifier;
        this.transactionAssembler = transactionAssembler;
        this.userService = userService;
    }

    public void buyTickets(TransactionDto transactionDto, String token) {
        Transaction transaction = transactionAssembler.create(transactionDto);
        logTransaction(transaction);

        for (CartItem cartItem : transaction.getCartItems()) {
            userService.addNewDestinationToUser(token, cartItem.getArrivalAirport());
        }

        transactionRepository.save(transaction);
        transactionNotifier.notifyOnNewCompletedTransaction(transaction);
    }

    private void logTransaction(Transaction transaction) {
        String transactionInfo = "User " + transaction.getEmailAddress() + " has bought tickets.";
        logger.info(transactionInfo);
    }
}
