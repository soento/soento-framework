package com.soento.framework.core.support;

import com.soento.framework.core.lang.Msg;
import com.soento.framework.core.lang.Resp;
import com.soento.framework.core.lang.RespHead;

import java.util.Date;
import java.util.Locale;

public class PojoBuilder {

    public static <T> Resp<T> buildResp(Msg msg, Locale locale) {
        Resp resp = new Resp();
        RespHead head = new RespHead();
        head.setCode(msg.getCode());
        head.setDesc(msg.getMessage(locale));
        head.setReplyDate(new Date());
        resp.setHead(head);
        return resp;
    }
}
