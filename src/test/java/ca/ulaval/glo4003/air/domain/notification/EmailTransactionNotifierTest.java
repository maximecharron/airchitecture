package ca.ulaval.glo4003.air.domain.notification;

import ca.ulaval.glo4003.air.domain.transaction.CartItem;
import ca.ulaval.glo4003.air.domain.transaction.Transaction;
import ca.ulaval.glo4003.air.infrastructure.EmailSender;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class EmailTransactionNotifierTest {

    private static final String TO_ADDRESS = "to@domain.tld";

    @Mock
    private EmailSender emailSender = mock(EmailSender.class);
    @Mock
    private Transaction transaction = mock(Transaction.class);
    private TransactionNotifier transactionNotifier;

    @Before
    public void setUp() {
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(1, "123", LocalDateTime.now()));

        Mockito.doReturn(TO_ADDRESS).when(transaction).getEmailAddress();
        Mockito.doReturn(cartItems).when(transaction).getCartItems();

        transactionNotifier = new EmailTransactionNotifier(emailSender);
    }

    @Test
    public void emailIsSentOnANewCompletedTransaction() throws Exception {
        transactionNotifier.notifyOnNewCompletedTransaction(transaction);
        verify(emailSender).sendEmail(any(Message.class));
    }
}
