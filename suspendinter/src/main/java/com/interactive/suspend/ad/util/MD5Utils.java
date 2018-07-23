package com.interactive.suspend.ad.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by vincent on 2017/4/19.
 */

public class MD5Utils {

    public static byte[] md5bin(byte[] bin) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        return digest.digest(bin);
    }
}
