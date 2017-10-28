package com.soento.framework.core.enums;

import java.util.regex.Pattern;

/**
 * 正则表达式
 */
public enum RegEx {
    /**
     * 英文字母 、数字和下划线
     */
    GENERAL("^\\w+$"),
    /**
     * 数字
     */
    NUMBERS("\\d+"),
    /**
     * 汉字
     */
    CHINESE("^[\u4E00-\u9FFF]+"),
    /**
     * IPV4地址
     */
    IPV4("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b"),
    /**
     * 邮箱
     */
    EMAIL("(\\w|.)+@\\w+(\\.\\w+){1,2}"),
    /**
     * 手机号
     */
    MOBILE("1\\d{10}"),
    /**
     * 身份证号码
     */
    CITIZEN_ID("[1-9]\\d{5}[1-2]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}(\\d|X|x)"),
    /**
     * 邮编
     */
    ZIP_CODE("\\d{6}"),
    /**
     * 生日
     */
    BIRTHDAY("^(\\d{2,4})([/\\-\\.年]?)(\\d{1,2})([/\\-\\.月]?)(\\d{1,2})日?$"),
    /**
     * URL
     */
    URL("(https://|http://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?"),
    /**
     * 中文字、英文字母、数字和下划线
     */
    GENERAL_WITH_CHINESE("^[\u4E00-\u9FFF\\w]+$"),
    /**
     * UUID
     */
    UUID("^[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}$"),
    /**
     * 不带横线的UUID
     */
    UUID_SIMPLE("^[0-9a-z]{32}$"),
    /**
     * 中国车牌号码
     */
    PLATE_NUMBER("^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$"),
    /**
     * MAC地址正则
     */
    MAC_ADDRESS("((?:[A-F0-9]{1,2}[:-]){5}[A-F0-9]{1,2})|(?:0x)(\\d{12})(?:.+ETHER)"),
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
    DECIMAL("[-+]?[0-9]+(\\.[0-9]*)?"),
    /**
     * 小数点
     */
    DECIMAL_POINT("\\."),
    /**
     * 手机端
     */
    USER_AGENT_MOBILE("\\b(ip(hone|od)|android|opera m(ob|in)i|windows (phone|ce)|blackberry|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp|laystation portable)|nokia|fennec|htc[-_]|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b"),
    /**
     * 平板端
     */
    USER_AGENT_TABLET("\\b(ipad|tablet|(Nexus 7)|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b");

    private final String value;

    private RegEx(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public Pattern pattern() {
        if (value.equals(MAC_ADDRESS.value())) {
            return Pattern.compile(value, Pattern.CASE_INSENSITIVE);
        }
        return Pattern.compile(value);
    }

    public Pattern pattern(int flag) {
        return Pattern.compile(value, flag);
    }
}