package com.soento.framework.core.util;

import com.soento.framework.core.enums.Charset;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;

public class StringUtil extends StringUtils {

    /**
     * 将对象转换为字符串<br>
     * 默认编码UTF-8
     */
    public static String toString(Object obj) {
        return toString(obj, Charset.UTF8);
    }

    /**
     * 将对象转换为字符串<br>
     */
    public static String toString(Object obj, String charsetName) {
        return toString(obj, Charset.parse(charsetName));
    }

    /**
     * 将对象转换为字符串<br>
     */
    public static String toString(Object obj, Charset charset) {
        if (null == obj) {
            return null;
        }
        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof byte[]) {
            return new String((byte[]) obj, charset.instance());
        } else if (obj instanceof Byte[]) {
            Byte[] data = (Byte[]) obj;
            byte[] bytes = new byte[data.length];
            Byte dataByte;
            for (int i = 0; i < data.length; i++) {
                dataByte = data[i];
                bytes[i] = (null == dataByte) ? -1 : dataByte.byteValue();
            }
            return new String(bytes, charset.instance());
        } else if (obj instanceof ByteBuffer) {
            return charset.instance().decode((ByteBuffer) obj).toString();
        } else if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).toPlainString();
        } else if (obj instanceof SecretKey) {
            SecretKey key = (SecretKey) obj;
            return Base64Util.encodeBase64String(key.getEncoded());
        } else if (obj.getClass().isArray()) {
            return ArrayUtil.toString(obj);
        }
        return obj.toString();
    }

    /**
     * 去除左边的空格
     */
    public static String leftTrim(String content) {
        if (isNotEmpty(content)) {
            return content;
        }
        return content.replaceAll("^[　 ]+", "");
    }

    /**
     * 去除右边的空格
     */
    public static String rightTrim(String content) {
        if (isNotEmpty(content)) {
            return content;
        }
        return content.replaceAll("[　 ]+$", "");
    }

    /**
     * 格式化文本, {} 表示占位符<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") =》 this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") =》 this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") =》 this is \a for b<br>
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param params   参数值
     * @return 格式化后的文本
     */
    public static String format(CharSequence template, Object... params) {
        return MessageFormatter.arrayFormat(new StringBuffer().append(template).toString(), params).getMessage();
    }

    /**
     * 编码字符串<br>
     * 使用系统默认编码
     *
     * @param str 字符串
     * @return 编码后的字节码
     */
    public static byte[] bytes(CharSequence str) {
        return bytes(str, Charset.UTF8);
    }

    /**
     * 编码字符串
     *
     * @param str     字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 编码后的字节码
     */
    public static byte[] bytes(CharSequence str, String charset) {
        return bytes(str, isBlank(charset) ? Charset.UTF8 : Charset.parse(charset));
    }

    /**
     * 编码字符串
     *
     * @param str     字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 编码后的字节码
     */
    public static byte[] bytes(CharSequence str, Charset charset) {
        try {
            if (str == null) {
                return null;
            }
            if (null == charset) {
                return str.toString().getBytes(Charset.UTF8.value());
            }
            return str.toString().getBytes(charset.value());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
