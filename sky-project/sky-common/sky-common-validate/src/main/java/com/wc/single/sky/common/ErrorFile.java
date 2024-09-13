package com.wc.single.sky.common;

import com.wc.single.sky.common.exception.QdpException;
import com.wc.single.sky.common.utils.poi.NewExcelUtil;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * * 验证失败的文件
 * @param <T>
 */
public class ErrorFile<T> {

    /**
     * * 构建验证失败的文件
     * @param list
     * @param clazz
     * @param fileName
     * @return
     */
    public  MockMultipartFile getErrorFile(List<T> list, Class<T> clazz, String fileName) {
        NewExcelUtil<T> util = new NewExcelUtil<>();
        util.writeSheet(list, "Mass Upload", null, clazz, false);
        File newFile = util.writeMoreSheetToExcel(getNewFileName(fileName));
        MockMultipartFile excelFile;
        try {
            excelFile = new MockMultipartFile(newFile.getName(), newFile.getName(), null, new FileInputStream(newFile));
        } catch (IOException e) {
            e.printStackTrace();
            throw new QdpException(e.getMessage());
        }
        return excelFile;
    }

    private String getNewFileName(String fileName) {
        String date = new SimpleDateFormat("MMddyyyyhhmmss").format(new Date());
        return fileName + "_" + "_" + "Error_" + date + ".xlsx";
    }
}
