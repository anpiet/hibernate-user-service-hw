package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private UserDao userDao;

    @Override
    public User add(String email, String password) throws RegistrationException {
        if (findByEmail(email).isPresent()) {
            throw new RegistrationException("Email in use: " + email);
        }
        String salt = HashUtil.generateSalt();
        String hash = HashUtil.hash(password, salt);
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setSalt(salt);
        newUser.setPassword(hash);
        return userDao.add(newUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
