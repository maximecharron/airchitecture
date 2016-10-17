package ca.ulaval.glo4003.air.api.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.TransactionDto;
import ca.ulaval.glo4003.air.domain.transaction.TransactionService;

import javax.ws.rs.Path;

@Path("/transactions")
public class TransactionResource {

    private final TransactionService transactionService;

    public TransactionResource(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void buyTickets(TransactionDto transactionDto) {
        transactionService.buyTickets(transactionDto);
    }
}
