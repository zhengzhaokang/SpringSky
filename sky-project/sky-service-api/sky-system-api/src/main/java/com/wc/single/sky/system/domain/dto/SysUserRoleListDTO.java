package com.wc.single.sky.system.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class SysUserRoleListDTO {
    private Long roleId;
    private String roleKey;
    private String loginName;
    private String userName;
    private List<Long>userIds;
}
