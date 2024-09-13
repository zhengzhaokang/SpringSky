package com.wc.single.sky.disclosures.component;

import com.github.pagehelper.PageInfo;
import com.wc.single.sky.common.exception.QdpException;
import com.wc.single.sky.common.exception.message.DefaultErrorMessage;
import com.wc.single.sky.common.utils.CommonUtils;
import com.wc.single.sky.disclosures.entity.EmailCondition;
import com.wc.single.sky.disclosures.entity.EmailDimension;
import com.wc.single.sky.disclosures.entity.EmailTemplateEntity;
import com.wc.single.sky.disclosures.mapper.EmailTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 * @Author: shirui3
 * @Date: 2021/12/31
 */
@Component
public class EmailTemplateService {

    @Autowired
    public EmailTemplateMapper emailTemplateMapper;


    public PageInfo<EmailTemplateEntity> getEmailTemplateByPage(EmailTemplateEntity emailTemplateEntity){
        emailTemplateEntity.setDeleteFlag(false);
        List<EmailTemplateEntity> emailTemplateByPage = emailTemplateMapper.getEmailTemplateByPage(emailTemplateEntity);
        PageInfo<EmailTemplateEntity> resultPageInfo = new PageInfo<>(emailTemplateByPage);
        resultPageInfo.setTotal(resultPageInfo.getTotal());
        return resultPageInfo;
    }

    public List<EmailTemplateEntity> getConditionOfEmail(EmailCondition emailCondition){
        return emailTemplateMapper.getConditionOfEmail(emailCondition);
    }


    public List<EmailTemplateEntity> getOneEmailTemplate(EmailDimension emailCondition) {
        return emailTemplateMapper.getOneEmailTemplate(emailCondition);
    }


    public int updateEmailTemplate(EmailTemplateEntity emailTemplateEntity,String loginName) {
        emailTemplateEntity.setUpdateBy(loginName);
        return emailTemplateMapper.updateEmailTemplate(emailTemplateEntity);
    }

    public int updateEmailTemplateEnable(EmailTemplateEntity emailTemplateEntity,String loginName) {
        CommonUtils.isNull(emailTemplateEntity.getId(),"Id is empty!");
        CommonUtils.isNull(emailTemplateEntity.getActive(),"Active Status is empty!");

        emailTemplateEntity.setUpdateBy(loginName);

        //如果 size= 1 新的为启用 则比较 id  同则成功 不同则更新新旧, 新的为不起用 则比较id 同则 不允许更改, 不同则 直接改
        //修改启用不启用 去检验 是否是唯一的
        EmailDimension emailCondition = new EmailDimension();
        emailCondition.setId(emailTemplateEntity.getId());
        List<EmailTemplateEntity> nowTemplate = emailTemplateMapper.getOneEmailTemplate(emailCondition);

        EmailDimension oldOneDimension = new EmailDimension();
        oldOneDimension.setEmailType(nowTemplate.get(0).getEmailType());
        oldOneDimension.setModule(nowTemplate.get(0).getModule());
        oldOneDimension.setBusinessGroup(nowTemplate.get(0).getBusinessGroup());
        oldOneDimension.setBusinessType(nowTemplate.get(0).getBusinessType());
        oldOneDimension.setGeoCode(nowTemplate.get(0).getGeoCode());
        oldOneDimension.setActive(true);
        List<EmailTemplateEntity>  oldOne = emailTemplateMapper.getOneEmailTemplate(oldOneDimension);
        if(oldOne.size() == 1){
            //新的改为启用 active = true
            if(Boolean.TRUE.equals(emailTemplateEntity.getActive())){
                if(emailTemplateEntity.getId().equals(oldOne.get(0).getId())){
                    //成功
                    return 1;
                }else{
                    //老的更新为不启用
                    EmailTemplateEntity oldTemplate = EmailTemplateEntity.builder()
                            .active(false)
                            .updateBy("temp")
                            .id(oldOne.get(0).getId())
                            .build();
                    emailTemplateMapper.updateEmailTemplateEnable(oldTemplate);
                    //新的更新为启用
                    return emailTemplateMapper.updateEmailTemplateEnable(emailTemplateEntity);
                }
            }else{
                //新的改为不起用
                if(emailTemplateEntity.getId().equals(oldOne.get(0).getId())){
                    throw new QdpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.EMAIL_TEMPLATE_CHANGE_INACTIVE_ERROR), DefaultErrorMessage.EMAIL_TEMPLATE_CHANGE_INACTIVE_ERROR.intValue());
                }else{
                    //新的更新为不启用
                    return emailTemplateMapper.updateEmailTemplateEnable(emailTemplateEntity);
                }
            }
        }else if(oldOne.isEmpty()){
            //把当前的 更新为启用
            emailTemplateEntity.setActive(true);
            return emailTemplateMapper.updateEmailTemplateEnable(emailTemplateEntity);
        }else{  //大于1 异常
            throw new QdpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.EMAIL_TEMPLATE_STATUS_ERROR), DefaultErrorMessage.EMAIL_TEMPLATE_STATUS_ERROR.intValue());
        }
    }

    public int insertEmailTemplate(EmailTemplateEntity emailTemplateEntity,String loginName) {
        CommonUtils.isNull(emailTemplateEntity.getModule(),"Module is empty!");
        CommonUtils.isNull(emailTemplateEntity.getBusinessType(),"Business type is empty!");
        CommonUtils.isNull(emailTemplateEntity.getEmailType(),"Email type is empty!");
        CommonUtils.isNull(emailTemplateEntity.getBusinessGroup(),"Business group is empty!");
        CommonUtils.isNull(emailTemplateEntity.getGeoCode(),"Geo is empty!");
        CommonUtils.isNull(emailTemplateEntity.getMailTitle(),"Mail title is empty!");
        CommonUtils.isNull(emailTemplateEntity.getMailBody(),"Mail content is empty!");
        emailTemplateEntity.setDeleteFlag(false);
        emailTemplateEntity.setCreateBy(loginName);
        emailTemplateEntity.setUpdateBy(loginName);
        return emailTemplateMapper.insertEmailTemplate(emailTemplateEntity);
    }

    public int copyEmailTemplate(Long id,String loginName) {
        EmailDimension emailCondition = new EmailDimension();
        emailCondition.setId(id);
        List<EmailTemplateEntity> templateToBeCoyp = emailTemplateMapper.getOneEmailTemplate(emailCondition);
        if(templateToBeCoyp.size()!=1){
            throw new QdpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.EMAIL_TEMPLATE_DATA_ERROR), DefaultErrorMessage.EMAIL_TEMPLATE_DATA_ERROR.intValue());
        }
        EmailTemplateEntity emailTemplateEntity = templateToBeCoyp.get(0);
        emailTemplateEntity.setActive(false);
        emailTemplateEntity.setCreateBy(loginName);
        emailTemplateEntity.setUpdateBy(loginName);
        return emailTemplateMapper.insertEmailTemplate(emailTemplateEntity);
    }
}
