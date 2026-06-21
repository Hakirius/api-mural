package com.github.userrest.domain.dtos;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;

public class MessageAddForm {

    @NotBlank(message = "from mandatory")
    private String from;

    @NotBlank(message = "to mandatory")
    private String to;

    @NotBlank(message = "message mandatory")
    private String message;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @AssertTrue(message = "from and to are the same")
    public boolean isFromAndToDifferent() {
        if (from == null || to == null || from.isBlank() || to.isBlank()) {
            return true;
        }
        return !from.trim().equals(to.trim());
    }
}
