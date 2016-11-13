package ca.ulaval.glo4003.air.domain.user.hashing;

public interface HashingStrategy {

    String hashPassword(String password);

    boolean validatePassword(String hashedPassword, String plaintextPassword);
}
