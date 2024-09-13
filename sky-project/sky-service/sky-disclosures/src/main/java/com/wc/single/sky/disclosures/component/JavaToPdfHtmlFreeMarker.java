package com.wc.single.sky.disclosures.component;

import com.itextpdf.text.pdf.BaseFont;
import com.lowagie.text.DocumentException;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

@Component
public class JavaToPdfHtmlFreeMarker {
    private static FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        JavaToPdfHtmlFreeMarker.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    private static final String HTML = "template.html";
    private static final String FONT = "templates/simsun.ttf";

    public static void createPdf(String content, OutputStream outputStream) {
        try {
            ITextRenderer renderer = new ITextRenderer();
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            // 添加多种字体
            fontResolver.addFont("templates/方正小标宋.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.setDocumentFromString(content);
            renderer.layout();
            renderer.createPDF(outputStream);
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * freemarker渲染html
     */
    public static String freeMarkerRender(Map<String, Object> data, String htmlTmp) {
        Writer out = new StringWriter();
        try {
            // 获取模板,并设置编码方式
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate(htmlTmp);
            template.setEncoding("UTF-8");
            // 合并数据模型与模板
            template.process(data, out);
            out.flush();
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
