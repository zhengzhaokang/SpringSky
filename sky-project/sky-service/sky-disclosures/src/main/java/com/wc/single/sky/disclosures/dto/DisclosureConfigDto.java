package com.wc.single.sky.disclosures.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "disclosure_config")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisclosureConfigDto {

    @Id
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
}
