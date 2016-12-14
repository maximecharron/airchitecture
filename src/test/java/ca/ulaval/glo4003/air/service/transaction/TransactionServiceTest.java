package ca.ulaval.glo4003.air.service.transaction;

import ca.ulaval.glo4003.air.service.user.UserService;
import ca.ulaval.glo4003.air.transfer.transaction.dto.TransactionDto;
import ca.ulaval.glo4003.air.domain.notification.EmailTransactionNotifier;
import ca.ulaval.glo4003.air.domain.transaction.Transaction;
import ca.ulaval.glo4003.air.domain.transaction.TransactionRepository;
import ca.ulaval.glo4003.air.transfer.transaction.TransactionAssembler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private EmailTransactionNotifier emailSender;

    @Mock
    private Transaction transaction;

    @Mock
    private TransactionDto transactionDto;

    @Mock
    private TransactionAssembler transactionAssembler;

    @Mock
    private UserService userService;
    private TransactionService transactionService;

    @Before
    public void setup() {
        willReturn(transaction).given(transactionAssembler).create(transactionDto);
        transactionService = new TransactionService(transactionRepository, emailSender, transactionAssembler, userService);
    }

    @Test
    public void givenATransaction_whenTheServiceProceedsWithTheTransaction_thenTheTransactionIsPersisted() {
        transactionService.buyTickets(transactionDto, "");

        verify(transactionRepository).save(transaction);
    }

    @Test
    public void givenATransaction_whenTheServiceProceedsWithTheTransaction_thenAnEmailIsSentToTheCustomer() {
        transactionService.buyTickets(transactionDto, "");

        verify(emailSender).notifyOnNewCompletedTransaction(transaction);
    }
}
