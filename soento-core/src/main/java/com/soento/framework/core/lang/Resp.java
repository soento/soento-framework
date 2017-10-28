package com.soento.framework.core.lang;

import lombok.Getter;
import lombok.Setter;

public class Resp<T> extends Pojo {
    /**
     * 响应头
     */
    @Getter
    @Setter
    private RespHead head;
    /**
     * 响应体
     */
    @Getter
    @Setter
    private T body;
}
