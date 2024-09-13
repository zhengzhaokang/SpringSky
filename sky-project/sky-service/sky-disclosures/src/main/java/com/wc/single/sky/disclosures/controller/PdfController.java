package com.wc.single.sky.disclosures.controller;

import com.wc.single.sky.common.core.controller.BaseController;
import com.wc.single.sky.common.core.domain.ResultDTO;
import com.wc.single.sky.disclosures.component.ITextPDFService;
import com.wc.single.sky.disclosures.component.JavaToPdfHtmlFreeMarker;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pdf")
@Api(value = "pdf Files", tags = {"pdf Files"})
@Slf4j
public class PdfController extends BaseController {

    @Autowired
    private ITextPDFService iTextPDFService;

    @PostMapping("/exportPaper")
    public void exportPdf(HttpServletResponse response) throws IOException {
        Map<String, Object> data = new HashMap<>();

        response.setCharacterEncoding("UTF-8");
        // 设置强制下载不打开
        response.setContentType("application/force-download");
        String encode = URLEncoder.encode("pdf", "utf-8");
        // 设置文件名
        response.addHeader("Content-Disposition", "attachment;fileName=" + encode);
        String content = JavaToPdfHtmlFreeMarker.freeMarkerRender(data, "template.html");
        ServletOutputStream outputStream = response.getOutputStream();
        JavaToPdfHtmlFreeMarker.createPdf(content, outputStream);

        // 关闭流
        outputStream.close();
        response.flushBuffer();
    }

    @PostMapping("/exportPdf")
    public ResultDTO<Object> exportPdf2(@RequestParam("disclosureId") String disclosureId) throws IOException {
        return ResultDTO.success(iTextPDFService.exportPdf(disclosureId));
    }
}
