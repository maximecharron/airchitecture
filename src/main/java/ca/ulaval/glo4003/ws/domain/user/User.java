package ca.ulaval.glo4003.ws.domain.user;

import ca.ulaval.glo4003.ws.service.TokenHandler;
import org.mindrot.jbcrypt.BCrypt;

public class User {

    private String email;
    private String password;
    private String token;

    public User(String email, String password) {
        this.email = email;
        hashPassword(password);
    }

    public String getEmail() {
        return this.email;
    }

    public String getToken() {
        return this.token;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isPasswordValid(String password) {
        return BCrypt.checkpw(password, this.password);
    }

    public void generateToken() {
        this.token = TokenHandler.createToken(email);
    }

    private void hashPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

}