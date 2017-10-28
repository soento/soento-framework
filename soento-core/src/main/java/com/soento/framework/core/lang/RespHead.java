package com.soento.framework.core.lang;

import lombok.Getter;
import lombok.Setter;

public class RespHead extends Pojo {
    /**
     * 应答返回码
     */
    @Getter
    @Setter
    private String code;
    /**
     * 应答描述
     */
    @Getter
    @Setter
    private String desc;
    /**
     * 应答时间，格式：yyyy-MM-dd HH:mm:ss SSS
     */
    @Getter
    @Setter
    private String replyDate;

}
