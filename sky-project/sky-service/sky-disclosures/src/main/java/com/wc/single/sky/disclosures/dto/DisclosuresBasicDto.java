package com.wc.single.sky.disclosures.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Table(name = "disclosures_basic")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisclosuresBasicDto {

    @Id
    private Long id;

    private String basicNumber;

    private String businessGroup;

    private String geoCode;

    private String fiscalYear;

    private String quarter;

    private String qdpFocal;

    private Date dueDate;

    private String status;

    private String delFlag;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    @Transient
    private List<String> businessGroupList;

    @Transient
    private List<String> geoCodeList;
}
