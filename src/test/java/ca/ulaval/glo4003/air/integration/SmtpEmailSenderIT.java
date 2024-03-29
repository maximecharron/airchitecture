package ca.ulaval.glo4003.air.integration;

import ca.ulaval.glo4003.air.domain.notification.Email;
import ca.ulaval.glo4003.air.domain.notification.EmailSender;
import ca.ulaval.glo4003.air.infrastructure.notification.SmtpEmailSender;
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

public class SmtpEmailSenderIT {

    private static final String FROM_ADDRESS = "from@somedomain.tld";
    private static final String TO_ADDRESS = "to@anotherdomain.tld";
    private static final String SUBJECT_LINE = "Subject line.";
    private static final String MESSAGE_BODY = "Body";
    private static final int NB_EXPECTED_EMAILS_RECEIVED = 1;
    private static final String SECRET_PWD = "secret-pwd";

    @Rule
    public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP_IMAP);

    private Session smtpSession;
    private EmailSender emailSender;
    private GreenMail greenMailServer;
    private Email email;

    @Before
    public void setUp() {
        greenMailServer = new GreenMail(ServerSetupTest.SMTPS);
        greenMailServer.start();
        smtpSession = greenMail.getSmtp().createSession();
        greenMail.setUser(TO_ADDRESS, TO_ADDRESS, SECRET_PWD);
        this.email = new Email(FROM_ADDRESS, TO_ADDRESS, SUBJECT_LINE, MESSAGE_BODY);
    }

    @After
    public void tearDown() {
        greenMailServer.stop();
    }

    @Test
    public void givenNoSession_whenAnEmailIsSent_thenPropertiesAreReadAndCorrectlyApplied() throws Exception {
        this.emailSender = new SmtpEmailSender();

        this.emailSender.sendEmail(this.email);

        assertTrue(greenMail.waitForIncomingEmail(5000, 1));
        assertEquals(NB_EXPECTED_EMAILS_RECEIVED, greenMail.getReceivedMessagesForDomain(TO_ADDRESS).length);
        Message msgReceived = greenMail.getReceivedMessagesForDomain(TO_ADDRESS)[0];
        assertEquals(this.email.getSubject(), msgReceived.getSubject());
    }

    @Test
    public void givenASession_whenAnEmailIsSent_thenEmailIsCorrectlySent() throws Exception {
        this.emailSender = new SmtpEmailSender(smtpSession);

        this.emailSender.sendEmail(this.email);

        assertEquals(NB_EXPECTED_EMAILS_RECEIVED, greenMail.getReceivedMessagesForDomain(TO_ADDRESS).length);
        Message msgReceived = greenMail.getReceivedMessagesForDomain(TO_ADDRESS)[0];
        assertEquals(this.email.getSubject(), msgReceived.getSubject());
    }
}
