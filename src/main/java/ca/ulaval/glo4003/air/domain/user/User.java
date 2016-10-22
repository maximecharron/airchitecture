package ca.ulaval.glo4003.air.domain.user;

public class User {
    private String emailAddress;
    private String password;
    private String token;
    private HashingStrategy hashingStrategy;
    private TokenEncoder tokenEncoder;
    private boolean showWeightFilteredAlert = true;

    public User(String emailAddress, String password, TokenEncoder tokenEncoder, HashingStrategy hashingStrategy) {
        this.emailAddress = emailAddress;
        this.tokenEncoder = tokenEncoder;
        this.hashingStrategy = hashingStrategy;
        hashPassword(password);
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public String getToken() {
        return this.token;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isPasswordValid(String password) {
        return hashingStrategy.validatePassword(this.password, password);
    }

    public void generateToken() {
        this.token = tokenEncoder.encode(emailAddress);
    }

    public boolean showsWeightFilteredAlert() {
        return this.showWeightFilteredAlert;
    }

    public void stopShowingFilteredAlert() {
        this.showWeightFilteredAlert = false;
    }

    private void hashPassword(String password) {
        this.password = hashingStrategy.hashPassword(password);
    }

}