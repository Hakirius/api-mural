package com.github.userrest.controllers;

import com.github.userrest.services.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class DeleteMessageController {

    private static final Logger logger = LoggerFactory.getLogger(DeleteMessageController.class);

    private final MessageService messageService;

    public DeleteMessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
        logger.info("DELETE /api/messages/{} User: {}", id, authentication.getName());
        messageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
