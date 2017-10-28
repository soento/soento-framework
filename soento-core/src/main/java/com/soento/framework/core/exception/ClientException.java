package com.soento.framework.core.exception;

import com.soento.framework.core.lang.MessageInfo;

public class ClientException extends ServiceException {
    public ClientException() {
        super();
    }

    public ClientException(MessageInfo messageInfo) {
        super(messageInfo);
    }

    public ClientException(MessageInfo messageInfo, Throwable cause) {
        super(messageInfo, cause);
    }

    public ClientException(Throwable cause) {
        super(cause);
    }

    public static ClientException build(String code, String... args) {
        return new ClientException(MessageInfo.build(code, args));
    }

    public static ClientException build(Throwable cause, String code, String... args) {
        return new ClientException(MessageInfo.build(code, args), cause);
    }
}
