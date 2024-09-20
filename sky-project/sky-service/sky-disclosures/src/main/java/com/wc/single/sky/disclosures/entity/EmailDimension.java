package com.wc.single.sky.disclosures.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
  * @Description 用于email_template表 联合主键
  * @author shirui3
  * @date 2022/11/9 11:36
  */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDimension {
    private Long id;
    private String emailType;
    private String market;
    private String businessGroup;
    private String geoCode;
    private String businessType;
    private String module;
    private Boolean active;


}
