package com.wc.single.sky.disclosures.manager.impl;

import com.wc.single.sky.common.exception.QdpException;
import com.wc.single.sky.common.exception.message.DefaultErrorMessage;
import com.wc.single.sky.common.utils.CommonUtils;
import com.wc.single.sky.common.utils.DateUtils;
import com.wc.single.sky.disclosures.dto.EmailReceipentDTO;
import com.wc.single.sky.disclosures.entity.EmailReceipentDO;
import com.wc.single.sky.disclosures.manager.IEmailReceipentManager;
import com.wc.single.sky.disclosures.mapper.EmailReceipentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Email ReceipentManager业务层处理
 * 
 * @author lovefamily
 * @date 2022-11-10
 */
@Service
public class EmailReceipentManagerImpl implements IEmailReceipentManager
{
    @Autowired
    private EmailReceipentMapper emailReceipentMapper;

    /**
     * 查询Email Receipent
     * 
     * @param id Email Receipent主键
     * @return Email ReceipentDO
     */
    @Override
    public EmailReceipentDO selectEmailReceipentById(Long id)
    {
        return emailReceipentMapper.selectEmailReceipentById(id);
    }

    /**
     * 查询Email Receipent列表
     *
     * @param emailReceipent Email Receipent
     * @return Email ReceipentDO
     */
    @Override
    public List<EmailReceipentDO> selectEmailReceipentList(EmailReceipentDTO emailReceipent)
    {
        return emailReceipentMapper.selectEmailReceipentList(emailReceipent);
    }

    /**
     * 新增Email Receipent
     *
     * @param emailReceipent Email Receipent
     * @return 结果
     */
    @Override
    public int insertEmailReceipent(EmailReceipentDO emailReceipent)
    {
        EmailReceipentDTO emailReceipentDb = new EmailReceipentDTO();
        emailReceipentDb.setJobType(emailReceipent.getJobType());
        emailReceipentDb.setBusinessGroup(emailReceipent.getBusinessGroup());
        emailReceipentDb.setGeoCode(emailReceipent.getGeoCode());
        emailReceipentDb.setDeleteFlag(false);
        List<EmailReceipentDO> emailReceipentsDb = emailReceipentMapper.selectEmailReceipentList(emailReceipentDb);
        if(!CommonUtils.isNullList(emailReceipentsDb)){
            throw new QdpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.EMAIL_RECIPIENT_CONFIG_EXSIT), DefaultErrorMessage.EMAIL_RECIPIENT_CONFIG_EXSIT.intValue());
        }
        emailReceipent.setCreateTime(DateUtils.getNowDate());
        return emailReceipentMapper.insertEmailReceipent (emailReceipent);
    }

    /**
     * 修改Email Receipent
     *
     * @param emailReceipent  Email Receipent
     * @return 结果
     */
    @Override
    public int updateEmailReceipent(EmailReceipentDO emailReceipent)
    {
        emailReceipent.setUpdateTime(DateUtils.getNowDate());
        return emailReceipentMapper.updateEmailReceipent (emailReceipent);
    }

    /**
     * 批量删除Email Receipent
     * 
     * @param ids 需要删除的Email Receipent主键
     * @return 结果
     */
    @Override
    public int deleteEmailReceipentByIds(Long[] ids)
    {
        return emailReceipentMapper.deleteEmailReceipentByIds(ids);
    }

    /**
     * 删除Email Receipent信息
     * 
     * @param id Email Receipent主键
     * @return 结果
     */
    @Override
    public int deleteEmailReceipentById(Long id)
    {
        return emailReceipentMapper.deleteEmailReceipentById(id);
    }
}
