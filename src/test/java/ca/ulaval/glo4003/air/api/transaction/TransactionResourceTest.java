package ca.ulaval.glo4003.air.api.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.TransactionDto;
import ca.ulaval.glo4003.air.domain.transaction.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransactionResourceTest {

    @Mock
    private TransactionService transactionService;

    private TransactionDto transactionDto;

    private TransactionResource transactionResource;

    @Before
    public void setup() {
        transactionResource = new TransactionResource(transactionService);
    }

    @Test
    public void givenATransactionResource_whenBuyingTickets_thenItsDelegatedToTheService() {
        transactionResource.buyTickets(transactionDto);

        verify(transactionService).buyTickets(transactionDto);
    }
}