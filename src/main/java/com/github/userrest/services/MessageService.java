package com.github.userrest.services;

import com.github.userrest.domain.dtos.MessageAddForm;
import com.github.userrest.domain.dtos.MessageListForm;
import com.github.userrest.domain.entities.Message;
import com.github.userrest.repositories.MessageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<MessageListForm> listMessages() {
        return messageRepository.findAllByOrderByIdDesc()
                .stream()
                .map(this::toListForm)
                .toList();
    }

    public MessageListForm findById(Long id) {
        return messageRepository.findById(id)
                .map(this::toListForm)
                .orElseThrow(() -> new EntityNotFoundException("Message not found"));
    }

    public MessageListForm save(MessageAddForm messageAddForm) {
        var message = new Message();
        message.setFrom(messageAddForm.getFrom().trim());
        message.setTo(messageAddForm.getTo().trim());
        message.setMessage(messageAddForm.getMessage().trim());
        message.setTimestamp(LocalDateTime.now());

        return toListForm(messageRepository.save(message));
    }

    public void delete(Long id) {
        if (!messageRepository.existsById(id)) {
            throw new EntityNotFoundException("Message not found");
        }
        messageRepository.deleteById(id);
    }

    private MessageListForm toListForm(Message message) {
        var messageListForm = new MessageListForm();
        messageListForm.setId(message.getId());
        messageListForm.setFrom(message.getFrom());
        messageListForm.setTo(message.getTo());
        messageListForm.setMessage(message.getMessage());
        messageListForm.setTimestamp(message.getTimestamp());
        return messageListForm;
    }
}
