package com.wc.single.sky.disclosures.service;

import com.wc.single.sky.disclosures.vo.DisclosureS3AttachmentVO;
import com.wc.single.sky.disclosures.vo.S3FileVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IoS3AttachmentService {

    Boolean deleteFile(String fileKey);


    List<DisclosureS3AttachmentVO> getS3AttachmentByTicketNumber(String ticketNumber);

    Boolean downloadCors(HttpServletRequest req, HttpServletResponse res, String fileKey);

    String downloadUrl(String fileKey);

    DisclosureS3AttachmentVO uploadFile(S3FileVO s3FileVO);
}
