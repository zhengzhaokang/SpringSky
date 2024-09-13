package com.wc.single.sky.common.utils;

import cn.hutool.core.io.IoUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class IOUtils {


    public static void exportTemplate(HttpServletResponse response, String path, Class cls) throws Exception{
        InputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            inputStream = cls.getResourceAsStream(path);
            response.setContentType("application/x-download");
            response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(path.substring(path.lastIndexOf("/")+1), "utf-8"));//设置头部信息
            response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
            outputStream = response.getOutputStream();
            bis = new BufferedInputStream(inputStream);
            bos = new BufferedOutputStream(outputStream);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while((bytesRead = bis.read(buffer,0,8192))!=-1){
                bos.write(buffer, 0, bytesRead);
            }
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
           IoUtil.close(bos);
            IoUtil.close(outputStream);
            IoUtil.close(bis);
            IoUtil.close(inputStream);
        }
    }

    public static void downloadTemplate(HttpServletResponse response,String path,Class cls) throws IOException {
        //获取要下载的模板名称
        String fileName = "Pool Massupload Template (1).xlsx";
        //设置要下载的文件的名称
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        //通知客服文件的MIME类型
        response.setContentType("application/vnd.ms-template;charset=UTF-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
        //获取文件的路径
        try{
            //读取excel模板
            InputStream inputStream = cls.getResourceAsStream(path);
            XSSFWorkbook wb = new XSSFWorkbook(inputStream);
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
