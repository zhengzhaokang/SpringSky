package com.wc.single.sky.disclosures.component;

import com.alibaba.fastjson.JSON;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.tool.xml.ElementList;
import com.wc.single.sky.common.utils.DateUtils;
import com.wc.single.sky.common.utils.StringUtils;
import com.wc.single.sky.disclosures.common.DisclosureConstant;
import com.wc.single.sky.disclosures.common.MailConstant;
import com.wc.single.sky.disclosures.dto.DisclosuresItemDto;
import com.wc.single.sky.disclosures.manager.DisclosuresItemManager;
import com.wc.single.sky.disclosures.service.DisclosuresBasicService;
import com.wc.single.sky.disclosures.util.AidXMLWorkerUtil;
import com.wc.single.sky.disclosures.util.SendMailUtil;
import com.wc.single.sky.disclosures.vo.DisclosureApproveLogVO;
import com.wc.single.sky.disclosures.vo.DisclosuresBasicVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.util.List;

@Component
@Slf4j
public class ITextPDFService {

    @Autowired
    private DisclosuresBasicService disclosuresBasicService;

    @Autowired
    private DisclosuresItemManager disclosuresItemManager;

    @Autowired
    private SendMailUtil mailUtil;

    private final static float[] columnWidths = {20, 40, 20, 40, 40, 40, 20, 60, 20, 20};

    private final static float leading = 6f;

    private final static float widthPercentage = 100;

    private final static String fontName = "Arial.ttf";

    @Value("${pdfPath: /}")
    private String pdfPath;

    private final static String[] titleValues = {"Item#", "Disclosure Item Description", "region", "Root Cause",
             "Impact (value in USD if possible)", "Acctg. Entry(if possible)", "Was the entry recorded in qtr books? (Y/N)",
            "Action/ Owner/ Target Date","Reported in Prior Quarter (Y/N)", "Item Classification(O/ L/ A)"};

    private final static int FONT_SIZE = 10;

    private final static int TITLE_FONT_SIZE = 14;

    @Async("qdpTaskExecutor")
    public void exportPdfAndSendEmail(DisclosuresBasicVO disclosuresBasicVO, DisclosuresBasicVO curDisclosuresBasic, DisclosureApproveLogVO disclosureApproveLogVO) {
        log.info("### ITextPDFService exportPdfAndSendEmail start");
        long start = System.currentTimeMillis();
        String path = exportPdf(disclosuresBasicVO.getBasicId());
        if (StringUtils.isBlank(path)) {
            log.error("### exportPdfAndSendEmail path is Blank");
            return;
        }
        List<String> toList = disclosuresBasicVO.getToList();
        if (CollectionUtils.isNotEmpty(toList)) {
            log.info("### ITextPDFService exportPdfAndSendEmail toList is {}", JSON.toJSONString(toList));
            for (String sendTo : toList) {
                disclosuresBasicVO.setEmailTo(sendTo);
                sendApproveEmail(disclosuresBasicVO, curDisclosuresBasic, disclosureApproveLogVO, path);
            }
        } else {
            log.error("### ITextPDFService exportPdfAndSendEmail toList is Empty");
        }

        File file = new File(path);
        //删除临时文件
        if (file.exists()) {
            file.delete();
        }
        log.info("### ITextPDFService exportPdfAndSendEmail end time is {}", System.currentTimeMillis() -start);
    }

    private void sendApproveEmail(DisclosuresBasicVO disclosuresBasicVO, DisclosuresBasicVO curDisclosuresBasic, DisclosureApproveLogVO disclosureApproveLogVO, String path) {
        List<String> toList = Lists.newArrayList();
        // 收件人待修改
        String emailTo = disclosuresBasicVO.getEmailTo();
        toList.add(emailTo + MailConstant.EMAIL_SUFFIX_LENOVO);

        ArrayList<String> paths = new ArrayList<>();
        paths.add(path);

        Map<String, Object> params = new HashMap<>();
        params.put(MailConstant.CFO, emailTo);
        params.put(MailConstant.GEO_BG, getGeoBg(curDisclosuresBasic));
        params.put(MailConstant.DATE, getCurDateStr());
        params.put("contentTitle", getContentTitle(curDisclosuresBasic));
        params.put("approveUuid", disclosureApproveLogVO.getApproveUuid());
        params.put("basicId", disclosuresBasicVO.getBasicId());
        params.put("approver", emailTo);

        SendMailUtil.MailContentBean mailContentBean = new SendMailUtil.MailContentBean(params);
        mailUtil.sendMailToCFO(disclosuresBasicVO.getUpdateBy(), disclosuresBasicVO.getUpdateBy(), toList, null, null, mailContentBean, paths);
    }

