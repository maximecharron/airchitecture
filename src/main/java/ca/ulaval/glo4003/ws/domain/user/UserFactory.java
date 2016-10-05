package ca.ulaval.glo4003.ws.domain.user;

public class UserFactory {

    private TokenGenerator tokenGenerator;
    private HashingStrategy hashingStrategy;

    public UserFactory (TokenGenerator tokenGenerator, HashingStrategy hashingStrategy){
        this.tokenGenerator = tokenGenerator;
        this.hashingStrategy = hashingStrategy;
    }

    public User createUser(String email, String password){
        return new User(email, password, tokenGenerator, hashingStrategy);
    }

}
