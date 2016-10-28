package ca.ulaval.glo4003.air.domain.notification;

import org.junit.Assert;
import org.junit.Test;

public class MessageTest {

    private static final String FROM_ADDRESS = "from@domain.tld";
    private static final String TO_ADDRESS = "to@anotherdomain.tld";
    private static final String SUBJECT_LINE = "Subject line.";
    private static final String BODY_TEXT = "Body text.";

    @Test
    public void MessageValuesAreAsSettedByConstructor() {
        Message message = new Message(FROM_ADDRESS, TO_ADDRESS, SUBJECT_LINE, BODY_TEXT);

        Assert.assertEquals(message.getFrom(), FROM_ADDRESS);
        Assert.assertEquals(message.getTo(), TO_ADDRESS);
        Assert.assertEquals(message.getSubject(), SUBJECT_LINE);
        Assert.assertEquals(message.getBody(), BODY_TEXT);
    }
}
