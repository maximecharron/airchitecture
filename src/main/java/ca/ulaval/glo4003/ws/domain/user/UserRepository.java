package ca.ulaval.glo4003.ws.domain.user;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public interface UserRepository {

    User findUserByEmail(String username);

    void save(User user);

    void create(User user) throws UserAlreadyExistException;
}
