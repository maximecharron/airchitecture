package ca.ulaval.glo4003.air.domain.notification;

public class Email {

    private final String from;
    private final String to;
    private final String body;
    private final String subject;

    public Email(String from, String to, String subject, String body) {
        this.from = from;
        this.to = to;
        this.body = body;
        this.subject = subject;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getBody() {
        return this.body;
    }
}
