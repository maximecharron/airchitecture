package ca.ulaval.glo4003.air.infrastructure.user.encoding;

import ca.ulaval.glo4003.air.domain.user.Exceptions.InvalidTokenException;
import ca.ulaval.glo4003.air.domain.user.encoding.TokenDecoder;
import ca.ulaval.glo4003.air.domain.user.encoding.TokenEncoder;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;

public class JWTTokenEncoder implements TokenEncoder, TokenDecoder {

    public final static String SECRET = "My_Super_Secret";
    private final static String ISSUER_KEY = "iss";
    private final JWTVerifier jwtVerifier;
    private final JWTSigner jwtSigner;

    public JWTTokenEncoder(JWTSigner jwtSigner, JWTVerifier jwtVerifier) {
        this.jwtSigner = jwtSigner;
        this.jwtVerifier = jwtVerifier;
    }

    public String encode(String issuer) {
        final HashMap<String, Object> claims = new HashMap<>();
        claims.put(ISSUER_KEY, issuer);

        return jwtSigner.sign(claims);
    }

    @Override
    public String decode(final String token) throws InvalidTokenException {
        try {
            return (String) jwtVerifier.verify(token).get(ISSUER_KEY);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | IOException | JWTVerifyException | IllegalStateException e) {
            throw new InvalidTokenException(e.getMessage(), e);
        }
    }
}
