package com.wc.single.sky.system.domain.dto;

import com.wc.single.sky.common.core.domain.BaseDTO;
import lombok.Data;

@Data
public class DimensionDTO extends BaseDTO {
    private String regionMarketCode;
    private String geoCode;
    private String businessGroup;
    private String gtnTypeCode;
    private String salesOrgCode;
    private String salesOfficeCode;
    private String segmentCode;
    private String  accrualVersion;
}
