package com.wc.single.sky.disclosures.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wc.single.sky.common.exception.QdpException;
import com.wc.single.sky.common.exception.message.DefaultErrorMessage;
//import com.wc.single.common.kafka.util.KafkaSend;
import com.wc.single.sky.common.utils.CommonUtils;
import com.wc.single.sky.common.utils.StringUtils;
import com.wc.single.sky.disclosures.common.enums.EmailEnum;
import com.wc.single.sky.disclosures.dto.EmailSendDTO;
import com.wc.single.sky.disclosures.entity.*;
import com.wc.single.sky.disclosures.mapper.EmailSendMapper;
import com.wc.single.sky.disclosures.util.EmailUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.annotation.Resource;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.wc.single.sky.disclosures.common.ConstantEmail.*;


/**
 * @Description:
 * @Author: shirui3
 * @Date: 2021/12/31
 */
@Service
public class EmailSendService {

    public static final String CREATE_BY = "createBy";
    public static final String MARKET = "market";
    public static final String SEND_FROM = "sendFrom";
    public static final String TRACE_ID = "traceId";
    public static final String MODULE = "module";
    public static final String BUSINESS_TYPE = "businessType";
    public static final String EMAIL_TYPE = "emailType";
    public static final String BUSINESS_GROUP = "businessGroup";
    public static final String GEO_CODE = "geoCode";
    public static final String SEND_TO_LIST = "sendToList";
    public static final String SEND_CC = "sendCc";
    private final Logger logger = LoggerFactory.getLogger(EmailSendService.class);

//    @Autowired
//    private KafkaLogListener kafkaLogListener;
//    @Autowired
//    private KafkaSend kafkaSend;


    private static final String KAFKA_LISTENER_ID = "sendEmail";

//    @Value("${kafka.topic.send-email}")
    private String sendEmailTopic;

    @Autowired
    public EmailSendMapper emailSendMapper;


//    @Value("${email-config.token}")
//    private String emailToken;
//
//    @Value("${email-config.text-api-url}")
//    private String textApiPath;
//
//    @Value("${email-config.service}")
//    private String serviceName;


    @Autowired
    private EmailSendService sendService;
    @Autowired
    private EmailTemplateService emailTemplateService;

    @Resource
    private EmailService emailService;

    public void checkSendStatus(EmailSendHistoryRequest emailSendHistoryRequest)  {
        List<EmailSendHistoryRequest> emailSendHistoryListForChecks ;
        emailSendHistoryListForChecks = sendService.getEmailSendHistoryListForCheck(emailSendHistoryRequest);
        if(!CommonUtils.isNullList(emailSendHistoryListForChecks)){
            for (EmailSendHistoryRequest emailSendHistoryFail: emailSendHistoryListForChecks) {
                Map<String, List<String>> sendInfo = new HashMap<>();
                sendInfo.put(SEND_TO_LIST, JSON.parseArray(emailSendHistoryFail.getMailTo(), String.class));
                sendInfo.put(SEND_CC, JSON.parseArray(emailSendHistoryFail.getMailCc(), String.class));

                Map<String,String> emailInfo = new HashMap<>();
                emailInfo.put(CREATE_BY,emailSendHistoryFail.getCreateBy());
                emailInfo.put(MARKET,emailSendHistoryFail.getMarket());
                emailInfo.put(SEND_FROM,emailSendHistoryFail.getMailFrom());
                emailInfo.put(TRACE_ID,emailSendHistoryFail.getTraceId());

                Map<String,String> emailDimension = new HashMap<>();
                emailDimension.put(MODULE,emailSendHistoryFail.getModule());
                emailDimension.put(BUSINESS_TYPE,emailSendHistoryFail.getBusinessType());
                emailDimension.put(EMAIL_TYPE,emailSendHistoryFail.getEmailType());
                emailDimension.put(BUSINESS_GROUP,emailSendHistoryFail.getBusinessGroup());
                emailDimension.put(GEO_CODE,emailSendHistoryFail.getGeoCode());

                JSONObject normalParam = JSON.parseObject(emailSendHistoryFail.getBean());
                Map<String, String> tableParamsForBean = emailSendHistoryFail.getTableParamsForBean();
                JSONObject mailTitleBean = JSON.parseObject(emailSendHistoryFail.getMailTitleBean());

                sendPackageEmailInfo(null, emailDimension, tableParamsForBean,normalParam,mailTitleBean,emailInfo,sendInfo);
            }
        }
    }


