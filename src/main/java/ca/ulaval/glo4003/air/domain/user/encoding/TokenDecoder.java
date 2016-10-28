package ca.ulaval.glo4003.air.domain.user.encoding;

import ca.ulaval.glo4003.air.domain.user.InvalidTokenException;

public interface TokenDecoder {
    String decode(String token) throws InvalidTokenException;
}
