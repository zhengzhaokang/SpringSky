package com.wc.single.sky.disclosures.service;

import com.wc.single.sky.disclosures.vo.DisclosureItemAttachmentVO;
import com.wc.single.sky.disclosures.vo.DisclosureS3AttachmentVO;

import java.util.List;

public interface DisclosureItemAttachmentService {

    int saveDisclosureItemAttachment(List<DisclosureItemAttachmentVO> disclosureItemAttachmentVOList, String business, String module);

    List<DisclosureItemAttachmentVO> queryDisclosureItemAttachment(String business, String module);

    int deleteDisclosureItemAttachment(String business, String module);

    int addDisclosureItemAttachment(List<DisclosureS3AttachmentVO> disclosureItemAttachmentVOList, String business);

    int removeDisclosureItemAttachment(String business);

    List<DisclosureS3AttachmentVO> getS3AttachmentByTicketNumber(String ticketNumber);

}
