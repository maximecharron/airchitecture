package ca.ulaval.glo4003.ws.domain.user;

public class UserFactory {

    private TokenEncoder tokenEncoder;
    private HashingStrategy hashingStrategy;

    public UserFactory (TokenEncoder tokenEncoder, HashingStrategy hashingStrategy){
        this.tokenEncoder = tokenEncoder;
        this.hashingStrategy = hashingStrategy;
    }

    public User createUser(String email, String password){
        return new User(email, password, tokenEncoder, hashingStrategy);
    }

}
