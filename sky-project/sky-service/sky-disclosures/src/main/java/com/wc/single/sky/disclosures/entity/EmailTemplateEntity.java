package com.wc.single.sky.disclosures.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
  * @Description
  * @author shirui3
  * @date 2022/11/9 11:36
  */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplateEntity {
    private Long id;
    private String emailType;
    private String businessGroup;
    private String mailTitle;
    private String mailBody;
    private String paramsList;
    private String paramsTableList;
    private String attachment;
    private String market;
    private String createBy;
    private Date createTime;
    private String updateBy;
    private String geoCode;
    private String remark;
    private Date updateTime;
    private Boolean active;
    private Boolean deleteFlag;
    private Integer needParams;

    private String businessType;
    private String module;

    private String emailDimension;   // module#business_type#email_type#geo#business_group


}
