package ca.ulaval.glo4003.air.domain.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.TransactionDto;
import ca.ulaval.glo4003.air.domain.notification.EmailTransactionNotifier;
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
    private EmailTransactionNotifier emailSender;

    @Mock
    private Transaction transaction;

    private TransactionService transactionService;

    @Before
    public void setup() {
        transactionService = new TransactionService(transactionRepository, emailSender);
    }

    @Test
    public void givenATransaction_whenTheServiceProceedsWithTheTransaction_thenTheTransactionIsPersisted() {
        transactionService.buyTickets(transaction);

        verify(transactionRepository).save(transaction);
    }

    @Test
    public void givenATransaction_whenTheServiceProceedsWithTheTransaction_thenAnEmailIsSentToTheCustomer() {
        transactionService.buyTickets(transaction);

        verify(emailSender).notifyOnNewCompletedTransaction(transaction);
    }
}
