package ca.ulaval.glo4003.ws.service;

import ca.ulaval.glo4003.ws.domain.user.TokenGenerator;
import com.auth0.jwt.JWTSigner;

import java.util.HashMap;

public class TokenGeneratorImpl implements TokenGenerator {

    private final static String SECRET = "My_Super_Secret";
    private final static long A_DAY = 86400L;

    public String createToken(String issuer){

        final long issuedTime = System.currentTimeMillis() / 1000l;
        final long expiration = issuedTime + A_DAY;

        final JWTSigner signer = new JWTSigner(SECRET);
        final HashMap<String, Object> claims = new HashMap<>();
        claims.put("iss", issuer);
        claims.put("exp", expiration);
        claims.put("iat", issuedTime);

       return signer.sign(claims);
    }
}