    public List<EmailSendHistoryRequest> getFailSendEmail(EmailSendHistoryRequest emailSendHistoryRequest) {
        return sendService.getEmailSendHistoryListForCheck(emailSendHistoryRequest);
    }

//    @KafkaListener(containerFactory = KafkaFactory.SEND_EMAIL_KAFKA_FACTORY, topics = {"${kafka.topic.send-email}"}, groupId = KafkaGroup.OTMP_SVC, id = KAFKA_LISTENER_ID)
//    public void listen(ConsumerRecord<?, ?> consumerRecord, Acknowledgment ack) {
//        String value = (String) consumerRecord.value();
//        logger.info("send email 接收参数：{}", value);
//        try{
//            EmailSendDTO sendRequests = JSON.parseObject(value, EmailSendDTO.class);
//            joinParameters(sendRequests);
//            //手动提交
//            ack.acknowledge();
//        }catch (Exception e){
//           // kafkaLogListener.consumerErrorRecordLog(consumerRecord,e,ack);
//        }
//    }


    public Integer joinParameters(EmailSendDTO emailSendDTO) {
        logger.info("emailSendHistoryRequest params:{}",JSON.toJSON(emailSendDTO));
        //不能重发
        List<EmailSendHistoryRequest> sendHistorySuccess =  checkSendHistory(emailSendDTO);
        if(!CommonUtils.isNullList(sendHistorySuccess)){
            throw new QdpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.EMAIL_SEND_REPEAT), DefaultErrorMessage.EMAIL_SEND_REPEAT.intValue());
        }

        Map<String, List<String>> sendInfo = new HashMap<>();
        sendInfo.put(SEND_TO_LIST, emailSendDTO.getSendTo() );
        sendInfo.put(SEND_CC, emailSendDTO.getSendCc() );

        Map<String,String> emailInfo = new HashMap<>();
        emailInfo.put(CREATE_BY,emailSendDTO.getCreateBy());
        emailInfo.put(MARKET,emailSendDTO.getMarket());
        emailInfo.put(SEND_FROM,emailSendDTO.getMailFrom());
        emailInfo.put(TRACE_ID,emailSendDTO.getTraceId());

        Map<String,String> emailDimension = new HashMap<>();
        emailDimension.put(MODULE,emailSendDTO.getModule());
        emailDimension.put(BUSINESS_TYPE,emailSendDTO.getBusinessType());
        emailDimension.put(EMAIL_TYPE,emailSendDTO.getEmailType());
        emailDimension.put(BUSINESS_GROUP,emailSendDTO.getBusinessGroup());
        emailDimension.put(GEO_CODE,emailSendDTO.getGeoCode());

        JSONObject normalParam=  JSON.parseObject(emailSendDTO.getBean());
        Map<String,String> tableParamsForBean=  emailSendDTO.getTableParamsForBean();
        JSONObject mailTitleBean= JSON.parseObject(emailSendDTO.getMailTitleBean());
        return sendPackageEmailInfo(emailSendDTO, emailDimension, tableParamsForBean,normalParam,mailTitleBean,emailInfo,sendInfo);
    }

    public Integer sendPackageEmailInfo(EmailSendDTO emailSendDTO, Map<String,String> emailDimension, Map<String,String> tableParams, JSONObject normalParams, JSONObject mailTitleBean, Map<String,String> emailInfo, Map<String, List<String>> sendInfo) {
        EmailSendKafka emailSendKafka = new EmailSendKafka();
        try{
            CommonUtils.isNull(emailInfo, "emailInfo is empty");
            CommonUtils.isNull(emailInfo.get(TRACE_ID), "traceId is empty");
            CommonUtils.isNull(emailInfo.get(CREATE_BY), "createBy is empty");
//            CommonUtils.isNull(emailInfo.get(MARKET), "market is empty");
            CommonUtils.isNull(emailInfo.get(SEND_FROM), "sendFrom is empty");
            CommonUtils.isNull(sendInfo.get(SEND_TO_LIST), "sendToList is empty");

            EmailDimension ed=  this.generateEmailDimension(emailDimension);
            EmailParamEntity emailParamEntity = generateEmailInfoByBean(ed, tableParams,  normalParams,mailTitleBean);

            CommonUtils.isNull(emailParamEntity, "The generated emailParamEntity is empty");


            emailSendKafka.setEmailDimension(emailDimension);
            emailSendKafka.setEmailInfo(emailInfo);

            if (emailSendDTO != null && CollectionUtils.isNotEmpty(emailSendDTO.getAttachmentPath())) {
                emailParamEntity.setAttachmentPath(emailSendDTO.getAttachmentPath());
            }
            emailSendKafka.setEmailParamEntity(emailParamEntity);

            emailSendKafka.setSendInfo(sendInfo);
            emailSendKafka.setNormalParams(normalParams);
            emailSendKafka.setTableParams(tableParams);

            sendService.sendEmailFromKafka(emailSendKafka);
            return 1;
        }catch(Exception e){
            logger.error("email校验不通过"+e.getMessage(), e);
            emailSendKafka.setEmailDimension(emailDimension);
            emailSendKafka.setEmailInfo(emailInfo);
            emailSendKafka.setSendInfo(sendInfo);
            emailSendKafka.setNormalParams(normalParams);
            emailSendKafka.setTableParams(tableParams);
            emailSendKafka.setFailReason(e.getMessage());
            logger.error("email校验不通过,参数:{}",JSON.toJSONString(emailSendKafka));
            logger.error("邮件模板参数：{}", emailDimension.get(MODULE)+"_"+emailDimension.get(EMAIL_TYPE)+"_"+emailDimension.get(GEO_CODE)+"_"+emailDimension.get(BUSINESS_GROUP)+"_"+emailDimension.get(BUSINESS_TYPE));
            insertValidateFailHistory(emailSendKafka);
            return 0;
        }
    }


    public void insertValidateFailHistory(EmailSendKafka emailSendKafka){
        Map<String, String> emailDimension = emailSendKafka.getEmailDimension();
        Map<String, String> emailInfo = emailSendKafka.getEmailInfo();
        Map<String, List<String>> sendInfo = emailSendKafka.getSendInfo();
        JSONObject normalParams = emailSendKafka.getNormalParams();
        Map<String,String> tableParams = emailSendKafka.getTableParams();
        String uniqueId = UUID.randomUUID().toString();
        //插入一条发送记录
        sendService.insertSendHistory(EmailSendHistoryEntity.builder()
                .traceId(emailInfo.get(TRACE_ID))
                .uniqueId(uniqueId)
                .failReason(emailSendKafka.getFailReason())
                .emailType(emailDimension.get(EMAIL_TYPE))
                .module(emailDimension.get(MODULE))
                .geoCode(emailDimension.get(GEO_CODE))
                .businessType(emailDimension.get(BUSINESS_TYPE))
                .normalParams(JSON.toJSONString(normalParams))
                .tableParams(JSON.toJSONString(tableParams))
                .deleteFlag(false)
                .createBy(emailInfo.get(CREATE_BY))
                .updateBy(emailInfo.get(CREATE_BY))
                .market(emailInfo.get(MARKET))
                .businessGroup(emailDimension.get(BUSINESS_GROUP))
//                .mailTitle(emailParamEntity.getSubject())
//                .mailBody(emailParamEntity.getContent())
                .attachment("none")
                .mailFrom(emailInfo.get(SEND_FROM))
                .mailTo(JSON.toJSONString(sendInfo.get(SEND_TO_LIST)))
                .mailCc(StringUtils.isEmpty(sendInfo.get(SEND_CC))?"":JSON.toJSONString(sendInfo.get(SEND_CC)))
                .success(EmailEnum.FAIL_SEND.getCode())
                .build());

    }

    public void sendEmailFromKafka(EmailSendKafka emailSendKafka) {
        Map<String, String> emailDimension = emailSendKafka.getEmailDimension();
        Map<String, String> emailInfo = emailSendKafka.getEmailInfo();
        EmailParamEntity emailParamEntity = emailSendKafka.getEmailParamEntity();
        Map<String, List<String>> sendInfo = emailSendKafka.getSendInfo();
        JSONObject normalParams = emailSendKafka.getNormalParams();
        Map<String,String> tableParams = emailSendKafka.getTableParams();

        //收件人
        emailParamEntity.setTo(sendInfo.get(SEND_TO_LIST));
        emailParamEntity.setCc(sendInfo.get(SEND_CC));
        String uniqueId = UUID.randomUUID().toString();
        //插入一条发送记录
        sendService.insertSendHistory(EmailSendHistoryEntity.builder()
                .traceId(emailInfo.get(TRACE_ID))
                .uniqueId(uniqueId)
                .emailType(emailDimension.get(EMAIL_TYPE))
                .module(emailDimension.get(MODULE))
                .geoCode(emailDimension.get(GEO_CODE))
                .businessType(emailDimension.get(BUSINESS_TYPE))
                .normalParams(JSON.toJSONString(normalParams))
                .tableParams(JSON.toJSONString(tableParams))
                .deleteFlag(false)
                .createBy(emailInfo.get(CREATE_BY))
                .updateBy(emailInfo.get(CREATE_BY))
                .market(emailInfo.get(MARKET))
                .businessGroup(emailDimension.get(BUSINESS_GROUP))
                .mailTitle(emailParamEntity.getSubject())
                .mailBody(emailParamEntity.getContent())
                .attachment("none")
                .mailFrom(emailInfo.get(SEND_FROM))
                .mailTo(JSON.toJSONString(sendInfo.get(SEND_TO_LIST)))
                .mailCc(StringUtils.isEmpty(sendInfo.get(SEND_CC))?"":JSON.toJSONString(sendInfo.get(SEND_CC)))
                .success(EmailEnum.NOT_SEND.getCode())
                .build());


        //逻辑删除 之前发送的记录
        sendService.deleteBeforeSendHistory(EmailSendHistoryEntity.builder()
                .traceId(emailInfo.get(TRACE_ID))
                .uniqueId(uniqueId)
                .updateBy(emailInfo.get(CREATE_BY))
                .deleteFlag(true)
                .build());

        boolean emailSend = emailService.delivery(emailParamEntity);
        sendService.updateSendHistory(EmailSendHistoryEntity.builder()
                .uniqueId(uniqueId)
                .deleteFlag(false)
                .updateBy(emailInfo.get(CREATE_BY))
                //根据是否发送成功 更新历史表数据状态
                .success(emailSend? EmailEnum.SUCCESS_SEND.getCode():EmailEnum.FAIL_SEND.getCode())
                .failReason(emailSend?"":"调用xmessage失败")
                .build());

        //逻辑删除 历史数据 即 同traceId的 非uniqueId(本次)的数据
        if(emailSend){
            sendService.deleteSendHistory(EmailSendHistoryEntity.builder()
                    .uniqueId(uniqueId)
                    .deleteFlag(true)
                    .traceId(emailInfo.get(TRACE_ID))
                    .updateBy(emailInfo.get(CREATE_BY))
                    //根据是否发送成功 更新历史表数据状态
                    .build());
        }
    }

    private boolean sendEmail(EmailParamEntity emailParamEntity) {
        try {
            EmailEntity emailEntity = new EmailEntity();
//            emailEntity.setTextSendURL(textApiPath);
//            emailEntity.setAttachmentSendURL("https://xmessage-api.earth.xpaas.lenovo.com/api/open/email/sendwithattachment");
//
//            emailParamEntity.setToken(emailToken);
//            emailParamEntity.setService(serviceName);
            emailParamEntity.setFrom("SDMS@lenovo.com");

            emailEntity.setEmailParamEntity(emailParamEntity);

            return EmailUtils.sendEmail(emailEntity);
        } catch(Exception e){
            e.printStackTrace();
            throw new QdpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.EMAIL_SEND_FAIL), DefaultErrorMessage.EMAIL_SEND_FAIL.intValue());
        }
    }


    private EmailParamEntity generateEmailInfoByBean(EmailDimension emailDimension, Map<String,String> tableparams, JSONObject params,JSONObject mailTitleBean) throws DocumentException, SAXException {

        EmailParamEntity emailMessage = new EmailParamEntity();
        //查模板
        List<EmailTemplateEntity> emailTemplateEntity =emailTemplateService.getOneEmailTemplate(emailDimension);
        CommonUtils.isNullList(emailTemplateEntity,DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.EMAIL_TEMPLATE__NOT_EXSIT));


        String template = emailTemplateEntity.get(0).getMailBody();
        String mailTitle = emailTemplateEntity.get(0).getMailTitle();

        String flagStart = "#{{";
        String flagEnd = "}}";
        String flagStartFortitle = "#{{";
        String flagEndFortitle = "}}";
        List<String> parms =   getParams(template,flagStart,flagEnd);
        List<String> titleparms =   getParams(mailTitle,flagStartFortitle,flagEndFortitle);

        template = replaceNormalParams(params, template, flagStart, flagEnd, parms);
        mailTitle = repalceTitleParms(params, mailTitleBean, mailTitle, flagStartFortitle, flagEndFortitle, titleparms);

        //加头尾
        template = handleHtmlDoctype(template);


        SAXReader reader = new SAXReader(false);
        reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
        reader.setFeature("http://xml.org/sax/features/external-general-entities", false);
        reader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);


        Reader stringReader = new StringReader(template);
        Document doc = reader.read(stringReader);
        


        //匹配出表格里的参数 都是 >approver</td> 这种的, 用正则匹配出来 之后 对于 没传参数的 也就是无值的 就进行替换成></td>
        List<String> noValue = new ArrayList<>();
        //有几个表格
        template = replaceTableParams(tableparams, template, doc, noValue);

        noValue= noValue.stream().distinct().collect(Collectors.toList());
        for (String nv:noValue) {
            template = template.replace(nv,"></td>");
        }

        //最后过滤一遍干扰字符
        template= template.replace("]><html", "<html");

        emailMessage.setContent(template);
        emailMessage.setSubject(mailTitle);

        return emailMessage;
    }

    private static String replaceTableParams(Map<String,String> tableparams, String template, Document doc, List<String> noValue) throws DocumentException {

        if (null != tableparams) {
            String oneRowTemplate = "";
            for (Map.Entry<String, String> entry : tableparams.entrySet()) {
                String paramKey = entry.getKey();
                Object value = JSON.parseObject(entry.getValue());
                //包含 table1
                if (template.contains(paramKey)) {
                    //name 需要加在模板的 表头下面的那个数据上(非表头) <tr name ="table1">
                    String test2 = "//tr[@class='" + paramKey + "']";
                    Node oldNode = doc.selectSingleNode(test2);
                    List<Node> elepar = oldNode.getParent().content();
                    oneRowTemplate = oldNode.asXML();
                    //正则匹配
                    Matcher m1 = Pattern.compile("(>.*?</td>+)").matcher(oneRowTemplate);
                    while (m1.find()) {
                        noValue.add(m1.group());
                    }

                    List<Map<String, Object>> rows = (List<Map<String, Object>>)value;
                    int beReplaceIndex = elepar.indexOf(oldNode);
                    int rowCount = rows.size();
                    for (int i = 0; i < rows.size(); i++) {
                        setElepar(oneRowTemplate, elepar, rows, beReplaceIndex, rowCount, i);
                    }
                }
                template = doc.asXML();
            }
        }
        return template;
    }

    private static void setElepar(String oneRowTemplate, List<Node> elepar, List<Map<String, Object>> rows, int beReplaceIndex, int rowCount, int i) throws DocumentException {
        StringBuilder oneRow = new StringBuilder();
        String temp = oneRowTemplate;
        for (String key : rows.get(i).keySet()) {
            //匹配String integer类型的
            if((!CommonUtils.isNullObject(rows.get(i).get(key)))&&((rows.get(i).get(key) instanceof String)||(rows.get(i).get(key) instanceof Integer))){
                    //需要精确匹配 所以加 ><
                    temp = temp.replace(">"+key+"<", ">"+ rows.get(i).get(key)+"<");
            }

        }
        oneRow.append(temp);
        Element newElement = DocumentHelper.parseText(oneRow.toString()).getRootElement();
        //
        if (i == 0) {
            //先节点list扩容 扩容(行数-1)个(因为第一个是替换)
            for (int j = 1; j < rowCount -1; j++) {
                elepar.add(newElement);
            }
            elepar.set(beReplaceIndex, newElement);
        } else {
            //有两行 则 要插入位置之后的元素后移
            for (int h = elepar.size() - 2; h > rowCount; h--) {
                elepar.set(h + 1, elepar.get(h));
            }
            //把第二行添加到 第一行位置之后
            elepar.set(beReplaceIndex + i, newElement);
        }
    }

    private static String handleHtmlDoctype(String template) {
        if(!template.contains(HTML_DOCTYPE)){
            template = HTML_BEGIN + template +HTML_END;
        }
        return template;
    }

    private static String repalceTitleParms(JSONObject params, JSONObject mailTitleBean, String mailTitle, String flagStartFortitle, String flagEndFortitle, List<String> titleparms) {
        if(null!= mailTitleBean){
            for (String par : titleparms){
                assert params != null;
                if(null!= mailTitleBean.getString(par)){
                    mailTitle = mailTitle.replace(flagStartFortitle +par+ flagEndFortitle, "null".equals(mailTitleBean.getString(par))?"-----": mailTitleBean.getString(par));
                }else{
                    throw new QdpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.EMAIL_TITLE_PARAMS_NO_MATCH, par), DefaultErrorMessage.EMAIL_TITLE_PARAMS_NO_MATCH.intValue());
                }

                }
        }
        return mailTitle;
    }

    private static String replaceNormalParams(JSONObject params, String template, String flagStart, String flagEnd, List<String> parms) {
        if(null!= params){
            for (String par : parms){
                if(null!= params.getString(par)){
                    template = template.replace(flagStart +par+ flagEnd, "null".equals(params.getString(par))?"-----": params.getString(par));
                }else{
                    throw new QdpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.EMAIL_TEMPLATE_PARAMS_NO_MATCH, par), DefaultErrorMessage.EMAIL_TEMPLATE_PARAMS_NO_MATCH.intValue());
                }

            }
        }
        return template;
    }

    private static List<String> getParams(String template, String flagStart,String flagEnd ){
        List<String> re = new ArrayList<>();
        int count = getCount(template,flagStart);
        for (int i = 0; i < count; i++) {
            String quStr=template.substring(template.indexOf(flagStart)+3,template.indexOf(flagEnd));  //progr a
            re.add(quStr);
            template=template.replaceFirst("\\#\\{\\{"+quStr+"\\}\\}","");
        }
        return re;
    }
    public static int getCount(String str, String key) {
        if (str == null || key == null || "".equals(str.trim()) || "".equals(key.trim())) {
            return 0;
        }
        int count = 0;
        int index = 0;
        while ((index = str.indexOf(key, index)) != -1) {
            index = index + key.length();
            count++;
        }
        return count;
    }

    public void sendkafka(EmailSendDTO emailSendDTO) {
        String emailSendDTOStr = JSON.toJSONString(emailSendDTO);
        if("string".equals(emailSendDTOStr)){
            emailSendDTOStr = "{\"bean\":\"{\\\"claimUrl\\\":\\\"https://otmp-ui-tst.t-sy-in.earth.xcloud.lenovo.com/home/taskApproval?type=claim\\\",\\\"incentiveId\\\":\\\"0200006666\\\",\\\"invoiceClaimCode\\\":\\\"2023_Q1_0200006666_31103\\\",\\\"invoiceClaimCodeUrl\\\":\\\"https://otmp-ui-tst.t-sy-in.earth.xcloud.lenovo.com/invoiceonlinemanagement/ClaimList\\\",\\\"invoiceNumber\\\":\\\"43\\\",\\\"params\\\":{},\\\"wfNextApprover\\\":\\\"buyang1\\\"}\",\"businessGroup\":\"ALL\",\"businessType\":\"Workflow\",\"createBy\":\"buyang1\",\"emailType\":\"workflow_approving\",\"geoCode\":\"ALL\",\"mailFrom\":\"SDMS@lenovo.com\",\"mailTitleBean\":\"{\\\"claimUrl\\\":\\\"https://otmp-ui-tst.t-sy-in.earth.xcloud.lenovo.com/home/taskApproval?type=claim\\\",\\\"incentiveId\\\":\\\"0200006666\\\",\\\"invoiceClaimCode\\\":\\\"2023_Q1_0200006666_31103\\\",\\\"invoiceClaimCodeUrl\\\":\\\"https://otmp-ui-tst.t-sy-in.earth.xcloud.lenovo.com/invoiceonlinemanagement/ClaimList\\\",\\\"invoiceNumber\\\":\\\"43\\\",\\\"params\\\":{},\\\"wfNextApprover\\\":\\\"buyang1\\\"}\",\"market\":\"ALL\",\"module\":\"Claim\",\"sendTo\":[\"wenjw1@lenovo.com\"],\"tableParamsForBean\":{\"statusNumber\":\"null\"},\"traceId\":\"aa295433-077f-4b47-9d29-1a0345a359c1\"}";
        }
        //kafkaSend.asynSend(sendEmailTopic, UUID.randomUUID().toString(), emailSendDTOStr, KafkaFactory.SEND_EMAIL_KAFKA_PRODUCER_FACTORY, new KafkaCallback(sendEmailTopic), false);
    }

    public int insertSendHistory(EmailSendHistoryEntity emailSendHistoryEntity) {
        return emailSendMapper.insertSendHistory(emailSendHistoryEntity);
    }

    public int updateSendHistory(EmailSendHistoryEntity emailSendEntity) {
        return emailSendMapper.updateSendHistory(emailSendEntity);
    }

    public int deleteBeforeSendHistory(EmailSendHistoryEntity emailSendEntity) {
        return emailSendMapper.deleteBeforeSendHistory(emailSendEntity);
    }

    public int deleteSendHistory(EmailSendHistoryEntity emailSendEntity) {
        return emailSendMapper.deleteSendHistory(emailSendEntity);
    }


    public PageInfo<EmailSendHistoryRequest> getEmailSendHistoryList(EmailSendHistoryRequest emailSendRequest)
    {
        List<EmailSendHistoryRequest> emailSendHistoryList = emailSendMapper.getEmailSendHistoryList(emailSendRequest);
        PageInfo<EmailSendHistoryRequest> resultPageInfo = new PageInfo<>(emailSendHistoryList);
        resultPageInfo.setTotal(resultPageInfo.getTotal());
        return resultPageInfo;
    }

    public List<EmailSendHistoryRequest> getSendHistoryById(String id)
    {
        return emailSendMapper.getSendHistoryById(id);
    }

    public List<EmailSendHistoryRequest> checkSendHistory(EmailSendDTO emailSendRequest)
    {
        return emailSendMapper.checkSendHistory(emailSendRequest);
    }

    public List<EmailSendHistoryRequest> getEmailSendHistoryListForCheck(EmailSendHistoryRequest requestEmailSendHistoryVO){
        return emailSendMapper.getEmailSendHistoryListForCheck(requestEmailSendHistoryVO);
    }
    public EmailDimension generateEmailDimension(Map<String,String> emailDimension){

        CommonUtils.isNull(emailDimension, "emailType is empty");
        CommonUtils.isNull(emailDimension.get(MODULE), "Module is empty");
        CommonUtils.isNull(emailDimension.get(BUSINESS_TYPE), "Business type is empty");
        CommonUtils.isNull(emailDimension.get(EMAIL_TYPE), "Email type is empty");
        CommonUtils.isNull(emailDimension.get(BUSINESS_GROUP), "Business group is empty");
        CommonUtils.isNull(emailDimension.get(GEO_CODE), "Geo is empty");

        return EmailDimension.builder()
                .emailType(emailDimension.get(EMAIL_TYPE))
                .module(emailDimension.get(MODULE))
                .businessGroup(emailDimension.get(BUSINESS_GROUP))
                .businessType(emailDimension.get(BUSINESS_TYPE))
                .geoCode(emailDimension.get(GEO_CODE))
                .active(true)
                .build();

    }
}
