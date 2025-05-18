package tech.jamersondev.gratitude.core.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import tech.jamersondev.gratitude.core.model.User;
import tech.jamersondev.gratitude.core.service.UserServiceImpl;
import tech.jamersondev.gratitude.payload.form.CreateUserForm;
import tech.jamersondev.gratitude.payload.form.UserForm;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping
    public ResponseEntity<UserForm> createUser(@RequestBody @Valid CreateUserForm form, UriComponentsBuilder uriBuilder){
        User user = this.userServiceImpl.create(form);
        uriBuilder.path("/user/{identifier}").buildAndExpand(user.getIdentifier()).toUri();
        return ResponseEntity.ok().body(new UserForm(user));
    }
}
