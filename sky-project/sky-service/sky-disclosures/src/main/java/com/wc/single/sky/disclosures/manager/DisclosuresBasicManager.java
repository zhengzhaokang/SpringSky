package com.wc.single.sky.disclosures.manager;

import com.wc.single.sky.disclosures.dto.DisclosuresBasicDto;
import com.wc.single.sky.disclosures.entity.DisclosuresBasicCount;

import java.util.List;

public interface DisclosuresBasicManager {

    int insertDisclosuresBasic(DisclosuresBasicDto disclosuresBasicDto);

    int updateDisclosuresBasic(DisclosuresBasicDto disclosuresBasicDto);

    int updateDisclosuresBasicByExample(DisclosuresBasicDto disclosuresBasicDto);

    int deleteDisclosuresBasicByIds(List<String> ids, String updateBy);

    DisclosuresBasicDto selectDisclosuresBasicById(Long id);

    List<DisclosuresBasicDto> selectDisclosuresBasicList(DisclosuresBasicDto disclosuresBasicDto);

    int selectDisclosuresBasicCountByStatus(String status);

    List<DisclosuresBasicCount> selectDisclosuresBasicCount();
}
