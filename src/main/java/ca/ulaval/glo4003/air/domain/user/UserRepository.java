package ca.ulaval.glo4003.air.domain.user;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findUserByEmail(String username);

    void update(User user);

    void persist(User user) throws UserAlreadyExistException;
}
