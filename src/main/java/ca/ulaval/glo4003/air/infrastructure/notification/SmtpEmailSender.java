package ca.ulaval.glo4003.air.infrastructure.notification;

import ca.ulaval.glo4003.air.domain.notification.Email;
import ca.ulaval.glo4003.air.domain.notification.EmailSender;
import ca.ulaval.glo4003.air.domain.notification.NotificationFailedException;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class SmtpEmailSender implements EmailSender {

    private static final String SMTP_CONFIGURATIONS_PROPERTIES_FILE = "smtpConfigurations.properties";
    private static final String PROPERTY_SMTP_USER = "mail.smtp.user";
    private static final String PROPERTY_SMTP_PASSWORD = "mail.smtp.password";
    private Session session;

    public SmtpEmailSender() {
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(SMTP_CONFIGURATIONS_PROPERTIES_FILE);

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty(PROPERTY_SMTP_USER),
                    properties.getProperty(PROPERTY_SMTP_PASSWORD));
            }
        });
    }

    public SmtpEmailSender(Session session) {
        this.session = session;
    }

    public void sendEmail(Email email) throws NotificationFailedException {
        MimeMessage mimeMessage = new MimeMessage(this.session);
        Transport transport;

        try {
            mimeMessage.setFrom(new InternetAddress(email.getFrom()));
            mimeMessage.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email.getTo()));
            mimeMessage.setSubject(email.getSubject());
            mimeMessage.setText(email.getBody());

            transport = this.session.getTransport();

            transport.connect();
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            throw new NotificationFailedException(e);
        }
    }
}
