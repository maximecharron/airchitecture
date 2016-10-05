package ca.ulaval.glo4003.ws.domain.user;

public interface TokenGenerator {

    public String createToken(String email);
}
