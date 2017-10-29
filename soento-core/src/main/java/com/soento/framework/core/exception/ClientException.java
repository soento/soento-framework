package com.soento.framework.core.exception;

import com.soento.framework.core.lang.Msg;

public class ClientException extends ServiceException {
    public ClientException() {
        super();
    }

    public ClientException(Msg msg) {
        super(msg);
    }

    public ClientException(Msg msg, Throwable cause) {
        super(msg, cause);
    }

    public ClientException(Throwable cause) {
        super(cause);
    }

    public static ClientException build(String code, String... args) {
        return new ClientException(Msg.build(code, args));
    }

    public static ClientException build(Throwable cause, String code, String... args) {
        return new ClientException(Msg.build(code, args), cause);
    }
}
