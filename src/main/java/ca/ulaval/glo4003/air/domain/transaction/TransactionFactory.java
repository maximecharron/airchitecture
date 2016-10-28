package ca.ulaval.glo4003.air.domain.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.TransactionDto;
import ca.ulaval.glo4003.air.domain.transaction.cart.CartItem;
import ca.ulaval.glo4003.air.domain.transaction.cart.CartItemFactory;

import java.util.List;

public class TransactionFactory {

    private final CartItemFactory cartItemFactory;

    public TransactionFactory(CartItemFactory cartItemFactory) {
        this.cartItemFactory = cartItemFactory;
    }

    public Transaction create(TransactionDto transactionDto) {
        List<CartItem> cartItems = cartItemFactory.create(transactionDto.cartItemDtos);
        return new Transaction(transactionDto.emailAddress, cartItems);
    }
}
