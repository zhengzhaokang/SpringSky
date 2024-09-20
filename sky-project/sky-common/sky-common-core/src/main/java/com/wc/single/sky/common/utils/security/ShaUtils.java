package com.wc.single.sky.common.utils.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * SHA256加密工具类
 *
 * @author lovefamily
 */
public class ShaUtils {
    private static final Logger log = LoggerFactory.getLogger(ShaUtils.class);

    public static String encodeSHA256(String testString) {
        byte[] data = testString.getBytes();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(data);
            return bytes2Hex(digest);
        } catch (Exception e) {
            log.error("EncodeSHA256 Error...", e);
            return "";
        }
    }

    private static String bytes2Hex(byte[] bts) {
        StringBuilder sb = new StringBuilder();
        String tmp;
        for (byte bt : bts) {
            tmp = (Integer.toHexString(bt & 0xFF));
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
        }
        return sb.toString().toUpperCase();
    }
}
