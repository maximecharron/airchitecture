package ca.ulaval.glo4003.air.domain.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.CartItemDto;
import ca.ulaval.glo4003.air.api.transaction.dto.TransactionDto;
import ca.ulaval.glo4003.air.transfer.transaction.CartItemAssembler;
import ca.ulaval.glo4003.air.transfer.user.UserAssembler;
import ca.ulaval.glo4003.air.transfer.transaction.TransactionAssembler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.BDDMockito.willReturn;

@RunWith(MockitoJUnitRunner.class)
public class TransactionFactoryTest {

    private static final String EMAIL_ADDRESS = "hey@ho.com";

    @Mock
    private CartItemFactory cartItemFactory;

    @Mock
    private UserAssembler userAssembler;

    @Mock
    private CartItem cartItem;

    private CartItemDto cartItemDto;
    private TransactionDto transactionDto;

    private TransactionFactory transactionFactory;

    @Before
    public void setup() {
        transactionFactory = new TransactionFactory(cartItemFactory);
        cartItemDto = new CartItemDto();
        setupCartItemMock();
        setupTransactionDto();
    }

    @Test
    public void givenATransactionDto_whenAssemblingATransactionObject_thenItsWellAssembled() {
        willReturn(Collections.singletonList(cartItem)).given(cartItemFactory).create(Collections.singletonList(cartItemDto));

        Transaction transaction = transactionFactory.create(transactionDto);

        assertHasAllTheRightProperties(transaction);
    }

    private void assertHasAllTheRightProperties(Transaction transaction) {
        assertThat(transaction.getEmailAddress(), is(equalTo(EMAIL_ADDRESS)));
    }

    private void setupCartItemMock() {

    }

    private void setupTransactionDto() {
        transactionDto = new TransactionDto();
        transactionDto.emailAddress = EMAIL_ADDRESS;
        transactionDto.cartItemDtos = Collections.singletonList(cartItemDto);
    }
}
