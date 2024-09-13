package com.wc.single.sky.system.domain;

import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;

/**
 * 消息通知记录对象 msg_remind
 * 
 * @author lovefamily
 * @date 2022-10-26
 */
@Data
public class MsgRemindDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    private String msgUuid;

    /** IT code */
    @Excel(name = "IT code")
    private String itCodes;

    @Excel(name = "Module")
    private String module;

    /** Msg Title */
    @Excel(name = "Msg Title")
    private String msgTitle;

    /** Msg Info */
    @Excel(name = "Msg Info")
    private String msgInfo;

    private String msgTopic;

    private String msgType;

    private String sysCatalog;

    /** Has Read 0未读 1 已读*/
    @Excel(name = "Has Read")
    private Integer hasRead;

    private Integer isUpdate;

    private Integer notReadCount;

}
