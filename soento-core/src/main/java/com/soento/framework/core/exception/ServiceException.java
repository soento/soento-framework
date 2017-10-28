package com.soento.framework.core.exception;

import com.soento.framework.core.consts.BaseMessageCode;
import com.soento.framework.core.lang.MessageInfo;

public class ServiceException extends RuntimeException {
    private MessageInfo messageInfo;

    public ServiceException() {
        super();
        defaultMessageInfo();
    }

    public ServiceException(MessageInfo messageInfo) {
        super(messageInfo.getMessage());
        this.messageInfo = messageInfo;
    }

    public ServiceException(MessageInfo messageInfo, Throwable cause) {
        super(messageInfo.getMessage(), cause);
        this.messageInfo = messageInfo;
    }

    public ServiceException(Throwable cause) {
        super(cause);
        defaultMessageInfo();
    }

    private void defaultMessageInfo() {
        this.messageInfo = new MessageInfo();
        this.messageInfo.setCode(BaseMessageCode.E9998);
    }

    public static ServiceException build(String code, String... args) {
        return new ServiceException(MessageInfo.build(code, args));
    }

    public static ServiceException build(Throwable cause, String code, String... args) {
        return new ServiceException(MessageInfo.build(code, args), cause);
    }

    public MessageInfo getMessageInfo() {
        return messageInfo;
    }
}
