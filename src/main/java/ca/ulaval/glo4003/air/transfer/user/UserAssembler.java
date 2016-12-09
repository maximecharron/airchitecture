package ca.ulaval.glo4003.air.transfer.user;

import ca.ulaval.glo4003.air.transfer.user.dto.UserDto;
import ca.ulaval.glo4003.air.transfer.user.dto.UserPreferencesDto;
import ca.ulaval.glo4003.air.domain.user.User;
import ca.ulaval.glo4003.air.domain.user.UserPreferences;

public class UserAssembler {

    public UserDto create(User user) {
        UserDto userDto = new UserDto();
        userDto.token = user.getToken();
        userDto.emailAddress = user.getEmailAddress();
        userDto.showsWeightFilteredAlert = user.showsWeightFilteredAlert();
        userDto.isAdmin = user.isAdmin();
        return userDto;
    }

    public UserPreferences createUserPreferences(UserPreferencesDto userPreferencesDto) {
        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setShowWeightFilteredAlert(userPreferencesDto.showWeightFilteredAlert);
        return userPreferences;
    }
}
