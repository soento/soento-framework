package com.soento.framework.core.enums;

/**
 * 音频类型
 */
public enum Audio {
    MP3(".mp3");

    private String value;

    private Audio(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean has(String extension) {
        if (Audio.MP3.getValue().equalsIgnoreCase(extension)) {
            return true;
        }
        return false;
    }
}
