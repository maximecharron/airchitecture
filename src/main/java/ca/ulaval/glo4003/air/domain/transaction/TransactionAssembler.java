package ca.ulaval.glo4003.air.domain.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.TransactionDto;
import ca.ulaval.glo4003.air.domain.transaction.cartitems.CartItem;
import ca.ulaval.glo4003.air.domain.transaction.cartitems.CartItemAssembler;

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
