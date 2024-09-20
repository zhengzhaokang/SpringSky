package com.wc.single.sky.disclosures.service.impl;

import com.github.pagehelper.PageInfo;
import com.wc.single.sky.common.utils.SnowFlakeUtil;
import com.wc.single.sky.disclosures.common.DisclosureConstant;
import com.wc.single.sky.disclosures.dto.DisclosureItemLogDto;
import com.wc.single.sky.disclosures.mapper.DisclosureItemLogMapper;
import com.wc.single.sky.disclosures.service.DisclosureItemLogService;
import com.wc.single.sky.disclosures.vo.DisclosureItemLogVO;
import com.wc.single.sky.common.utils.StringUtils;
import com.wc.single.sky.common.utils.bean.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DisclosureItemLogServiceImpl implements DisclosureItemLogService {

    @Autowired
    private DisclosureItemLogMapper disclosureItemLogMapper;

    @Autowired
    private SnowFlakeUtil snowFlakeUtil;

    @Override
    public int insertDisclosureItemLog(DisclosureItemLogVO disclosureItemLogVO) {
        log.info("### DisclosureItemLogServiceImpl insertDisclosureItemLog:{}", disclosureItemLogVO);
        if (disclosureItemLogVO == null) {
            log.info("### DisclosureItemLogServiceImpl insertDisclosureItemLog disclosureItemLogVO is null");
            return 0;
        }
        DisclosureItemLogDto disclosureItemLogDto = new DisclosureItemLogDto();
        BeanUtils.copyProperties(disclosureItemLogVO, disclosureItemLogDto);
        disclosureItemLogDto.setId(SnowFlakeUtil.nextId());
        disclosureItemLogDto.setDelFlag(DisclosureConstant.ENABLE);
        disclosureItemLogDto.setCreateTime(new Date());
        disclosureItemLogDto.setUpdateTime(new Date());
        disclosureItemLogDto.setCreateBy(disclosureItemLogVO.getOperator());
        disclosureItemLogDto.setUpdateBy(disclosureItemLogVO.getOperator());
        return disclosureItemLogMapper.insertSelective(disclosureItemLogDto);
    }

    @Override
    public List<DisclosureItemLogVO> selectDisclosureItemLog(DisclosureItemLogVO disclosureItemLogVO) {
        List<DisclosureItemLogDto> disclosureItemLogDtoList = getDisclosureItemLogDtoList(disclosureItemLogVO);
        if (CollectionUtils.isNotEmpty(disclosureItemLogDtoList)) {
            List<DisclosureItemLogVO> disclosureItemLogVOList = new ArrayList<>();
            BeanUtils.copyListProperties(disclosureItemLogDtoList, disclosureItemLogVOList, DisclosureItemLogVO.class);
            handleDisclosureItemLogVOList(disclosureItemLogVOList);
            return disclosureItemLogVOList;
        }
        log.info("### DisclosureItemLogServiceImpl selectDisclosureItemLog disclosureItemLogDtoList is null");
        return null;
    }

    private void handleDisclosureItemLogVOList(List<DisclosureItemLogVO> disclosureItemLogVOList) {
        if (CollectionUtils.isEmpty(disclosureItemLogVOList)) {
            log.info("### DisclosureItemLogServiceImpl handleDisclosureItemLogVOList disclosureItemLogVOList isEmpty");
            return;
        }
        disclosureItemLogVOList.forEach(item -> {
            item.setLogId(String.valueOf(item.getId()));
            item.setId(null);
        });
    }

    private List<DisclosureItemLogDto> getDisclosureItemLogDtoList(DisclosureItemLogVO disclosureItemLogVO) {
        if (StringUtils.isBlank(disclosureItemLogVO.getBusiness())) {
            log.info("### DisclosureItemLogServiceImpl selectDisclosureItemLog business is null");
            return null;
        }
        Example example = new Example(DisclosureItemLogDto.class);
        example.createCriteria().andEqualTo(DisclosureConstant.BUSINESS, disclosureItemLogVO.getBusiness())
                .andEqualTo(DisclosureConstant.MODULE, DisclosureConstant.MODULE_ITEM)
                .andEqualTo(DisclosureConstant.DEL_FLAG, DisclosureConstant.ENABLE);
        example.orderBy(DisclosureConstant.CREATE_TIME).desc();
        return disclosureItemLogMapper.selectByExample(example);
    }

    @Override
    public PageInfo<DisclosureItemLogVO> selectDisclosureItemLogPage(DisclosureItemLogVO disclosureItemLogVO) {
        List<DisclosureItemLogDto> disclosureItemLogDtoList = getDisclosureItemLogDtoList(disclosureItemLogVO);
        if(CollectionUtils.isEmpty(disclosureItemLogDtoList)) {
            log.info("### DisclosureItemLogServiceImpl selectDisclosureItemLogPage disclosureItemLogDtoList is null");
            return null;
        }
        PageInfo<DisclosureItemLogDto> disclosureItemLogDtoPageInfo = new PageInfo<>(disclosureItemLogDtoList);
        long total = disclosureItemLogDtoPageInfo.getTotal();
        List<DisclosureItemLogVO> disclosureItemLogVOList = new ArrayList<>();
        BeanUtils.copyListProperties(disclosureItemLogDtoList, disclosureItemLogVOList, DisclosureItemLogVO.class);
        handleDisclosureItemLogVOList(disclosureItemLogVOList);
        PageInfo<DisclosureItemLogVO> disclosureItemLogVOPageInfo = new PageInfo<>(disclosureItemLogVOList);
        disclosureItemLogVOPageInfo.setTotal(total);
        return disclosureItemLogVOPageInfo;
    }
}
