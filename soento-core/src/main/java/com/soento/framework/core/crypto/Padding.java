package com.soento.framework.core.crypto;

/**
 * 补码方式
 */
public enum Padding {
    /**
     * 无补码
     */
    NoPadding,
    ISO10126Padding,
    OAEPPadding,
    PKCS1Padding,
    PKCS5Padding,
    SSL3Padding
}
