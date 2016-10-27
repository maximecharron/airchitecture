package ca.ulaval.glo4003.air.domain.user;

public class UserFactory {

    private TokenEncoder tokenEncoder;
    private HashingStrategy hashingStrategy;

    public UserFactory (TokenEncoder tokenEncoder, HashingStrategy hashingStrategy){
        this.tokenEncoder = tokenEncoder;
        this.hashingStrategy = hashingStrategy;
    }

    public User createUser(String email, String password, boolean isAdmin){
        return new User(email, password, tokenEncoder, hashingStrategy, isAdmin);
    }

}
