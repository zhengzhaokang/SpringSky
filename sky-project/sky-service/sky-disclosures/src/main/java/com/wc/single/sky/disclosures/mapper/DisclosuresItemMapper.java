package com.wc.single.sky.disclosures.mapper;

import com.wc.single.sky.disclosures.dto.DisclosuresItemDto;
import com.wc.single.sky.common.core.dao.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DisclosuresItemMapper extends BaseMapper<DisclosuresItemDto>  {

    Integer deleteDisclosuresItemByIds(@Param("updateBy") String updateBy, @Param("ids") List<String> ids);

    List<DisclosuresItemDto> selectDisclosuresItemList(DisclosuresItemDto disclosuresItemDto);

    Integer updateDisclosuresItemById(DisclosuresItemDto disclosuresItemDto);
}

