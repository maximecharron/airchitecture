package ca.ulaval.glo4003.air.domain.user.hashing;

public interface HashingStrategy {

    public String hashPassword(String password);

    public boolean validatePassword(String hashedPassword, String plaintextPassword);
}
