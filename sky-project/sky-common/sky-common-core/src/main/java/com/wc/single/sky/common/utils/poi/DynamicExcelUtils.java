package com.wc.single.sky.common.utils.poi;


import com.wc.single.sky.common.exception.QdpException;
import com.wc.single.sky.common.redis.util.RedisUtils;
import com.wc.single.sky.common.utils.StringUtils;
import com.wc.single.sky.common.utils.ToolUtil;
import com.wc.single.sky.common.utils.spring.SpringContextHolder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DynamicExcelUtils {

    public static int sheetSize = 655;

    public static long recordLimit = 10000000L;
    private static final String SYS_CONFIG_EXCEL_LIMIT = "sys.export.excel.limit";
    private static final String SYS_CONFIG_SHEET_SIZE = "sys.export.sheet.size";

    private static DynamicExcelUtils instance = new DynamicExcelUtils();

    private DynamicExcelUtils() {
    }

    public static DynamicExcelUtils getInstance() {
        return instance;
    }

    /**
     * 将 List<Map<String,Object>> 类型的数据导出为 Excel
     * 默认 Excel 文件的输出路径为 项目根目录下
     * 文件名为 filename + 时间戳 + .xlsx
     *
     * @param mapList  数据源(通常为数据库查询数据)
     * @param headList 表头
     * @param filename 文件名前缀, 实际文件名后会加上日期
     * @return 文件输出路径
     */
    public void createExcel(HttpServletResponse response, List<Map<String, Object>> mapList, List<String> headList,Map<String, String> headMappingMap, String filename,String sheetName) {
        //赋值
        initOtherInfo();

        if (recordLimit <= mapList.size()) {
            throw new QdpException("The data exceeds " + recordLimit + " lines and can't be exported. Pls adjust the searching conditions to export less data.");

        }
        XSSFWorkbook wb = writeToExcel(mapList, headList,headMappingMap, sheetName,false);

        response(response, filename, wb);


    }

    /**
     * 单个sheet页生成file
     * @param list
     * @param sheetName
     * @param fileName
     * @return
     */
    public File writeToExcel(List<Map<String, Object>> list,  List<String> headList,String sheetName, String fileName) {


        XSSFWorkbook wb = writeToExcel(list, headList, null,sheetName,false);
        String tempPath = ToolUtil.getTempPath();
        File file = new File(tempPath + fileName);
        try {
            boolean newFile = file.createNewFile();
            if (newFile) {
                FileOutputStream stream = FileUtils.openOutputStream(file);
                wb.write(stream);
                stream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
    public File writeToExcelByUpload(List<Map<String, Object>> list,  List<String> headList,Map<String, String> headMappingMap,String sheetName, String fileName,Boolean uploadType) {


        XSSFWorkbook wb = writeToExcel(list, headList,headMappingMap, sheetName,uploadType);
        String tempPath = ToolUtil.getTempPath();
        File file = new File(tempPath + fileName);
        try {
            boolean newFile = file.createNewFile();
            if (newFile) {
                FileOutputStream stream = FileUtils.openOutputStream(file);
                wb.write(stream);
                stream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
    /**
     * 数据存放到excel
     * @param mapList
     * @param headList
     * @param headMappingMap
     * @param sheetName
     * @return
     */
    private XSSFWorkbook writeToExcel(List<Map<String, Object>> mapList, List<String> headList,Map<String, String> headMappingMap, String sheetName,Boolean uploadType) {
        //定义一个新的工作簿
        XSSFWorkbook wb = new XSSFWorkbook();

        // 取出一共有多少个sheet.
        int sheetNo = Math.max(1, (int) Math.ceil(mapList.size() * 1.0 / sheetSize));

        for (int index = 0; index < sheetNo; index++) {

            //创建一个Sheet页 设置工作表的名称.
            XSSFSheet sheet =   wb.createSheet();
            creatSheetName(sheetName, wb, sheetNo, index, sheet);

            Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

            XSSFCellStyle style = setHeadStyle(wb, styles);
            //设置行高
            sheet.setDefaultRowHeight((short) (2 * 128));
            for (int i = 0; i < headList.size(); i++) {
                sheet.setColumnWidth(i, 6500);
            }

            //获得表格第一行
            XSSFRow row = sheet.createRow(0);
            //根据数据源信息给第二行每一列设置标题
            for (int i = 0; i < headList.size(); i++) {
                XSSFCell cell = row.createCell(i);
                cell.setCellValue(headList.get(i));
                cell.setCellStyle(style);
                DataFormat format = wb.createDataFormat();
                XSSFCellStyle styleExcel = wb.createCellStyle();
                styleExcel.setDataFormat(format.getFormat("@"));
//对所有列进行样式赋值，第一个参数为列数，第二个参数为样式
                sheet.setDefaultColumnStyle(i, styleExcel);
            }

            XSSFRow rows;
            XSSFCell cells;
            XSSFCellStyle styleData = wb.createCellStyle();
            setStyle(wb, styleData);
            styleData.setAlignment(HorizontalAlignment.LEFT);
            styleData.setVerticalAlignment(VerticalAlignment.CENTER);
            //循环拿到的数据给所有行每一列设置对应的值
            Integer pageNo = index + 1;
            List<Map<String, Object>>  mapListResult = mapList.stream().skip((pageNo - 1) * sheetSize).limit(sheetSize).collect(Collectors.toList());
            XSSFCellStyle styleNumData = wb.createCellStyle();
           if(CollectionUtils.isNotEmpty(mapListResult)){
               for (int i = 0; i < mapListResult.size(); i++) {
                   //在这个sheet页里创建一行
                   rows = sheet.createRow(i + 1);
                   //给该行数据赋值
                   for (int j = 0; j < headList.size(); j++) {

                       if (null == mapListResult.get(i).get(headMappingMap==null? headList.get(j) : getKey(headMappingMap,headList.get(j))) ) {
                           String value = "";
                           cells = rows.createCell(j);
                           cells.setCellStyle(styleData);
                           cells.setCellValue(value);

                           continue;
                       }
                       if(uploadType){
                           String value = mapList.get(i).get(headMappingMap==null? headList.get(j) :getKey(headMappingMap,headList.get(j))).toString();
                           cells = rows.createCell(j);
                           cells.setCellStyle(styleData);
                           cells.setCellValue(value);
                       }else{
                           String value = mapListResult.get(i).get(headMappingMap==null? headList.get(j) :getKey(headMappingMap,headList.get(j))).toString();
                           if (value.contains(".")) {
                               Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
                               Matcher isNum = pattern.matcher(value);
                               if (isNum.matches()) {
                                   setStyle(wb, styleNumData);
                                   styleNumData.setAlignment(HorizontalAlignment.RIGHT);
                                   styleNumData.setVerticalAlignment(VerticalAlignment.CENTER);
                                   cells = rows.createCell(j);
                                   XSSFDataFormat df = wb.createDataFormat();
                                   styleNumData.setDataFormat(df.getFormat("#,##0.00"));

                                   cells.setCellStyle(styleNumData);
                                   cells.setCellValue(Double.parseDouble(value));
                               }else{
                                   cells = rows.createCell(j);
                                   XSSFDataFormat format = wb.createDataFormat();
                                   style.setDataFormat(format.getFormat("@"));
                                   cells.setCellStyle(styleData);
                                   cells.setCellValue(value);
                               }

                           } else {
                               cells = rows.createCell(j);

                               cells.setCellStyle(styleData);
                               cells.setCellValue(value);
                           }
                       }
                   }
               }
           }
        }
        return wb;
    }

    private void creatSheetName(String sheetName, XSSFWorkbook wb, int sheetNo, int index, XSSFSheet sheet) {
        if (sheetNo > 1 && index > 0)
        {
            int currentSheetIndex = wb.getSheetIndex(sheet);
            wb.setSheetName(currentSheetIndex, sheetName + (index+1));
        }
        if (sheetNo > 1 && index == 0)
        {
            int currentSheetIndex = wb.getSheetIndex(sheet);
            wb.setSheetName(currentSheetIndex, sheetName +1);
        }else{
            int currentSheetIndex = wb.getSheetIndex(sheet);
            wb.setSheetName( currentSheetIndex,sheetName);
        }
    }

    private void response(HttpServletResponse response, String filename, XSSFWorkbook wb) {
        //输出流
        OutputStream os = null;
        try {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xlsx");
//             response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setContentType("application/octet-stream;charset=UTF-8");  //流输出
            os = new BufferedOutputStream(response.getOutputStream());
            wb.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private XSSFCellStyle setHeadStyle(XSSFWorkbook wb, Map<String, CellStyle> styles) {
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        setStyle(wb, style);
        styles.put("data", style);
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        return style;
    }

    private void setStyle(XSSFWorkbook wb, XSSFCellStyle styleData) {
        XSSFDataFormat format = wb.createDataFormat();
        styleData.setDataFormat(format.getFormat("@"));
        styleData.setBorderRight(BorderStyle.THIN);
        styleData.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        styleData.setBorderLeft(BorderStyle.THIN);
        styleData.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        styleData.setBorderTop(BorderStyle.THIN);
        styleData.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        styleData.setBorderBottom(BorderStyle.THIN);
        styleData.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFiledFont = wb.createFont();
        dataFiledFont.setFontName("Arial");
        dataFiledFont.setFontHeightInPoints((short) 10);
        styleData.setFont(dataFiledFont);
    }

    private void initOtherInfo() {
        ApplicationContext applicationContext = SpringContextHolder.getApplicationContext();
        RedisUtils redis = applicationContext.getBean(RedisUtils.class);
        String tempLimitNum = redis.get(SYS_CONFIG_EXCEL_LIMIT);
        String tempSizeNum = redis.get(SYS_CONFIG_SHEET_SIZE);
        if (StringUtils.isNotEmpty(tempSizeNum)) {
            DynamicExcelUtils.sheetSize = Long.valueOf(StringUtils.isNotEmpty(tempSizeNum) ? tempSizeNum : "0").intValue();
        }
        if (StringUtils.isNotEmpty(tempLimitNum)) {
            DynamicExcelUtils.recordLimit = Long.valueOf(StringUtils.isNotEmpty(tempLimitNum) ? tempLimitNum : "0");
        }
    }
    public static String getKey(Map map, Object value){
        for(Object key: map.keySet()){
            if(map.get(key).equals(value)){
                return key.toString();
            }
            continue;
        }
        return null;
    }

}
