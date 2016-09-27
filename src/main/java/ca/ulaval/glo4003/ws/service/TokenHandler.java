package ca.ulaval.glo4003.ws.service;

import com.auth0.jwt.JWTSigner;

import java.util.HashMap;

public class TokenHandler {

    private final static String secret = "My_Super_Secret";
    private final static long A_DAY = 86400L;

    public static String createToken(String issuer){

        final long issuedTime = System.currentTimeMillis() / 1000l;
        final long expiration = issuedTime + A_DAY;

        final JWTSigner signer = new JWTSigner(secret);
        final HashMap<String, Object> claims = new HashMap<>();
        claims.put("iss", issuer);
        claims.put("exp", expiration);
        claims.put("iat", issuedTime);

       return signer.sign(claims);
    }
}
