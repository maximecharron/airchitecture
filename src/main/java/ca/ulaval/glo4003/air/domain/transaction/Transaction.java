package ca.ulaval.glo4003.air.domain.transaction;

import java.util.List;

public class Transaction {

    private String emailAddress;
    private List<CartItem> cartItems;

    public Transaction(String emailAddress, List<CartItem> cartItems) {
        this.emailAddress = emailAddress;
        this.cartItems = cartItems;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }
}
