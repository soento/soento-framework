package com.soento.framework.core.crypto;

/**
 * 模式
 */
public enum Mode {
    /**
     * 无模式
     */
    NONE,
    /**
     * Cipher Block Chaining
     */
    CBC,
    /**
     * Cipher Feedback
     */
    CFB,
    /**
     * A simplification of OFB
     */
    CTR,
    /**
     * Cipher Text Stealing
     */
    CTS,
    /**
     * Electronic Codebook
     */
    ECB,
    /**
     * Output Feedback
     */
    OFB,
    /**
     * Propagating Cipher Block
     */
    PCBC;
}
