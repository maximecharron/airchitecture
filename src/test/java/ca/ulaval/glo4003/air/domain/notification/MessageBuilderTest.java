package ca.ulaval.glo4003.air.domain.notification;

import org.junit.Assert;
import org.junit.Test;

public class MessageBuilderTest {

    private static final String ADDRESS_FROM = "from@domain.tld";
    private static final String ADDRESS_TO = "to@anotherdomain.tld";
    private static final String SUBJECT_LINE = "This is a subject.";
    private static final String BODY_CONTENT = "This is an actual e-mail body.";

    @Test
    public void givenASetOfData_whenMessageIsBuilt_thenMessageDataIsValid() throws Exception {
        Message expectedMessage = new Message(ADDRESS_FROM, ADDRESS_TO, SUBJECT_LINE, BODY_CONTENT);
        Message aktualMessage = new MessageBuilder().addFrom(ADDRESS_FROM).addTo(ADDRESS_TO).addSubject(SUBJECT_LINE).addBody(BODY_CONTENT).build();

        Assert.assertEquals(expectedMessage.getFrom(), aktualMessage.getFrom());
        Assert.assertEquals(expectedMessage.getTo(), aktualMessage.getTo());
        Assert.assertEquals(expectedMessage.getSubject(), aktualMessage.getSubject());
        Assert.assertEquals(expectedMessage.getBody(), aktualMessage.getBody());
    }
}
