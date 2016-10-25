package ca.ulaval.glo4003.air.domain.user;

public interface TokenDecoder {
    String decode(String token) throws InvalidTokenException;
}
