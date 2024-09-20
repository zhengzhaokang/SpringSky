package com.wc.single.sky.disclosures.entity;

import lombok.Data;

/**
 * @Description 邮件参数,用于EmailUtils
 * @Author shirui
 * @Date 2022-11-09 10:54:02
 */
@Data
public class EmailEntity {
    // 文本内容发送地址
    private String textSendURL;
    // 附件内容发送地址
    private String attachmentSendURL;

    private EmailParamEntity emailParamEntity;

}
