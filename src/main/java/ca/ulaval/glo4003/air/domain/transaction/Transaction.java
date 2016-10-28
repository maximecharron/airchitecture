package ca.ulaval.glo4003.air.domain.transaction;

import ca.ulaval.glo4003.air.domain.transaction.cart.CartItem;

import java.time.LocalDateTime;
import java.util.List;

public class Transaction {

    private String emailAddress;
    private List<CartItem> cartItems;
    private LocalDateTime timeOfTransaction;

    public Transaction(String emailAddress, List<CartItem> cartItems) {
        this.emailAddress = emailAddress;
        this.cartItems = cartItems;
        this.timeOfTransaction = LocalDateTime.now();
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }
}
