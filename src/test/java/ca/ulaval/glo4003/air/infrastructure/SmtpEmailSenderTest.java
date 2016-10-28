package ca.ulaval.glo4003.air.infrastructure;

import ca.ulaval.glo4003.air.domain.notification.MessageBuilder;
import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.mail.Message;
import javax.mail.Session;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SmtpEmailSenderTest {
    private static final String FROM_ADDRESS = "from@somedomain.tld";
    private static final String TO_ADDRESS = "to@anotherdomain.tld";
    private static final String SUBJECT_LINE = "Subject line.";
    private static final String MESSAGE_BODY = "Body";
    private static final int NB_EXPECTED_EMAILS_RECEIVED = 1;
    private static final String SECRET_PWD = "secret-pwd";

    @Rule
    public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP_IMAP);
    private Session smtpSession;
    public EmailSender emailSender;
    private GreenMail greenMailServer;
    private ca.ulaval.glo4003.air.domain.notification.Message message;

    @Before
    public void setUp() {
        greenMailServer = new GreenMail(ServerSetupTest.SMTPS);
        greenMailServer.start();
        smtpSession = greenMail.getSmtp().createSession();
        Session smtpSession = greenMail.getSmtp().createSession();
        greenMail.setUser(TO_ADDRESS, TO_ADDRESS, SECRET_PWD);
        this.message = new MessageBuilder().addFrom(FROM_ADDRESS)
                                           .addTo(TO_ADDRESS)
                                           .addSubject(SUBJECT_LINE)
                                           .addBody(MESSAGE_BODY)
                                           .build();
    }

    @After
    public void tearDown() {
        greenMailServer.stop();
    }

    @Test
    public void givenNoSession_whenAnEmailIsSent_thenPropertiesAreReadAndCorrectlyApplied() throws Exception {
        this.emailSender = new SmtpEmailSender();
        this.emailSender.sendEmail(this.message);

        assertTrue(greenMail.waitForIncomingEmail(5000, 1));
        assertEquals(NB_EXPECTED_EMAILS_RECEIVED, greenMail.getReceivedMessagesForDomain(TO_ADDRESS).length);
        Message msgReceived = greenMail.getReceivedMessagesForDomain(TO_ADDRESS)[0];
        assertEquals(this.message.getSubject(), msgReceived.getSubject());
    }

    @Test
    public void givenASession_whenAnEmailIsSent_thenEmailIsCorrectlySent() throws Exception {
        this.emailSender = new SmtpEmailSender(smtpSession);
        this.emailSender.sendEmail(this.message);

        assertEquals(NB_EXPECTED_EMAILS_RECEIVED, greenMail.getReceivedMessagesForDomain(TO_ADDRESS).length);
        Message msgReceived = greenMail.getReceivedMessagesForDomain(TO_ADDRESS)[0];
        assertEquals(this.message.getSubject(), msgReceived.getSubject());
    }
}
