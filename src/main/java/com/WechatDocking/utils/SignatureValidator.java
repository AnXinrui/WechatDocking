package com.WechatDocking.utils;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SignatureValidator {

    /**
     * 验证签名
     */
    public static boolean validateSignature(String TOKEN, String signature, String timestamp, String nonce) {
        String[] tmpArr = {TOKEN, timestamp, nonce};
        Arrays.sort(tmpArr);

        StringBuilder tmpStr = new StringBuilder();
        for (String item : tmpArr) {
            tmpStr.append(item);
        }

        String sha1Hash = getSHA1(tmpStr.toString());

        return sha1Hash.equals(signature);
    }

    @NotNull
    private static String getSHA1(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}