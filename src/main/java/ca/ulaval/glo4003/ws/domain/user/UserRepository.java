package ca.ulaval.glo4003.ws.domain.user;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findUserByEmail(String username);

    void save(User user);

    void persist(User user) throws UserAlreadyExistException;
}
