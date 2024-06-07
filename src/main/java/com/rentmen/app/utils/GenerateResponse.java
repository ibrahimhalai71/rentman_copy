package com.rentmen.app.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.rentmen.app.DTO.Response;
import com.rentmen.app.services.MessageService;

@Component
public class GenerateResponse {
    private static final Logger logger = LoggerFactory.getLogger(GenerateResponse.class);
    private static MessageService messageService;

    @Autowired
    public GenerateResponse(MessageService messageService) {
        GenerateResponse.messageService = messageService;
    }

    public static ResponseEntity<?> ok(String message, Object data) {
        try {
            return ResponseEntity.ok(Response.getInstance(messageService.getMessage(message), data));
        } catch (Exception e) {
            logger.warn(String.format("Message not set for %s", message));
            message = message.replaceAll("_"," ").replaceAll("message","");
            return ResponseEntity.ok(Response.getInstance(message, data));
        }
    }

    public static ResponseEntity<?> ok(String message) {
        try {
            return ResponseEntity.ok(Response.getInstance(messageService.getMessage(message)));
        } catch (Exception e) {
            logger.warn(String.format("Message not set for %s", message));
            return ResponseEntity.ok(Response.getInstance(message));
        }
    }

    public static ResponseEntity<?> ok(String message, Long unreadCounter) {
        if (message.contains("_")) {
            return ResponseEntity.ok(Response.getInstance(messageService.getMessage(message), unreadCounter));
        } else
            return ResponseEntity.ok(Response.getInstance(message, unreadCounter));
    }

    public static ResponseEntity<?> ok(String message, Long count, Object data) {
        try {
            return ResponseEntity.ok(Response.getInstance(messageService.getMessage(message), count, data));
        } catch (Exception e) {
            logger.warn(String.format("Message not set for %s", message));
            return ResponseEntity.ok(Response.getInstance(message));
        }
    }


    public static ResponseEntity<?> badRequest(String message) {
        try {
            return ResponseEntity.badRequest().body(Response.getInstance(messageService.getMessage(message)));
        } catch (Exception e) {
            logger.warn(String.format("Message not set for %s", message));
            return ResponseEntity.badRequest().body(Response.getInstance(message));
        }
    }

    public static ResponseEntity<?> noContent() {
        return ResponseEntity.noContent().build();
    }
}
