package ca.ulaval.glo4003.ws.infrastructure.user;

import ca.ulaval.glo4003.ws.domain.user.TokenEncoder;
import com.auth0.jwt.JWTSigner;

import java.util.HashMap;

public class JWTTokenEncoder implements TokenEncoder {

    public final static String SECRET = "My_Super_Secret";
    private final static long A_DAY = 86400L;
    private JWTSigner jwtSigner;

    public JWTTokenEncoder(JWTSigner jwtSigner){
        this.jwtSigner = jwtSigner;
    }

    public String encode(String issuer){
        final long issuedTime = System.currentTimeMillis() / 1000L;
        final long expiration = issuedTime + A_DAY;

        final HashMap<String, Object> claims = new HashMap<>();
        claims.put("iss", issuer);
        claims.put("exp", expiration);
        claims.put("iat", issuedTime);

       return this.jwtSigner.sign(claims);
    }
}
