package com.wc.single.sky.disclosures.manager.impl;

import com.wc.single.sky.disclosures.common.DisclosureConstant;
import com.wc.single.sky.disclosures.manager.DisclosuresBasicManager;
import com.wc.single.sky.disclosures.mapper.DisclosuresBasicMapper;
import com.wc.single.sky.disclosures.dto.DisclosuresBasicDto;
import com.wc.single.sky.disclosures.entity.DisclosuresBasicCount;
import com.wc.single.sky.common.utils.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Component
public class DisclosuresBasicManagerImpl implements DisclosuresBasicManager {

    @Autowired
    private DisclosuresBasicMapper disclosuresBasicMapper;

    @Override
    public int insertDisclosuresBasic(DisclosuresBasicDto disclosuresBasicDto) {
        return disclosuresBasicMapper.insertSelective(disclosuresBasicDto);
    }

    @Override
    public int updateDisclosuresBasic(DisclosuresBasicDto disclosuresBasicDto) {
        if (disclosuresBasicDto == null || disclosuresBasicDto.getId() == null) {
            return 0;
        }
        return disclosuresBasicMapper.updateByPrimaryKeySelective(disclosuresBasicDto);
    }

    @Override
    public int updateDisclosuresBasicByExample(DisclosuresBasicDto disclosuresBasicDto) {
        if (disclosuresBasicDto == null || disclosuresBasicDto.getId() == null) {
            return 0;
        }
        Example example = new Example(DisclosuresBasicDto.class);
        example.createCriteria().andEqualTo(DisclosureConstant.ID, disclosuresBasicDto.getId());
        return disclosuresBasicMapper.updateByExample(disclosuresBasicDto,example);
    }

    @Override
    public int deleteDisclosuresBasicByIds(List<String> ids, String updateBy) {
        if (CollectionUtils.isEmpty(ids)) {
            return 0;
        }
        return disclosuresBasicMapper.deleteDisclosuresBasicByIds(updateBy, ids);
    }

    @Override
    public DisclosuresBasicDto selectDisclosuresBasicById(Long id) {
        if (id== null) {
            return null;
        }
        return disclosuresBasicMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DisclosuresBasicDto> selectDisclosuresBasicList(DisclosuresBasicDto disclosuresBasicDto) {
        if (disclosuresBasicDto == null) {
            return null;
        }
        return disclosuresBasicMapper.selectDisclosuresBasicList(disclosuresBasicDto);
    }

    @Override
    public int selectDisclosuresBasicCountByStatus(String status) {
        if (StringUtils.isBlank(status)) {
            return 0;
        }
        return disclosuresBasicMapper.selectDisclosuresBasicCountByStatus(status);
    }

    @Override
    public List<DisclosuresBasicCount> selectDisclosuresBasicCount() {
        return disclosuresBasicMapper.selectDisclosuresBasicCount();
    }
}
