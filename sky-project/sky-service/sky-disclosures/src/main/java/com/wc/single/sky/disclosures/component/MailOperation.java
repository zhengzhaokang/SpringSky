package com.wc.single.sky.disclosures.component;

import com.alibaba.druid.util.StringUtils;
import com.wc.single.sky.disclosures.entity.EmailParamEntity;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.property.BodyType;
import microsoft.exchange.webservices.data.core.enumeration.property.Importance;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.search.LogicalOperator;
import microsoft.exchange.webservices.data.core.enumeration.service.ConflictResolutionMode;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.EmailMessageSchema;
import microsoft.exchange.webservices.data.core.service.schema.ItemSchema;
import microsoft.exchange.webservices.data.property.complex.Attachment;
import microsoft.exchange.webservices.data.property.complex.AttachmentCollection;
import microsoft.exchange.webservices.data.property.complex.FileAttachment;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;
import microsoft.exchange.webservices.data.search.filter.SearchFilter;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MailOperation {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
    private ExchangeService service;
    private int numMail = 1000;



    public MailOperation(){

    }

    public void init(ExchangeService service){
        if (service != null){
            this.service = service;
        }else{
            throw new IllegalArgumentException("service should no be null");
        }
    }




    public boolean sendMail(EmailParamEntity mail) throws Exception {
        EmailMessage msg = new EmailMessage(service);
        msg.setSubject(mail.getSubject());
        MessageBody body = MessageBody.getMessageBodyFromText(mail.getContent());
        body.setBodyType(BodyType.HTML);
        msg.setBody(body);
        if(StringUtils.equals(mail.getImportance(), "High"))
            msg.setImportance(Importance.High);
        for (String s : mail.getTo()) {
            msg.getToRecipients().add(s);
        }

        if (mail.getCc() != null && !mail.getCc().isEmpty()) {
            for (String s : mail.getCc()) {
                msg.getBccRecipients().add(s);
            }
        }
        if(mail.getAttachmentPath()!=null){
            for (String s : mail.getAttachmentPath()) {
                msg.getAttachments().addFileAttachment(s);
            }
        }
        msg.send();
        return true;
    }


    public int GetUnReadMailCountByUserMailAddress()
    {
        int unRead = 0;
        try
        {
            unRead = Folder.bind(service,WellKnownFolderName.Inbox).getUnreadCount();

        }
        catch (Exception ex)
        {
            return unRead;
        }
        return unRead;
    }


    public ArrayList<EmailParamEntity> receiveUnReadingMail(String receiveMailCategory, String archiveFolder) throws Exception {
        ArrayList<EmailParamEntity> eMails = new ArrayList<>();
        SearchFilter conditions = this.getSearchFilter(receiveMailCategory);
        FindItemsResults<Item> findResults = this.receiveMailWithCondition(conditions);

        for (Item item : findResults) {
            EmailMessage mailItems = EmailMessage.bind(this.service, item.getId());

            mailItems.load();
            eMails.add(this.buildMail(mailItems, receiveMailCategory, archiveFolder));

            this.afterReadingMail(mailItems);
        }

        return eMails;
    }

    private FindItemsResults receiveMailWithCondition(SearchFilter conditions) throws Exception {
        return this.service.findItems(WellKnownFolderName.Inbox, conditions, new ItemView(this.numMail));
    }


    private SearchFilter getSearchFilter(String receiveMailCategory) {
        return new SearchFilter.SearchFilterCollection(LogicalOperator.And,
                new SearchFilter.IsEqualTo(EmailMessageSchema.IsRead, false),
                new SearchFilter.ContainsSubstring(ItemSchema.Subject, receiveMailCategory));
    }


    private EmailParamEntity buildMail(EmailMessage emailMessage, String receiveMailCategory, String archiveFolderPath) throws Exception {
        EmailParamEntity mail = new EmailParamEntity();
        List<String> address = new ArrayList<>();
        emailMessage.getToRecipients().forEach(recipient ->{
            address.add(recipient.getAddress());
        });

        ArrayList<String> ccList = new ArrayList<>();
        emailMessage.getCcRecipients().forEach(cc ->{
            ccList.add(cc.getAddress());
        });

        mail.setCategory(receiveMailCategory);
        mail.setTo(address);
        mail.setCc(ccList);
        mail.setFrom(emailMessage.getFrom().getAddress());
        mail.setSubject(emailMessage.getSubject());
        mail.setHasAttachment(emailMessage.getHasAttachments());

        if (emailMessage.getHasAttachments()){
            mail.setAttachmentCount(emailMessage.getAttachments().getCount());
            mail.setAttachmentNames(this.saveMailAttachment(emailMessage.getAttachments(), archiveFolderPath));
            mail.setArchiveFolderPath(archiveFolderPath);
        }

        return mail;
    }


    private final static String attPrefix = "NEW";
    private ArrayList<String> saveMailAttachment(AttachmentCollection attachments, String archiveFolderPath) throws Exception {
        ArrayList<String> attachmentNames= new ArrayList<>();

        this.checkDirectory(archiveFolderPath);

        for (Attachment attachment: attachments) {
            FileAttachment iAttachment = (FileAttachment) attachment;
            String attName= iAttachment.getName();
            iAttachment.load(archiveFolderPath + File.separator + attachment.getName());
            /* add by sunhh  20180904 处理csv \"特殊字符  start -----*/
            if( attachment.getName().endsWith(".csv")|| attachment.getName().endsWith(".CSV")) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(archiveFolderPath + File.separator + attachment.getName()));
                    BufferedWriter bw = new BufferedWriter(new FileWriter(archiveFolderPath + File.separator + attPrefix + attachment.getName()));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        line = line.replaceAll("\\\\\\\"", "\"\"");
                        bw.write(line);
                        bw.newLine();
                    }
                    bw.close();
                    br.close();
                    attName = attPrefix+iAttachment.getName() ;
                    new File(archiveFolderPath + File.separator + attachment.getName()).delete();
                } catch (IOException e) {
                    throw e;
                }
            }
            attachmentNames.add(attName);

        }

        return attachmentNames;
    }

    private void checkDirectory(String directoryPath) throws IOException {
        File dir = new File(directoryPath);

        if (!dir.exists()){
            FileUtils.forceMkdir(dir);
        }
    }

    private void afterReadingMail(EmailMessage emailMessage) throws Exception {
        emailMessage.setIsRead(true);
        emailMessage.update(ConflictResolutionMode.NeverOverwrite);
    }



}
