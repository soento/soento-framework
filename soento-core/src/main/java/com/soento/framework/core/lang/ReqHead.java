package com.soento.framework.core.lang;

import lombok.Getter;
import lombok.Setter;

public class ReqHead extends Pojo {
    /**
     * 访问秘钥
     */
    @Getter
    @Setter
    private String secret;
    /**
     * 应用ID
     */
    @Getter
    @Setter
    private String appId;
    /**
     * 设备编号
     */
    @Getter
    @Setter
    private String deviceNo;
    /**
     * 前置流水号
     */
    @Getter
    @Setter
    private String frontSerialNo;
    /**
     * 访问时间，格式：yyyy-MM-dd HH:mm:ss SSS
     */
    @Getter
    @Setter
    private String accessDate;
}
