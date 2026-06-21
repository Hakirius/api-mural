package com.github.userrest.controllers;

import com.github.userrest.domain.dtos.MessageAddForm;
import com.github.userrest.domain.dtos.MessageListForm;
import com.github.userrest.services.MessageService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/messages")
public class SaveMessageController {

    private static final Logger logger = LoggerFactory.getLogger(SaveMessageController.class);

    private final MessageService messageService;

    public SaveMessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageListForm> save(
            @RequestBody @Valid MessageAddForm messageAddForm,
            Authentication authentication) {
        logger.info("POST /api/messages User: {}", authentication.getName());

        var savedMessage = messageService.save(messageAddForm);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedMessage.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedMessage);
    }
}
