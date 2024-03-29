package ca.ulaval.glo4003.air.domain.notification;

import ca.ulaval.glo4003.air.domain.airplane.SeatMap;
import ca.ulaval.glo4003.air.domain.transaction.Transaction;
import ca.ulaval.glo4003.air.domain.transaction.cart.CartItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EmailTransactionNotifierTest {

    private static final String TO_ADDRESS = "to@domain.tld";
    private static final String FROM_ADDRESS = "airchitecture1@gmail.com";

    @Mock
    private EmailSender emailSender;

    @Mock
    private Transaction transaction;

    @Mock
    private EmailTransactionNotifierConfiguration emailConfiguration;

    @Before
    public void setUp() throws IOException {
        List<CartItem> cartItems = new ArrayList<>();
        SeatMap seatMap = new SeatMap(10, 5, 1);
        cartItems.add(new CartItem(seatMap, "YQB", "AirFrenette", LocalDateTime.now(), 10));

        given(transaction.getEmailAddress()).willReturn(TO_ADDRESS);
        given(transaction.getCartItems()).willReturn(cartItems);
        given(emailConfiguration.getFromAddress()).willReturn(FROM_ADDRESS);
    }

    @Test
    public void givenANewTransactionCompleted_whenNotificationIsCalled_thenAnEmailIsSent() throws Exception {
        TransactionNotifier transactionNotifier = new EmailTransactionNotifier(emailSender, emailConfiguration);

        transactionNotifier.notifyOnNewCompletedTransaction(transaction);

        verify(emailSender).sendEmail(any(Email.class));
    }
}
