package ca.ulaval.glo4003.air.infrastructure;

import ca.ulaval.glo4003.air.domain.notification.NotificationFailedException;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SMTPEmailSender implements EmailSender {

    private Session session;

    public SMTPEmailSender() {
        Properties properties = System.getProperties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("smtpConfigurations.properties");

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.session = Session.getDefaultInstance(properties);
    }

    public SMTPEmailSender(final Session session) {
        this.session = session;
    }

    public void sendEmail(ca.ulaval.glo4003.air.domain.notification.Message message) throws NotificationFailedException {
        try {
            MimeMessage mimeMessage = new MimeMessage(this.session);

            mimeMessage.setFrom(new InternetAddress(message.getFrom()));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(message.getTo()));
            mimeMessage.setSubject(message.getSubject());
            mimeMessage.setText(message.getBody());

            Transport.send(mimeMessage);
        } catch (Exception e) {
            throw new NotificationFailedException(e);
        }
    }

}
