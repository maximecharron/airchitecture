package ca.ulaval.glo4003.ws.infrastructure.user;

import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.UserAlreadyExistException;
import ca.ulaval.glo4003.ws.domain.user.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryInMemory implements UserRepository {


    private Map<String, User> users = new HashMap<>();

    @Override
    public void update(User user) {
        users.put(user.getEmail(), user);
    }

    @Override
    public Optional<User> findUserByEmail(String username) {
        return Optional.ofNullable(users.get(username));
    }

    @Override
    public void persist(User user) throws UserAlreadyExistException {
        if (users.containsKey(user.getEmail())){
            throw new UserAlreadyExistException("User with email " + user.getEmail() + " already exists.");
        } else {
            users.put(user.getEmail(), user);
        }

    }
}
