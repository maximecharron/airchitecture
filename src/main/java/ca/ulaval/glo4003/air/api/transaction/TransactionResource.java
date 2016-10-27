package ca.ulaval.glo4003.air.api.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.CartItemDto;
import ca.ulaval.glo4003.air.api.transaction.dto.TransactionDto;
import ca.ulaval.glo4003.air.domain.transaction.TransactionService;
import ca.ulaval.glo4003.air.domain.transaction.cartitems.CartItem;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/transactions")
public class TransactionResource {

    private final TransactionService transactionService;

    public TransactionResource(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @POST
    @Path("/buy")
    public void buyTickets(TransactionDto transactionDto) {
        transactionService.buyTickets(transactionDto);
    }

    @POST
    @Path("/reserve")
    public void reserverTickets(CartItemDto transactionDto) {
        transactionService.reserveTickets(transactionDto);
    }
}
