package ca.ulaval.glo4003.ws.infrastructure.user;

import ca.ulaval.glo4003.ws.domain.flight.Flight;
import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.UserFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDevDataFactory {

    private UserFactory userFactory;

    public UserDevDataFactory(UserFactory userfactory){
        this.userFactory = userfactory;
    }

    public List<User> createMockData() {
        List<User> users = new ArrayList<>();
        User user = userFactory.createUser("bob@test.com", "1234");
        users.add(user);
        return users;
    }
}
