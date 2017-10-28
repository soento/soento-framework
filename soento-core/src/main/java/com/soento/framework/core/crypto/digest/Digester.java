package com.soento.framework.core.crypto.digest;

import com.soento.framework.core.enums.Charset;
import com.soento.framework.core.util.FileUtil;
import com.soento.framework.core.util.HexUtil;
import com.soento.framework.core.util.IoUtil;
import com.soento.framework.core.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 摘要算法<br>
 * 注意：此对象实例化后为非线程安全！
 */
public class Digester {
    private MessageDigest digest;

    public Digester(DigestAlgorithm algorithm) {
        init(algorithm.getValue());
    }

    /**
     * 初始化
     *
     * @param algorithm 算法
     * @return {@link Digester}
     */
    public Digester init(String algorithm) {
        try {
            digest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    // ------------------------------------------------------------------------------------------- Digest

    /**
     * 生成文件摘要
     *
     * @param data    被摘要数据
     * @param charset 编码
     * @return 摘要
     */
    public byte[] digestBytes(String data, String charset) {
        return digestBytes(StringUtil.bytes(data, charset));
    }

    /**
     * 生成文件摘要
     *
     * @param data 被摘要数据
     * @return 摘要
     */
    public byte[] digestBytes(String data) {
        return digestBytes(data, Charset.UTF8.value());
    }

    /**
     * 生成文件摘要，并转为16进制字符串
     *
     * @param data    被摘要数据
     * @param charset 编码
     * @return 摘要
     */
    public String digest(String data, String charset) {
        return HexUtil.encode(digestBytes(data, charset));
    }

    /**
     * 生成文件摘要
     *
     * @param data 被摘要数据
     * @return 摘要
     */
    public String digest(String data) {
        return digest(data, Charset.UTF8.value());
    }

    /**
     * 生成文件摘要<br>
     *
     * @param file 被摘要文件
     * @return 摘要bytes
     */
    public byte[] digestBytes(File file) throws RuntimeException {
        InputStream in = null;
        try {
            in = FileUtil.getInputStream(file);
            return digestBytes(in);
        } finally {
            IoUtil.close(in);
        }
    }

    /**
     * 生成文件摘要，并转为16进制字符串<br>
     *
     * @param file 被摘要文件
     * @return 摘要
     */
    public String digest(File file) {
        return HexUtil.encode(digestBytes(file));
    }

    /**
     * 生成摘要
     *
     * @param data 数据bytes
     * @return 摘要bytes
     */
    public byte[] digestBytes(byte[] data) {
        byte[] result;
        try {
            result = digest.digest(data);
        } finally {
            digest.reset();
        }
        return result;
    }

    /**
     * 生成摘要，并转为16进制字符串<br>
     *
     * @param data 被摘要数据
     * @return 摘要
     */
    public String digest(byte[] data) {
        return HexUtil.encode(digestBytes(data));
    }

    /**
     * 生成摘要，使用默认缓存大小
     *
     * @param data {@link InputStream} 数据流
     * @return 摘要bytes
     */
    public byte[] digestBytes(InputStream data) {
        return digestBytes(data, IoUtil.DEFAULT_BUFFER_SIZE);
    }

    /**
     * 生成摘要，并转为16进制字符串<br>
     * 使用默认缓存大小，见 {@link IoUtil#DEFAULT_BUFFER_SIZE}
     *
     * @param data 被摘要数据
     * @return 摘要
     */
    public String digest(InputStream data) {
        return HexUtil.encode(digestBytes(data));
    }

    /**
     * 生成摘要
     *
     * @param data         {@link InputStream} 数据流
     * @param bufferLength 缓存长度，不足1使用 {@link IoUtil#DEFAULT_BUFFER_SIZE} 做为默认值
     * @return 摘要bytes
     * @throws RuntimeException IO异常
     */
    public byte[] digestBytes(InputStream data, int bufferLength) throws RuntimeException {
        if (bufferLength < 1) {
            bufferLength = IoUtil.DEFAULT_BUFFER_SIZE;
        }
        byte[] buffer = new byte[bufferLength];

        byte[] result = null;
        try {
            int read = data.read(buffer, 0, bufferLength);

            while (read > -1) {
                digest.update(buffer, 0, read);
                read = data.read(buffer, 0, bufferLength);
            }
            result = digest.digest();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            digest.reset();
        }
        return result;
    }

    /**
     * 生成摘要，并转为16进制字符串<br>
     * 使用默认缓存大小
     *
     * @param data         被摘要数据
     * @param bufferLength 缓存长度
     * @return 摘要
     */
    public String digest(InputStream data, int bufferLength) {
        return HexUtil.encode(digestBytes(data, bufferLength));
    }

    /**
     * 获得 {@link MessageDigest}
     *
     * @return {@link MessageDigest}
     */
    public MessageDigest getDigest() {
        return digest;
    }
}
