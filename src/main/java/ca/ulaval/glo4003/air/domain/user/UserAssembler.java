package ca.ulaval.glo4003.air.domain.user;

import ca.ulaval.glo4003.air.api.user.dto.UserDto;

public class UserAssembler {

    public UserDto create(User user) {
        UserDto userDto = new UserDto();
        userDto.token = user.getToken();
        userDto.emailAddress = user.getEmailAddress();
        return userDto;
    }
}
