package com.wc.single.sky.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 用户和角色关联 sys_user_role
 * 
 * @author lovefamily
 */
@Data
public class SysUserRole
{
    private List<SysUser> sysUsers;
    /** 用户ID */
    private Long userId;
    
    /** 角色ID */
    private Long roleId;

    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String roleName;

    private String roleKey;

}
