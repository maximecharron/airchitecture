package ca.ulaval.glo4003.air.api.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.TransactionDto;
import ca.ulaval.glo4003.air.domain.transaction.Transaction;
import ca.ulaval.glo4003.air.domain.transaction.TransactionService;
import ca.ulaval.glo4003.air.transfer.transaction.TransactionAssembler;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/transactions")
public class TransactionResource {

    private final TransactionService transactionService;
    private final TransactionAssembler transactionAssembler;

    public TransactionResource(TransactionService transactionService, TransactionAssembler transactionAssembler) {
        this.transactionService = transactionService;
        this.transactionAssembler = transactionAssembler;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void buyTickets(TransactionDto transactionDto) {
        Transaction transaction = transactionAssembler.create(transactionDto);
        transactionService.buyTickets(transaction);
    }
}
