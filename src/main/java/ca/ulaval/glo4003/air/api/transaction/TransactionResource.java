package ca.ulaval.glo4003.air.api.transaction;

import ca.ulaval.glo4003.air.transfer.transaction.dto.TransactionDto;
import ca.ulaval.glo4003.air.service.transaction.TransactionService;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/transactions")
public class TransactionResource {

    private final TransactionService transactionService;

    public TransactionResource(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void checkout(TransactionDto transactionDto, @HeaderParam("X-Access-Token") String token) {
        transactionService.buyTickets(transactionDto, token);
    }
}
