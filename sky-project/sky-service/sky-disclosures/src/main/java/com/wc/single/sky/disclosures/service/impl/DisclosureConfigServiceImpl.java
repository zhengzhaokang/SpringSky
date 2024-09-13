package com.wc.single.sky.disclosures.service.impl;

import com.github.pagehelper.PageInfo;
import com.wc.single.sky.common.utils.SnowFlakeUtil;
import com.wc.single.sky.common.utils.StringUtils;
import com.wc.single.sky.common.utils.bean.BeanUtils;
import com.wc.single.sky.disclosures.common.DisclosureConstant;
import com.wc.single.sky.disclosures.dto.DisclosureConfigDto;
import com.wc.single.sky.disclosures.mapper.DisclosureConfigMapper;
import com.wc.single.sky.disclosures.service.DisclosureConfigService;
import com.wc.single.sky.disclosures.vo.DisclosureConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DisclosureConfigServiceImpl implements DisclosureConfigService {

    @Autowired
    private DisclosureConfigMapper disclosureConfigMapper;

    @Autowired
    private SnowFlakeUtil snowFlakeUtil;

    @Override
    public int saveDisclosureConfig(DisclosureConfigVO disclosureConfigVO) {
        if (disclosureConfigVO == null || StringUtils.isBlank(disclosureConfigVO.getConfigParentId())) {
            log.info("### DisclosureConfigServiceImpl saveDisclosureConfig disclosureConfigVO is null");
            return 0;
        }
        DisclosureConfigDto disclosureConfigDto = new DisclosureConfigDto();
        BeanUtils.copyProperties(disclosureConfigVO, disclosureConfigDto);
        disclosureConfigDto.setId(SnowFlakeUtil.nextId());
        disclosureConfigDto.setParentId(Long.parseLong(disclosureConfigVO.getConfigParentId()));
        disclosureConfigDto.setDelFlag(DisclosureConstant.ENABLE);
        disclosureConfigDto.setCreateTime(new Date());
        disclosureConfigDto.setUpdateTime(new Date());
        return disclosureConfigMapper.insertSelective(disclosureConfigDto);
    }

    @Override
    public DisclosureConfigVO getDisclosureConfig(String id) {
        if (StringUtils.isBlank(id)) {
            log.info("### DisclosureConfigServiceImpl getDisclosureConfig id is null");
            return null;
        }
        DisclosureConfigDto disclosureConfigDto = disclosureConfigMapper.selectByPrimaryKey(Long.parseLong(id));
        if (disclosureConfigDto != null) {
            DisclosureConfigVO disclosureConfigVO = new DisclosureConfigVO();
            BeanUtils.copyProperties(disclosureConfigDto, disclosureConfigVO);
            disclosureConfigVO.setConfigId(String.valueOf(disclosureConfigDto.getId()));
            disclosureConfigVO.setConfigParentId(String.valueOf(disclosureConfigDto.getParentId()));
            disclosureConfigVO.setId(null);
            disclosureConfigVO.setParentId(null);
            return disclosureConfigVO;
        }
        log.info("### DisclosureConfigServiceImpl getDisclosureConfig disclosureConfigDto is null");
        return null;
    }

    @Override
    public int updateDisclosureConfig(DisclosureConfigVO disclosureConfigVO) {
        if (disclosureConfigVO == null || StringUtils.isBlank(disclosureConfigVO.getConfigId())) {
            log.info("### DisclosureConfigServiceImpl updateDisclosureConfig disclosureConfigVO is null");
            return 0;
        }
        DisclosureConfigDto disclosureConfigDto = new DisclosureConfigDto();
        BeanUtils.copyProperties(disclosureConfigVO, disclosureConfigDto);
        disclosureConfigDto.setId(Long.parseLong(disclosureConfigVO.getConfigId()));
        disclosureConfigDto.setUpdateTime(new Date());
        disclosureConfigDto.setParentId(Long.parseLong(disclosureConfigVO.getConfigParentId()));
        return disclosureConfigMapper.updateByPrimaryKeySelective(disclosureConfigDto);
    }

    @Override
    public int deleteDisclosureConfig(String ids) {
        if (StringUtils.isBlank(ids)) {
            log.info("### DisclosureConfigServiceImpl deleteDisclosureConfig ids is null");
            return 0;
        }
        return disclosureConfigMapper.deleteByIds(ids);
    }

    @Override
    public List<DisclosureConfigVO> listDisclosureConfig(DisclosureConfigVO disclosureConfigVO) {
        if (disclosureConfigVO == null) {
            log.info("### DisclosureConfigServiceImpl listDisclosureConfig disclosureConfigVO is null");
            return null;
        }
        List<DisclosureConfigDto> disclosureConfigDtoList = getDisclosureConfigVOS(disclosureConfigVO);
        if (CollectionUtils.isEmpty(disclosureConfigDtoList)) {
            log.info("### DisclosureConfigServiceImpl listDisclosureConfig disclosureConfigDtoList is null");
            return null;
        }
        List<DisclosureConfigVO> disclosureConfigVOS = new ArrayList<>();
        BeanUtils.copyListProperties(disclosureConfigDtoList, disclosureConfigVOS, DisclosureConfigVO.class);
        handleDisclosureConfigVOS(disclosureConfigVOS);
        return disclosureConfigVOS;
    }

    private List<DisclosureConfigDto> getDisclosureConfigVOS(DisclosureConfigVO disclosureConfigVO) {
        DisclosureConfigDto disclosureConfigDto = new DisclosureConfigDto();
        BeanUtils.copyProperties(disclosureConfigVO, disclosureConfigDto);
        if (StringUtils.isNotBlank(disclosureConfigVO.getConfigParentId())) {
            disclosureConfigDto.setParentId(Long.parseLong(disclosureConfigVO.getConfigParentId()));
        }
        List<DisclosureConfigDto> disclosureConfigDtoList = disclosureConfigMapper.listDisclosureConfig(disclosureConfigDto);
        if (CollectionUtils.isEmpty(disclosureConfigDtoList)) {
            log.info("### DisclosureConfigServiceImpl listDisclosureConfig disclosureConfigDtoList is null");
            return null;
        }
        return disclosureConfigDtoList;
    }

    private void handleDisclosureConfigVOS(List<DisclosureConfigVO> disclosureConfigVOS) {
        if (CollectionUtils.isEmpty(disclosureConfigVOS)) {
            return;
        }
        for (DisclosureConfigVO disclosureConfigVO : disclosureConfigVOS) {
            disclosureConfigVO.setConfigId(String.valueOf(disclosureConfigVO.getId()));
            disclosureConfigVO.setConfigParentId(String.valueOf(disclosureConfigVO.getParentId()));
            disclosureConfigVO.setId(null);
            disclosureConfigVO.setParentId(null);
        }
    }

    @Override
    public PageInfo<DisclosureConfigVO> pageDisclosureConfig(DisclosureConfigVO disclosureConfigVO) {
        if (disclosureConfigVO == null) {
            log.info("### DisclosureConfigServiceImpl pageDisclosureConfig disclosureConfigVO is null");
            return null;
        }
        List<DisclosureConfigDto> disclosureConfigDtoList = getDisclosureConfigVOS(disclosureConfigVO);
        if (CollectionUtils.isEmpty(disclosureConfigDtoList)) {
            log.info("### DisclosureConfigServiceImpl pageDisclosureConfig disclosureConfigDtoList is null");
            return null;
        }
        PageInfo<DisclosureConfigDto> disclosureConfigDtoPageInfo = new PageInfo<>(disclosureConfigDtoList);
        long total = disclosureConfigDtoPageInfo.getTotal();
        List<DisclosureConfigVO> disclosureConfigVOList =  new ArrayList<>();
        BeanUtils.copyListProperties(disclosureConfigDtoList, disclosureConfigVOList, DisclosureConfigVO.class);
        handleDisclosureConfigVOS(disclosureConfigVOList);

        PageInfo<DisclosureConfigVO> disclosureConfigVOPageInfo = new PageInfo<>(disclosureConfigVOList);
        disclosureConfigVOPageInfo.setTotal(total);

        return disclosureConfigVOPageInfo;
    }
}
