package com.interactive.suspend.ad.util;

import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by jeden on 2017/6/20.
 */

public class AES {
    public static final String AES_KEY = "FvijHlomwg8xwxds";
    public static final byte[] IV = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public static byte[] encrypt(final String data, String key, final byte[] iv) {
        try {
            if (TextUtils.isEmpty(data) || TextUtils.isEmpty(key)) {
                return null;
            }
            byte[] keyByte = MD5Utils.md5bin(AES_KEY.getBytes());

            byte[] dataByte = data.getBytes();

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            Key secretKey = new SecretKeySpec(keyByte, "AES");

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
            final byte[] encrypted = cipher.doFinal(dataByte);

            ByteArrayOutputStream bos = new ByteArrayOutputStream(encrypted.length);
            bos.write(encrypted);
            return bos.toByteArray();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密
     *
     * @param dataByte
     * @param key
     * @return
     */
    public static byte[] decrypt(final byte[] dataByte, String key, final byte[] iv) {
        try {
            if (TextUtils.isEmpty(key)) {
                return null;
            }

            byte[] keyByte = MD5Utils.md5bin(AES_KEY.getBytes());

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            Key secretKey = new SecretKeySpec(keyByte, "AES");

            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            final byte[] decrypted = cipher.doFinal(dataByte);

            return decrypted;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String doGetAES(InputStream is) throws Exception {
//        InputStream is = doGetInputStream(url);

        if (is == null)
            return null;

        ByteArrayOutputStream bao = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int readed = 0;
        while ((readed = is.read(buffer)) >= 0) {
            bao.write(buffer, 0, readed);
        }
        bao.close();
        try {
//            return new String(bao.toByteArray());
            return new String(AES.decrypt(bao.toByteArray(), AES_KEY, IV), "utf-8");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Throwable var2) {
                    var2.printStackTrace();
                }

            }
        }
    }

    public static String doGet(InputStream is) throws Exception {

        if (is == null)
            return null;

        ByteArrayOutputStream bao = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int readed = 0;
        while ((readed = is.read(buffer)) >= 0) {
            bao.write(buffer, 0, readed);
        }
        bao.close();
        try {
//            return new String(bao.toByteArray());
            return new String(bao.toByteArray(), "utf-8");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Throwable var2) {
                    var2.printStackTrace();
                }

            }
        }
    }

//	String content = HttpUtil.doGetAES(url, Md5.md5bin(pubKey), StringUtil.isEmpty(pubIv) ? AES.IV : Md5.md5bin(pubIv));
}
