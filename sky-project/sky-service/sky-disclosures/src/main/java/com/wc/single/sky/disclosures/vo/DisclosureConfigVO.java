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
public class DisclosureConfigVO {

    private Long id;

    private String configKey;

    private String configName;

    private String configType;

    private Long parentId;

    private String delFlag;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String configId;

    private String configParentId;
}
