package ca.ulaval.glo4003.air.domain.notification;

public class Message {

    private final String from;
    private final String to;
    private final String body;
    private final String subject;

    public Message(final String from, final String to, final String subject, final String body) {
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