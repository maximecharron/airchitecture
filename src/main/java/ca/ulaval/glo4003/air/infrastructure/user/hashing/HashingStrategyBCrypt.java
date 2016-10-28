package ca.ulaval.glo4003.air.infrastructure.user.hashing;

import ca.ulaval.glo4003.air.domain.user.hashing.HashingStrategy;
import org.mindrot.jbcrypt.BCrypt;

public class HashingStrategyBCrypt implements HashingStrategy {

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean validatePassword(String hashedPassword, String plaintextPassword) {
        return BCrypt.checkpw(plaintextPassword, hashedPassword);
    }
}
