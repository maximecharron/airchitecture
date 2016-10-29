package ca.ulaval.glo4003.air.api.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.TransactionDto;
import ca.ulaval.glo4003.air.domain.transaction.Transaction;
import ca.ulaval.glo4003.air.domain.transaction.TransactionService;
import ca.ulaval.glo4003.air.transfer.transaction.TransactionAssembler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransactionResourceTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private TransactionAssembler transactionAssembler;

    @Mock
    private Transaction transaction;

    private TransactionDto transactionDto = new TransactionDto();

    private TransactionResource transactionResource;

    @Before
    public void setup() {
        transactionResource = new TransactionResource(transactionService, transactionAssembler);
        given(transactionAssembler.create(transactionDto)).willReturn(transaction);
    }

    @Test
    public void givenATransactionResource_whenBuyingTickets_thenItsDelegatedToTheService() {
        transactionResource.buyTickets(transactionDto);

        verify(transactionService).buyTickets(transaction);
    }
}
