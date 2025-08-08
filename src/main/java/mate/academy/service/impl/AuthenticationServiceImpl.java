package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> maybeUser = userService.findByEmail(email);
        if (maybeUser.isEmpty()) {
            throw new AuthenticationException("User not found");
        }
        User user = maybeUser.get();
        String expectedHash = user.getPassword();
        String actualHash = HashUtil.hash(password, user.getSalt());
        if (!expectedHash.equals(actualHash)) {
            throw new AuthenticationException("Invalid email or password");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Email in use: " + email);
        }
        String salt = HashUtil.generateSalt();
        String hash = HashUtil.hash(password, salt);
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setSalt(salt);
        newUser.setPassword(hash);
        return userService.add(newUser);
    }
}
