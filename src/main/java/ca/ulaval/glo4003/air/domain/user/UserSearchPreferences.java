package ca.ulaval.glo4003.air.domain.user;

public class UserSearchPreferences {

    private int totalNumberOfFlightSearches = 0;
    private int numberOfFlightSearchesWithAirVivantFilter = 0;
    private int numberOfFlightSearchesWithEconomyClassFilter = 0;
    private int numberOfFlightSearchesWithRegularClassFilter = 0;
    private int numberOfFlightSearchesWithBusinessClassFilter = 0;

    void incrementSearchesPreferences(boolean hasSearchedForAirVivantFlights, boolean hasSearchedForEconomyClassFlights, boolean hasSearchedForRegularClassFlights, boolean hasSearchedForBusinessClassFlights) {
        totalNumberOfFlightSearches++;
        if (hasSearchedForAirVivantFlights) {
            numberOfFlightSearchesWithAirVivantFilter++;
        }
        if (hasSearchedForEconomyClassFlights) {
            numberOfFlightSearchesWithEconomyClassFilter++;
        }
        if (hasSearchedForRegularClassFlights) {
            numberOfFlightSearchesWithRegularClassFilter++;
        }
        if (hasSearchedForBusinessClassFlights) {
            numberOfFlightSearchesWithBusinessClassFilter++;
        }
    }

    public boolean hasMostlySearchedForAirVivantFlights() {
        return numberOfFlightSearchesWithAirVivantFilter > totalNumberOfFlightSearches / 2;
    }

    public boolean hasMostlySearchedForEconomyClassFlights() {
        return numberOfFlightSearchesWithEconomyClassFilter > totalNumberOfFlightSearches / 2;
    }

    public boolean hasMostlySearchedForRegularClassFlights() {
        return numberOfFlightSearchesWithRegularClassFilter > totalNumberOfFlightSearches / 2;
    }

    public boolean hasMostlySearchedForBusinessClassFlights() {
        return numberOfFlightSearchesWithBusinessClassFilter > totalNumberOfFlightSearches / 2;
    }
}
