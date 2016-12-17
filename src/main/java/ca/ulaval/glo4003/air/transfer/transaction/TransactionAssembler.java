package ca.ulaval.glo4003.air.transfer.transaction;

import ca.ulaval.glo4003.air.domain.transaction.Transaction;
import ca.ulaval.glo4003.air.domain.transaction.cart.CartItem;
import ca.ulaval.glo4003.air.transfer.transaction.dto.TransactionDto;

import java.util.List;

public class TransactionAssembler {

    private final CartItemAssembler cartItemAssembler;

    public TransactionAssembler(CartItemAssembler cartItemAssembler) {
        this.cartItemAssembler = cartItemAssembler;
    }

    public Transaction create(TransactionDto transactionDto) {
        List<CartItem> cartItems = cartItemAssembler.create(transactionDto.cartItemDtos);
        return new Transaction(transactionDto.emailAddress, cartItems);
    }
}
