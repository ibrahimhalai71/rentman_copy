package com.rentmen.app.services.imp;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.rentmen.app.services.MessageService;

@Component
public class MessageServiceImpl implements MessageService {

    @Autowired
    @Qualifier("validationMessageSource")
    private MessageSource mSource;

    public MessageServiceImpl(MessageSource mSource) {
        this.mSource = mSource;
    }

    @Override
    public String getMessage(String id) {
        Locale locale = LocaleContextHolder.getLocale();
        String message;
        try {
            message = mSource.getMessage(id, null, locale);
        } catch (NoSuchMessageException nsme) {
            message = id;
        } catch (Exception e) {
            message = id;
        }
        return message;
    }

    @Override
    public String getLanguageMessage(String id, String language) {
        Locale locale = LocaleContextHolder.getLocale();
        if (language != null) {
            locale = new Locale(language);
        }
        String message;
        try {
            message = mSource.getMessage(id, null, locale);
        } catch (NoSuchMessageException nsme) {
            message = id;
        } catch (Exception e) {
            message = id;
        }
        return message;
    }

    @Override
    public String getMessage(String id, Object[] arg) {
        Locale locale = LocaleContextHolder.getLocale();
        return mSource.getMessage(id, arg, locale);
    }
}
