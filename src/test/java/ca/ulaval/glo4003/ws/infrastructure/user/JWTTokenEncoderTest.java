package ca.ulaval.glo4003.ws.infrastructure.user;

import com.auth0.jwt.JWTSigner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class JWTTokenEncoderTest {
    private JWTTokenEncoder jwtTokenEncoder;
    private static final String AN_EMAIL = "bob.lamarche@gmail.com";
    private static final String A_TOKEN = "boblamarchetoken";
    @Mock
    private JWTSigner jwtSigner;

    @Before
    public void setup() {
        jwtTokenEncoder = new JWTTokenEncoder(jwtSigner);
    }

    @Test
    public void givenAnEmail_whenEncodingAToken_thenTheResultIsEncoded() {
        given(jwtSigner.sign(any())).willReturn(A_TOKEN);

        String result = jwtTokenEncoder.encode(AN_EMAIL);

        assertEquals(result, A_TOKEN);
    }
}
