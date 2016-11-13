package ca.ulaval.glo4003.air.domain.user;

import ca.ulaval.glo4003.air.domain.user.encoding.TokenDecoder;

import javax.naming.AuthenticationException;
import java.util.logging.Logger;

public class UserService {

    private final Logger logger = Logger.getLogger(UserService.class.getName());
    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final TokenDecoder tokenDecoder;

    public UserService(UserRepository userRepository, UserFactory userFactory, TokenDecoder tokenDecoder) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
        this.tokenDecoder = tokenDecoder;
    }

    public User authenticateUser(String email, String password) throws AuthenticationException {
        try {
            User user = findUser(email);

            if (!user.isPasswordValid(password)) {
                logger.info("Unable to login with email " + user.getEmailAddress() + " because password is invalid");
                throw new InvalidPasswordException("Password is invalid");
            }

            user.generateToken();
            userRepository.update(user);
            return user;
        } catch (NoSuchUserException e) {
            logger.info("Unable to authenticate user with email " + email + " because it doesn't exist");
            throw new AuthenticationException("Authentication failed");
        } catch (InvalidPasswordException e) {
            logger.info("Unable to authenticate user with email " + email + " because password is invalid");
            throw new AuthenticationException("Authentication failed");
        }
    }

    public void createUser(String email, String password, boolean isAdmin) throws UserAlreadyExistException {
        User user = userFactory.createUser(email, password, isAdmin);
        try {
            userRepository.persist(user);
        } catch (UserAlreadyExistException e) {
            logger.info("Unable to persist user with email " + email + " because it already exists");
            throw e;
        }
    }

    public User updateAuthenticatedUser(String token, UserPreferences userPreferences) throws InvalidTokenException {
        try {
            User user = findUser(tokenDecoder.decode(token));
            updateUser(user, userPreferences);
            userRepository.update(user);
            return user;
        } catch (InvalidTokenException | NoSuchUserException e) {
            logger.info("Unable to find the authenticated user because the token is invalid.");
            throw new InvalidTokenException("Invalid token.", e);
        }
    }

    private void updateUser(User user, UserPreferences userPreferences) {
        if (!userPreferences.userWantsToSeeWeightFilteredAlert()) {
            user.stopShowingFilteredAlert();
        }
    }

    private User findUser(String email) throws NoSuchUserException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new NoSuchUserException("User " + email + " does not exists."));
    }
}
