package com.soento.framework.core.enums;

/**
 * 日期时间单位，每个单位都是以毫秒为基数
 */
public enum DateUnit {
    /**
     * 一毫秒
     */
    MS(1),
    /**
     * 一秒的毫秒数
     */
    SECOND(MS.value() * 1000),
    /**
     * 一分钟的毫秒数
     */
    MINUTE(SECOND.value() * 60),
    /**
     * 一小时的毫秒数
     */
    HOUR(MINUTE.value() * 60),
    /**
     * 一天的毫秒数
     */
    DAY(HOUR.value() * 24),
    /**
     * 一周的毫秒数
     */
    WEEK(DAY.value() * 7);

    private long millis;

    private DateUnit(long millis) {
        this.millis = millis;
    }

    /**
     * @return 单位对应的毫秒数
     */
    public long value() {
        return this.millis;
    }
}
