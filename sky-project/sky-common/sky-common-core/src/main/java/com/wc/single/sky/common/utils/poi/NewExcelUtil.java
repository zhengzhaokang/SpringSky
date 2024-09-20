package com.wc.single.sky.common.utils.poi;

import cn.hutool.core.util.StrUtil;
import com.wc.single.sky.common.exception.QdpException;
import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.annotation.Excels;
import com.wc.single.sky.common.core.text.Convert;
import com.wc.single.sky.common.exception.message.DefaultErrorMessage;
import com.wc.single.sky.common.redis.util.RedisUtils;
import com.wc.single.sky.common.utils.DateUtils;
import com.wc.single.sky.common.utils.StringUtils;
import com.wc.single.sky.common.utils.ToolUtil;
import com.wc.single.sky.common.utils.file.FileTypeUtils;
import com.wc.single.sky.common.utils.file.ImageUtils;
import com.wc.single.sky.common.utils.reflect.ReflectUtils;
import com.wc.single.sky.common.utils.spring.SpringContextHolder;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Excel相关处理
 *
 * @author lovefamily
 */
public class NewExcelUtil<T> {
    private static final Logger log = LoggerFactory.getLogger(NewExcelUtil.class);

    private static final String SYS_CONFIG_EXCEL_LIMIT = "sys.export.excel.limit";
    private static final String SYS_CONFIG_SHEET_SIZE = "sys.export.sheet.size";
    private static final String SYS_CONFIG_SHEET_LIMIT = "sys.export.sheet.limit";

    public static final String[] FORMULA_STR = {"=", "-", "+", "@"};
    public static final String FILE_TYPE_EXCEL = "excel";
    public static final String FILE_TYPE_CSV = "csv";

    /**
     * 数字格式
     */
    private static final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("######0.00");

    /**
     * sheet添加保护
     */
    private static final String PROTECT_SHEET = "zhimakaimen";

    /**
     * Excel sheet最大行数，默认65536
     */
    //public static final int sheetSize = 65536;
    private int sheetSize = 10000;

    private long recordLimit = 10000000L;

    private long sheetLimit = 5;

    /**
     * 工作表名称
     */
    private String sheetName;

    /**
     * 工作表名称
     */
    private String[] sheetNames;

    /**
     * 导出类型（EXPORT:导出数据；IMPORT：导入模板）
     */
    private Excel.Type type;

    /**
     * 工作薄对象
     */
    private Workbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 工作表对象
     */
    private List<Sheet> sheets;

    /**
     * 样式列表
     */
    private Map<String, CellStyle> styles;

    /**
     * 导入导出数据列表
     */
    private List<?> list;

    /**
     * 导入导出数据列表
     */
    private List<List<?>> lists;

    /**
     * 注解列表
     */
    public List<Object[]> fields;

    /**
     * 注解列表
     */
    public List<List<Object[]>> moreSheetFields;

    /**
     * 当前行号
     */
    private int rownum;

    /**
     * 标题
     */
    private String title;

    /**
     * 最大高度
     */
    private short maxHeight;

    /**
     * 统计列表
     */
    private Map<Integer, Double> statistics = new HashMap<Integer, Double>();

    /**
     * 实体对象
     */
    public Class<T> clazz;

    /**
     * 实体对象
     */
    public List<Class<T>> clazzs;

    /**
     * 是否锁定sheet中的单元格
     */
    public Boolean lockSheet;
    /**
     * sample内的示例数据detail
     * total 指定合并单元格的行数
     */

    public String detail;

    private Integer total;

    private Long totalNumber;

    private Integer sheetNo;

    public void initDetail(String detail, Integer total) {
        this.detail = detail;
        this.total = total;
    }

    /**
     * 初始化NewExcelUtil没有指定clazz，则必须通过writeSheet方法指定clazz，才可以导出数据到excel
     */
    public NewExcelUtil() {
        initOtherInfo();
        createWorkbook();
    }

    /**
     * 初始化NewExcelUtil直接指定clazz，则可以直接通过exportExcel导出数据到excel
     *
     * @param clazz
     */
    public NewExcelUtil(Class<T> clazz) {
        initOtherInfo();
        this.clazz = clazz;
    }

    /**
     * 初始化NewExcelUtil直接指定clazzs，则可以直接通过exportExcel导出数据到excel
     *
     * @param clazzs
     */
    public NewExcelUtil(List<Class<T>> clazzs) {
        initOtherInfo();
        this.clazzs = clazzs;
    }

    public void init(List<?> list, String sheetName, String title, Excel.Type type) {
        this.list = list;
        this.sheetName = sheetName;
        this.type = type;
        this.title = title;
        createExcelField();
        createWorkbook();
    }

    public void init(String sheetName, String title, Excel.Type type, Long totalNumber) {
        this.sheetName = sheetName;
        this.type = type;
        this.title = title;
        this.totalNumber = totalNumber;
        createExcelField();
        createWorkbook();
    }

    /**
     * 初始化导出配置参数
     */
    private void initOtherInfo() {
        ApplicationContext applicationContext = SpringContextHolder.getApplicationContext();
        if (null != applicationContext) {

            RedisUtils redis = applicationContext.getBean(RedisUtils.class);
            if (list == null) {
                list = new ArrayList<T>();
            }
            String tempLimitNum = redis.get(SYS_CONFIG_EXCEL_LIMIT);
            String tempSizeNum = redis.get(SYS_CONFIG_SHEET_SIZE);
            if (StringUtils.isNotEmpty(tempSizeNum)) {
                this.sheetSize = Long.valueOf(StringUtils.isNotEmpty(tempSizeNum) ? tempSizeNum : "0").intValue();
            }
            if (StringUtils.isNotEmpty(tempLimitNum)) {
                this.recordLimit = Long.valueOf(StringUtils.isNotEmpty(tempLimitNum) ? tempLimitNum : "0");
            }
        }
    }

