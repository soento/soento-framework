package com.soento.framework.core.util;

import com.soento.framework.core.crypto.asymmetric.AsymmetricAlgorithm;
import com.soento.framework.core.crypto.asymmetric.DSA;
import com.soento.framework.core.crypto.asymmetric.RSA;
import com.soento.framework.core.crypto.digest.DigestAlgorithm;
import com.soento.framework.core.crypto.digest.Digester;
import com.soento.framework.core.crypto.digest.HMac;
import com.soento.framework.core.crypto.digest.HmacAlgorithm;
import com.soento.framework.core.crypto.symmetric.AES;
import com.soento.framework.core.crypto.symmetric.DES;
import com.soento.framework.core.crypto.symmetric.SymmetricCrypto;
import com.soento.framework.core.enums.Charset;
import org.springframework.util.Assert;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.UUID;


/**
 * 安全相关工具类<br>
 * 加密分为三种：<br>
 * 1、对称加密（symmetric），例如：AES、DES等<br>
 * 2、非对称加密（asymmetric），例如：RSA、DSA等<br>
 * 3、摘要加密（digest），例如：MD5、SHA-1、SHA-256、HMAC等<br>
 */
public final class CryptoUtil {

    private CryptoUtil() {
    }

    /**
     * 默认密钥字节数
     * <p>
     * <pre>
     * RSA/DSA
     * Default Keysize 1024
     * Keysize must be a multiple of 64, ranging from 512 to 1024 (inclusive).
     * </pre>
     */
    public static final int DEFAULT_KEY_SIZE = 1024;

