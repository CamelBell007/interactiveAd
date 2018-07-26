package com.interactive.suspend.ad.html;


import android.net.Uri;
import android.util.Base64;


public class DigestUtils {
    private static final String UTF8 = "UTF-8";

    private static String srcStr =  "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ%";

    private static String destStr = "hS2gI6tZxdwYlLkeKmDG73Tjpqi9V18APcJsOWoXv5nRUbfzN4BuQH0rayECMF-";


    public static String encrypt(final String str) {
        try {
            return encrypt(str.getBytes(UTF8));
        } catch (final Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private static String encrypt(final byte[] data) {
        try {
            final byte[] base64En = Base64.encode(data, Base64.NO_WRAP);
            return switchPositionString(new String(base64En, UTF8), srcStr, destStr);
        } catch (final Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private static String switchPositionString(String inputString, final String src, final String dest) {
        char[] inputChar = inputString.toCharArray();
        char[] charArray = new char[inputString.length()];
        for (int i = 0; i < inputString.length(); i++) {
            int idx = src.indexOf(inputChar[i]);
            if (idx != -1) {
                charArray[i] = dest.toCharArray()[idx];
            } else {
                charArray[i] = inputChar[i];
            }
        }
        return String.copyValueOf(charArray);
    }

    public static String decrypt(final String str) {
        try {
            String switchString = switchPositionString(str, destStr, srcStr);
            String urlDecode = Uri.decode(Uri.decode(switchString));
            return decrypt(urlDecode.getBytes());
        } catch (final Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private static String decrypt(final byte[] data) {
        try {
            final byte[] base64De = Base64.decode(data, Base64.NO_WRAP);
            return  new String(base64De, UTF8);
        } catch (final Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
}