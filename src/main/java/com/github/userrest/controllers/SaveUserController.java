package com.github.userrest.controllers;

import com.github.userrest.domain.dtos.UserAddForm;
import com.github.userrest.domain.dtos.UserListForm;
import com.github.userrest.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/user")
public class SaveUserController {

    private static final Logger logger = LoggerFactory.getLogger(SaveUserController.class);

    private final UserService userService;

    public SaveUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserListForm> save(@RequestBody @Valid UserAddForm userAddForm) {
        logger.info("POST /api/user username: {}", userAddForm.getUsername());

        var savedUser = userService.registerUser(userAddForm);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedUser);
    }
}
