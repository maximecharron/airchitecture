package ca.ulaval.glo4003.air.domain.notification;

import org.junit.Assert;
import org.junit.Test;

public class EmailBuilderTest {

    private static final String ADDRESS_FROM = "from@domain.tld";
    private static final String ADDRESS_TO = "to@anotherdomain.tld";
    private static final String SUBJECT_LINE = "This is a subject.";
    private static final String BODY_CONTENT = "This is an actual e-mail body.";

    @Test
    public void givenASetOfData_whenMessageIsBuilt_thenMessageDataIsValid() throws Exception {
        Email expectedEmail = new Email(ADDRESS_FROM, ADDRESS_TO, SUBJECT_LINE, BODY_CONTENT);

        Email actualEmail = new EmailBuilder().addFrom(ADDRESS_FROM)
                                              .addTo(ADDRESS_TO)
                                              .addSubject(SUBJECT_LINE)
                                              .addBody(BODY_CONTENT)
                                              .build();

        Assert.assertEquals(expectedEmail.getFrom(), actualEmail.getFrom());
        Assert.assertEquals(expectedEmail.getTo(), actualEmail.getTo());
        Assert.assertEquals(expectedEmail.getSubject(), actualEmail.getSubject());
        Assert.assertEquals(expectedEmail.getBody(), actualEmail.getBody());
    }
}
