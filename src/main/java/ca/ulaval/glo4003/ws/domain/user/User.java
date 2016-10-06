package ca.ulaval.glo4003.ws.domain.user;

public class User {

    private String email;
    private String password;
    private String token;
    private HashingStrategy hashingStrategy;
    private TokenEncoder tokenEncoder;

    public User(String email, String password, TokenEncoder tokenEncoder, HashingStrategy hashingStrategy) {
        this.email = email;
        this.tokenEncoder = tokenEncoder;
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
        this.token = tokenEncoder.encode(email);
    }

    private void hashPassword(String password) {
        this.password = hashingStrategy.hashPassword(password);
    }

}