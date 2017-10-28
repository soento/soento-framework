package com.soento.framework.core.support;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class MessageSourceAccessor {

    private MessageSource messageSource;

    public MessageSourceAccessor(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String code, Object... args) {
        return this.messageSource.getMessage(code, args, null);
    }

    public String getMessage(String code, String... args) {
        return this.messageSource.getMessage(code, args, null);
    }

    public String getMessage(Locale locale, String code, Object... args) {
        return this.messageSource.getMessage(code, args, locale);
    }

    public String getMessage(Locale locale, String code, String... args) {
        return this.messageSource.getMessage(code, args, locale);
    }
}
