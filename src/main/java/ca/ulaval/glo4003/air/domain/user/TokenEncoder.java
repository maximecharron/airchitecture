package ca.ulaval.glo4003.air.domain.user;

public interface TokenEncoder {

    public String encode(String email);
}
