package com.soento.framework.core.lang;

import com.soento.framework.core.support.MessageSourceAccessor;
import com.soento.framework.core.util.SpringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageInfo extends Pojo {
    private String code;
    private List<String> args;

    public void addArgCode(String argCode) {
        if (args == null) {
            args = new ArrayList<String>();
        }
        args.add(argCode);
    }

    public String[] getArguments() {
        MessageSourceAccessor msa = SpringUtil.getBean(MessageSourceAccessor.class);
        String[] arguments = null;
        if (this.args != null && this.args.size() > 0) {
            arguments = new String[this.args.size()];
            for (int i = 0; i < this.args.size(); i++) {
                arguments[i] = msa.getMessage(this.args.get(i));
            }
        }
        return arguments;
    }

    public String getMessage() {
        MessageSourceAccessor msa = SpringUtil.getBean(MessageSourceAccessor.class);
        return msa.getMessage(this.code, getArguments());
    }

    public static MessageInfo build(String code, String... args) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setCode(code);
        if (args != null && args.length > 0) {
            messageInfo.setArgs(Arrays.asList(args));
        }
        return messageInfo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }
}
