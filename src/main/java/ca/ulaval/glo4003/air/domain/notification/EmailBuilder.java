package ca.ulaval.glo4003.air.domain.notification;

public class EmailBuilder {

    private String from;
    private String to;
    private String subject;
    private String body;

    public EmailBuilder addFrom(final String from) {
        this.from = from;
        return this;
    }

    public EmailBuilder addTo(final String to) {
        this.to = to;
        return this;
    }

    public EmailBuilder addSubject(final String subject) {
        this.subject = subject;
        return this;
    }

    public EmailBuilder addBody(final String body) {
        this.body = body;
        return this;
    }

    public Email build() {
        return new Email(this.from, this.to, this.subject, this.body);
    }
}
