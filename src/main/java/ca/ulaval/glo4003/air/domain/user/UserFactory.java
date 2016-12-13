package ca.ulaval.glo4003.air.domain.user;

import ca.ulaval.glo4003.air.domain.user.encoding.TokenEncoder;
import ca.ulaval.glo4003.air.domain.user.hashing.HashingStrategy;

public class UserFactory {

    private TokenEncoder tokenEncoder;
    private HashingStrategy hashingStrategy;

    public UserFactory(TokenEncoder tokenEncoder, HashingStrategy hashingStrategy) {
        this.tokenEncoder = tokenEncoder;
        this.hashingStrategy = hashingStrategy;
    }

    public User createUser(String email, String password, boolean isAdmin) {
        return new User.UserBuilder()
            .emailAddress(email)
            .password(password)
            .isAdmin(isAdmin)
            .tokenEncoder(tokenEncoder)
            .hashingStrategy(hashingStrategy)
            .userSearchPreferences(new UserSearchPreferences())
            .build();
    }
}
