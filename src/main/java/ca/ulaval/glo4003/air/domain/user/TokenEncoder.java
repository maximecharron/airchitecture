package ca.ulaval.glo4003.air.domain.user;

public interface TokenEncoder {
    String encode(String email);
}
