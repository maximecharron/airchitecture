package ca.ulaval.glo4003.air.domain.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.TransactionDto;
import ca.ulaval.glo4003.air.domain.flight.FlightRepository;
import ca.ulaval.glo4003.air.domain.transaction.cartitems.CartItem;

import java.util.logging.Logger;

public class TransactionService {

    private Logger logger = Logger.getLogger(TransactionService.class.getName());

    private final TransactionRepository transactionRepository;
    private final EmailSender emailSender;
    private final TransactionAssembler transactionAssembler;
    private final FlightRepository flightRepository;

    public TransactionService(TransactionRepository transactionRepository, FlightRepository flightRepository, EmailSender emailSender, TransactionAssembler transactionAssembler) {
        this.transactionRepository = transactionRepository;
        this.flightRepository = flightRepository;
        this.emailSender = emailSender;
        this.transactionAssembler = transactionAssembler;
    }

    public void buyTickets(TransactionDto transactionDto) {
        logTransaction(transactionDto);

        Transaction transaction = transactionAssembler.create(transactionDto);

        transactionRepository.save(transaction);
        emailSender.sendTransactionDetails(transaction);
    }

    public void reserveTickets(CartItem cartItem){
        updateFlight(cartItem);
    }

    private void updateFlight(CartItem cartItem){
        flightRepository.findOne(cartItem.getAirlineCompany(), cartItem.getArrivalAirport(), cartItem.getDepartureDate());
    }

    private void logTransaction(TransactionDto transactionDto) {
        String transactionInfo = "User " + transactionDto.emailAddress + " has bought tickets.";
        logger.info(transactionInfo);
    }


}
