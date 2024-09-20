package com.wc.single.sky.disclosures.mapper;

import com.wc.single.sky.common.core.dao.BaseMapper;
import com.wc.single.sky.disclosures.dto.DisclosureNumberDto;

public interface DisclosureNumberMapper extends BaseMapper<DisclosureNumberDto> {
    int updateDisclosureNumber(DisclosureNumberDto disclosureNumberDto);

}
