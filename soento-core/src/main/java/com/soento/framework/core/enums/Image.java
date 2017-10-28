package com.soento.framework.core.enums;

/**
 * 图片类型
 */
public enum Image {
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

    private Image(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean has(String extension) {
        if (Image.JPG.getValue().equalsIgnoreCase(extension)) {
            return true;
        } else if (Image.BMP.getValue().equalsIgnoreCase(extension)) {
            return true;
        } else if (Image.GIF.getValue().equalsIgnoreCase(extension)) {
            return true;
        } else if (Image.PNG.getValue().equalsIgnoreCase(extension)) {
            return true;
        }
        return false;
    }
}
