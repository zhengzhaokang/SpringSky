package com.wc.single.sky.disclosures.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisclosureItemLogVO {

    private Long id;

    private String logId;

    private String module;

    private String operator;

    private String business;

    private String description;

    private String delFlag;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;
}
