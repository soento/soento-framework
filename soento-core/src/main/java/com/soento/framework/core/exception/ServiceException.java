package com.soento.framework.core.exception;

import com.soento.framework.core.consts.BaseMessageCode;
import com.soento.framework.core.lang.Msg;

public class ServiceException extends RuntimeException {
    private Msg msg;

    public ServiceException() {
        super();
        defaultMessageInfo();
    }

    public ServiceException(Msg msg) {
        super(msg.getMessage());
        this.msg = msg;
    }

    public ServiceException(Msg msg, Throwable cause) {
        super(msg.getMessage(), cause);
        this.msg = msg;
    }

    public ServiceException(Throwable cause) {
        super(cause);
        defaultMessageInfo();
    }

    private void defaultMessageInfo() {
        this.msg = new Msg();
        this.msg.setCode(BaseMessageCode.E9998);
    }

    public static ServiceException build(String code, String... args) {
        return new ServiceException(Msg.build(code, args));
    }

    public static ServiceException build(Throwable cause, String code, String... args) {
        return new ServiceException(Msg.build(code, args), cause);
    }

    public Msg getMsg() {
        return msg;
    }
}