    private Object getCurDateStr() {
        return "Date: " + DateUtils.dateFormat(new Date(), DateUtils.DATE_YYYY_MM_DD_EN);
    }

    private String getGeoBg(DisclosuresBasicVO curDisclosuresBasic) {
        String content = "";
        String geoCode = curDisclosuresBasic.getGeoCode();
        String businessGroup = curDisclosuresBasic.getBusinessGroup();
        if (StringUtils.isBlank(geoCode)) {
            content += businessGroup;
        }
        if (StringUtils.isBlank(businessGroup)) {
            content += geoCode;
        }
        if (StringUtils.isNotBlank(geoCode) && StringUtils.isNotBlank(businessGroup)) {
            content += geoCode + "(" + businessGroup + ")";
        }
        return content;
    }

    public String exportPdf(String disclosureId) {

        // pdf中表格需要填充的数据
        List<List<String>> data = new ArrayList<>();
        DisclosuresItemDto disclosuresItemDto = new DisclosuresItemDto();
        disclosuresItemDto.setDisclosuresId(disclosureId);
        List<DisclosuresItemDto> disclosuresItemDtoList = disclosuresItemManager.selectDisclosuresItemList(disclosuresItemDto);
        if (CollectionUtils.isEmpty(disclosuresItemDtoList)) {
            log.error("### ITextPDFService exportPdf disclosuresItemDtoList is empty");
            return null;
        }
        DisclosuresBasicVO disclosuresBasicById = disclosuresBasicService.selectDisclosuresBasicById(Long.parseLong(disclosureId));
        if (disclosuresBasicById == null) {
            log.error("### ITextPDFService exportPdf disclosuresBasicById is null");
            return null;
        }
        String contentTitle = getContentTitle(disclosuresBasicById);
        // ### 设置Tab数据 ###
        for (int i = 0; i < disclosuresItemDtoList.size(); i++) {
            List<String> valueList = getValueList(disclosuresItemDtoList, i);
            data.add(valueList);
        }
        //创建文件
        Document document = new Document(PageSize.A4);
        // 保存的pdf全路径
        String outPdfPath = pdfPath + contentTitle + ".pdf";
        File file = new File(outPdfPath);
        try {
            PdfWriter.getInstance(document, Files.newOutputStream(file.toPath()));
            //打开文件
            document.open();
//            URL resource = getClass().getClassLoader().getResource(fontName);
//            if (resource == null) {
//                log.error("### exportPdf resource is null");
//                return "";
//            }
//            String fontPath = resource.getPath();
//            log.info("### exportPdf fontPath is {}", fontPath);
            // 指定字体为 默认字体，支持修改
            // 创建BaseFont对象，指定字体路径和编码
            BaseFont bfChinese = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);

            Font fontChineseTitle = new Font(bfChinese, TITLE_FONT_SIZE, Font.BOLD);
            Paragraph paragraphTitle = new Paragraph(contentTitle, fontChineseTitle);
            paragraphTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraphTitle);
            Paragraph blankRow1 = new Paragraph(leading, " ", fontChineseTitle);
            document.add(blankRow1);

            Font fontChineseParagraph = new Font(bfChinese, FONT_SIZE, Font.NORMAL);
            paragraphTitle.setSpacingAfter(100);
            paragraphTitle.setSpacingBefore(100);
            Paragraph blankRow2 = new Paragraph(leading, " ", fontChineseParagraph);
            document.add(blankRow2);

