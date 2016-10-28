package ca.ulaval.glo4003.air.domain.user;

import ca.ulaval.glo4003.air.api.user.dto.UserDto;
import ca.ulaval.glo4003.air.api.user.dto.UserUpdateDto;
import ca.ulaval.glo4003.air.domain.user.encoding.TokenDecoder;
import ca.ulaval.glo4003.air.transfer.user.UserAssembler;

import javax.naming.AuthenticationException;
import java.util.logging.Logger;

public class UserService {

    private Logger logger = Logger.getLogger(UserService.class.getName());
    private UserRepository userRepository;
    private UserAssembler userAssembler;
    private UserFactory userFactory;
    private TokenDecoder tokenDecoder;

    public UserService(UserRepository userRepository, UserAssembler userAssembler, UserFactory userFactory, TokenDecoder tokenDecoder) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
        this.userFactory = userFactory;
        this.tokenDecoder = tokenDecoder;
    }

    public UserDto authenticateUser(String email, String password) throws AuthenticationException {
        try {
            User user = findUser(email);
            verifyPassword(user, password);
            generateToken(user);
            userRepository.update(user);
            return userAssembler.create(user);
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

    public UserDto updateAuthenticatedUser(String token, UserUpdateDto userUpdateDto) throws InvalidTokenException {
        try {
            User user = findUserWithToken(token);
            updateUser(user, userUpdateDto);
            userRepository.update(user);
            return userAssembler.create(user);
        } catch (InvalidTokenException | NoSuchUserException e) {
            logger.info("Unable to find the authenticated user because the token is invalid.");
            throw new InvalidTokenException("Invalid token.", e);
        }
    }

    private void updateUser(User user, UserUpdateDto userUpdateDto) {
        if (!userUpdateDto.showWeightFilteredAlert) {
            user.stopShowingFilteredAlert();
        }
    }

    private User findUserWithToken(String token) throws NoSuchUserException, InvalidTokenException {
        return findUser(tokenDecoder.decode(token));
    }

    private User findUser(String email) throws NoSuchUserException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new NoSuchUserException("User " + email + " does not exists."));
    }

    private void generateToken(User user) {
        user.generateToken();
    }

    private void verifyPassword(User user, String password) throws InvalidPasswordException {
        if (!user.isPasswordValid(password)) {
            logger.info("Unable to login with email " + user.getEmailAddress() + " because password is invalid");
            throw new InvalidPasswordException("Password is invalid");
        }
    }
}
