package ca.ulaval.glo4003.air.domain.user.encoding;

public interface TokenEncoder {

    String encode(String email);
}
