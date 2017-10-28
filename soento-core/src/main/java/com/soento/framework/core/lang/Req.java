package com.soento.framework.core.lang;

import lombok.Getter;
import lombok.Setter;

public class Req<T> extends Pojo {
    /**
     * 请求头
     */
    @Getter
    @Setter
    private ReqHead head;
    /**
     * 请求体
     */
    @Getter
    @Setter
    private T body;
}