    /**
     * 创建excel第一行标题
     */
    public void createTitle() {
        if (StringUtils.isNotEmpty(title)) {
            Row titleRow = sheet.createRow(rownum == 0 ? rownum++ : 0);
            titleRow.setHeightInPoints(30);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(styles.get("title"));
            titleCell.setCellValue(title);
            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), titleRow.getRowNum(),
                    this.fields.size() - 1));
        }
    }

    /**
     * 对excel表单默认第一个索引名转换成list
     *
     * @param is 输入流
     * @return 转换后集合
     */
    public List<T> importExcel(InputStream is) throws Exception {
        return importExcel(is, 0);
    }

    /**
     * 对excel表单默认第一个索引名转换成list
     *
     * @param is       输入流
     * @param titleNum 标题占用行数
     * @return 转换后集合
     */
    public List<T> importExcel(InputStream is, int titleNum) throws Exception {
        return importExcel(StringUtils.EMPTY, is, titleNum);
    }

    /**
     * 对excel表单指定sheet页转换成list
     *
     * @param is 输入流
     * @return 转换后集合
     */
    public List<T> importExcel(String sheetName, InputStream is) throws Exception {
        return importExcel(sheetName, is, 0);
    }

    /**
     * 对excel表单指定表格索引名转换成list
     *
     * @param sheetName 表格索引名
     * @param titleNum  标题占用行数
     * @param is        输入流
     * @return 转换后集合
     */
    public List<T> importExcel(String sheetName, InputStream is, int titleNum) throws Exception {
        this.type = Excel.Type.IMPORT;
        this.wb = WorkbookFactory.create(is);
        List<T> list = new ArrayList<T>();
        // 如果指定sheet名,则取指定sheet中的内容 否则默认指向第1个sheet
        Sheet sheet = StringUtils.isNotEmpty(sheetName) ? wb.getSheet(sheetName) : wb.getSheetAt(0);
        if (sheet == null) {
            throw new QdpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.EXCEL_EXPORT_SHEET_CHECK, ""),
                    DefaultErrorMessage.EXCEL_EXPORT_SHEET_CHECK.intValue());
        }

        // 获取最后一个非空行的行下标，比如总行数为n，则返回的为n-1
        int rows = sheet.getLastRowNum();

        if (rows > 0) {
            // 定义一个map用于存放excel列的序号和field.
            Map<String, Integer> cellMap = new HashMap<>();
            // 获取表头
            Row heard = sheet.getRow(titleNum);
            for (int i = 0; i < heard.getPhysicalNumberOfCells(); i++) {
                Cell cell = heard.getCell(i);
                if (StringUtils.isNotNull(cell)) {
                    String value = this.getCellValue(heard, i).toString();
                    cellMap.put(value.trim(), i);
                } else {
                    cellMap.put(null, i);
                }
            }
            Set<String> headerkey = cellMap.keySet();
            Set<String> columnName = getColumnName(clazz);
            String matchResult = equalsSet(headerkey, columnName);
            if (StrUtil.isNotBlank(matchResult)) {
                throw new QdpException(matchResult + " " + DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.EXCEL_EXPORT_SHEET_CHECK, ""), DefaultErrorMessage.EXCEL_EXPORT_SHEET_CHECK.intValue());
            }
            // 有数据时才处理 得到类的所有field.
            List<Object[]> fields = this.getFields();
            Map<Integer, Object[]> fieldsMap = new HashMap<Integer, Object[]>();
            for (Object[] objects : fields) {
                Excel attr = (Excel) objects[1];
                Integer column = cellMap.get(attr.name());
                if (column != null) {
                    fieldsMap.put(column, objects);
                }
            }
            for (int i = titleNum + 1; i <= rows; i++) {
                // 从第2行开始取数据,默认第一行是表头.
                Row row = sheet.getRow(i);
                // 判断当前行是否是空行
                if (isRowEmpty(row)) {
                    continue;
                }
                T entity = null;
                for (Map.Entry<Integer, Object[]> entry : fieldsMap.entrySet()) {
                    Object val = this.getCellValue(row, entry.getKey());

                    // 如果不存在实例则新建.
                    entity = (entity == null ? clazz.newInstance() : entity);
                    // 从map中得到对应列的field.
                    Field field = (Field) entry.getValue()[0];
                    Excel attr = (Excel) entry.getValue()[1];
                    // 取得类型,并根据对象类型设置值.
                    Class<?> fieldType = field.getType();
                    if (String.class == fieldType) {
                        String s = Convert.toStr(val);
                        if (StringUtils.endsWith(s, ".0")) {
                            val = StringUtils.substringBefore(s, ".0");
                        } else {
                            String dateFormat = field.getAnnotation(Excel.class).dateFormat();
                            if (StringUtils.isNotEmpty(dateFormat) && StringUtils.isNotEmpty(Convert.toStr(val))) {
                                val = DateUtils.parseDateToStr(dateFormat, (Date) val);
                            } else {
                                val = Convert.toStr(val);
                            }
                        }
                    } else if ((Integer.TYPE == fieldType || Integer.class == fieldType) && StringUtils.isNumeric(Convert.toStr(val))) {
                        val = Convert.toInt(val);
                    } else if (Long.TYPE == fieldType || Long.class == fieldType) {
                        val = Convert.toLong(val);
                    } else if (Double.TYPE == fieldType || Double.class == fieldType) {
                        val = Convert.toDouble(val);
                    } else if (Float.TYPE == fieldType || Float.class == fieldType) {
                        val = Convert.toFloat(val);
                    } else if (BigDecimal.class == fieldType) {
                        if (val.toString().contains(",")) {
                            DecimalFormat format = new DecimalFormat();
                            format.setParseBigDecimal(true);
                            ParsePosition position = new ParsePosition(0);
                            val = (BigDecimal) format.parse(val.toString(), position);

                        } else {
                            val = Convert.toBigDecimal(val);
                        }

                    } else if (Date.class == fieldType) {
                        if (val instanceof String && StringUtils.isNotEmpty(val.toString())) {
                            val = DateUtils.parseDate(val);
                        } else if (val instanceof Double) {
                            val = DateUtil.getJavaDate((Double) val);
                        }
                    } else if (Boolean.TYPE == fieldType || Boolean.class == fieldType) {
                        val = Convert.toBool(val, false);
                    }
                    if (StringUtils.isNotNull(fieldType)) {
                        String propertyName = field.getName();
                        if (StringUtils.isNotEmpty(attr.targetAttr())) {
                            propertyName = field.getName() + "." + attr.targetAttr();
                        } else if (StringUtils.isNotEmpty(attr.readConverterExp())) {
                            val = reverseByExp(Convert.toStr(val), attr.readConverterExp(), attr.separator());
                        } else if (!attr.handler().equals(ExcelHandlerAdapter.class)) {
                            val = dataFormatHandlerAdapter(val, attr);
                        }
                        ReflectUtils.invokeSetter(entity, propertyName, val);
                    }
                }
                list.add(entity);
            }
        }
        return list;
    }

    public void exportZip(HttpServletResponse response, List<?> list, String sheetName) {
        String fileName = "" + sheetName.replaceAll(" ", "_") + ".zip";
        response.setContentType("application/zip");
        response.setHeader("content-disposition", "attachment;filename=" + fileName);
        ZipOutputStream zos = null;
        try {
            int size = list.size();
            int threadSize = sheetSize;//每个sheet的记录条数 sheetSize
            int threadNum = size / threadSize + 1;

            boolean special = size % threadSize == 0;
            zos = new ZipOutputStream(response.getOutputStream());
            List<?> cutList = null;
            // 确定每条线程的数据
            for (int i = 0; i < threadNum; i++) {
                if (i == threadNum - 1) {
                    if (special) {
                        break;
                    }
                    cutList = list.subList(threadSize * i, size);
                } else {
                    cutList = list.subList(threadSize * i, threadSize * (i + 1));
                }
                ZipEntry entry = new ZipEntry(sheetName + i + ".xlsx");
                zos.putNextEntry(entry);
                ByteOutputStream bos = new ByteOutputStream();
                this.byteOutputStreamExcel(bos, cutList, sheetName, "");
                bos.writeTo(zos);
                bos.close();
                zos.closeEntry();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (Exception e) {
                    log.error("Export Zip Error.Exception:", e);
                }
            }
        }
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param response  返回数据
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @return 结果
     * @throws IOException
     */
    public void exportExcel(HttpServletResponse response, List<?> list, String sheetName) {

        // 取出一共有多少个sheet.
        int sheetNo = Math.max(1, (int) Math.ceil(list.size() * 1.0 / sheetSize));

        if (recordLimit <= list.size()) {
            throw new QdpException("The data exceeds " + recordLimit + " lines and can't be exported. Pls adjust the searching conditions to export less data.");
        }
        if (sheetNo > sheetLimit) {
            exportZip(response, list, sheetName);

        } else {
            exportExcel(response, list, sheetName, StringUtils.EMPTY);
        }
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param response  返回数据
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @param fileType  导出文件类型 【csv、excel】
     * @return 结果
     * @throws IOException
     */
    public void exportByFileType(HttpServletResponse response, List<?> list, String sheetName, String fileType) {

        if (recordLimit <= list.size()) {
            throw new QdpException("The data exceeds " + recordLimit + " lines and can't be exported. Pls adjust the searching conditions to export less data.");
        }
        if (FILE_TYPE_CSV.equals(fileType)) {
            exportCsv(response, list, sheetName, StringUtils.EMPTY);

        } else {
            exportExcel(response, list, sheetName, StringUtils.EMPTY);

        }
    }

    /**
     * 单个sheet页生成file
     *
     * @param list
     * @param sheetName
     * @param fileName
     * @return
     */
    public File writeToExcel(List<?> list, String sheetName, String fileName) {
        this.init(list, sheetName, title, Excel.Type.EXPORT);
        writeSheet();
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
     * 多个sheet页生成file
     *
     * @param fileName
     * @return
     */
    public File writeMoreSheetToExcel(String fileName) {

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
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param response  返回数据
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @param title     标题
     * @return 结果
     * @throws IOException
     */
    public void exportExcel(HttpServletResponse response, List<?> list, String sheetName, String title) {
        this.init(list, sheetName, title, Excel.Type.EXPORT);
        exportExcel(response);
    }

    public void exportCsv(HttpServletResponse response, List<?> list, String sheetName, String title) {
        this.init(list, sheetName, title, Excel.Type.EXPORT);
        StringBuffer csvContent = new StringBuffer();
        for (Object[] os : fields) {
            Excel excel = (Excel) os[1];
            String name = excel.name();
            csvContent.append(name).append(",");
        }
        csvContent = new StringBuffer(csvContent.substring(0, csvContent.length() - 1));
        csvContent.append("\n");

        for (int i = 0; i < list.size(); i++) {

            // 得到导出对象.
            T vo = (T) list.get(i);
            for (Object[] os : fields) {
                Field field = (Field) os[0];
                Excel excel = (Excel) os[1];
                // 用于读取对象中的属性
                Object value = null;
                try {
                    value = getTargetValue(vo, field, excel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String dateFormat = excel.dateFormat();
                String readConverterExp = excel.readConverterExp();
                String separator = excel.separator();
                if (StringUtils.isNotEmpty(dateFormat) && StringUtils.isNotNull(value)) {
                    csvContent.append(DateUtils.parseDateToStr(dateFormat, (Date) value)).append(",");
                } else if (StringUtils.isNotEmpty(readConverterExp) && StringUtils.isNotNull(value)) {
                    csvContent.append(convertByExp(Convert.toStr(value), readConverterExp, separator)).append(",");
                } else if (value instanceof BigDecimal && -1 < excel.scale()) {
                    BigDecimal bigDecimalValue = ((BigDecimal) value).setScale(excel.scale(), excel.roundingMode());
                    String bigDecimalValueStr = null;
                    if (excel.thousandSeparate()) {
                        bigDecimalValueStr = String.format("%,." + excel.scale() + "f", bigDecimalValue);
                    } else {
                        bigDecimalValueStr = String.valueOf(bigDecimalValue);
                    }
                    csvContent.append(bigDecimalValueStr).append(",");
                } else if (!excel.handler().equals(ExcelHandlerAdapter.class)) {
                    csvContent.append(dataFormatHandlerAdapter(value, excel)).append(",");
                } else {
                    // 设置列类型
                    if (Excel.ColumnType.STRING == excel.cellType()) {
                        String cellValue = Convert.toStr(value);
                        if (StringUtils.isNull(cellValue)) {
                            cellValue = excel.defaultValue();
                            csvContent.append(cellValue).append(",");
                        } else {
                            if (isHasComma(cellValue)) {
                                cellValue = "\"" + cellValue + "\"" + excel.suffix();
                                csvContent.append(cellValue).append(",");
                            } else {
                                csvContent.append(cellValue + excel.suffix()).append(",");
                            }
                        }
                    } else if (Excel.ColumnType.NUMERIC == excel.cellType()) {
                        if (StringUtils.isNotNull(value)) {
                            if (StringUtils.contains(Convert.toStr(value), ".")) {
                                csvContent.append(Convert.toDouble(value)).append(",");
                            } else {
                                csvContent.append(Convert.toInt(value)).append(",");
                            }
                        }
                    }
                }
            }
            csvContent = new StringBuffer(csvContent.substring(0, csvContent.length() - 1));
            csvContent.append("\n");

        }

        exportCsv2Response(response, csvContent);
    }

    private boolean isHasComma(String sourceStr) {
        char ch = ',';
        boolean containsChar = sourceStr.contains(Character.toString(ch));
        return containsChar;
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param sheetName 工作表的名称
     * @return 结果
     */
    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param sheetName 工作表的名称
     * @return 结果
     */
    public void importTemplateExcel(HttpServletResponse response, String sheetName) {
        importTemplateExcel(response, sheetName, StringUtils.EMPTY);
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param sheetName 工作表的名称
     * @param title     标题
     * @return 结果
     */
    public void importTemplateExcel(HttpServletResponse response, String sheetName, String title) {
        this.init(Collections.EMPTY_LIST, sheetName, title, Excel.Type.IMPORT);
        exportExcel(response);
    }

    public void byteOutputStreamExcel(ByteOutputStream byteOutputStream, List<?> list, String sheetName, String title) {
        this.init(list, sheetName, title, Excel.Type.EXPORT);
        try {
            writeSheet();
            wb.write(byteOutputStream);
        } catch (Exception e) {
            log.error("导出Excel异常{}", e.getMessage());
        } finally {
            IOUtils.closeQuietly(wb);
        }
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @return 结果
     */
    public void exportExcel(HttpServletResponse response) {
        try {
            writeSheet();
            this.exportExcel2Response(response);
        } catch (Exception e) {
            log.error("导出Excel异常{}", e.getMessage());
        } finally {
            IOUtils.closeQuietly(wb);
        }
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @return 结果
     */
    public void exportExcelWorkBook(HttpServletResponse response) {
        try {
            /* writeSheet();*/
            this.exportExcel2Response(response);
        } catch (Exception e) {
            log.error("导出Excel异常{}", e.getMessage());
        } finally {
            IOUtils.closeQuietly(wb);
        }
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @return 结果
     */
    public void exportExcel2Response(HttpServletResponse response) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        try {
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            log.error("导出Excel异常{}", e.getMessage());
        } finally {
            IOUtils.closeQuietly(wb);
        }
    }

    /**
     * 对list数据源将其里面的数据导入到csv表单
     *
     * @return 结果
     */
    public void exportCsv2Response(HttpServletResponse response, StringBuffer csvContent) {

//        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"result.csv\"");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
            writer.write(csvContent.toString());
            writer.flush();
            writer.close();

        } catch (Exception e) {
            log.error("导出CSV异常{}", e.getMessage());
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

    /**
     * 创建写入数据到Sheet
     */
    public void writeSheet() {

        // 取出一共有多少个sheet.
        int sheetNo = Math.max(1, (int) Math.ceil(list.size() * 1.0 / sheetSize));

        // 初始化sheet
        initSheet();

        for (int index = 0; index < sheetNo; index++) {
            createSheet(sheetNo, index);

            // 产生一行
            Row row = sheet.createRow(rownum);
            int column = 0;
            // 写入各个字段的列头名称
            for (Object[] os : fields) {
                Excel excel = (Excel) os[1];
                this.createCell(excel, row, column++);
            }
            if (Excel.Type.EXPORT.equals(type)) {
                fillExcelData(index);
                if (StringUtils.isNotBlank(detail)) {
                    detailsPage(index, detail);
                }
                addStatisticsRow();
            }
        }
        if (Objects.equals(Boolean.TRUE, lockSheet)) {
            this.sheet.protectSheet(PROTECT_SHEET);
        }
    }

    /**
     * 创建写入数据到Sheet
     */
    public void writeSheet(List<?> listExcel, int i) {
        //计算sheet 个数
        int size = listExcel.size();
        int sheetNo = size / sheetSize;
        sheetNo = size % sheetSize > 0 ? sheetNo + 1 : sheetNo;

        for (int index = 0; index < sheetNo; index++) {
            createSheet(i++);
            // 产生一行
            Row row = createRow(0);
            int column = 0;
            // 写入各个字段的列头名称
            for (Object[] os : fields) {
                Excel excel = (Excel) os[1];
                createCell(excel, row, column++);
            }
            if (Excel.Type.EXPORT.equals(type)) {

                fillExcelData(index, listExcel);

                if (StringUtils.isNotBlank(detail)) {
                    detailsPage(index, detail);
                }
                addStatisticsRow();
            }
        }
        if (Objects.equals(Boolean.TRUE, lockSheet)) {
            this.sheet.protectSheet(PROTECT_SHEET);
        }
    }

    public Row createRow(int rownum) {
        return sheet.createRow(rownum);
    }

    /**
     * 创建写入数据到Sheet
     * 默认导出excel允许修改
     *
     * @param list      导出数据
     * @param sheetName sheet名称
     * @param title     数据标题
     * @param clazz     数据对应类型
     */
    public void writeSheet(List<T> list, String sheetName, String title, Class<T> clazz) {
        this.writeSheet(list, sheetName, title, clazz, null);
    }

    /**
     * 创建写入数据到Sheet
     * 默认导出excel允许修改
     *
     * @param list      导出数据
     * @param sheetName sheet名称
     * @param clazz     数据对应类型
     */
    public void writeSheet(List<?> list, String sheetName, Class<T> clazz) {
        this.list = list;
        this.sheetName = sheetName;
        this.clazz = clazz;
        this.type = Excel.Type.EXPORT;
        createExcelField();
        this.writeSheet();
    }

    /**
     * 创建写入数据到Sheet
     *
     * @param list      导出数据
     * @param sheetName sheet名称
     * @param title     数据标题
     * @param clazz     数据对应类型
     * @param lockSheet 是否允许修改
     */
    public void writeSheet(List<T> list, String sheetName, String title, Class<T> clazz, Boolean lockSheet) {
        this.list = list;
        this.sheetName = sheetName;
        this.title = title;
        this.clazz = clazz;
        this.type = Excel.Type.EXPORT;
        this.lockSheet = lockSheet;
        createExcelField();
        this.writeSheet();
    }


    /**
     * 填充excel数据
     *
     * @param index 序号
     */
    public void fillExcelData(int index) {
        int startNo = index * sheetSize;
        int endNo = Math.min(startNo + sheetSize, list.size());
        for (int i = startNo; i < endNo; i++) {
            Row row = sheet.createRow(i + 1 + rownum - startNo);
            // 得到导出对象.
            T vo = (T) list.get(i);
            int column = 0;
            for (Object[] os : fields) {
                Field field = (Field) os[0];
                Excel excel = (Excel) os[1];
                this.addCell(excel, row, vo, field, column++);
            }
        }
    }

    /**
     * 填充excel数据
     *
     * @param index 序号
     */
    public void fillExcelData(int index, List<?> listExcel) {
        int startNo = index * sheetSize;
        int endNo = Math.min(startNo + sheetSize, listExcel.size());
        for (int i = startNo; i < endNo; i++) {
            Row row = sheet.createRow(i + 1 + rownum - startNo);
            // 得到导出对象.
            T vo = (T) listExcel.get(i);
            int column = 0;
            for (Object[] os : fields) {
                Field field = (Field) os[0];
                Excel excel = (Excel) os[1];
                this.addCell(excel, row, vo, field, column++);
            }
        }
    }

    /**
     * 填充excel数据
     *
     * @param index 序号
     */
    public int fillExcelData(int index, List<?> listExcel, Sheet sheet, int rowNum) {
        int startNo = index * sheetSize;
        int endNo = Math.min(startNo + sheetSize, listExcel.size());
        int rowIndex = 0;
        System.out.println("startNo:" + startNo + ",endNo:" + endNo);
        for (int i = startNo; i < endNo; i++) {
            rowIndex++;
            Row row = sheet.createRow(rowIndex + rowNum);
            // 得到导出对象.
            T vo = (T) listExcel.get(i);
            int column = 0;
            for (Object[] os : fields) {
                Field field = (Field) os[0];
                Excel excel = (Excel) os[1];
                this.addCell(excel, row, vo, field, column++);
            }
        }
        return rowNum;
    }

    /**
     * 填充所有行
     *
     * @param listExcel
     * @param sheet
     * @param rowNum
     * @return
     */
    public int fillExcelData(List<?> listExcel, Sheet sheet, int rowNum) {
        for (Object o : listExcel) {
            rowNum++;
            Row row = sheet.createRow(rowNum);
            // 得到导出对象.
            T vo = (T) o;
            int column = 0;
            for (Object[] os : fields) {
                Field field = (Field) os[0];
                Excel excel = (Excel) os[1];
                this.addCell(excel, row, vo, field, column++);
            }
        }
        return rowNum;
    }

    public void detailsPage(int index, String datail) {
        int startNo = index * sheetSize;
        int rowNum = list.size() + 1 + rownum - startNo + 6;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + total - 1, 0, fields.size() - 1));
        CellStyle cellStyle = wb.createCellStyle();
//5、先设置为自动换行
        cellStyle.setWrapText(true);

        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        cellStyle.setFont(headerFont);

        Row row = sheet.createRow(rowNum);
        Cell cell = row.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(datail);
    }

    public void detailsPage(int index, String datail, Sheet sheet, int rownum, int size) {
        int startNo = index * sheetSize;
        int rowNum = 1 + rownum;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + total - 1, 0, fields.size() - 1));
        CellStyle cellStyle = wb.createCellStyle();
//5、先设置为自动换行
        cellStyle.setWrapText(true);

        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        cellStyle.setFont(headerFont);

        Row row = sheet.createRow(rowNum);
        Cell cell = row.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(datail);
    }

    /**
     * 创建表格样式
     *
     * @param wb 工作薄对象
     * @return 样式列表
     */
    private Map<String, CellStyle> createStyles(Workbook wb) {
        // 写入各条记录,每条记录对应excel表中的一行
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style = wb.createCellStyle();
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font titleFont = wb.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        style.setFont(titleFont);
        styles.put("title", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        styles.put("data", style);

        style = wb.createCellStyle();
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
        styles.put("header", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font totalFont = wb.createFont();
        totalFont.setFontName("Arial");
        totalFont.setFontHeightInPoints((short) 10);
        style.setFont(totalFont);
        styles.put("total", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.LEFT);
        styles.put("data1", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        styles.put("data2", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        styles.put("data3", style);

        return styles;
    }

    /**
     * 创建单元格
     */
    public Cell createCell(Excel attr, Row row, int column) {
        // 创建列
        Cell cell = row.createCell(column);
        // 写入列信息
        cell.setCellValue(attr.name());
        setDataValidation(attr, row, column);
        cell.setCellStyle(tableColourStyle(attr));
        return cell;
    }

    /**
     * 创建单元格
     */
    public Cell createCell(Excel attr, Row row, int column, Sheet sheet) {
        // 创建列
        Cell cell = row.createCell(column);
        // 写入列信息
        cell.setCellValue(attr.name());
        if (attr.name().contains("注：")) {
            sheet.setColumnWidth(column, 6000);
        } else {
            // 设置列宽
            sheet.setColumnWidth(column, (int) ((attr.width() + 0.72) * 256));
        }
        // 如果设置了提示信息则鼠标放上去提示.
        if (StringUtils.isNotEmpty(attr.prompt())) {
            // 这里默认设了2-101列提示.
            setXSSFPrompt(sheet, "", attr.prompt(), 1, 100, column, column);
        }
        // 如果设置了combo属性则本列只能选择不能输入
        if (attr.combo().length > 0) {
            // 这里默认设了2-101列只能选择不能输入.
            setXSSFValidation(sheet, attr.combo(), 1, 100, column, column);
        }
        cell.setCellStyle(tableColourStyle(attr));
        return cell;
    }

    private CellStyle tableColourStyle(Excel attr) {
        CellStyle style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(attr.tableHeadColour().value());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        return style;
    }

    /**
     * 设置单元格信息
     *
     * @param value 单元格值
     * @param attr  注解相关
     * @param cell  单元格信息
     */
    public void setCellVo(Object value, Excel attr, Cell cell) {
        if (Excel.ColumnType.STRING == attr.cellType()) {
            String cellValue = Convert.toStr(value);
            // 对于任何以表达式触发字符 =-+@开头的单元格，直接使用tab字符作为前缀，防止CSV注入。暂时不考虑CSV注入
//            if (StringUtils.containsAny(cellValue, FORMULA_STR))
//            {
//                cellValue = StringUtils.replaceEach(cellValue, FORMULA_STR, new String[] { "\t=", "\t-", "\t+", "\t@" });
//            }
            cell.setCellValue(StringUtils.isNull(cellValue) ? attr.defaultValue() : cellValue + attr.suffix());
        } else if (Excel.ColumnType.NUMERIC == attr.cellType()) {
            if (StringUtils.isNotNull(value)) {
                if (StringUtils.contains(Convert.toStr(value), ".")) {
                    cell.setCellValue(Convert.toDouble(value));
                } else {
                    cell.setCellValue(Convert.toInt(value));
                }
            }
        } else if (Excel.ColumnType.IMAGE == attr.cellType()) {
            ClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, (short) cell.getColumnIndex(), cell.getRow().getRowNum(), (short) (cell.getColumnIndex() + 1), cell.getRow().getRowNum() + 1);
            String imagePath = Convert.toStr(value);
            if (StringUtils.isNotEmpty(imagePath)) {
                byte[] data = ImageUtils.getImage(imagePath);
                getDrawingPatriarch(cell.getSheet()).createPicture(anchor,
                        cell.getSheet().getWorkbook().addPicture(data, getImageType(data)));
            }
        }
    }

    /**
     * 获取画布
     */
    public static Drawing<?> getDrawingPatriarch(Sheet sheet) {
        if (sheet.getDrawingPatriarch() == null) {
            sheet.createDrawingPatriarch();
        }
        return sheet.getDrawingPatriarch();
    }

    /**
     * 获取图片类型,设置图片插入类型
     */
    public int getImageType(byte[] value) {
        String type = FileTypeUtils.getFileExtendName(value);
        if ("JPG".equalsIgnoreCase(type)) {
            return Workbook.PICTURE_TYPE_JPEG;
        } else if ("PNG".equalsIgnoreCase(type)) {
            return Workbook.PICTURE_TYPE_PNG;
        }
        return Workbook.PICTURE_TYPE_JPEG;
    }

    /**
     * 创建表格样式
     */
    public void setDataValidation(Excel attr, Row row, int column) {
        if (attr.name().contains("注：")) {
            sheet.setColumnWidth(column, 6000);
        } else {
            // 设置列宽
            sheet.setColumnWidth(column, (int) ((attr.width() + 0.72) * 256));
        }
        // 如果设置了提示信息则鼠标放上去提示.
        if (StringUtils.isNotEmpty(attr.prompt())) {
            // 这里默认设了2-101列提示.
            setXSSFPrompt(sheet, "", attr.prompt(), 1, 100, column, column);
        }
        // 如果设置了combo属性则本列只能选择不能输入
        if (attr.combo().length > 0) {
            // 这里默认设了2-101列只能选择不能输入.
            setXSSFValidation(sheet, attr.combo(), 1, 100, column, column);
        }
    }

    public Cell addCellIi(Excel attr, Row row, Object obj, Field field, int column) {
        Cell cell = null;
        try {
            // 设置行高
            row.setHeight(maxHeight);
            // 根据Excel中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
            if (attr.isExport()) {
                // 创建cell
                cell = row.createCell(column);
                cell.setCellStyle(this.matchCellStyle(attr, this.lockSheet));

                // 用于读取对象中的属性
                Object value = getTargetValue(obj, field, attr);
                String dateFormat = attr.dateFormat();
                String readConverterExp = attr.readConverterExp();
                String separator = attr.separator();
                if (StringUtils.isNotEmpty(dateFormat) && StringUtils.isNotNull(value)) {
                    cell.setCellValue((Date) value);
                } else if (StringUtils.isNotEmpty(readConverterExp) && StringUtils.isNotNull(value)) {
                    cell.setCellValue(convertByExp(Convert.toStr(value), readConverterExp, separator));
                } else if (value instanceof BigDecimal && -1 < attr.scale()) {
                    BigDecimal bigDecimalValue = ((BigDecimal) value).setScale(attr.scale(), attr.roundingMode());
                    String bigDecimalValueStr = null;
                    if (attr.thousandSeparate()) {
                        bigDecimalValueStr = String.format("%,." + attr.scale() + "f", bigDecimalValue);
                    } else {
                        bigDecimalValueStr = String.valueOf(bigDecimalValue);
                    }
                    cell.setCellValue(bigDecimalValueStr);
                } else if (!attr.handler().equals(ExcelHandlerAdapter.class)) {
                    cell.setCellValue(dataFormatHandlerAdapter(value, attr));
                } else {
                    // 设置列类型
                    setCellVo(value, attr, cell);
                }
                addStatisticsData(column, Convert.toStr(value), attr);
            }
        } catch (Exception e) {
            log.error("导出Excel失败{}", e);
            e.printStackTrace();
        }
        return cell;
    }

    /**
     * 添加单元格
     */
    public Cell addCell(Excel attr, Row row, T vo, Field field, int column) {
        Cell cell = null;
        try {
            // 设置行高
            row.setHeight(maxHeight);
            // 根据Excel中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
            if (attr.isExport()) {
                // 创建cell
                cell = row.createCell(column);
                cell.setCellStyle(this.matchCellStyle(attr, this.lockSheet));

                // 用于读取对象中的属性
                Object value = getTargetValue(vo, field, attr);
                String dateFormat = attr.dateFormat();
                String readConverterExp = attr.readConverterExp();
                String separator = attr.separator();
                if (StringUtils.isNotEmpty(dateFormat) && StringUtils.isNotNull(value)) {
                    cell.setCellValue((Date) value);
                } else if (StringUtils.isNotEmpty(readConverterExp) && StringUtils.isNotNull(value)) {
                    cell.setCellValue(convertByExp(Convert.toStr(value), readConverterExp, separator));
                } else if (value instanceof BigDecimal && -1 < attr.scale()) {
                    BigDecimal bigDecimalValue = ((BigDecimal) value).setScale(attr.scale(), attr.roundingMode());
                    String bigDecimalValueStr = null;
                    if (attr.thousandSeparate()) {
                        bigDecimalValueStr = String.format("%,." + attr.scale() + "f", bigDecimalValue);
                    } else {
                        bigDecimalValueStr = String.valueOf(bigDecimalValue);
                    }
                    cell.setCellValue(bigDecimalValueStr);
                } else if (!attr.handler().equals(ExcelHandlerAdapter.class)) {
                    cell.setCellValue(dataFormatHandlerAdapter(value, attr));
                } else {
                    // 设置列类型
                    setCellVo(value, attr, cell);
                }
                addStatisticsData(column, Convert.toStr(value), attr);
            }
        } catch (Exception e) {
            log.error("导出Excel失败{}", e);
            e.printStackTrace();
        }
        return cell;
    }

    /**
     * 设置 POI XSSFSheet 单元格提示
     *
     * @param sheet         表单
     * @param promptTitle   提示标题
     * @param promptContent 提示内容
     * @param firstRow      开始行
     * @param endRow        结束行
     * @param firstCol      开始列
     * @param endCol        结束列
     */
    public void setXSSFPrompt(Sheet sheet, String promptTitle, String promptContent, int firstRow, int endRow,
                              int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = helper.createCustomConstraint("DD1");
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        dataValidation.createPromptBox(promptTitle, promptContent);
        dataValidation.setShowPromptBox(true);
        sheet.addValidationData(dataValidation);
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     *
     * @param sheet    要设置的sheet.
     * @param textlist 下拉框显示的内容
     * @param firstRow 开始行
     * @param endRow   结束行
     * @param firstCol 开始列
     * @param endCol   结束列
     * @return 设置好的sheet.
     */
    public void setXSSFValidation(Sheet sheet, String[] textlist, int firstRow, int endRow, int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        // 加载下拉列表内容
        DataValidationConstraint constraint = helper.createExplicitListConstraint(textlist);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        // 处理Excel兼容性问题
        if (dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        } else {
            dataValidation.setSuppressDropDownArrow(false);
        }

        sheet.addValidationData(dataValidation);
    }

    /**
     * 解析导出值 0=男,1=女,2=未知
     *
     * @param propertyValue 参数值
     * @param converterExp  翻译注解
     * @param separator     分隔符
     * @return 解析后值
     */
    public static String convertByExp(String propertyValue, String converterExp, String separator) {
        StringBuilder propertyString = new StringBuilder();
        String[] convertSource = converterExp.split(",");
        for (String item : convertSource) {
            String[] itemArray = item.split("=");
            if (StringUtils.containsAny(separator, propertyValue)) {
                for (String value : propertyValue.split(separator)) {
                    if (itemArray[0].equals(value)) {
                        propertyString.append(itemArray[1] + separator);
                        break;
                    }
                }
            } else {
                if (itemArray[0].equals(propertyValue)) {
                    return itemArray[1];
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 反向解析值 男=0,女=1,未知=2
     *
     * @param propertyValue 参数值
     * @param converterExp  翻译注解
     * @param separator     分隔符
     * @return 解析后值
     */
    public static String reverseByExp(String propertyValue, String converterExp, String separator) {
        StringBuilder propertyString = new StringBuilder();
        String[] convertSource = converterExp.split(",");
        for (String item : convertSource) {
            String[] itemArray = item.split("=");
            if (StringUtils.containsAny(separator, propertyValue)) {
                for (String value : propertyValue.split(separator)) {
                    if (itemArray[1].equals(value)) {
                        propertyString.append(itemArray[0] + separator);
                        break;
                    }
                }
            } else {
                if (itemArray[1].equals(propertyValue)) {
                    return itemArray[0];
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 数据处理器
     *
     * @param value 数据值
     * @param excel 数据注解
     * @return
     */
    public String dataFormatHandlerAdapter(Object value, Excel excel) {
        try {
            Object instance = excel.handler().newInstance();
            Method formatMethod = excel.handler().getMethod("format", new Class[]{Object.class, String[].class});
            value = formatMethod.invoke(instance, value, excel.args());
        } catch (Exception e) {
            log.error("不能格式化数据 " + excel.handler(), e.getMessage());
        }
        return Convert.toStr(value);
    }

    /**
     * 合计统计信息
     */
    private void addStatisticsData(Integer index, String text, Excel entity) {
        if (entity != null && entity.isStatistics()) {
            Double temp = 0D;
            if (!statistics.containsKey(index)) {
                statistics.put(index, temp);
            }
            try {
                temp = Double.valueOf(text);
            } catch (NumberFormatException e) {
            }
            statistics.put(index, statistics.get(index) + temp);
        }
    }

    /**
     * 创建统计行
     */
    public void addStatisticsRow() {
        if (statistics.size() > 0) {
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);
            Set<Integer> keys = statistics.keySet();
            Cell cell = row.createCell(0);
            cell.setCellStyle(styles.get("total"));
            cell.setCellValue("合计");

            for (Integer key : keys) {
                cell = row.createCell(key);
                cell.setCellStyle(styles.get("total"));
                cell.setCellValue(DOUBLE_FORMAT.format(statistics.get(key)));
            }
            statistics.clear();
        }
    }

    /**
     * 创建统计行
     */
    public void addStatisticsRow(Sheet sheet) {
        if (statistics.size() > 0) {
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);
            Set<Integer> keys = statistics.keySet();
            Cell cell = row.createCell(0);
            cell.setCellStyle(styles.get("total"));
            cell.setCellValue("合计");

            for (Integer key : keys) {
                cell = row.createCell(key);
                cell.setCellStyle(styles.get("total"));
                cell.setCellValue(DOUBLE_FORMAT.format(statistics.get(key)));
            }
            statistics.clear();
        }
    }

    /**
     * 获取bean中的属性值
     *
     * @param field 字段
     * @param excel 注解
     * @return 最终的属性值
     * @throws Exception
     */
    private Object getTargetValue(Object obj, Field field, Excel excel) throws Exception {
        Object o = field.get(obj);
        if (StringUtils.isNotEmpty(excel.targetAttr())) {
            String target = excel.targetAttr();
            if (target.contains(".")) {
                String[] targets = target.split("[.]");
                for (String name : targets) {
                    o = getValue(o, name);
                }
            } else {
                o = getValue(o, target);
            }
        }
        return o;
    }

    /**
     * 以类的属性的get方法方法形式获取值
     *
     * @param o
     * @param name
     * @return value
     * @throws Exception
     */
    private Object getValue(Object o, String name) throws Exception {
        if (StringUtils.isNotNull(o) && StringUtils.isNotEmpty(name)) {
            Class<?> clazz = o.getClass();
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            o = field.get(o);
        }
        return o;
    }

    /**
     * 得到所有定义字段
     */
    private void createExcelField() {
        this.fields = getFields();
        this.fields = this.fields.stream().sorted(Comparator.comparing(objects -> ((Excel) objects[1]).sort())).collect(Collectors.toList());
        this.maxHeight = getRowHeight();
    }

    /**
     * 获取字段注解信息
     */
    public List<Object[]> getFields() {
        List<Object[]> fields = new ArrayList<Object[]>();
        List<Field> tempFields = new ArrayList<>();
        tempFields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
        tempFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        for (Field field : tempFields) {
            // 单注解
            if (field.isAnnotationPresent(Excel.class)) {
                Excel attr = field.getAnnotation(Excel.class);
                if (attr != null && (attr.type() == Excel.Type.ALL || attr.type() == type)) {
                    field.setAccessible(true);
                    fields.add(new Object[]{field, attr});
                }
            }

            // 多注解
            if (field.isAnnotationPresent(Excels.class)) {
                Excels attrs = field.getAnnotation(Excels.class);
                Excel[] excels = attrs.value();
                for (Excel attr : excels) {
                    if (attr != null && (attr.type() == Excel.Type.ALL || attr.type() == type)) {
                        field.setAccessible(true);
                        fields.add(new Object[]{field, attr});
                    }
                }
            }
        }
        return fields;
    }

    /**
     * 根据注解获取最大行高
     */
    public short getRowHeight() {
        double maxHeight = 0;
        for (Object[] os : this.fields) {
            Excel excel = (Excel) os[1];
            maxHeight = Math.max(maxHeight, excel.height());
        }
        return (short) (maxHeight * 20);
    }

    /**
     * 创建一个工作簿
     */
    public void createWorkbook() {
        this.wb = new SXSSFWorkbook(1000);
        this.styles = createStyles(wb);
    }

    /**
     * 创建工作表
     *
     * @param sheetNo sheet数量
     * @param index   序号
     */
    public void createSheet(int sheetNo, int index) {
        // 设置工作表的名称.
        if (sheetNo > 1 && index > 0) {
            this.sheet = wb.createSheet();
            int currentSheetIndex = wb.getSheetIndex(this.sheet);
            this.createTitle();
            wb.setSheetName(currentSheetIndex, sheetName + (index + 1));
        }
        if (sheetNo > 1 && index == 0) {
            int currentSheetIndex = wb.getSheetIndex(this.sheet);
            wb.setSheetName(currentSheetIndex, sheetName + 1);
        }

    }

    /**
     * 创建工作表
     *
     * @param index 序号
     */
    public synchronized void createSheet(int index) {
        // 设置工作表的名称.
        Sheet sheet = wb.createSheet();
        int currentSheetIndex = wb.getSheetIndex(sheet);
        this.createTitle();
        if (0 == index) {
            wb.setSheetName(currentSheetIndex, sheetName);
        } else {
            wb.setSheetName(currentSheetIndex, sheetName + " " + index);
        }
    }

    /**
     * 初始化工作表
     *
     * @return
     */
    public void initSheet() {
        this.sheet = wb.createSheet();
        int currentSheetNo = wb.getSheetIndex(this.sheet);
        wb.setSheetName(currentSheetNo, sheetName);
        createTitle();
    }

    /**
     * 获取单元格值
     *
     * @param row    获取的行
     * @param column 获取单元格列号
     * @return 单元格值
     */
    public Object getCellValue(Row row, int column) {
        if (row == null) {
            return row;
        }
        Object val = "";
        try {
            Cell cell = row.getCell(column);
            if (StringUtils.isNotNull(cell)) {
                if (cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA) {
                    val = cell.getNumericCellValue();
                    if (DateUtil.isCellDateFormatted(cell)) {
                        val = DateUtil.getJavaDate((Double) val); // POI Excel 日期格式转换
                    } else {
                        if ((Double) val % 1 != 0) {
                            val = new BigDecimal(val.toString());
                        } else {
                            val = new DecimalFormat("0").format(val);
                        }
                    }
                } else if (cell.getCellTypeEnum() == CellType.STRING) {
                    val = cell.getStringCellValue();
                } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
                    val = cell.getBooleanCellValue();
                } else if (cell.getCellTypeEnum() == CellType.ERROR) {
                    val = cell.getErrorCellValue();
                }

            }
        } catch (Exception e) {
            return val;
        }
        return val;
    }

    /**
     * 判断是否是空行
     *
     * @param row 判断的行
     * @return
     */
    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellTypeEnum() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    private CellStyle matchCellStyle(Excel attr, Boolean lock) {

        int align = attr.align().value();
        String dateFormat = attr.dateFormat();

        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append("data");
        keyBuilder.append(align >= 1 && align <= 3 ? align : "");

        CellStyle defaultCellStyle = this.styles.get(keyBuilder.toString());
        CellStyle cellStyle = defaultCellStyle;
        cellStyle.setWrapText(true);
        if (StringUtils.isNotEmpty(dateFormat)) {
            keyBuilder.append(dateFormat);
            if (this.styles.get(keyBuilder.toString()) == null) {
                CellStyle newCellStyle = wb.createCellStyle();
                newCellStyle.cloneStyleFrom(cellStyle);
                newCellStyle.setDataFormat(wb.getCreationHelper().createDataFormat().getFormat(dateFormat));
                this.styles.put(keyBuilder.toString(), newCellStyle);
                cellStyle = newCellStyle;
            } else {
                cellStyle = this.styles.get(keyBuilder.toString());
            }
        }
        if (Objects.equals(Boolean.TRUE, lock)) {
            keyBuilder.append(lock);
            if (this.styles.get(keyBuilder.toString()) == null) {
                CellStyle newCellStyle = wb.createCellStyle();
                newCellStyle.cloneStyleFrom(cellStyle);
                newCellStyle.setLocked(lock);
                this.styles.put(keyBuilder.toString(), newCellStyle);
                cellStyle = newCellStyle;
            } else {
                cellStyle = this.styles.get(keyBuilder.toString());
            }
        }

        return cellStyle;
    }

    public static Set<String> getColumnName(Class clazz) {

        Set<String> excelHeaderSet = new HashSet<>();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {

            if (field.isAnnotationPresent(Excel.class)) {

                Excel declaredAnnotation = field.getDeclaredAnnotation(Excel.class);

                String column = declaredAnnotation.name();
                if (!column.equals("Error Message")) {
                    excelHeaderSet.add(column);
                }
            }
        }
        return excelHeaderSet;

    }

    public static String equalsSet(Set<String> excelHeaders, Set<String> fields) {
        if (excelHeaders == null || fields == null) {//null就直接不比了
            return "";
        }
        Set<String> error = new HashSet<>();
        for (String s : fields) {
            if (StrUtil.isBlank(s) || "Error Message".equals(s) || "Amount (P)".equals(s) || "Amount (L)".equals(s) || "Amount ($)".equals(s)) {
                continue;
            }
            if (!excelHeaders.contains(s.trim())) {
                error.add(s);
            }
        }
        if (error.size() != 0) {
            return org.apache.commons.lang3.StringUtils.join(error, ",");
        }
        return "";
    }

    /**
     * 获取表头
     *
     * @param sheetName
     * @return
     * @throws Exception
     */
    public Map<String, String> getExcelHeader(String sheetName) throws Exception {
        if (StringUtils.isNotEmpty(sheetName)) {
            // 如果指定sheet名,则取指定sheet中的内容.
            sheet = wb.getSheet(sheetName);
        } else {
            // 如果传入的sheet名不存在则默认指向第1个sheet.
            sheet = wb.getSheetAt(0);
        }
        if (sheet == null) {
            throw new QdpException("Wrong file template,sheet Name is error");
        }
        Row row = sheet.getRow(0);
        Map<String, String> excelFieldsMap = new HashMap<>();
        for (Cell cell : row) {
            excelFieldsMap.put(cell.getStringCellValue(), cell.getStringCellValue());
        }
        return excelFieldsMap;
    }

    /**
     * 获取表头
     *
     * @param sheetNames
     * @return
     * @throws Exception
     */
    public Integer getCount(String... sheetNames) throws Exception {
        int count = 0;
        for (String s : sheetNames) {
            if (StringUtils.isNotEmpty(s)) {
                // 如果指定sheet名,则取指定sheet中的内容.
                sheet = wb.getSheet(s);
            } else {
                // 如果传入的sheet名不存在则默认指向第1个sheet.
                sheet = wb.getSheetAt(0);
            }
            if (sheet == null) {
                return 0;
            }
            // 减去表头，指要数据的长度
            count = count + sheet.getPhysicalNumberOfRows() - 1;
        }
        return count;
    }

    /**
     * 对excel表单指定表格索引名转换成list
     *
     * @param sheetName 表格索引名
     * @param is        输入流
     * @return 转换后集合
     */
    public List<T> importExcelPage(String sheetName, InputStream is, int titleNum, int pageNum, int pageSize) throws Exception {
        this.type = Excel.Type.IMPORT;
        this.wb = WorkbookFactory.create(is);
        List<T> list = new ArrayList<T>();
        // 如果指定sheet名,则取指定sheet中的内容 否则默认指向第1个sheet
        Sheet sheet = StringUtils.isNotEmpty(sheetName) ? wb.getSheet(sheetName) : wb.getSheetAt(0);
        if (sheet == null) {
            throw new QdpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.EXCEL_EXPORT_SHEET_CHECK, ""),
                    DefaultErrorMessage.EXCEL_EXPORT_SHEET_CHECK.intValue());
        }

        // 获取最后一个非空行的行下标，比如总行数为n，则返回的为n-1
        int rows = sheet.getLastRowNum();

        if (rows > 0) {
            // 定义一个map用于存放excel列的序号和field.
            Map<String, Integer> cellMap = new HashMap<String, Integer>();
            // 获取表头
            Row heard = sheet.getRow(titleNum);
            for (int i = 0; i < heard.getPhysicalNumberOfCells(); i++) {
                Cell cell = heard.getCell(i);
                if (StringUtils.isNotNull(cell)) {
                    String value = this.getCellValue(heard, i).toString();
                    cellMap.put(value, i);
                } else {
                    cellMap.put(null, i);
                }
            }
            Set<String> headerkey = cellMap.keySet();
            Set<String> columnName = getColumnName(clazz);
            String matchResult = equalsSet(headerkey, columnName);
            if (StrUtil.isNotBlank(matchResult)) {
                throw new QdpException(matchResult + DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.EXCEL_EXPORT_SHEET_CHECK, ""),
                        DefaultErrorMessage.EXCEL_EXPORT_SHEET_CHECK.intValue());
            }
            // 有数据时才处理 得到类的所有field.
            List<Object[]> fields = this.getFields();
            Map<Integer, Object[]> fieldsMap = new HashMap<Integer, Object[]>();
            for (Object[] objects : fields) {
                Excel attr = (Excel) objects[1];
                Integer column = cellMap.get(attr.name());
                if (column != null) {
                    fieldsMap.put(column, objects);
                }
            }

            for (int i = pageNum * pageSize; i < pageSize; i++) {
                // 从第2行开始取数据,默认第一行是表头.
                Row row = sheet.getRow(i + titleNum);
                // 判断当前行是否是空行
                if (isRowEmpty(row)) {
                    continue;
                }
                T entity = null;
                for (Map.Entry<Integer, Object[]> entry : fieldsMap.entrySet()) {
                    Object val = this.getCellValue(row, entry.getKey());

                    // 如果不存在实例则新建.
                    entity = (entity == null ? clazz.newInstance() : entity);
                    // 从map中得到对应列的field.
                    Field field = (Field) entry.getValue()[0];
                    Excel attr = (Excel) entry.getValue()[1];
                    // 取得类型,并根据对象类型设置值.
                    Class<?> fieldType = field.getType();
                    if (String.class == fieldType) {
                        String s = Convert.toStr(val);
                        if (StringUtils.endsWith(s, ".0")) {
                            val = StringUtils.substringBefore(s, ".0");
                        } else {
                            String dateFormat = field.getAnnotation(Excel.class).dateFormat();
                            if (StringUtils.isNotEmpty(dateFormat)) {
                                val = DateUtils.parseDateToStr(dateFormat, (Date) val);
                            } else {
                                val = Convert.toStr(val);
                            }
                        }
                    } else if ((Integer.TYPE == fieldType || Integer.class == fieldType) && StringUtils.isNumeric(Convert.toStr(val))) {
                        val = Convert.toInt(val);
                    } else if (Long.TYPE == fieldType || Long.class == fieldType) {
                        val = Convert.toLong(val);
                    } else if (Double.TYPE == fieldType || Double.class == fieldType) {
                        val = Convert.toDouble(val);
                    } else if (Float.TYPE == fieldType || Float.class == fieldType) {
                        val = Convert.toFloat(val);
                    } else if (BigDecimal.class == fieldType) {
                        if (val.toString().contains(",")) {
                            DecimalFormat format = new DecimalFormat();
                            format.setParseBigDecimal(true);
                            ParsePosition position = new ParsePosition(0);
                            val = format.parse(val.toString(), position);

                        } else {
                            val = Convert.toBigDecimal(val);
                        }

                    } else if (Date.class == fieldType) {
                        if (val instanceof String) {
                            val = DateUtils.parseDate(val);
                        } else if (val instanceof Double) {
                            val = DateUtil.getJavaDate((Double) val);
                        }
                    } else if (Boolean.TYPE == fieldType || Boolean.class == fieldType) {
                        val = Convert.toBool(val, false);
                    }
                    String propertyName = field.getName();
                    if (StringUtils.isNotEmpty(attr.targetAttr())) {
                        propertyName = field.getName() + "." + attr.targetAttr();
                    } else if (StringUtils.isNotEmpty(attr.readConverterExp())) {
                        val = reverseByExp(Convert.toStr(val), attr.readConverterExp(), attr.separator());
                    } else if (!attr.handler().equals(ExcelHandlerAdapter.class)) {
                        val = dataFormatHandlerAdapter(val, attr);
                    }
                    ReflectUtils.invokeSetter(entity, propertyName, val);
                }
                list.add(entity);
            }
        }
        return list;
    }

    public int getSheetSize() {
        return sheetSize;
    }

    public long getRecordLimit() {
        return recordLimit;
    }

    public int getSheetLimit() {
        return (int) sheetLimit;
    }

    public Workbook getWb() {
        return wb;
    }

    public static String getProtectSheet() {
        return PROTECT_SHEET;
    }

    public Excel.Type getType() {
        return type;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void setWb(Workbook wb) {
        this.wb = wb;
    }

    /**
     * 获取excel第一列(不包括第一行的数据)
     *
     * @param is
     * @return
     * @throws Exception
     */
    public List<String> getFirstColumn(InputStream is) throws Exception {
        List<String> list = new ArrayList<>();
        this.wb = WorkbookFactory.create(is);
        try {
            Sheet sheet = wb.getSheetAt(0);

            int rows = sheet.getLastRowNum();
            for (int i = 1; i <= rows; i++) {
                Row row = sheet.getRow(i);
                String value = getCellValue(row, 0).toString();
                list.add(value);
            }
            wb.close();
        } catch (IOException e) {
            log.error("获取excel第一列数据异常{}", e);
        }
        return list;
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param response   返回数据
     * @param list       导出数据集合
     * @param sheetNames 工作表的名称集合
     * @return 结果
     * @throws IOException
     */
    public void exportMoreSheetExcel(HttpServletResponse response, List<List<?>> list, String[] sheetNames) {


        if (recordLimit <= list.size()) {
            throw new QdpException("The data exceeds " + recordLimit + " lines and can't be exported. Pls adjust the searching conditions to export less data.");
        }
        if (list.size() != sheetNames.length) {
            throw new QdpException("sheet size must be equal to to class size");
        }
        exportMoreSheetExcel(response, list, sheetNames, org.apache.commons.lang3.StringUtils.EMPTY);
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param response   返回数据
     * @param list       导出数据集合
     * @param sheetNames 工作表的名称
     * @param title      标题
     * @return 结果
     * @throws IOException
     */
    public void exportMoreSheetExcel(HttpServletResponse response, List<List<?>> list, String[] sheetNames, String title) {
        this.init(list, sheetNames, title, Excel.Type.EXPORT);
        exportMoreSheetExcel(response);
    }

    public void init(List<List<?>> list, String[] sheetNames, String title, Excel.Type type) {
        this.lists = list;
        this.sheetNames = sheetNames;
        this.type = type;
        this.title = title;
        createMoreSheetExcelField();
        createWorkbook();
    }

    /**
     * 得到所有定义字段
     */
    private void createMoreSheetExcelField() {
        List<List<Object[]>> sheetFields = new ArrayList<>();
        for (Class<T> moreClass : clazzs) {
            this.clazz = moreClass;
            this.fields = getFields();
            this.fields = this.fields.stream().sorted(Comparator.comparing(objects -> ((Excel) objects[1]).sort())).collect(Collectors.toList());
            sheetFields.add(fields);
        }
        this.moreSheetFields = sheetFields;
        this.maxHeight = getRowHeight();
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @return 结果
     */
    public void exportMoreSheetExcel(HttpServletResponse response) {
        try {
            writeMoreSheet();
            this.exportExcel2Response(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导出Excel异常{}", e.getMessage());
        } finally {
            IOUtils.closeQuietly(wb);
        }
    }

    /**
     * 创建写入数据到Sheet
     */
    public void writeMoreSheet() {

        // 取出一共有多少个sheet.
        int moreSheetNo = lists.size();

        // 初始化sheet
        this.sheetName = sheetNames[0];
        initMoreSheet();


        for (int index = 1; index < moreSheetNo; index++) {
            createMoreSheet(moreSheetNo, index);

            // 产生一行
            Row row = sheet.createRow(rownum);
            int column = 0;
            // 写入各个字段的列头名称
            for (Object[] os : moreSheetFields.get(index)) {
                Excel excel = (Excel) os[1];
                this.createCell(excel, row, column++);
            }
            if (Excel.Type.EXPORT.equals(type)) {
                fillMoreSheetExcelData(index);
                if (org.apache.commons.lang3.StringUtils.isNotBlank(detail)) {
                    detailsPage(index, detail);
                }
                addStatisticsRow();
            }
        }
        if (Objects.equals(Boolean.TRUE, lockSheet)) {
            this.sheet.protectSheet(PROTECT_SHEET);
        }
    }

    /**
     * 创建工作表
     *
     * @param sheetNo sheet数量
     * @param index   序号
     */
    public void createMoreSheet(int sheetNo, int index) {
        // 设置工作表的名称.
        if (sheetNo != index) {
            this.sheet = wb.createSheet();
            int currentSheetIndex = wb.getSheetIndex(this.sheet);
            this.createTitle();
            wb.setSheetName(currentSheetIndex, sheetNames[index]);
            this.sheets.add(sheet);
        }

    }

    /**
     * 填充excel数据
     *
     * @param index 序号
     */
    public void fillMoreSheetExcelData(int index) {
        for (int j = 0; j < lists.size(); j++) {
            if (j >= sheets.size()) {
                continue;
            }
            this.list = lists.get(j);
            if (list.size() > 65535) {
                throw new QdpException("data size > 65535");
            }
            int startNo = 0;
            int endNo = Math.min(startNo + sheetSize, list.size());
            for (int i = startNo; i < endNo; i++) {
                Row row = sheets.get(j).createRow(i + 1 + rownum - startNo);
                // 得到导出对象.
                T vo = (T) list.get(i);
                int column = 0;
                for (Object[] os : moreSheetFields.get(j)) {
                    Field field = (Field) os[0];
                    Excel excel = (Excel) os[1];
                    this.addCell(excel, row, vo, field, column++);
                }
            }
            if (index == 0) {
                break;
            }
        }
    }

    /**
     * 初始化工作表
     *
     * @return
     */
    public void initMoreSheet() {
        this.sheet = wb.createSheet();
        int currentSheetNo = wb.getSheetIndex(this.sheet);
        wb.setSheetName(currentSheetNo, sheetName);
        createTitle();
        if (this.sheets == null) {
            this.sheets = new ArrayList<>();
        }
        this.sheets.add(sheet);
        // 产生一行
        Row row = sheet.createRow(rownum);
        int column = 0;
        // 写入各个字段的列头名称
        for (Object[] os : moreSheetFields.get(0)) {
            Excel excel = (Excel) os[1];
            this.createCell(excel, row, column++);
        }
        if (Excel.Type.EXPORT.equals(type)) {
            fillMoreSheetExcelData(0);
            if (org.apache.commons.lang3.StringUtils.isNotBlank(detail)) {
                detailsPage(0, detail);
            }
            addStatisticsRow();
        }
    }

}
