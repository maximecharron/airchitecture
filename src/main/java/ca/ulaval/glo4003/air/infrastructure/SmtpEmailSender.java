package ca.ulaval.glo4003.air.infrastructure;

import ca.ulaval.glo4003.air.domain.notification.Message;
import ca.ulaval.glo4003.air.domain.notification.NotificationFailedException;

import javax.mail.*;
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

    public SmtpEmailSender(final Session session) {
        this.session = session;
    }

    public void sendEmail(Message message) throws NotificationFailedException {
        MimeMessage mimeMessage = new MimeMessage(this.session);
        Transport transport;

        try {
            mimeMessage.setFrom(new InternetAddress(message.getFrom()));
            mimeMessage.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(message.getTo()));
            mimeMessage.setSubject(message.getSubject());
            mimeMessage.setText(message.getBody());

            transport = this.session.getTransport();

            transport.connect();
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();
        } catch (NoSuchProviderException e) {
            throw new NotificationFailedException(e);
        } catch (MessagingException e) {
            throw new NotificationFailedException(e);
        }
    }
}
