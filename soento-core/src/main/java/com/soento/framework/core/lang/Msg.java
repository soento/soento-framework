package com.soento.framework.core.lang;

import com.soento.framework.core.support.MessageSourceAccessor;
import com.soento.framework.core.util.SpringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Msg extends Pojo {
    private String code;
    private List<String> args;

    public void addArgCode(String argCode) {
        if (args == null) {
            args = new ArrayList<String>();
        }
        args.add(argCode);
    }

    public String[] getArguments() {
        return getArguments(null);
    }

    public String[] getArguments(Locale locale) {
        MessageSourceAccessor msa = SpringUtil.getBean(MessageSourceAccessor.class);
        String[] arguments = null;
        if (this.args != null && this.args.size() > 0) {
            arguments = new String[this.args.size()];
            for (int i = 0; i < this.args.size(); i++) {
                arguments[i] = msa.getMessage(locale, this.args.get(i), null);
            }
        }
        return arguments;
    }

    public String getMessage() {
        return getMessage(null);
    }

    public String getMessage(Locale locale) {
        MessageSourceAccessor msa = SpringUtil.getBean(MessageSourceAccessor.class);
        return msa.getMessage(locale, this.code, getArguments());
    }

    public static Msg build(String code, String... args) {
        Msg msg = new Msg();
        msg.setCode(code);
        if (args != null && args.length > 0) {
            msg.setArgs(Arrays.asList(args));
        }
        return msg;
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
