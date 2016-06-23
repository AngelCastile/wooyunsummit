package com.wooyun.summit;


import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AESUtil {

    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";   ///ECB mode cannot use IV
    // 加密算法
    private static final String KEY_ALGORITHM = "AES";
    // IV 的bytes长度
    public static final int IV_LENGTH_BYTES = 16;
    // *** POINT 3 *** 使用足够长度的key以保证加密强度.
    private static final int MIN_KEY_LENGTH_BYTES = 16;
    private byte[] mIV = null;

    public byte[] getIV() {
        return mIV;
    }

    public AESUtil(final byte[] iv) {
        mIV = iv;
    }

    public AESUtil() {
    }

    public final String decry(final String key, final String sec) {

        byte[] result = decrypt(key.getBytes(), hexStringToBytes(sec));

        String out = new String(result);

        if (out.isEmpty()) {
            return "decry failed, please check the key or plaintext";
        } else {
            return out;
        }

    }

    public final String encry(final String key, final String plain) {

        byte[] sec = encrypt(key.getBytes(), plain.getBytes());

        return bytesToUcHexString(sec);

    }

    public final byte[] encrypt(final byte[] keyData, final byte[] plain) {
        byte[] encrypted = null;
        try {
            // *** POINT 1 *** 明确指定加密模式和填充
            // *** POINT 2 *** 使用健壮的加密方法,包括算法/块加密模式/填充模式
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            SecretKey secretKey = generateKey(keyData);
            if (secretKey != null && mIV == null) {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                encrypted = cipher.doFinal(plain);
            } else if (secretKey != null && mIV != null) {

                IvParameterSpec ivParameterSpec = new IvParameterSpec(mIV);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
                encrypted = cipher.doFinal(plain);

            }

        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        } catch (NoSuchPaddingException e) {
            System.out.println(e);
        } catch (InvalidKeyException e) {
            System.out.println(e);
        } catch (IllegalBlockSizeException e) {
            System.out.println(e);
        } catch (BadPaddingException e) {
            System.out.println(e);
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } finally {
        }
        return encrypted;
    }

    public final byte[] decrypt(final byte[] keyData, final byte[] encrypted) {
        byte[] plain = null;
        try {

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            SecretKey secretKey = generateKey(keyData);
            if (secretKey != null) {
//				IvParameterSpec ivParameterSpec = new IvParameterSpec(mIV);
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                plain = cipher.doFinal(encrypted);
            }
        } catch (NoSuchAlgorithmException e) {
        } catch (NoSuchPaddingException e) {
        } catch (InvalidKeyException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (BadPaddingException e) {
        } finally {
        }
        return plain;
    }

    private final SecretKey generateKey(final byte[] keyData) {
        SecretKey secretKey = null;
        try {
            // *** POINT 3 *** 使用足够长度的key以保证加密强度.
            if (keyData.length >= MIN_KEY_LENGTH_BYTES) {
                // *** POINT 2 *** 使用健壮的加密方法,包括算法/块加密模式/填充模式
                secretKey = new SecretKeySpec(keyData, KEY_ALGORITHM);
            } else {
                System.out.println("keyData is short");
            }
        } catch (IllegalArgumentException e) {
        } finally {
        }
        return secretKey;
    }


    private byte[] hexStringToBytes(String arg6) {
        int i = arg6.length() / 2;
        byte[] array_b = new byte[i];
        int i1;
        for (i1 = 0; i1 < i; ++i1) {
            array_b[i1] = Integer.valueOf(arg6.substring(i1 * 2, i1 * 2 + 2), 0x10).byteValue();
        }

        return array_b;
    }

    private String bytesToUcHexString(byte[] arg4) {
        String string0;
        if (arg4 == null) {
            string0 = null;
        } else {
            StringBuffer stringBuffer0 = new StringBuffer(arg4.length * 2);
            int i;
            for (i = 0; i < arg4.length; ++i) {
                stringBuffer0.append(this.byteToUcHexString(arg4[i]));
            }

            string0 = stringBuffer0.toString();
        }

        return string0;
    }

    private String byteToUcHexString(byte arg4) {
        return new StringBuffer().append("0123456789ABCDEF".charAt(arg4 >> 4 & 0xF)).append("0123456789ABCDEF"
                .charAt(arg4 & 0xF)).toString();
    }

    //懒汉模式,线程不安全.
    private static AESUtil instance;

    public static AESUtil getInstance() {

        if (instance == null) {

            instance = new AESUtil();
        }

        return instance;
    }
}
