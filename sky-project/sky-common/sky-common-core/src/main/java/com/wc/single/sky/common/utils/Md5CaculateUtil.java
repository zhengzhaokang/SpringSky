package com.wc.single.sky.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

import com.wc.single.sky.common.utils.file.FileUploadUtils;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * MD5计算工具
 *
 * @author lovefamily
 */
public class Md5CaculateUtil {

    /**
     * 获取一个文件的md5值(可处理大文件)
     *
     * @param file
     * @return md5 value
     */
    public static String getMD5(File file) throws Exception {
        FileInputStream fileInputStream = null;
        try {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(MD5.digest()));
        } catch (Exception e) {
            throw new Exception();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                throw new IOException();
            }
        }
    }

    /**
     * 获取一个文件的md5值(可处理大文件)
     *
     * @param multipartFile
     * @return md5 value
     */
    public static String getMD5ForMultipartFile(MultipartFile multipartFile) throws Exception {
        return getMD5(FileUploadUtils.multipartFileToFile(multipartFile));
    }

    /**
     * 求一个字符串的md5值
     *
     * @param target 字符串
     * @return md5 value
     */
    public static String MD5(String target) {
        return DigestUtils.md5Hex(target);
    }

    public static void main(String[] args) throws Exception {
        long beginTime = System.currentTimeMillis();
        File file = new File("C:/Users/T14/Downloads/GtnType_02152023152552.xlsx");
        String md5 = getMD5(file);
        long endTime = System.currentTimeMillis();
        System.out.println("MD5:" + md5 + "\n 耗时:" + ((endTime - beginTime) / 1000) + "s");
    }
}
