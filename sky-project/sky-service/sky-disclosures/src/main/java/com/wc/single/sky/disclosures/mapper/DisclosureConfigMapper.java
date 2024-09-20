package com.wc.single.sky.disclosures.mapper;

import com.wc.single.sky.common.core.dao.BaseMapper;
import com.wc.single.sky.disclosures.dto.DisclosureConfigDto;

import java.util.List;

public interface DisclosureConfigMapper extends BaseMapper<DisclosureConfigDto> {

    List<DisclosureConfigDto> listDisclosureConfig(DisclosureConfigDto disclosureConfigDto);
}
