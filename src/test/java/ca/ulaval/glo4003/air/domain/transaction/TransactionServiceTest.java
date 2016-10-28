package ca.ulaval.glo4003.air.domain.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.TransactionDto;
import ca.ulaval.glo4003.air.domain.transaction.notification.EmailSender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    private static final String EMAIL = "wow@ok.com";

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private EmailSender emailSender;

    @Mock
    private TransactionFactory transactionFactory;

    @Mock
    private Transaction transaction;

    private TransactionDto transactionDto;

    private TransactionService transactionService;

    @Before
    public void setup() {
        transactionService = new TransactionService(transactionRepository, emailSender, transactionFactory);
        transactionDto = new TransactionDto();

        willReturn(transaction).given(transactionFactory).create(transactionDto);
    }

    @Test
    public void givenATransaction_whenTheServiceProceedsWithTheTransaction_thenTheTransactionIsPersisted() {
        transactionService.buyTickets(transactionDto);

        verify(transactionRepository).save(transaction);
    }

    @Test
    public void givenATransaction_whenTheServiceProceedsWithTheTransaction_thenAnEmailIsSentToTheCustomer() {
        transactionService.buyTickets(transactionDto);

        verify(emailSender).sendTransactionDetails(transaction);
    }
}