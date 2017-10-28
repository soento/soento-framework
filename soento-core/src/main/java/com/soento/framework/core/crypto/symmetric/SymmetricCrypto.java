package com.soento.framework.core.crypto.symmetric;

import com.soento.framework.core.enums.Charset;
import com.soento.framework.core.util.*;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEParameterSpec;
import java.io.IOException;
import java.io.InputStream;
import java.security.spec.AlgorithmParameterSpec;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 对称加密算法<br>
 */
public class SymmetricCrypto {

    /**
     * SecretKey 负责保存对称密钥
     */
    private SecretKey secretKey;
    /**
     * Cipher负责完成加密或解密工作
     */
    private Cipher clipher;
    /**
     * 加密解密参数
     */
    private AlgorithmParameterSpec params;
    private Lock lock = new ReentrantLock();

    //------------------------------------------------------------------ Constructor start

    /**
     * 构造，使用随机密钥
     *
     * @param algorithm {@link SymmetricAlgorithm}
     */
    public SymmetricCrypto(SymmetricAlgorithm algorithm) {
        this(algorithm, null);
    }

    /**
     * 构造，使用随机密钥
     *
     * @param algorithm 算法，可以是"algorithm/mode/padding"或者"algorithm"
     */
    public SymmetricCrypto(String algorithm) {
        this(algorithm, null);
    }

    /**
     * 构造
     *
     * @param algorithm 算法 {@link SymmetricAlgorithm}
     * @param key       自定义KEY
     */
    public SymmetricCrypto(SymmetricAlgorithm algorithm, byte[] key) {
        this(algorithm.getValue(), key);
    }

    /**
     * 构造
     *
     * @param algorithm 算法
     * @param key       密钥
     */
    public SymmetricCrypto(String algorithm, byte[] key) {
        init(algorithm, key);
    }
    //------------------------------------------------------------------ Constructor end

    /**
     * 初始化
     *
     * @param algorithm 算法
     * @param key       密钥，如果为<code>null</code>自动生成一个key
     * @return {@link SymmetricCrypto}
     */
    public SymmetricCrypto init(String algorithm, byte[] key) {
        return init(algorithm, CryptoUtil.generateKey(algorithm, key));
    }

