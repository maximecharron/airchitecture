package ca.ulaval.glo4003.air.domain.user;

import ca.ulaval.glo4003.air.domain.user.encoding.TokenEncoder;
import ca.ulaval.glo4003.air.domain.user.hashing.HashingStrategy;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class User {

    private String emailAddress;
    private String password;
    private String token;
    private boolean isAdmin;
    private HashingStrategy hashingStrategy;
    private TokenEncoder tokenEncoder;

    private final UserSearchPreferences userSearchPreferences;
    private boolean showingWeightFilteredAlertPreference = true;
    private Map<String, Integer> preferredDestinations = new HashMap<>();

    public static class UserBuilder {

        private String emailAddress;
        private String password;
        private TokenEncoder tokenEncoder;
        private HashingStrategy hashingStrategy;
        private UserSearchPreferences userSearchPreferences;
        private boolean isAdmin;


        public UserBuilder emailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder tokenEncoder(TokenEncoder tokenEncoder) {
            this.tokenEncoder = tokenEncoder;
            return this;
        }

        public UserBuilder hashingStrategy(HashingStrategy hashingStrategy) {
            this.hashingStrategy = hashingStrategy;
            return this;
        }

        public UserBuilder userSearchPreferences(UserSearchPreferences userSearchPreferences) {
            this.userSearchPreferences = userSearchPreferences;
            return this;
        }

        public UserBuilder isAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    private User(UserBuilder userBuilder) {
        this.emailAddress = userBuilder.emailAddress;
        this.tokenEncoder = userBuilder.tokenEncoder;
        this.hashingStrategy = userBuilder.hashingStrategy;
        this.userSearchPreferences = userBuilder.userSearchPreferences;
        this.isAdmin = userBuilder.isAdmin;
        this.password = hashingStrategy.hashPassword(userBuilder.password);
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
        return this.showingWeightFilteredAlertPreference;
    }

    public void turnOffShowingFilteredAlertPreference() {
        this.showingWeightFilteredAlertPreference = false;
    }

    public void incrementSearchPreferences(boolean hasSearchedForAirVivantFlights, boolean hasSearchedForEconomyClassFlights, boolean hasSearchedForRegularClassFlights, boolean hasSearchedForBusinessClassFlights) {
        userSearchPreferences.incrementSearchesPreferences(hasSearchedForAirVivantFlights, hasSearchedForEconomyClassFlights, hasSearchedForRegularClassFlights, hasSearchedForBusinessClassFlights);
    }

    public void addPreferredDestination(String destination){
        if (preferredDestinations.containsKey(destination)){
            preferredDestinations.put(destination, preferredDestinations.get(destination) + 1);
        } else {
            preferredDestinations.put(destination, 1);
        }
    }

    public Map<String, Integer> getPreferredDestination(){
        //Some Java8 lambda to reverseSort the map before returning it.
        Map<String, Integer> sortedMap =
                preferredDestinations.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));
        return sortedMap;
    }

    public UserSearchPreferences getUserSearchPreferences() {
        return userSearchPreferences;
    }
}