            for (int i = 0; i < data.size(); i++) {
                Font fontChineseTable = new Font(bfChinese, FONT_SIZE, Font.NORMAL);
                PdfPTable table = new PdfPTable(titleValues.length);
                table.setWidthPercentage(widthPercentage);
                List<PdfPRow> listRow = table.getRows();
                //设置列宽
                table.setWidths(columnWidths);
                //行1
                PdfPCell[] cells1 = new PdfPCell[titleValues.length];
                PdfPRow row1 = new PdfPRow(cells1);
                //单元格
                setTableTitle(cells1, fontChineseTable);
                //把第一行添加到集合
                listRow.add(row1);
                PdfPRow rowData = getRowDate(data, i, fontChineseParagraph, titleValues.length);
                listRow.add(rowData);
                //把表格添加到文件中
                document.add(table);
                Paragraph blankRow3 = new Paragraph(leading, " ", fontChineseParagraph);
                document.add(blankRow3);

                document.newPage(); //分页
            }

            log.info("### ITextPDFService exportPdf success");
        } catch (Exception e) {
            log.error("导出pdf失败", e);
        } finally {
            //关闭文档
            document.close();
        }
        return outPdfPath;
    }

    private String getContentTitle(DisclosuresBasicVO disclosuresBasicById) {
        String contentTitle = DisclosureConstant.QDP + disclosuresBasicById.getFiscalYear() + "_" + disclosuresBasicById.getQuarter();
        String geoCode = disclosuresBasicById.getGeoCode();
        String businessGroup = disclosuresBasicById.getBusinessGroup();
        if (StringUtils.isBlank(geoCode)) {
            contentTitle += "_" + businessGroup;
        }
        if (StringUtils.isBlank(businessGroup)) {
            contentTitle += "_" + geoCode;
        }
        if (StringUtils.isNotBlank(geoCode) && StringUtils.isNotBlank(businessGroup)) {
            contentTitle += "_" + geoCode + "(" + businessGroup + ")";
        }
        return contentTitle;
    }

    private List<String> getValueList(List<DisclosuresItemDto> disclosuresItemDtoList, int i) {
        List<String> valueList = new ArrayList<>();
        DisclosuresItemDto item = disclosuresItemDtoList.get(i);
        valueList.add(getParamWithP(String.valueOf(i + 1)));
        valueList.add(item.getDescription());
        valueList.add(getParamWithP(item.getRegion()));
        valueList.add(item.getRootCause());
        valueList.add(item.getImpact());
        valueList.add(getParamWithP(item.getAcctEntry()));
        valueList.add(getParamWithP(item.getInQtrBooks()));
        valueList.add("<p>Action: " + item.getAction() + "</p> <p>Owner: " + item.getOwner() + "</p> <p>Target Date: " + DateUtils.dateFormat(item.getTargetDate(), "") + "</p>");
        valueList.add(getParamWithP(item.getInPriorQuarter()));
        valueList.add(getParamWithP(item.getItemClassification()));
        return valueList;
    }

    public String getParamWithP(String param) {
        if (StringUtils.isBlank(param)) {
            return "";
        }
        return "<p>" + param + "</p>";
    }

    private PdfPRow getRowDate(List<List<String>> data, int i, Font fontChineseParagraph, int length) {
        PdfPCell[] cellsi = new PdfPCell[length];
        PdfPRow rowi = new PdfPRow(cellsi);
        try {
            for (int j = 0; j < length; j++) {
                Paragraph paragraph = new Paragraph();
                cellsi[j] = new PdfPCell(paragraph);
                String content = data.get(i).get(j);
                ElementList elements = AidXMLWorkerUtil.parseToElementList(content, null);
                for (Element element : elements) {
                    cellsi[j].addElement(element);
                }
                paragraph.setFont(fontChineseParagraph);
                cellsi[j].setBorderColor(BaseColor.BLACK);
                cellsi[j].setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellsi[j].setBackgroundColor(BaseColor.PINK);
            }
        } catch (Exception e) {
            log.error("### ITextPDFService getRowDate error", e);
        }
        return rowi;
    }

    private void setTableTitle(PdfPCell[] cells1, Font fontChineseTable) {
        try {
            for (int i = 0; i < ITextPDFService.titleValues.length; i++) {
                Paragraph elements = new Paragraph(titleValues[i], fontChineseTable);
                cells1[i] = new PdfPCell(elements);
                cells1[i].setBorderColor(BaseColor.BLACK);
                cells1[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
                cells1[i].setBackgroundColor(BaseColor.RED);
            }
        } catch (Exception e) {
            log.error("### ITextPDFService setTableTitle error", e);
        }
    }
}
