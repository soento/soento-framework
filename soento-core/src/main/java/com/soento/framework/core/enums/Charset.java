package com.soento.framework.core.enums;

/**
 * 编码格式
 */
public enum Charset {
    /**
     * UTF-8
     */
    UTF8("UTF-8"),
    /**
     * GBK
     */
    GBK("GBK");

    private final String value;

    private Charset(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
