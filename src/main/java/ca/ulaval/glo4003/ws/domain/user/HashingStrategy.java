package ca.ulaval.glo4003.ws.domain.user;

public interface HashingStrategy {

    public String hashPassword(String password);

    public boolean validatePassword(String hashedPassword, String plaintextPassword);
}
