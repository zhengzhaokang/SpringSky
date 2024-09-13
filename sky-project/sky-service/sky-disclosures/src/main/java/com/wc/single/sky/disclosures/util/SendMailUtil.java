package com.wc.single.sky.disclosures.util;

import com.wc.single.sky.common.utils.JsonUtil;
import com.wc.single.sky.disclosures.common.MailConstant;
import com.wc.single.sky.disclosures.component.EmailSendService;
import com.wc.single.sky.disclosures.dto.EmailSendDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class SendMailUtil {
    @Autowired
    public EmailSendService emailSendService;

    private static final String PARAM_DISCLOSURE_NAME = "disclosureName";

    private EmailSendDTO createMail(String createBy, String sendFrom, List<String> sendTo, List<String> sendCc, MailTitleBean titleBean, MailContentBean contentBean, ArrayList<String> attachmentPath) {
        EmailSendDTO mail = new EmailSendDTO();
        mail.setTraceId(UUID.randomUUID().toString());
        mail.setCreateBy(createBy);
        mail.setMarket(null);
        mail.setMailFrom(sendFrom);
        mail.setSendTo(sendTo);
        mail.setSendCc(sendCc);
        mail.setBean(contentBean == null ? null : JsonUtil.toJSON(contentBean));
        mail.setMailTitleBean(titleBean == null ? null : JsonUtil.toJSON(titleBean));
        mail.setGeoCode(MailConstant.GEO_CODE_ALL);
        mail.setBusinessGroup(MailConstant.BUSINESS_GROUP_ALL);
        mail.setModule(MailConstant.MAIL_MODULE_QDP);
        if (CollectionUtils.isNotEmpty(attachmentPath)) {
            mail.setAttachmentPath(attachmentPath);
        }
        return mail;
    }

    public boolean sendMailToCFO(String createBy, String sendFrom, List<String> sendTo, List<String> sendCc, MailTitleBean titleBean, MailContentBean contentBean, ArrayList<String> attachmentPath) {
        EmailSendDTO mail = createMail(createBy, sendFrom, sendTo, sendCc, titleBean, contentBean, attachmentPath);

        mail.setBusinessType(MailConstant.BUSINESS_TYPE_BASIC);
        mail.setEmailType(MailConstant.MAIL_TYPE_BASIC_REVIEWED);

        return sendMail(mail);
    }

    public boolean sendMailToQdpFocal(String createBy, String sendFrom, List<String> sendTo, List<String> sendCc, MailTitleBean titleBean, MailContentBean contentBean) {
        EmailSendDTO mail = createMail(createBy, sendFrom, sendTo, sendCc, titleBean, contentBean, null);

        mail.setBusinessType(MailConstant.BUSINESS_TYPE_BASIC);
        mail.setEmailType(MailConstant.MAIL_TYPE_BASIC_TIP);

        return sendMail(mail);
    }

    public boolean sendMailToQdpBasicByQuarter(String createBy, String sendFrom, List<String> sendTo, List<String> sendCc, MailTitleBean titleBean, MailContentBean contentBean) {
        EmailSendDTO mail = createMail(createBy, sendFrom, sendTo, sendCc, titleBean, contentBean, null);

        mail.setBusinessType(MailConstant.BUSINESS_TYPE_BASIC);
        mail.setEmailType(MailConstant.MAIL_TYPE_BASIC_QUARTER_TIP);

        return sendMail(mail);
    }

    public boolean sendMailToFocalByQuarter(String createBy, String sendFrom, List<String> sendTo, List<String> sendCc, MailTitleBean titleBean, MailContentBean contentBean) {
        EmailSendDTO mail = createMail(createBy, sendFrom, sendTo, sendCc, titleBean, contentBean, null);

        mail.setBusinessType(MailConstant.BUSINESS_TYPE_BASIC);
        mail.setEmailType(MailConstant.MAIL_TYPE_BASIC_FOCAL_TIP);

        return sendMail(mail);
    }


    public boolean sendMail(EmailSendDTO emailSendDTO) {
        log.info("sendMail,mail content:{}", JsonUtil.toJSON(emailSendDTO));
        emailSendService.joinParameters(emailSendDTO);
        log.info("sendMail,success");
        return true;
    }

    public static class MailTitleBean extends HashMap<String, Object> {

        public MailTitleBean(String titleParam) {
            put(PARAM_DISCLOSURE_NAME, titleParam);
        }

        public MailTitleBean(Map<String, Object> params) {
            putAll(params);
        }
    }

    public static class MailContentBean extends HashMap<String, Object> {
        public MailContentBean(String contentParam) {
            put(PARAM_DISCLOSURE_NAME, contentParam);
        }

        public MailContentBean(Map<String, Object> params) {
            putAll(params);
        }
    }
}
