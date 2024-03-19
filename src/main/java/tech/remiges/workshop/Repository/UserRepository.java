package tech.remiges.workshop.Repository;

import org.springframework.stereotype.Repository;

import tech.remiges.workshop.Entity.User;

@Repository
public class UserRepository {
    public User findUserByEmail(String email) {
        User user = new User(email, "123456");
        user.setFirstName("test");
        user.setLastName("login");
        return user;
    }
}
