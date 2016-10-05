package ca.ulaval.glo4003.ws.domain.user;

public interface TokenEncoder {

    public String encode(String email);
}
