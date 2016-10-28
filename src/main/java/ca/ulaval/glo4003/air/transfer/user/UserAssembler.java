package ca.ulaval.glo4003.air.transfer.user;

import ca.ulaval.glo4003.air.api.user.dto.UserDto;
import ca.ulaval.glo4003.air.domain.user.User;

public class UserAssembler {
    public UserDto create(User user) {
        UserDto userDto = new UserDto();
        userDto.token = user.getToken();
        userDto.emailAddress = user.getEmailAddress();
        userDto.showsWeightFilteredAlert = user.showsWeightFilteredAlert();
        userDto.isAdmin = user.isAdmin();
        return userDto;
    }
}
