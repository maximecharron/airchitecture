package ca.ulaval.glo4003.ws.domain.user;

import ca.ulaval.glo4003.ws.domain.user.exception.UserAlreadyExistException;

public interface UserRepository {

    User findUserByEmail(String username);

    void save(User user);

    void create(User user) throws UserAlreadyExistException;
}
