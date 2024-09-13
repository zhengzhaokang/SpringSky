package com.wc.single.sky.disclosures.component;

import com.wc.single.sky.disclosures.entity.EmailParamEntity;
import lombok.Data;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Data
public class EmailService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${email.host}")
    private String emailUrl;
    @Value("${email.username}")
    private String username;
    @Value("${email.password}")
    private String password;

    protected ExchangeService service;
    private MailOperation mailOperation;

    private Lock lock = new ReentrantLock();

    public EmailService() {
        this.service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        this.mailOperation = new MailOperation();
    }

    public boolean delivery(EmailParamEntity mail) {
        try {
            initService();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            logger.error("邮件初始化失败:{}", e.getMessage());
        }
        try {
            lock.lock();
            return mailOperation.sendMail(mail);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("发送失败:{}", e.getMessage());
        } finally {
            lock.unlock();
        }
        return false;
    }

    public ArrayList<EmailParamEntity> receiveUnReadingMail(String receiveMailCategory, String archiveFolder) throws Exception {
        initService();

        return mailOperation.receiveUnReadingMail(receiveMailCategory, archiveFolder);
    }


    void initService() throws URISyntaxException {
        logger.info("email username:{}", username);
        logger.info("email password:{}", password);
        logger.info("email emailUrl:{}", emailUrl);
        ExchangeCredentials credentials = new WebCredentials(username, password);
        service.setCredentials(credentials);

        // 设置超时时间（毫秒）
//        int timeout = 60000; // 60,000 毫秒（或60秒）
//        service.setTimeout(timeout);

        service.setUrl(new URI(emailUrl));
        this.mailOperation.init(service);
    }


}

