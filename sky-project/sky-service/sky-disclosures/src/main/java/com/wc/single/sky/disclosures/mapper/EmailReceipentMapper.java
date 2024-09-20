package com.wc.single.sky.disclosures.mapper;


import com.wc.single.sky.disclosures.dto.EmailReceipentDTO;
import com.wc.single.sky.disclosures.entity.EmailReceipentDO;

import java.util.List;


/**
 * Email ReceipentMapper接口
 * 
 * @author lovefamily
 * @date 2022-11-10
 */
public interface EmailReceipentMapper
{
    /**
     * 查询Email Receipent
     * 
     * @param id Email Receipent主键
     * @return Email Receipent
     */
    public EmailReceipentDO selectEmailReceipentById(Long id);

    /**
     * 查询Email Receipent列表
     * 
     * @param emailReceipent Email Receipent
     * @return Email Receipent集合
     */
    public List<EmailReceipentDO> selectEmailReceipentList(EmailReceipentDTO emailReceipent);

    /**
     * 新增Email Receipent
     * 
     * @param emailReceipent Email Receipent
     * @return 结果
     */
    public int insertEmailReceipent(EmailReceipentDO emailReceipent);

    /**
     * 修改Email Receipent
     * 
     * @param emailReceipent Email Receipent
     * @return 结果
     */
    public int updateEmailReceipent (EmailReceipentDO emailReceipent);

    /**
     * 删除Email Receipent
     * 
     * @param id Email Receipent主键
     * @return 结果
     */
    public int deleteEmailReceipentById(Long id);

    /**
     * 批量删除Email Receipent
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEmailReceipentByIds(Long[] ids);
}
