package ca.ulaval.glo4003.air.domain.user;

import ca.ulaval.glo4003.air.domain.user.encoding.TokenEncoder;
import ca.ulaval.glo4003.air.domain.user.hashing.HashingStrategy;

public class User {

    private String emailAddress;
    private String password;
    private String token;
    private boolean isAdmin;
    private HashingStrategy hashingStrategy;
    private TokenEncoder tokenEncoder;
    private boolean showWeightFilteredAlert = true;

    private final UserSearchPreferences userSearchPreferences;

    public User(String emailAddress, String password, TokenEncoder tokenEncoder, HashingStrategy hashingStrategy, UserSearchPreferences userSearchPreferences, boolean isAdmin) {
        this.emailAddress = emailAddress;
        this.tokenEncoder = tokenEncoder;
        this.hashingStrategy = hashingStrategy;
        this.isAdmin = isAdmin;
        this.password = hashingStrategy.hashPassword(password);
        this.userSearchPreferences = userSearchPreferences;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public String getToken() {
        return this.token;
    }

    protected String getPassword() {
        return this.password;
    }

    public boolean isAdmin() {
        return this.isAdmin;
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

    public void incrementSearchPreferences(boolean hasSearchedForAirVivantFlights, boolean hasSearchedForEconomyClassFlights, boolean hasSearchedForRegularClassFlights, boolean hasSearchedForBusinessClassFlights) {
        userSearchPreferences.incrementSearchesPreferences(hasSearchedForAirVivantFlights, hasSearchedForEconomyClassFlights, hasSearchedForRegularClassFlights, hasSearchedForBusinessClassFlights);
    }

    public UserSearchPreferences getUserSearchPreferences() {
        return userSearchPreferences;
    }
}
