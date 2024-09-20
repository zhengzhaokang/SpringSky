package com.wc.single.sky.common.utils.file;

import com.wc.single.sky.common.exception.file.FtpException;
import org.apache.commons.net.ftp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.TimeZone;

/**
 * @author qiaodf2
 */
@Component
public class FTPUtil {
    private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);
    private FTPSClient ftpClient;
    private boolean isLogin;

    /**
     * @param url      ftp地址
     * @param port     ftp端口号
     * @param userName ftp 用户名
     * @param passWord ftp 密码
     * @return
     * @throws IOException
     * @Description: 连接 ftp服务器
     */
    public boolean connectFTP(String url, int port, String userName, String passWord) throws IOException {
        this.ftpClient = new FTPSClient();
        FTPClientConfig ftpClientConfig = new FTPClientConfig();
        ftpClientConfig.setServerTimeZoneId(TimeZone.getDefault().getID());
        this.ftpClient.setControlEncoding("UTF-8");
        this.ftpClient.configure(ftpClientConfig);
        if (!ftpClient.isConnected()) {
            this.ftpClient.connect(url.trim(), port);
        }
        //ftp 连接回答返回码
        int reply = this.ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            this.ftpClient.disconnect();
            logger.info("连接ftp服务器失败,code:\t{}", reply);
            return this.isLogin;
        }
        this.ftpClient.login(userName.trim(), passWord.trim());
        //设置传输协议
        this.ftpClient.enterLocalPassiveMode();
        this.ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

        this.ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.execPROT("P");
        logger.info("成功连接FTP服务器,userName:{},URL:{}", userName, url);
        this.isLogin = true;
        this.ftpClient.setBufferSize(1024 * 1024);
        this.ftpClient.setDataTimeout(60 * 1000);
        return this.isLogin;
    }

    /**
     * @param localFile        上传文件对象
     * @param remoteUploadPath 存储ftp服务器路径
     * @return
     * @Description 上传文件
     */
    public String uploadFile(File localFile, String remoteUploadPath, String fileName) throws Exception {
        ;
        boolean success;
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(localFile))) {
            this.ftpClient.changeWorkingDirectory(remoteUploadPath.trim());
            logger.info("开始上传文件名称:{}", fileName);
            fileName = spaceTransformationQuestion(fileName);

            //  检查存储路径
            if (!this.ftpClient.changeWorkingDirectory(remoteUploadPath)) {
                mkdirPath(remoteUploadPath, ftpClient);
            }
            success = this.ftpClient.storeFile(fileName, inputStream);
            if (!success) {
                throw new FtpException("Attachment upload failed, try again later.");
            }
            logger.info("上传成功,文件名称:{}", fileName);
            fileName = questionTransformationSpace(fileName);
            return remoteUploadPath + "/" + fileName;
        }
    }

    /**
     * 删除文件 *
     *
     * @param pathname FTP服务器保存目录 *
     * @param filename 要删除的文件名称 *
     * @return
     */
    public boolean deleteFile(String pathname, String filename) {
        try {
            logger.info("开始删除文件");
            filename = spaceTransformationQuestion(filename);
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.dele(filename);
            ftpClient.logout();
            logger.info("删除文件成功");
        } catch (Exception e) {
            logger.error("删除文件失败");
            throw new FtpException(e.getMessage());
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            }
        }
        return true;
    }

    /**
     * 下载文件 *
     *
     * @param pathname FTP服务器文件目录 *
     * @param filename 文件名称 *
     * @return
     */
    public boolean downloadFile(String pathname, String filename) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;
        HttpServletResponse response = servletRequestAttributes.getResponse();
        boolean flag = false;
        try {
            logger.info("开始下载文件");
            filename = spaceTransformationQuestion(filename);
            InputStream inputStream = ftpClient.retrieveFileStream(pathname + "/" + filename);

            if (null == inputStream) {
                throw new FtpException("File:" + filename + " does not exist!");
            }
            BufferedInputStream br = new BufferedInputStream(inputStream);
            byte[] buf = new byte[1024];
            int len;
            assert response != null;
            response.reset(); // 非常重要
            OutputStream out = response.getOutputStream();
            // 设置response的Header
            filename = questionTransformationSpace(filename);
            response.setContentType("application/octet-stream");
            filename = URLEncoder.encode(filename, "utf-8").replace("\\+", "%20");
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            while ((len = br.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            br.close();
            out.close();
            ftpClient.logout();
            logger.info("下载文件成功");
        } catch (Exception e) {
            logger.error("下载文件失败");
            throw new FtpException(e.getMessage());
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return flag;
    }

    /**
     * 下载文件 *
     *
     * @param pathname FTP服务器文件目录 *
     * @param filename 文件名称 *
     * @return
     */
    public InputStream downloadFileInputStream(String pathname, String filename) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;
        InputStream inputStream;
        try {
            logger.info("开始下载文件");
            filename = spaceTransformationQuestion(filename);
            inputStream = ftpClient.retrieveFileStream(pathname + "/" + filename);

            if (null == inputStream) {
                throw new FtpException("File:" + filename + " does not exist!");
            }
        } catch (Exception e) {
            logger.error("下载文件失败");
            throw new FtpException(e.getMessage());
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return inputStream;
    }


    /**
     * 下载文件 *
     *
     * @param pathname FTP服务器文件目录 *
     * @param filename 文件名称 *
     * @return
     */
    public ResponseEntity downloadApPoeFile(String pathname, String filename) throws Exception {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;
        HttpServletResponse response = servletRequestAttributes.getResponse();
        ResponseEntity responseEntity;
        //切换FTP目录
        ftpClient.changeWorkingDirectory(pathname);
        FTPFile[] ftpFiles = ftpClient.listFiles();
        InputStream inputStream = null;
        for (FTPFile file : ftpFiles) {
            if (filename.equalsIgnoreCase(file.getName())) {
                inputStream = ftpClient.retrieveFileStream(file.getName());
            }
        }
        if (null == inputStream) {
            throw new FtpException("File:" + filename + " does not exist!");
        }
        responseEntity = ResponseEntity.ok().body(inputStream);
        BufferedInputStream br = new BufferedInputStream(inputStream);
        byte[] buf = new byte[1024];
        int len;
        assert response != null;
        response.reset(); // 非常重要
        OutputStream out = response.getOutputStream();
        // 设置response的Header
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", URLEncoder.encode(filename, "utf-8")));
        while ((len = br.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        br.close();
        out.close();
        ftpClient.logout();
        logger.info("下载文件成功");
        if (ftpClient.isConnected()) {
            ftpClient.disconnect();
        }

        return responseEntity;
    }

    /**
     * @Description 退出 / 关闭 FTP 服务器连接
     */
    public void loginOut() {
        if (null != this.ftpClient && this.ftpClient.isConnected()) {
            try {
                boolean logout = this.ftpClient.logout();
                if (logout) {
                    logger.info("成功退出FTP服务器");
                }
            } catch (Exception e) {
                logger.error("退出FTP服务器异常:{}", e.getMessage());
            } finally {
                try {
                    this.ftpClient.disconnect();// 关闭ftp服务器连接
                } catch (Exception e) {
                    logger.error("FTP服务器关闭异常:{}", e.getMessage());
                }
            }

        }
    }

    /**
     * @param path ftp服务器文件目录
     * @param ftp  FTPClient对象
     * @return 创建状态
     */
    public boolean mkdirPath(String path, FTPClient ftp) {
        try {
            String directory = path.endsWith("/") ? path : path + "/";
            if (!directory.equalsIgnoreCase("/") && !ftp.changeWorkingDirectory(directory)) {
                // 如果远程目录不存在，则递归创建远程服务器目录
                int start = 0;
                int end;
                if (directory.startsWith("/")) {
                    start = 1;
                }
                end = directory.indexOf("/", start);
                do {
                    String subDirectory = path.substring(0, end);
                    if (!ftp.changeWorkingDirectory(subDirectory)) {
                        if (ftp.makeDirectory(subDirectory)) {
                            if (!ftp.changeWorkingDirectory(subDirectory)) {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                    start = end + 1;
                    end = directory.indexOf("/", start);
                } while (end > start);
            }
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    private String spaceTransformationQuestion(String str) {
        return str.replace(" ", "?");
    }

    private String questionTransformationSpace(String str) {
        return str.replace("?", " ");
    }
}
