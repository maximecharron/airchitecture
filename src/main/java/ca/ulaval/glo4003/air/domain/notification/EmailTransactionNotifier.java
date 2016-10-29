package ca.ulaval.glo4003.air.domain.notification;

import ca.ulaval.glo4003.air.domain.transaction.Transaction;
import ca.ulaval.glo4003.air.domain.transaction.cart.CartItem;
import ca.ulaval.glo4003.air.infrastructure.EmailSender;
import ca.ulaval.glo4003.air.infrastructure.EmailTransactionNotifierConfiguration;

public class EmailTransactionNotifier implements TransactionNotifier {

    private final EmailTransactionNotifierConfiguration emailConfiguration;
    private final EmailSender emailSender;
    private final static String NOTIFICATION_MESSAGE_SUBJECT = "Here are your transaction informations";
    private final static String NOTIFICATION_MESSAGE_BODY = "Here are the details of your last transaction with us: %s";

    public EmailTransactionNotifier(EmailSender emailSender, EmailTransactionNotifierConfiguration emailTransactionNotifierConfiguration) {
        this.emailConfiguration = emailTransactionNotifierConfiguration;
        this.emailSender = emailSender;
    }

    public void notifyOnNewCompletedTransaction(Transaction transaction) throws NotificationFailedException {
        StringBuilder bodyBuilder = new StringBuilder();

        bodyBuilder.append("AIRLINE\tDEPARTURE DATE\tTICKET QUANTITY");
        for (final CartItem cartItem : transaction.getCartItems()) {
            bodyBuilder.append(String.format("%s\t%s\t%s", cartItem.getAirlineCompany(), cartItem.getDepartureDate(), cartItem.getTicketsQuantity()));
        }

        Message message = new MessageBuilder().addFrom(this.emailConfiguration.getFromAddress())
                                              .addTo(transaction.getEmailAddress())
                                              .addSubject(NOTIFICATION_MESSAGE_SUBJECT)
                                              .addBody(String.format(NOTIFICATION_MESSAGE_BODY, bodyBuilder.toString()))
                                              .build();

        this.emailSender.sendEmail(message);
    }
}