package ca.ulaval.glo4003.air.domain.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.TransactionDto;

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
