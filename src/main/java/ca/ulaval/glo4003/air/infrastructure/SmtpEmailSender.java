package ca.ulaval.glo4003.air.infrastructure;

import ca.ulaval.glo4003.air.domain.notification.NotificationFailedException;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SmtpEmailSender implements EmailSender {

    private Session session;

    public SmtpEmailSender() {
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("smtpConfigurations.properties");

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("mail.smtp.user"),
                        properties.getProperty("mail.smtp.password"));
            }
        });
    }

    public SmtpEmailSender(final Session session) {
        this.session = session;
    }

    public void sendEmail(ca.ulaval.glo4003.air.domain.notification.Message message) throws NotificationFailedException {
        try {
            MimeMessage mimeMessage = new MimeMessage(this.session);

            mimeMessage.setFrom(new InternetAddress(message.getFrom()));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(message.getTo()));
            mimeMessage.setSubject(message.getSubject());
            mimeMessage.setText(message.getBody());

            Transport transport = this.session.getTransport();
            transport.connect();
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();

        } catch (Exception e) {
            throw new NotificationFailedException(e);
        }
    }
}
