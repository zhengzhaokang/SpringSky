package com.wc.single.sky.common.utils.file;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import com.wc.single.sky.common.exception.QdpException;
import com.wc.single.sky.common.exception.ServiceException;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件处理工具类
 * 
 * @author lovefamily
 */
public class FileUtils
{
    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    /**
     * 输出指定文件的byte数组
     * 
     * @param filePath 文件路径
     * @param os 输出流
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException
    {
        FileInputStream fis = null;
        try
        {
            File file = new File(filePath);
            if (!file.exists())
            {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0)
            {
                os.write(b, 0, length);
            }
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            if (os != null)
            {
                try
                {
                    os.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     * 
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath)
    {

        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists())
        {
            return file.delete();
        }
        return false;
    }

    /**
     * 文件名称验证
     * 
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFilename(String filename)
    {
        return filename.matches(FILENAME_PATTERN);
    }

    /**
     * 下载文件名重新编码
     * 
     * @param request 请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName)
            throws UnsupportedEncodingException
    {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE"))
        {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        }
        else if (agent.contains("Firefox"))
        {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        }
        else if (agent.contains("Chrome"))
        {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        else
        {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
    
    /**
     * 获取系统临时目录
     * @return
     */
    public static String getTemp()
    {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * 创建临时文件
     * @param filePath
     * @param data
     * @return
     */
    public static File createTempFile(String filePath, byte[] data) throws IOException
    {
        String temp = getTemp() + filePath;
        File file = new File(temp);
        if (!file.getParentFile().exists())
        {
            file.getParentFile().mkdirs();
        }
        if (!file.exists())
        {
            if(!file.createNewFile()){
                throw new IOException();
            }

        }
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(data, 0, data.length);
            fos.flush();
        } catch (Exception e) {
            throw  new IOException();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
        return file;
    }

    /**
     * 判断文件大小
     *
     * @param file
     *            文件
     * @param size
     *            限制大小
     * @param unit
     *            限制单位（B,K,M,G）
     * @return
     */
    public static boolean checkFileSize(MultipartFile file, int size, String unit) {
        long len = file.getSize();
        double fileSize;
        switch (unit.toUpperCase()) {
            case "B":
                fileSize = (double) len;
                break;
            case "K":
                fileSize = (double) len / 1024;
                break;
            case "M":
                fileSize = (double) len / 1048576;
                break;
            case "G":
                fileSize = (double) len / 1073741824;
                break;
            default:
                fileSize = 0;
        }
        return fileSize > size;
    }

    /**
     * 判断文件大小
     *
     * @param len
     *            文件
     * @param conditionSize
     *            限制大小
     * @param unit
     *            限制单位（B,K,M,G）
     * @return
     */
    public static boolean checkFileSize(long len, int conditionSize, String unit) {
        double fileSize;
        switch (unit.toUpperCase()) {
            case "B":
                fileSize = len;
                break;
            case "K":
                fileSize = (double) len / 1024;
                break;
            case "M":
                fileSize = (double) len / 1048576;
                break;
            case "G":
                fileSize = (double) len / 1073741824;
                break;
            default:
                fileSize = 0;
        }
        return fileSize > conditionSize;
    }
    /**
     * 下载文件名重新编码
     *
     * @param response     响应对象
     * @param realFileName 真实文件名
     * @return
     */
    public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName) throws UnsupportedEncodingException {
        String percentEncodedFileName = percentEncode(realFileName);

        StringBuilder contentDispositionValue = new StringBuilder();
        contentDispositionValue.append("attachment; filename=")
                .append(percentEncodedFileName)
                .append(";")
                .append("filename*=")
                .append("utf-8''")
                .append(percentEncodedFileName);

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition,download-filename");
        response.setHeader("Content-disposition", contentDispositionValue.toString());
        response.setHeader("download-filename", percentEncodedFileName);
    }


    /**
     * 百分号编码工具方法
     *
     * @param s 需要百分号编码的字符串
     * @return 百分号编码后的字符串
     */
    public static String percentEncode(String s) throws UnsupportedEncodingException {
        String encode = URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
        return encode.replaceAll("\\+", "%20");
    }

    /**
     * 文件下载通用方法
     *
     * @param originalName 真实文件名
     * @param url 文件Url地址
     */
    public static void downloadSysOss(HttpServletResponse response, String originalName, String url) throws IOException {
        if (ObjectUtil.isNull(originalName)||ObjectUtil.isNull(url)) {
            throw new ServiceException("文件数据不存在!");
        }
        response.reset();
        FileUtils.setAttachmentResponseHeader(response, originalName);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE + ";charset=UTF-8");
        long data;
        try {
            data = HttpUtil.download(url, response.getOutputStream(), false);
        } catch (HttpException e) {
            if (e.getMessage().contains("403")) {
                throw new ServiceException("无读取权限, 请在对应的OSS开启'公有读'权限!");
            } else {
                throw new ServiceException(e.getMessage());
            }
        }
        response.setContentLength(Convert.toInt(data));
    }

    /**
     * 可读的文件大小<br>
     * 参考 http://stackoverflow.com/questions/3263892/format-file-size-as-mb-gb-etc
     *
     * @param size Long类型大小
     * @return 大小
     */
    public static String readableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[] { "B", "kB", "MB", "GB", "TB", "EB" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.##").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static File getFile(MultipartFile upload, String fileName) throws IOException {
        File toFile = null;
        if (upload != null) {
            try (InputStream ins = upload.getInputStream()) {
                toFile = new File(fileName);
                inputStreamToFile(ins, toFile);
            } catch (Exception ex) {
                throw new QdpException(ex.getMessage());
            }
        }
        return toFile;
    }

    public static void inputStreamToFile(InputStream ins, File file) throws IOException {
        try (OutputStream os = Files.newOutputStream(file.toPath())) {
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception ex) {
            throw new QdpException(ex.getMessage());
        } finally {
            if (null != ins) {
                ins.close();
            }
        }

    }
}
