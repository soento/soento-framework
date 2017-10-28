package com.soento.framework.core.enums;

import java.text.SimpleDateFormat;

/**
 * 日期格式
 */
public enum DateFormat {
    /**
     * 时分
     */
    HHMI("HHmm"),
    /**
     * 时分秒
     */
    HHMISS("HHmmss"),
    /**
     * 时:分:秒
     */
    HHMISS_COLON("HH:mm:ss"),
    /**
     * 年月
     */
    YYYYMM("yyyyMM"),
    /**
     * 年/月
     */
    YYYYMM_SLASH("yyyy/MM"),
    /**
     * 年-月
     */
    YYYYMM_DASH("yyyy-MM"),
    /**
     * 年月日
     */
    YYYYMMDD("yyyyMMdd"),
    /**
     * 年/月/日
     */
    YYYYMMDD_SLASH("yyyy/MM/dd"),
    /**
     * 年-月-日
     */
    YYYYMMDD_DASH("yyyy-MM-dd"),
    /**
     * 年月日 时分
     */
    YYYYMMDDHHMI("yyyyMMddHHmm"),
    /**
     * 年/月/日 时:分
     */
    YYYYMMDDHHMI_SLASH("yyyy/MM/dd HH:mm"),
    /**
     * 年-月-日 时:分
     */
    YYYYMMDDHHMI_DASH("yyyy-MM-dd HH:mm"),
    /**
     * 年月日时分秒
     */
    YYYYMMDDHHMISS("yyyyMMddHHmmss"),
    /**
     * 年/月/日 时:分:秒
     */
    YYYYMMDDHHMISS_SLASH("yyyy/MM/dd HH:mm:ss"),
    /**
     * 年-月-日 时:分:秒
     */
    YYYYMMDDHHMISS_DASH("yyyy-MM-dd HH:mm:ss"),
    /**
     * 年月日时分秒毫秒
     */
    YYYYMMDDHHMISSMS("yyyyMMddHHmmssSSS"),
    /**
     * 年/月/日 时:分:秒 毫秒
     */
    YYYYMMDDHHMISSMS_SLASH("yyyy/MM/dd HH:mm:ss SSS"),
    /**
     * 年-月-日 时:分:秒 毫秒
     */
    YYYYMMDDHHMISSMS_DASH("yyyy-MM-dd HH:mm:ss SSS"),
    /**
     * 年/月/日.时:分:秒.毫秒
     */
    YYYYMMDDHHMISSMS_SLASH_POINT("yyyy/MM/dd.HH:mm:ss.SSS"),
    /**
     * 年-月-日.时:分:秒.毫秒
     */
    YYYYMMDDHHMISSMS_DASH_POINT("yyyy-MM-dd.HH:mm:ss.SSS");

    private final String value;

    private DateFormat(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public SimpleDateFormat instance() {
        return new SimpleDateFormat(value);
    }
}
