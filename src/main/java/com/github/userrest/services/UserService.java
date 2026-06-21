package com.github.userrest.services;

import com.github.userrest.domain.dtos.UserAddForm;
import com.github.userrest.domain.dtos.UserListForm;
import com.github.userrest.domain.entities.User;
import com.github.userrest.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("username already exists");
        }
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        logger.info("user: {}, password: {}", user.getUsername(), encodedPassword);
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public UserListForm registerUser(UserAddForm userAddForm) {
        var user = registerUser(new User(userAddForm.getUsername(), userAddForm.getPassword(), "USER"));
        return toListForm(user);
    }

    public User ensureUser(String username, String password, String role) {
        return userRepository.findByUsername(username)
                .orElseGet(() -> registerUser(new User(username, password, role)));
    }

    public long count() {
        return userRepository.count();
    }

    public List<UserListForm> listAllUsers() {
        List<UserListForm> userListForms = new ArrayList<>();
        userRepository.findAll().forEach(u -> {
            userListForms.add(toListForm(u));
        });
        return userListForms;
    }

    private UserListForm toListForm(User user) {
        UserListForm userListForm = new UserListForm();
        userListForm.setId(user.getId());
        userListForm.setUsername(user.getUsername());
        userListForm.setRole(user.getRole());
        return userListForm;
    }
}
