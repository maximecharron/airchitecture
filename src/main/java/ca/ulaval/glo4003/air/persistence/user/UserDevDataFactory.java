package ca.ulaval.glo4003.air.persistence.user;

import ca.ulaval.glo4003.air.domain.user.User;
import ca.ulaval.glo4003.air.domain.user.UserFactory;

import java.util.ArrayList;
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