    /**
     * 初始化
     *
     * @param algorithm 算法
     * @param key       密钥，如果为<code>null</code>自动生成一个key
     * @return {@link SymmetricCrypto}
     */
    public SymmetricCrypto init(String algorithm, SecretKey key) {
        this.secretKey = key;
        if (algorithm.startsWith("PBE")) {
            //对于PBE算法使用随机数加盐
            this.params = new PBEParameterSpec(RandomUtil.randomBytes(8), 100);
        }
        try {
            clipher = Cipher.getInstance(algorithm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * 设置 {@link AlgorithmParameterSpec}，通常用于加盐或偏移向量
     *
     * @param params {@link AlgorithmParameterSpec}
     * @return 自身
     */
    public SymmetricCrypto setParams(AlgorithmParameterSpec params) {
        this.params = params;
        return this;
    }

    //--------------------------------------------------------------------------------- Encrypt

    /**
     * 加密
     *
     * @param data 被加密的bytes
     * @return 加密后的bytes
     */
    public byte[] encryptBytes(byte[] data) {
        lock.lock();
        try {
            if (null == this.params) {
                clipher.init(Cipher.ENCRYPT_MODE, secretKey);
            } else {
                clipher.init(Cipher.ENCRYPT_MODE, secretKey, params);
            }
            return clipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 加密
     *
     * @param data 数据
     * @return 加密后的Hex
     */
    public String encrypt(byte[] data) {
        return HexUtil.encode(encryptBytes(data));
    }

    /**
     * 加密
     *
     * @param data    被加密的字符串
     * @param charset 编码
     * @return 加密后的bytes
     */
    public byte[] encryptBytes(String data, String charset) {
        return encryptBytes(StringUtil.bytes(data, charset));
    }

    /**
     * 加密
     *
     * @param data    被加密的字符串
     * @param charset 编码
     * @return 加密后的Hex
     */
    public String encrypt(String data, String charset) {
        return HexUtil.encode(encryptBytes(data, charset));
    }

    /**
     * 加密，使用UTF-8编码
     *
     * @param data 被加密的字符串
     * @return 加密后的bytes
     */
    public byte[] encryptBytes(String data) {
        return encryptBytes(StringUtil.bytes(data, Charset.UTF8.value()));
    }

    /**
     * 加密，使用UTF-8编码
     *
     * @param data 被加密的字符串
     * @return 加密后的Hex
     */
    public String encrypt(String data) {
        return HexUtil.encode(encryptBytes(data));
    }

    /**
     * 加密
     *
     * @param data 被加密的字符串
     * @return 加密后的bytes
     */
    public byte[] encryptBytes(InputStream data) {
        try {
            return encryptBytes(IoUtil.readBytes(data));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加密
     *
     * @param data 被加密的字符串
     * @return 加密后的Hex
     */
    public String encrypt(InputStream data) {
        return HexUtil.encode(encryptBytes(data));
    }
    //--------------------------------------------------------------------------------- Decrypt

    /**
     * 解密
     *
     * @param bytes 被解密的bytes
     * @return 解密后的bytes
     */
    public byte[] decryptBytes(byte[] bytes) {
        lock.lock();
        try {
            if (null == this.params) {
                clipher.init(Cipher.DECRYPT_MODE, secretKey);
            } else {
                clipher.init(Cipher.DECRYPT_MODE, secretKey, params);
            }
            return clipher.doFinal(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 解密
     *
     * @param bytes   被解密的bytes
     * @param charset 解密后的charset
     * @return 解密后的String
     */
    public String decrypt(byte[] bytes, Charset charset) {
        return StringUtil.toEncodedString(decryptBytes(bytes), charset.instance());
    }

    /**
     * 解密
     *
     * @param bytes 被解密的bytes
     * @return 解密后的String
     */
    public String decrypt(byte[] bytes) {
        return decrypt(bytes, Charset.UTF8);
    }

    /**
     * 解密
     *
     * @param data 被解密的String
     * @return 解密后的bytes
     */
    public byte[] decryptBytes(String data) {
        return decryptBytes(HexUtil.decodeHex(data));
    }

    /**
     * 解密
     *
     * @param data    被解密的String
     * @param charset 解密后的charset
     * @return 解密后的String
     */
    public String decrypt(String data, Charset charset) {
        return StringUtil.toEncodedString(decryptBytes(data), charset.instance());
    }

    /**
     * 解密
     *
     * @param data 被解密的String
     * @return 解密后的String
     */
    public String decrypt(String data) {
        return decrypt(data, Charset.UTF8);
    }

    /**
     * 解密
     *
     * @param data 被解密的bytes
     * @return 解密后的bytes
     */
    public byte[] decryptBytes(InputStream data) {
        try {
            return decryptBytes(IoUtil.readBytes(data));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密
     *
     * @param data    被解密的InputStream
     * @param charset 解密后的charset
     * @return 解密后的String
     */
    public String decrypt(InputStream data, Charset charset) {
        return StringUtil.toEncodedString(decryptBytes(data), charset.instance());
    }

    /**
     * 解密
     *
     * @param data 被解密的InputStream
     * @return 解密后的String
     */
    public String decrypt(InputStream data) {
        return decrypt(data, Charset.UTF8);
    }

    //--------------------------------------------------------------------------------- Getters

    /**
     * 获得对称密钥
     *
     * @return 获得对称密钥
     */
    public SecretKey getSecretKey() {
        return secretKey;
    }

    /**
     * 获得加密或解密器
     *
     * @return 加密或解密
     */
    public Cipher getClipher() {
        return clipher;
    }
}