    /**
     * 生成 {@link SecretKey}，仅用于对称加密和摘要算法密钥生成
     *
     * @param algorithm 算法，支持PBE算法
     * @return {@link SecretKey}
     */
    public static SecretKey generateKey(String algorithm) {
        SecretKey secretKey;
        try {
            secretKey = KeyGenerator.getInstance(algorithm).generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return secretKey;
    }

    /**
     * 生成 {@link SecretKey}，仅用于对称加密和摘要算法密钥生成
     *
     * @param algorithm 算法
     * @param key       密钥
     * @return {@link SecretKey}
     */
    public static SecretKey generateKey(String algorithm, byte[] key) {
        try {
            Assert.hasText(algorithm, "Algorithm is blank!");
            SecretKey secretKey = null;
            if (algorithm.startsWith("PBE")) {
                // PBE密钥
                secretKey = generatePBEKey(algorithm, (null == key) ? null : StringUtil.toString(key, Charset.UTF8.value()).toCharArray());

            } else if (algorithm.startsWith("DES")) {
                // DES密钥
                secretKey = generateDESKey(algorithm, key);
            } else {
                // 其它算法密钥
                secretKey = (null == key) ? generateKey(algorithm) : new SecretKeySpec(key, algorithm);
            }
            return secretKey;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成 {@link SecretKey}
     *
     * @param algorithm DES算法，包括DES、DESede等
     * @param key       密钥
     * @return {@link SecretKey}
     */
    public static SecretKey generateDESKey(String algorithm, byte[] key) {
        if (StringUtil.isBlank(algorithm) || false == algorithm.startsWith("DES")) {
            throw new RuntimeException("Algorithm [{}] is not a DES algorithm!");
        }

        SecretKey secretKey = null;
        if (null == key) {
            secretKey = generateKey(algorithm);
        } else {
            DESKeySpec keySpec;
            try {
                keySpec = new DESKeySpec(key);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            }
            secretKey = generateKey(algorithm, keySpec);
        }
        return secretKey;
    }

    /**
     * 生成PBE {@link SecretKey}
     *
     * @param algorithm PBE算法，包括：PBEWithMD5AndDES、PBEWithSHA1AndDESede、PBEWithSHA1AndRC2_40等
     * @param key       密钥
     * @return {@link SecretKey}
     */
    public static SecretKey generatePBEKey(String algorithm, char[] key) {
        if (StringUtil.isBlank(algorithm) || false == algorithm.startsWith("PBE")) {
            throw new RuntimeException("Algorithm [{}] is not a PBE algorithm!");
        }

        if (null == key) {
            key = RandomUtil.randomString(32).toCharArray();
        }
        PBEKeySpec keySpec = new PBEKeySpec(key);
        return generateKey(algorithm, keySpec);
    }

    /**
     * 生成 {@link SecretKey}，仅用于对称加密和摘要算法
     *
     * @param algorithm 算法
     * @param keySpec   {@link KeySpec}
     * @return {@link SecretKey}
     */
    public static SecretKey generateKey(String algorithm, KeySpec keySpec) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
            return keyFactory.generateSecret(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成私钥，仅用于非对称加密
     *
     * @param algorithm 算法
     * @param key       密钥
     * @return 私钥 {@link PrivateKey}
     */
    public static PrivateKey generatePrivateKey(String algorithm, byte[] key) {
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        try {
            return KeyFactory.getInstance(algorithm).generatePrivate(pkcs8KeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成私钥，仅用于非对称加密
     *
     * @param keyStore {@link KeyStore}
     * @param alias    别名
     * @param password 密码
     * @return 私钥 {@link PrivateKey}
     */
    public static PrivateKey generatePrivateKey(KeyStore keyStore, String alias, char[] password) {
        try {
            return (PrivateKey) keyStore.getKey(alias, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成公钥，仅用于非对称加密
     *
     * @param algorithm 算法
     * @param key       密钥
     * @return 公钥 {@link PublicKey}
     */
    public static PublicKey generatePublicKey(String algorithm, byte[] key) {
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        try {
            return KeyFactory.getInstance(algorithm).generatePublic(x509KeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成用于非对称加密的公钥和私钥，仅用于非对称加密
     *
     * @param algorithm 非对称加密算法
     * @return {@link KeyPair}
     */
    public static KeyPair generateKeyPair(String algorithm) {
        return generateKeyPair(algorithm, DEFAULT_KEY_SIZE, null);
    }

    /**
     * 生成用于非对称加密的公钥和私钥
     *
     * @param algorithm 非对称加密算法
     * @param keySize   密钥模（modulus ）长度
     * @return {@link KeyPair}
     */
    public static KeyPair generateKeyPair(String algorithm, int keySize) {
        return generateKeyPair(algorithm, keySize, null);
    }

    /**
     * 生成用于非对称加密的公钥和私钥
     *
     * @param algorithm 非对称加密算法
     * @param keySize   密钥模（modulus ）长度
     * @param seed      种子
     * @return {@link KeyPair}
     */
    public static KeyPair generateKeyPair(String algorithm, int keySize, byte[] seed) {
        KeyPairGenerator keyPairGen;
        try {
            keyPairGen = KeyPairGenerator.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        if (keySize <= 0) {
            keySize = DEFAULT_KEY_SIZE;
        }
        if (null != seed) {
            SecureRandom random = new SecureRandom(seed);
            keyPairGen.initialize(keySize, random);
        } else {
            keyPairGen.initialize(keySize);
        }
        return keyPairGen.generateKeyPair();
    }

    /**
     * 生成签名对象，仅用于非对称加密
     *
     * @param asymmetricAlgorithm {@link AsymmetricAlgorithm} 非对称加密算法
     * @param digestAlgorithm     {@link DigestAlgorithm} 摘要算法
     * @return {@link Signature}
     */
    public static Signature generateSignature(AsymmetricAlgorithm asymmetricAlgorithm, DigestAlgorithm digestAlgorithm) {
        String digestPart = (null == digestAlgorithm) ? "NONE" : digestAlgorithm.name();
        String algorithm = StringUtil.format("{}with{}", digestPart, asymmetricAlgorithm.getValue());
        try {
            return Signature.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取密钥库(Java Key Store，JKS) KeyStore文件<br>
     * KeyStore文件用于数字证书的密钥对保存<br>
     * see: http://snowolf.iteye.com/blog/391931
     *
     * @param in       {@link InputStream} 如果想从文件读取.keystore文件，使用 {@link FileUtil#getInputStream(File)} 读取
     * @param password 密码
     * @return {@link KeyStore}
     */
    public static KeyStore readJKSKeyStore(InputStream in, char[] password) {
        return readKeyStore("JKS", in, password);
    }

    /**
     * 读取KeyStore文件<br>
     * KeyStore文件用于数字证书的密钥对保存<br>
     * see: http://snowolf.iteye.com/blog/391931
     *
     * @param type     类型
     * @param in       {@link InputStream} 如果想从文件读取.keystore文件，使用 {@link FileUtil#getInputStream(File)} 读取
     * @param password 密码
     * @return {@link KeyStore}
     */
    public static KeyStore readKeyStore(String type, InputStream in, char[] password) {
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(type);
            keyStore.load(in, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return keyStore;
    }

    /**
     * 读取X.509 Certification文件<br>
     * Certification为证书文件<br>
     * see: http://snowolf.iteye.com/blog/391931
     *
     * @param in       {@link InputStream} 如果想从文件读取.cer文件，使用 {@link FileUtil#getInputStream(File)} 读取
     * @param password 密码
     * @return {@link KeyStore}
     */
    public static Certificate readX509Certificate(InputStream in, char[] password) {
        return readCertificate("X.509", in, password);
    }

    /**
     * 读取Certification文件<br>
     * Certification为证书文件<br>
     * see: http://snowolf.iteye.com/blog/391931
     *
     * @param type     类型
     * @param in       {@link InputStream} 如果想从文件读取.cer文件，使用 {@link FileUtil#getInputStream(File)} 读取
     * @param password 密码
     * @return {@link KeyStore}
     */
    public static Certificate readCertificate(String type, InputStream in, char[] password) {
        Certificate certificate;
        try {
            certificate = CertificateFactory.getInstance(type).generateCertificate(in);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return certificate;
    }

    /**
     * 获得 Certification
     *
     * @param keyStore {@link KeyStore}
     * @param alias    别名
     * @return {@link Certificate}
     */
    public static Certificate getCertificate(KeyStore keyStore, String alias) {
        try {
            return keyStore.getCertificate(alias);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ------------------------------------------------------------------- 对称加密算法

    /**
     * AES加密，生成随机KEY。注意解密时必须使用相同 {@link AES}对象或者使用相同KEY<br>
     * 例：
     * <pre>
     * AES加密：aes().encrypt(data)
     * AES解密：aes().decrypt(data)
     * </pre>
     *
     * @return {@link AES}
     */
    public static AES aes() {
        return new AES();
    }

    /**
     * AES加密<br>
     * 例：
     * <pre>
     * AES加密：aes(key).encrypt(data)
     * AES解密：aes(key).decrypt(data)
     * </pre>
     *
     * @param key 密钥
     * @return {@link SymmetricCrypto}
     */
    public static AES aes(byte[] key) {
        return new AES(key);
    }

    /**
     * DES加密，生成随机KEY。注意解密时必须使用相同 {@link DES}对象或者使用相同KEY<br>
     * 例：
     * <pre>
     * DES加密：des().encrypt(data)
     * DES解密：des().decrypt(data)
     * </pre>
     *
     * @return {@link DES}
     */
    public static DES des() {
        return new DES();
    }

    /**
     * DES加密<br>
     * 例：
     * <pre>
     * DES加密：des(key).encrypt(data)
     * DES解密：des(key).decrypt(data)
     * </pre>
     *
     * @param key 密钥
     * @return {@link DES}
     */
    public static DES des(byte[] key) {
        return new DES(key);
    }

    // ------------------------------------------------------------------- 摘要算法

    /**
     * MD5加密<br>
     * 例：
     * <pre>
     * MD5加密：md5().digest(data)
     * MD5加密并转为16进制字符串：md5().digestHex(data)
     * </pre>
     *
     * @return {@link Digester}
     */
    public static Digester md5() {
        return new Digester(DigestAlgorithm.MD5);
    }

    /**
     * MD5加密，生成16进制MD5字符串<br>
     *
     * @param data 数据
     * @return MD5字符串
     */
    public static String md5(String data) {
        return new Digester(DigestAlgorithm.MD5).digest(data);
    }

    /**
     * MD5加密，生成16进制MD5字符串<br>
     *
     * @param data 数据
     * @return MD5字符串
     */
    public static String md5(InputStream data) {
        return new Digester(DigestAlgorithm.MD5).digest(data);
    }

    /**
     * MD5加密文件，生成16进制MD5字符串<br>
     *
     * @param dataFile 被加密文件
     * @return MD5字符串
     */
    public static String md5(File dataFile) {
        return new Digester(DigestAlgorithm.MD5).digest(dataFile);
    }

    /**
     * SHA1加密<br>
     * 例：<br>
     * SHA1加密：sha1().digest(data)<br>
     * SHA1加密并转为16进制字符串：sha1().digestHex(data)<br>
     *
     * @return {@link Digester}
     */
    public static Digester sha1() {
        return new Digester(DigestAlgorithm.SHA1);
    }

    /**
     * SHA1加密，生成16进制SHA1字符串<br>
     *
     * @param data 数据
     * @return SHA1字符串
     */
    public static String sha1(String data) {
        return new Digester(DigestAlgorithm.SHA1).digest(data);
    }

    /**
     * SHA1加密，生成16进制SHA1字符串<br>
     *
     * @param data 数据
     * @return SHA1字符串
     */
    public static String sha1(InputStream data) {
        return new Digester(DigestAlgorithm.SHA1).digest(data);
    }

    /**
     * SHA1加密文件，生成16进制SHA1字符串<br>
     *
     * @param dataFile 被加密文件
     * @return SHA1字符串
     */
    public static String sha1(File dataFile) {
        return new Digester(DigestAlgorithm.SHA1).digest(dataFile);
    }

    /**
     * 创建HMac对象，调用digest方法可获得hmac值
     *
     * @param algorithm {@link HmacAlgorithm}
     * @param key       密钥，如果为<code>null</code>生成随机密钥
     * @return {@link HMac}
     */
    public static HMac hmac(HmacAlgorithm algorithm, byte[] key) {
        return new HMac(algorithm, key);
    }

    /**
     * 创建HMac对象，调用digest方法可获得hmac值
     *
     * @param algorithm {@link HmacAlgorithm}
     * @param key       密钥{@link SecretKey}，如果为<code>null</code>生成随机密钥
     * @return {@link HMac}
     */
    public static HMac hmac(HmacAlgorithm algorithm, SecretKey key) {
        return new HMac(algorithm, key);
    }

    /**
     * HmacMD5加密器<br>
     * 例：<br>
     * HmacMD5加密：hmacMd5(key).digest(data)<br>
     * HmacMD5加密并转为16进制字符串：hmacMd5(key).digestHex(data)<br>
     *
     * @param key 加密密钥
     * @return {@link HMac}
     */
    public static HMac hmacMd5(byte[] key) {
        return new HMac(HmacAlgorithm.HmacMD5, key);
    }

    /**
     * HmacMD5加密器，生成随机KEY<br>
     * 例：<br>
     * HmacMD5加密：hmacMd5().digest(data)<br>
     * HmacMD5加密并转为16进制字符串：hmacMd5().digestHex(data)<br>
     *
     * @return {@link HMac}
     */
    public static HMac hmacMd5() {
        return new HMac(HmacAlgorithm.HmacMD5);
    }

    /**
     * HmacSHA1加密器<br>
     * 例：<br>
     * HmacSHA1加密：hmacSha1(key).digest(data)<br>
     * HmacSHA1加密并转为16进制字符串：hmacSha1(key).digestHex(data)<br>
     *
     * @param key 加密密钥
     * @return {@link HMac}
     */
    public static HMac hmacSha1(byte[] key) {
        return new HMac(HmacAlgorithm.HmacSHA1, key);
    }

    /**
     * HmacSHA1加密器，生成随机KEY<br>
     * 例：<br>
     * HmacSHA1加密：hmacSha1().digest(data)<br>
     * HmacSHA1加密并转为16进制字符串：hmacSha1().digestHex(data)<br>
     *
     * @return {@link HMac}
     */
    public static HMac hmacSha1() {
        return new HMac(HmacAlgorithm.HmacSHA1);
    }

    // ------------------------------------------------------------------- 非称加密算法

    /**
     * 创建RSA算法对象<br>
     * 生成新的私钥公钥对
     *
     * @return {@link RSA}
     */
    public static RSA rsa() {
        return new RSA();
    }

    /**
     * 创建RSA算法对象<br>
     * 私钥和公钥同时为空时生成一对新的私钥和公钥<br>
     * 私钥和公钥可以单独传入一个，如此则只能使用此钥匙来做加密或者解密
     *
     * @param privateKeyBase64 私钥Base64
     * @param publicKeyBase64  公钥Base64
     * @return {@link RSA}
     */
    public static RSA rsa(String privateKeyBase64, String publicKeyBase64) {
        return new RSA(privateKeyBase64, publicKeyBase64);
    }

    /**
     * 创建RSA算法对象<br>
     * 私钥和公钥同时为空时生成一对新的私钥和公钥<br>
     * 私钥和公钥可以单独传入一个，如此则只能使用此钥匙来做加密或者解密
     *
     * @param privateKey 私钥
     * @param publicKey  公钥
     * @return {@link RSA}
     */
    public static RSA rsa(byte[] privateKey, byte[] publicKey) {
        return new RSA(privateKey, privateKey);
    }

    /**
     * 创建RSA算法对象<br>
     * 生成新的私钥公钥对
     *
     * @return {@link DSA}
     */
    public static DSA dsa() {
        return new DSA();
    }

    /**
     * 创建DSA算法对象<br>
     * 私钥和公钥同时为空时生成一对新的私钥和公钥<br>
     * 私钥和公钥可以单独传入一个，如此则只能使用此钥匙来做加密或者解密
     *
     * @param privateKeyBase64 私钥Base64
     * @param publicKeyBase64  公钥Base64
     * @return {@link DSA}
     */
    public static DSA dsa(String privateKeyBase64, String publicKeyBase64) {
        return new DSA(privateKeyBase64, publicKeyBase64);
    }

    /**
     * 创建DSA算法对象<br>
     * 私钥和公钥同时为空时生成一对新的私钥和公钥<br>
     * 私钥和公钥可以单独传入一个，如此则只能使用此钥匙来做加密或者解密
     *
     * @param privateKey 私钥
     * @param publicKey  公钥
     * @return {@link DSA}
     */
    public static DSA dsa(byte[] privateKey, byte[] publicKey) {
        return new DSA(privateKey, privateKey);
    }

    /**
     * URL解码
     */
    public static String urlDecode(String text, Charset charset) {
        try {
            if (text != null) {
                return URLDecoder.decode(text, charset.value());
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * URL编码
     */
    public static String urlEncode(String text, Charset charset) {
        try {
            if (text != null) {
                return URLEncoder.encode(text, charset.value());
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * URL解码（编码为UTF-8）
     */
    public static String urlDecode(String text) {
        try {
            if (text != null) {
                return URLDecoder.decode(text, "UTF-8");
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * URL编码（编码为UTF-8）
     */
    public static String urlEncode(String text) {
        try {
            if (text != null) {
                return URLEncoder.encode(text, "UTF-8");
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Base64编码
     */
    public static String base64Encode(String text, Charset charset) {
        try {
            return Base64Util.encodeBase64String(text.getBytes(charset.value()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Base64解码
     */
    public static String base64Decode(String text, Charset charset) {
        try {
            return new String(Base64Util.decodeBase64(text), charset.value());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Base64编码（编码为UTF-8）
     */
    public static String base64Encode(String text) {
        try {
            return Base64Util.encodeBase64String(text.getBytes("UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Base64编码（编码为UTF-8）
     */
    public static String toString(SecretKey key) {
        try {
            return Base64Util.encodeBase64String(key.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Base64解码（解码为UTF-8）
     */
    public static String base64Decode(String text) {
        try {
            return new String(Base64Util.decodeBase64(text), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // ------------------------------------------------------------------- UUID

    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
