package mate.academy.service;

import mate.academy.model.User;

public interface UserService {
    User findByEmail(String email);
    User login(String email, String password);
    User register(String email, String password, String salt);
}
