package tech.jamersondev.gratitude.core.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.jamersondev.gratitude.core.interfaces.UserService;
import tech.jamersondev.gratitude.core.model.User;
import tech.jamersondev.gratitude.core.repository.UserRepository;
import tech.jamersondev.gratitude.payload.form.CreateUserForm;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(CreateUserForm form) {
        String encodedPassword  = this.passwordEncoder.encode(form.password());
        User user = new User(form.email(), encodedPassword, form.name());
        return this.userRepository.save(user);
    }
}
