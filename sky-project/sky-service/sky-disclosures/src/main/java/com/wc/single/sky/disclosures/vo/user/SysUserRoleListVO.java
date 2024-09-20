package com.wc.single.sky.disclosures.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SysUserRoleListVO {
    private String loginName;
    private String userName;
    private String status;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date createTime;

    private Long   roleId;
    private Long userId;

    private String businessGroup;
    private String geoCode;
}
