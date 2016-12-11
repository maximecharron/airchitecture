package ca.ulaval.glo4003.air.transfer.user;

import ca.ulaval.glo4003.air.transfer.user.dto.UserDto;
import ca.ulaval.glo4003.air.transfer.user.dto.UserPreferencesDto;
import ca.ulaval.glo4003.air.domain.user.User;
import ca.ulaval.glo4003.air.domain.user.UserSearchPreferences;
import ca.ulaval.glo4003.air.domain.user.UserSettings;

public class UserAssembler {

    public UserDto create(User user) {
        UserDto userDto = new UserDto();
        userDto.token = user.getToken();
        userDto.emailAddress = user.getEmailAddress();
        userDto.hidesWeightFilteredAlert = user.showsWeightFilteredAlert();
        userDto.isAdmin = user.isAdmin();
        return userDto;
    }

    public UserSettings createUserSettings(UserSettingsDto userSettingsDto) {
        UserSettings userSettings = new UserSettings();
        userSettings.setHideWeightFilteredAlert(userSettingsDto.hideWeightFilteredAlert);
        return userSettings;
    }

    public UserSearchPreferencesDto createUserSearchPreferences(UserSearchPreferences userSearchPreferences) {
        UserSearchPreferencesDto userSearchPreferencesDto = new UserSearchPreferencesDto();
        userSearchPreferencesDto.hasMostlySearchedForAirVivantFlights = userSearchPreferences.hasMostlySearchedForAirVivantFlights();
        userSearchPreferencesDto.hasMostlySearchedForEconomyClassFlights = userSearchPreferences.hasMostlySearchedForEconomyClassFlights();
        userSearchPreferencesDto.hasMostlySearchedForRegularClassFlights = userSearchPreferences.hasMostlySearchedForRegularClassFlights();
        userSearchPreferencesDto.hasMostlySearchedForBusinessClassFlights = userSearchPreferences.hasMostlySearchedForBusinessClassFlights();
        return userSearchPreferencesDto;
    }
}
