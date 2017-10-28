package com.soento.framework.core.enums;

import java.util.regex.Pattern;

/**
 * 正则表达式
 */
public enum Regular {
    /**
     * 半角英数
     */
    HALF_ALPHA_DIGIT("^[0-9a-zA-Z]+$"),
    /**
     * 半角英文
     */
    HALF_ALPHA("^[a-zA-Z]+$"),
    /**
     * 半角数字
     */
    HALF_DIGIT("^[0-9]+$"),
    /**
     * 数值
     */
    HALF_FRACTION_DIGIT("[-+]?[0-9]+(\\.[0-9]*)?"),
    /**
     * 小数点
     */
    DECIMAL_POINT("\\."),
    /**
     * 邮箱
     */
    EMAIL("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"),
    /**
     * 密码
     */
    PASSWORD("^[a-zA-Z0-9]{6,16}$"),
    /**
     * 手机号
     */
    MOBILE("^((13)|(15])|(18))\\d{9}$"),
    /**
     * 汉字
     */
    CHINESE("^[\u4e00-\u9fa5],{0,}$"),
    /**
     * URL
     */
    URL("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?"),
    /**
     * IP地址
     */
    IP("(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})"),
    /**
     * 用户名
     */
    USERNAME("^[a-zA-Z]\\w{5,17}$"),
    /**
     * 邮编
     */
    ZIP_CODE("[1-9]\\d{5}(?!\\d)"),
    /**
     * 手机端
     */
    USER_AGENT_MOBILE("\\b(ip(hone|od)|android|opera m(ob|in)i|windows (phone|ce)|blackberry|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp|laystation portable)|nokia|fennec|htc[-_]|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b"),
    /**
     * 平板端
     */
    USER_AGENT_TABLET("\\b(ipad|tablet|(Nexus 7)|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b");

    private final String value;

    private Regular(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public Pattern pattern() {
        return Pattern.compile(value);
    }

    public Pattern pattern(int flag) {
        return Pattern.compile(value, flag);
    }
}