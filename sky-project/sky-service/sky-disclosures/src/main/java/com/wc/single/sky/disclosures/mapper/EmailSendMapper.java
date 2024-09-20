package com.wc.single.sky.disclosures.mapper;

import com.wc.single.sky.disclosures.dto.EmailSendDTO;
import com.wc.single.sky.disclosures.entity.EmailSendHistoryEntity;
import com.wc.single.sky.disclosures.entity.EmailSendHistoryRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Describe: 邮件发送模板
 * @Author shirui
 * @Date 2021-12-31 15:02
 */
public interface EmailSendMapper {

    /**
     * @Description 逻辑删除邮件发送历史记录
     * @author shirui3
     * @param emailSendHistoryEntity
     */
    Integer deleteBeforeSendHistory(@Param("emailSendHistoryEntity") EmailSendHistoryEntity emailSendHistoryEntity);
    /**
     * @Description 更新邮件发送历史状态
     * @author shirui3
     * @param emailSendHistoryEntity
     */
    Integer updateSendHistory(@Param("emailSendHistoryEntity") EmailSendHistoryEntity emailSendHistoryEntity);
    /**
     * @Description 逻辑删除邮件发送历史
     * @author shirui3
     * @param emailSendHistoryEntity
     */
    Integer deleteSendHistory(@Param("emailSendHistoryEntity") EmailSendHistoryEntity emailSendHistoryEntity);
    /**
     * @Description 新增邮件发送历史
     * @author shirui3
     * @param emailSendHistoryEntity
     */
    Integer insertSendHistory(@Param("emailSendHistoryEntity") EmailSendHistoryEntity emailSendHistoryEntity);

    List<EmailSendHistoryRequest> getEmailSendHistoryListForCheck(EmailSendHistoryRequest emailSendHistoryRequest);


    List<EmailSendHistoryRequest> getEmailSendHistoryList(EmailSendHistoryRequest emailSendHistoryRequest);
    List<EmailSendHistoryRequest> getSendHistoryById(@Param("id") String id);
    /**
     * @Description 邮件成功发送历史
     * @author shirui3
     * @param emailSendHistoryRequest
     */
    List<EmailSendHistoryRequest> checkSendHistory(EmailSendDTO emailSendHistoryRequest);
}
