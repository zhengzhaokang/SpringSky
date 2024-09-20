package com.wc.single.sky.disclosures.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "disclosure_log")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisclosureItemLogDto {

    @Id
    private Long id;

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
