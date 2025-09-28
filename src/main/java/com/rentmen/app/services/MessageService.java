package com.rentmen.app.services;

import com.rentmen.app.entities.Job;

public interface MessageService {
    String getMessage(String id);

    String getMessage(String id, Object[] args);

    String getLanguageMessage(String id, String language);

    void sendMessageToSp(Job job);
}
