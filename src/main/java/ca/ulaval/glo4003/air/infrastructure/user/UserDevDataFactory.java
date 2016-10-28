package ca.ulaval.glo4003.air.infrastructure.user;

import ca.ulaval.glo4003.air.domain.user.User;
import ca.ulaval.glo4003.air.domain.user.UserFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDevDataFactory {

    private UserFactory userFactory;

    public UserDevDataFactory(UserFactory userfactory) {
        this.userFactory = userfactory;
    }

    public List<User> createMockData() {
        List<User> users = new ArrayList<>();
        User user = userFactory.createUser("bob@test.com", "1234", false);
        users.add(user);
        User admin = userFactory.createUser("admin@airchitecture.com", "admin", true);
        users.add(admin);
        return users;
    }
}
