package com.wc.single.sky.system.domain;

import cn.hutool.core.util.StrUtil;
import com.wc.single.sky.common.annotation.Excel;
import com.wc.single.sky.common.core.domain.BaseDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * SysUserDataScope对象 sys_user_data_scope
 * 
 * @author lovefamily
 * @date 2022-04-24
 */
public class SysUserDataScope extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** User Data Scope ID */
    @Excel(name = "User Data Scope ID")
    private Long userDataScopeId;

    /** User ID */
    @Excel(name = "User ID")
    private Long userId;

    /** User Itcode */
    @Excel(name = "User Itcode")
    private String userItcode;

    /** User Data Scope Field */
    @Excel(name = "User Data Scope Field")
    private String dataScopeKey;

    /** User Data Scope Value */
    @Excel(name = "User Data Scope Value")
    private String dataScopeValue;

    /** Status（0 Y 1 N） */
    @Excel(name = "Status", readConverterExp = "0=,Y=,1=,N=")
    private String status;

    private String  selectAll;

    private String  notIn;

    public String getSelectAll() {
        if (StrUtil.isNotBlank(this.selectAll)) {
            this.selectAll = this.selectAll.trim();
        }
        return this.selectAll;
    }

    private Boolean canBeNull;

    public boolean getCanBeNull() {
        if (null == canBeNull) {
            canBeNull = Boolean.FALSE;
        }
        return canBeNull;
    }

    public void setCanBeNull(boolean canBeNull) {
        this.canBeNull = canBeNull;
    }

    public void setSelectAll(String selectAll) {
        this.selectAll = selectAll;
    }

    public String getNotIn() {
        return notIn;
    }

    public void setNotIn(String notIn) {
        this.notIn = notIn;
    }

    public void setUserDataScopeId(Long userDataScopeId)
    {
        this.userDataScopeId = userDataScopeId;
    }

    public Long getUserDataScopeId() 
    {
        return userDataScopeId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setUserItcode(String userItcode) 
    {
        this.userItcode = userItcode;
    }

    public String getUserItcode() 
    {
        return userItcode;
    }
    public void setDataScopeKey(String dataScopeKey) 
    {
        this.dataScopeKey = dataScopeKey;
    }

    public String getDataScopeKey() 
    {
        return dataScopeKey;
    }
    public void setDataScopeValue(String dataScopeValue) 
    {
        this.dataScopeValue = dataScopeValue;
    }

    public String getDataScopeValue() 
    {
        return dataScopeValue;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userDataScopeId", getUserDataScopeId())
            .append("userId", getUserId())
            .append("userItcode", getUserItcode())
            .append("dataScopeKey", getDataScopeKey())
            .append("dataScopeValue", getDataScopeValue())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
