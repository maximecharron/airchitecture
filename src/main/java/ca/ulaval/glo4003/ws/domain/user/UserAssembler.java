package ca.ulaval.glo4003.ws.domain.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;

public class UserAssembler {

    public UserDto create(User user) {
        UserDto userDto = new UserDto();
        userDto.token = user.getToken();
        userDto.email = user.getEmail();
        return userDto;
    }
}
