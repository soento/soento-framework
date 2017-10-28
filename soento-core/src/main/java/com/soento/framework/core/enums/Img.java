package com.soento.framework.core.enums;

/**
 * 图片类型
 */
public enum Img {
    /**
     * JPEG文件格式
     */
    JPG(".jpg"),
    /**
     * BMP图像文件格式
     */
    BMP(".bmp"),
    /**
     * GIF文件格式
     */
    GIF(".gif"),
    /**
     * PNG图像文件格式
     */
    PNG(".png");
    private String value;

    private Img(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean has(String extension) {
        if (Img.JPG.getValue().equalsIgnoreCase(extension)) {
            return true;
        } else if (Img.BMP.getValue().equalsIgnoreCase(extension)) {
            return true;
        } else if (Img.GIF.getValue().equalsIgnoreCase(extension)) {
            return true;
        } else if (Img.PNG.getValue().equalsIgnoreCase(extension)) {
            return true;
        }
        return false;
    }
}
