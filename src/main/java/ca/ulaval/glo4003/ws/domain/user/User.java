package ca.ulaval.glo4003.ws.domain.user;

import org.mindrot.jbcrypt.BCrypt;

public class User {

    private String email;
    private String password;
    private String token;
    private HashingStrategy hashingStrategy;
    private TokenGenerator tokenGenerator;

    public User(String email, String password, TokenGenerator tokenGenerator, HashingStrategy hashingStrategy) {
        this.email = email;
        this.tokenGenerator = tokenGenerator;
        this.hashingStrategy = hashingStrategy;
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
        return hashingStrategy.validatePassword(this.password, password);
    }

    public void generateToken() {
        this.token = tokenGenerator.createToken(email);
    }

    private void hashPassword(String password) {
        this.password = hashingStrategy.hashPassword(password);
    }

}