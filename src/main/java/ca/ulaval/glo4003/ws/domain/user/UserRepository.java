package ca.ulaval.glo4003.ws.domain.user;

public interface UserRepository {

    User findUserByEmail(String username);

    void save(User user);

    void create(User user) throws UserAlreadyExistException;
}
