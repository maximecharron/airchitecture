package ca.ulaval.glo4003.air.service.user;

import ca.ulaval.glo4003.air.domain.user.Exceptions.InvalidPasswordException;
import ca.ulaval.glo4003.air.domain.user.Exceptions.InvalidTokenException;
import ca.ulaval.glo4003.air.domain.user.Exceptions.UserAlreadyExistException;
import ca.ulaval.glo4003.air.domain.user.Exceptions.UserNotFoundException;
import ca.ulaval.glo4003.air.transfer.user.dto.UserDto;
import ca.ulaval.glo4003.air.transfer.user.dto.UserPreferencesDto;
import ca.ulaval.glo4003.air.domain.user.*;
import ca.ulaval.glo4003.air.domain.user.encoding.TokenDecoder;
import ca.ulaval.glo4003.air.transfer.user.UserAssembler;

import javax.naming.AuthenticationException;
import java.util.logging.Logger;

public class UserService {

    private final Logger logger = Logger.getLogger(UserService.class.getName());
    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final TokenDecoder tokenDecoder;
    private final UserAssembler userAssembler;

    public UserService(UserRepository userRepository, UserFactory userFactory, TokenDecoder tokenDecoder, UserAssembler userAssembler) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
        this.tokenDecoder = tokenDecoder;
        this.userAssembler = userAssembler;
    }

    public UserDto authenticateUser(String email, String password) throws AuthenticationException {
        try {
            User user = findUser(email);

            if (!user.isPasswordValid(password)) {
                logger.info("Unable to login with email " + user.getEmailAddress() + " because password is invalid");
                throw new InvalidPasswordException("Password is invalid");
            }

            user.generateToken();
            userRepository.update(user);
            return userAssembler.create(user);
        } catch (UserNotFoundException e) {
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

    public User authenticateUser(String token) throws InvalidTokenException {
        try {
            return findUser(tokenDecoder.decode(token));
        } catch (UserNotFoundException e) {
            logger.info("Unable to find the user because the token is invalid.");
            throw new InvalidTokenException("Unable to find the user because the token is invalid.", e);
        }
    }

    public UserDto updateAuthenticatedUser(String token, UserPreferencesDto userPreferencesDto) throws InvalidTokenException {
        UserPreferences userPreferences = userAssembler.createUserPreferences(userPreferencesDto);
        User user = authenticateUser(token);
        if (!userPreferences.userWantsToSeeWeightFilteredAlert()) {
            user.stopShowingFilteredAlert();
        }
        userRepository.update(user);
        return userAssembler.create(user);
    }

    private User findUser(String email) throws UserNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException("User " + email + " does not exists."));
    }
}
