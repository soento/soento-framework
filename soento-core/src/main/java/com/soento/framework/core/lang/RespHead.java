package com.soento.framework.core.lang;

import com.soento.framework.core.enums.DateFormat;

import java.util.Date;

public class RespHead extends Pojo {
    /**
     * 应答返回码
     */
    private String code;
    /**
     * 应答描述
     */
    private String desc;
    /**
     * 应答时间，格式：yyyy-MM-dd HH:mm:ss SSS
     */
    private String replyDate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(String replyDate) {
        this.replyDate = replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = DateFormat.YYYYMMDDHHMISSMS_DASH.instance().format(replyDate);
    }
}
