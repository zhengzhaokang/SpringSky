package com.wc.single.sky.disclosures.mapper;

import com.wc.single.sky.disclosures.dto.DisclosuresBasicDto;
import com.wc.single.sky.disclosures.entity.DisclosuresBasicCount;
import com.wc.single.sky.common.core.dao.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DisclosuresBasicMapper extends BaseMapper<DisclosuresBasicDto> {

    Integer deleteDisclosuresBasicByIds(@Param("updateBy") String updateBy, @Param("ids") List<String> ids);

    List<DisclosuresBasicDto> selectDisclosuresBasicList(DisclosuresBasicDto disclosuresBasicDto);

    Integer selectDisclosuresBasicCountByStatus(@Param("status") String status);

    List<DisclosuresBasicCount> selectDisclosuresBasicCount();
}
