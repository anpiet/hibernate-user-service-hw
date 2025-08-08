package mate.academy.dao;

import mate.academy.model.User;

public interface UserDao {
    User get(String email);
    User create(String email, String password, String salt);
}
