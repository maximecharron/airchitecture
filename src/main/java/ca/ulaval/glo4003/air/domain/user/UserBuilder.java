package ca.ulaval.glo4003.air.domain.user;

import ca.ulaval.glo4003.air.domain.user.encoding.TokenEncoder;
import ca.ulaval.glo4003.air.domain.user.hashing.HashingStrategy;

public class UserBuilder {

    private String emailAddress;
    private String password;
    private TokenEncoder tokenEncoder;
    private HashingStrategy hashingStrategy;
    private boolean isAdmin;

    public UserBuilder setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setTokenEncoder(TokenEncoder tokenEncoder) {
        this.tokenEncoder = tokenEncoder;
        return this;
    }

    public UserBuilder setHashingStrategy(HashingStrategy hashingStrategy) {
        this.hashingStrategy = hashingStrategy;
        return this;
    }

    public UserBuilder setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }

    public User build() {
        return new User(emailAddress, password, tokenEncoder, hashingStrategy, isAdmin);
    }
}
