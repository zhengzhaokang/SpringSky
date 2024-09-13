package com.wc.single.sky.disclosures.service;


import com.wc.single.sky.disclosures.dto.EmailReceipentDTO;

import java.util.List;

/**
 * Email ReceipentService接口
 * 
 * @author lovefamily
 * @date 2022-11-10
 */
public interface IEmailReceipentService
{
    /**
     * 查询Email Receipent
     * 
     * @param id Email Receipent主键
     * @return Email ReceipentDTO
     */
    public EmailReceipentDTO selectEmailReceipentById(Long id);

    /**
     * 查询Email Receipent列表
     *
     * @param emailReceipent Email Receipent
     * @return Email ReceipentDTO集合
     */
    public List<EmailReceipentDTO> selectEmailReceipentList(EmailReceipentDTO emailReceipent);

    /**
     * 新增Email Receipent
     *
     * @param emailReceipent Email Receipent
     * @param loginName
     * @return 结果
     */
    public int insertEmailReceipent(EmailReceipentDTO emailReceipent, String loginName);

    /**
     * 修改Email Receipent
     *
     * @param emailReceipent Email Receipent
     * @param loginName
     * @return 结果
     */
    public int updateEmailReceipent(EmailReceipentDTO emailReceipent, String loginName);

    /**
     * 批量删除Email Receipent
     * 
     * @param ids 需要删除的Email Receipent主键集合
     * @return 结果
     */
    public int deleteEmailReceipentByIds(Long[] ids);

    /**
     * 删除Email Receipent信息
     * 
     * @param id Email Receipent主键
     * @return 结果
     */
    public int deleteEmailReceipentById(Long id);
}
