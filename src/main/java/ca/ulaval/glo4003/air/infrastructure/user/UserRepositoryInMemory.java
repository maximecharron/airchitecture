package ca.ulaval.glo4003.air.infrastructure.user;

import ca.ulaval.glo4003.air.domain.user.User;
import ca.ulaval.glo4003.air.domain.user.UserAlreadyExistException;
import ca.ulaval.glo4003.air.domain.user.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryInMemory implements UserRepository {
    private Map<String, User> users = new HashMap<>();

    @Override
    public void update(User user) {
        users.put(user.getEmailAddress(), user);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return Optional.ofNullable(users.get(email));
    }

    @Override
    public void persist(User user) throws UserAlreadyExistException {
        if (users.containsKey(user.getEmailAddress())) {
            throw new UserAlreadyExistException("User with email " + user.getEmailAddress() + " already exists.");
        } else {
            users.put(user.getEmailAddress(), user);
        }
    }
}
