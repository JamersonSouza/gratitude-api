package tech.jamersondev.gratitude.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.jamersondev.gratitude.core.interfaces.UserService;
import tech.jamersondev.gratitude.core.model.User;
import tech.jamersondev.gratitude.core.repository.UserRepository;
import tech.jamersondev.gratitude.exceptions.EmailAlreadyExistsException;
import tech.jamersondev.gratitude.payload.form.CreateUserForm;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(CreateUserForm form){
        LOGGER.info("Create new user with name: {}", form.name());
        if(verifyExistUserEmail(form.email())){
            throw new EmailAlreadyExistsException(form.email());
        }
        String encodedPassword  = this.passwordEncoder.encode(form.password());
        User user = new User(form.email(), encodedPassword, form.name());
        return this.userRepository.save(user);
    }

    @Override
    public boolean verifyExistUserEmail(String email) {
      return this.userRepository.findByEmail(email).isPresent();
    }
}
