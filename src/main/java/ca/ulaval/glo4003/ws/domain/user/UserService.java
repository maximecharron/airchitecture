package ca.ulaval.glo4003.ws.domain.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
import ca.ulaval.glo4003.ws.domain.user.exception.InvalidPasswordException;
import ca.ulaval.glo4003.ws.domain.user.exception.NoSuchUserException;
import ca.ulaval.glo4003.ws.domain.user.exception.UserAlreadyExistException;

import javax.naming.AuthenticationException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {

    private Logger logger = Logger.getLogger(UserService.class.getName());
    private UserRepository userRepository;
    private UserAssembler userAssembler;

    public UserService(UserRepository userRepository, UserAssembler userAssembler) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
    }

    public UserDto authenticateUser(String email, String password) throws AuthenticationException{
        try{
            User user = findUser(email);
            verifyPassword(user, password);
            generateToken(user);
            updateUser(user);
            return userAssembler.create(user);
        } catch(RuntimeException e){
          throw new AuthenticationException("Authentication failed");
        }
    }

    public void createUser(String email, String password) throws UserAlreadyExistException {
        User user = new User(email, password);
        try {
            userRepository.create(user);
        } catch (UserAlreadyExistException e){
            logger.log(Level.INFO, "Unable to create user with email " + email + " because it already exists");
            throw e;
        }

    }

    private User findUser(String email) throws NoSuchUserException {
        User user = userRepository.findUserByEmail(email);
        if (user == null){
            throw new NoSuchUserException("User " + email + " does not exists");
        }
        return user;
    }

    private void generateToken(User user){
        user.generateToken();
    }

    private void updateUser(User user){
        userRepository.save(user);
    }

    private void verifyPassword(User user, String password) throws InvalidPasswordException {
        if (!user.isPasswordValid(password)){
            throw new InvalidPasswordException("Password is invalid");
        }
    }


}
