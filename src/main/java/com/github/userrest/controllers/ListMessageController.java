package com.github.userrest.controllers;

import com.github.userrest.domain.dtos.MessageListForm;
import com.github.userrest.services.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class ListMessageController {

    private static final Logger logger = LoggerFactory.getLogger(ListMessageController.class);

    private final MessageService messageService;

    public ListMessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<List<MessageListForm>> list(Authentication authentication) {
        logger.info("GET /api/messages User: {}", authentication.getName());
        return ResponseEntity.ok(messageService.listMessages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageListForm> getById(@PathVariable Long id, Authentication authentication) {
        logger.info("GET /api/messages/{} User: {}", id, authentication.getName());
        return ResponseEntity.ok(messageService.findById(id));
    }
}
