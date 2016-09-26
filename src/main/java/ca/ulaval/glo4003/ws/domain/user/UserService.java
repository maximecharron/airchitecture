package ca.ulaval.glo4003.ws.domain.user;

import javax.naming.AuthenticationException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {

    private Logger logger = Logger.getLogger(UserService.class.getName());
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String authenticateUser(String email, String password) throws AuthenticationException{
        try{
            User user = findUser(email);
            verifyPassword(user, password);
            return user.generateToken();
        } catch(RuntimeException e){
          throw new AuthenticationException("Authentication failed");
        }
    }

    public void createUser(String email, String password) throws UserAlreadyExistException{
        User user = new User(email, password);
        try {
            userRepository.create(user);
        } catch (UserAlreadyExistException e){
            logger.log(Level.INFO, "Unable to create user with email " + email + " because it already exists");
            throw e;
        }

    }

    private User findUser(String email) throws NoSuchUserException{
        User user = userRepository.findUserByEmail(email);
        if (user == null){
            throw new NoSuchUserException("User " + email + " does not exists");
        }
        return user;
    }

    private void verifyPassword(User user, String password) throws InvalidPasswordException{
        if (!user.isPasswordValid(password)){
            throw new InvalidPasswordException("Password is invalid");
        }
    }


}
