package com.rentmen.app.services;

public interface MessageService {
    String getMessage(String id);

    String getMessage(String id, Object[] args);
    String getLanguageMessage(String id, String language);
}
