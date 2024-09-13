package com.wc.single.sky.common.utils.file;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.util.Objects;

/**
 * @ClassName MultipartFileToFile
 * @Author liuhuayong
 * @Date 2020/5/15 17:23
 * @Version 1.0
 */
@Component
public class MultipartFileToFile {
    protected static final Logger LOGGER = LoggerFactory.getLogger(MultipartFileToFile.class);
    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) {
        File toFile;
        try {
            InputStream ins;
            ins = file.getInputStream();
            toFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
            inputStreamToFile(ins, toFile);
            ins.close();
        } catch (Exception e) {
            throw new RuntimeException("MultipartFile changge File abnormal!");
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) throws IOException {
        OutputStream os =null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (os != null) {
                os.close();
            }
            if (ins != null) {
                ins.close();
            }
        }
    }

    /**
     * 删除本地临时文件
     *
     * @param file
     */
    public void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            if(!del.delete()){

            };
        }

    }
    public static MultipartFile toMultipartFile(String fieldName, File file)  {
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        String contentType = new MimetypesFileTypeMap().getContentType(file);
        FileItem fileItem = diskFileItemFactory.createItem(fieldName, contentType, false, file.getName());
        try (
                InputStream inputStream = new ByteArrayInputStream(FileCopyUtils.copyToByteArray(file));
                OutputStream outputStream = fileItem.getOutputStream()
        ) {
            FileCopyUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            LOGGER.error("file to MultipartFile error:{}",e);
        }
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        return multipartFile;
    }

}
