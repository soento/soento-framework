package com.soento.framework.core.enums;

/**
 * 视频类型
 */
public enum Video {
    MP4(".mp4");
    private String value;

    private Video(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean has(String extension) {
        if (Video.MP4.getValue().equalsIgnoreCase(extension)) {
            return true;
        }
        return false;
    }
}