package ca.ulaval.glo4003.ws.domain.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;

import javax.naming.AuthenticationException;
import java.util.Optional;
import java.util.logging.Logger;

public class UserService {

    private Logger logger = Logger.getLogger(UserService.class.getName());
    private UserRepository userRepository;
    private UserAssembler userAssembler;
    private UserFactory userFactory;

    public UserService(UserRepository userRepository, UserAssembler userAssembler, UserFactory userFactory) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
        this.userFactory = userFactory;
    }

    public UserDto authenticateUser(String email, String password) throws AuthenticationException {
        try {
            User user = findUser(email);
            verifyPassword(user, password);
            generateToken(user);
            updateUser(user);
            return userAssembler.create(user);
        } catch (NoSuchUserException e) {
            logger.info("Unable to authenticate user with email " + email + " because it doesn't exist");
            throw new AuthenticationException("Authentication failed");
        } catch (InvalidPasswordException e) {
            logger.info("Unable to authenticate user with email " + email + " because password is invalid");
            throw new AuthenticationException("Authentication failed");
        }
    }

    public void createUser(String email, String password) throws UserAlreadyExistException {
        User user = userFactory.createUser(email, password);
        try {
            userRepository.persist(user);
        } catch (UserAlreadyExistException e) {
            logger.info("Unable to persist user with email " + email + " because it already exists");
            throw e;
        }

    }

    private User findUser(String email) throws NoSuchUserException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (!user.isPresent()) {
            logger.info("Unable to login with email " + email + " because user does not exist");
            throw new NoSuchUserException("User " + email + " does not exists");
        }
        return user.get();
    }

    private void generateToken(User user) {
        user.generateToken();
    }

    private void updateUser(User user) {
        userRepository.update(user);
    }

    private void verifyPassword(User user, String password) throws InvalidPasswordException {
        if (!user.isPasswordValid(password)) {
            logger.info("Unable to login with email " + user.getEmail() + " because password is invalid");
            throw new InvalidPasswordException("Password is invalid");
        }
    }

}
