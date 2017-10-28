package com.soento.framework.core.util;

import com.soento.framework.core.enums.Charset;
import org.apache.commons.lang3.CharSetUtils;

import java.io.UnsupportedEncodingException;

/**
 * 字符集工具类
 */
public final class CharsetUtil extends CharSetUtils {

    /**
     * 转换为Charset对象
     *
     * @param charsetName 字符集，为空则返回默认UTF8
     * @return Charset
     */
    public static Charset charset(String charsetName) {
        return StringUtil.isBlank(charsetName) ? Charset.UTF8 : Charset.parse(charsetName);
    }

    /**
     * 转换字符串的字符集编码
     *
     * @param source      字符串
     * @param srcCharset  源字符集，默认UTF-8
     * @param destCharset 目标字符集，默认UTF-8
     * @return 转换后的字符集
     */
    public static String convert(String source, String srcCharset, String destCharset) {
        return convert(source, Charset.parse(srcCharset), Charset.parse(destCharset));
    }

    /**
     * 转换字符串的字符集编码
     *
     * @param source      字符串
     * @param srcCharset  源字符集，默认UTF-8
     * @param destCharset 目标字符集，默认UTF-8
     * @return 转换后的字符集
     */
    public static String convert(String source, Charset srcCharset, Charset destCharset) {
        try {
            if (null == srcCharset) {
                srcCharset = Charset.UTF8;
            }

            if (null == destCharset) {
                destCharset = Charset.UTF8;
            }

            if (StringUtil.isBlank(source) || srcCharset.equals(destCharset)) {
                return source;
            }
            return new String(source.getBytes(srcCharset.value()), destCharset.value());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 系统默认字符集编码
     *
     * @return 系统字符集编码
     */
    public static String systemCharset() {
        return java.nio.charset.Charset.defaultCharset().name();
    }

}
