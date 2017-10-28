package com.soento.framework.core.enums;

/**
 * 客户端类型
 */
public enum Client {
    /**
     * 手机端
     */
    MOBILE("mobile"),
    /**
     * 平板端
     */
    TABLET("tablet"),
    /**
     * 个人电脑
     */
    PC("pc"),
    /**
     * H5
     */
    H5("h5"),
    /**
     * iOS应用
     */
    iOS("iOS"),
    /**
     * 应用
     */
    APP("app"),
    /**
     * Android应用
     */
    Android("Android"),
    /**
     * 未知
     */
    UNKNOWN("unknown");

    private final String value;

    private Client(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
