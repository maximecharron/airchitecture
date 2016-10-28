package ca.ulaval.glo4003.air.domain.notification;

public class MessageBuilder {

    private String from;
    private String to;
    private String subject;
    private String body;

    public MessageBuilder addFrom(final String from) {
        this.from = from;
        return this;
    }

    public MessageBuilder addTo(final String to) {
        this.to = to;
        return this;
    }

    public MessageBuilder addSubject(final String subject) {
        this.subject = subject;
        return this;
    }

    public MessageBuilder addBody(final String body) {
        this.body = body;
        return this;
    }

    public Message build() {
        return new Message(this.from, this.to, this.subject, this.body);
    }

}
