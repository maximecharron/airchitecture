package ca.ulaval.glo4003.air.domain.user.encoders;

import ca.ulaval.glo4003.air.domain.user.Exceptions.InvalidTokenException;
import ca.ulaval.glo4003.air.infrastructure.user.encoding.JWTTokenEncoder;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class JWTTokenEncoderTest {

    private static final String AN_EMAIL = "bob.lamarche@gmail.com";
    private static final String A_TOKEN = "boblamarchetoken";
    private static final String AN_INVALID_TOKEN = "boblacoursetoken";
    @Mock
    private JWTSigner jwtSigner;
    @Mock
    private JWTVerifier jwtVerifier;

    @Before
    public void setup() {

    }

    @Test
    public void givenAnEmail_whenEncodingAToken_thenTheResultIsEncoded() {
        JWTTokenEncoder jwtTokenEncoder = new JWTTokenEncoder(jwtSigner, jwtVerifier);
        given(jwtSigner.sign(any())).willReturn(A_TOKEN);

        String result = jwtTokenEncoder.encode(AN_EMAIL);

        assertEquals(result, A_TOKEN);
    }

    @Test
    public void givenAToken_whenDecodingTheToken_thenTheCorrectEmailIsReturned() throws SignatureException, NoSuchAlgorithmException, JWTVerifyException, InvalidKeyException, IOException, InvalidTokenException {
        JWTTokenEncoder jwtTokenEncoder = new JWTTokenEncoder(new JWTSigner(JWTTokenEncoder.SECRET), new JWTVerifier(JWTTokenEncoder.SECRET));
        String token = jwtTokenEncoder.encode(AN_EMAIL);

        String result = jwtTokenEncoder.decode(token);

        assertEquals(result, AN_EMAIL);
    }

    @Test(expected = InvalidTokenException.class)
    public void givenAnInvalidToken_whenDecodingTheToken_thenAnExceptionIsThrown() throws InvalidTokenException {
        JWTTokenEncoder jwtTokenEncoder = new JWTTokenEncoder(new JWTSigner(JWTTokenEncoder.SECRET), new JWTVerifier(JWTTokenEncoder.SECRET));

        jwtTokenEncoder.decode(AN_INVALID_TOKEN);
    }
}
