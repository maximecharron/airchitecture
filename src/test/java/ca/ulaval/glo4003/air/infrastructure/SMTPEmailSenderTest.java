package ca.ulaval.glo4003.air.infrastructure;

import ca.ulaval.glo4003.air.domain.notification.MessageBuilder;
import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.mail.Message;
import javax.mail.Session;

import static org.junit.Assert.assertEquals;

public class SMTPEmailSenderTest {
    private static final String FROM_ADDRESS = "from@domain.tld";
    private static final String TO_ADDRESS = "to@anotherdomain.tld";
    private static final String SUBJECT_LINE = "Subject line.";
    private static final String MESSAGE_BODY = "Body";
    private static final int NB_EXPECTED_EMAILS_RECEIVED = 1;

    @Rule
    public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP_IMAP);
    private Session smtpSession;
    public EmailSender emailSender;

    @Before
    public void setUp() {
        smtpSession = greenMail.getSmtp().createSession();
        Session smtpSession = greenMail.getSmtp().createSession();
        this.emailSender = new SMTPEmailSender(smtpSession);
    }

    @Test
    public void sendEmailActuallySendsEmailsThroughSMTP() throws Exception {
        ca.ulaval.glo4003.air.domain.notification.Message msg = new MessageBuilder().addFrom(FROM_ADDRESS)
                                                                                    .addTo(TO_ADDRESS)
                                                                                    .addSubject(SUBJECT_LINE)
                                                                                    .addBody(MESSAGE_BODY)
                                                                                    .build();
        this.emailSender.sendEmail(msg);

        greenMail.setUser(TO_ADDRESS, TO_ADDRESS, "secret-pwd");

        assertEquals(NB_EXPECTED_EMAILS_RECEIVED, greenMail.getReceivedMessagesForDomain(TO_ADDRESS).length);
        Message msgReceived = greenMail.getReceivedMessagesForDomain(TO_ADDRESS)[0];
        assertEquals(msg.getSubject(), msgReceived.getSubject());
    }

}
