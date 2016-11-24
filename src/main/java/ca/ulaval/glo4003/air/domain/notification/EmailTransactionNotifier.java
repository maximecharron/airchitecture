package ca.ulaval.glo4003.air.domain.notification;

import ca.ulaval.glo4003.air.domain.airplane.SeatMap;
import ca.ulaval.glo4003.air.domain.transaction.Transaction;
import ca.ulaval.glo4003.air.domain.transaction.cart.CartItem;

public class EmailTransactionNotifier implements TransactionNotifier {

    private static final String NOTIFICATION_MESSAGE_SUBJECT = "Here are your transaction informations";
    private static final String NOTIFICATION_MESSAGE_BODY = "Here are the details of your last transaction with us: %s";

    private final EmailTransactionNotifierConfiguration emailConfiguration;
    private final EmailSender emailSender;

    public EmailTransactionNotifier(EmailSender emailSender, EmailTransactionNotifierConfiguration emailTransactionNotifierConfiguration) {
        this.emailConfiguration = emailTransactionNotifierConfiguration;
        this.emailSender = emailSender;
    }

    public void notifyOnNewCompletedTransaction(Transaction transaction) throws NotificationFailedException {
        StringBuilder bodyBuilder = new StringBuilder();

        bodyBuilder.append("AIRLINE\tDEPARTURE DATE\tECONOMIC TICKETS\tREGULAR TICKETS\tBUSINESS TICKETS");

        for (CartItem cartItem : transaction.getCartItems()) {
            SeatMap seatMap = cartItem.getSeatMap();

            String body = String.format("%s\t%s\t%s\t%s\t%s", cartItem.getAirlineCompany(), cartItem.getDepartureDate(), seatMap.getEconomicSeats(), seatMap.getRegularSeats(), seatMap.getBusinessSeats());
            bodyBuilder.append(body);
        }

        Email email = new Email(emailConfiguration.getFromAddress(),
            transaction.getEmailAddress(),
            NOTIFICATION_MESSAGE_SUBJECT,
            String.format(NOTIFICATION_MESSAGE_BODY, bodyBuilder.toString()));

        this.emailSender.sendEmail(email);
    }
}
