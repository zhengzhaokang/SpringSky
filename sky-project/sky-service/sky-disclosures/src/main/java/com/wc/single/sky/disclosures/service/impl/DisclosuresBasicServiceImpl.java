package com.wc.single.sky.disclosures.service.impl;

import com.github.pagehelper.PageInfo;
import com.wc.single.sky.common.utils.DateUtils;
import com.wc.single.sky.disclosures.common.DisclosureConstant;
import com.wc.single.sky.disclosures.common.enums.DisclosureBasicEnum;
import com.wc.single.sky.disclosures.common.enums.DisclosureItemEnum;
import com.wc.single.sky.disclosures.dto.DisclosuresBasicDto;
import com.wc.single.sky.disclosures.dto.DisclosuresItemDto;
import com.wc.single.sky.disclosures.entity.DisclosuresBasicCount;
import com.wc.single.sky.disclosures.manager.DisclosuresBasicManager;
import com.wc.single.sky.disclosures.manager.DisclosuresItemManager;
import com.wc.single.sky.disclosures.service.DisclosureNumberService;
import com.wc.single.sky.disclosures.service.DisclosuresBasicService;
import com.wc.single.sky.disclosures.vo.DisclosuresBasicVO;
import com.wc.single.sky.common.utils.SnowFlakeUtil;
import com.wc.single.sky.common.utils.StringUtils;
import com.wc.single.sky.common.utils.bean.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DisclosuresBasicServiceImpl implements DisclosuresBasicService {

    @Autowired
    private DisclosuresBasicManager disclosuresBasicManager;

    @Autowired
    private SnowFlakeUtil snowFlakeUtil;

    @Autowired
    private DisclosureNumberService disclosureNumberService;

    @Autowired
    private DisclosuresItemManager disclosuresItemManager;

    @Override
    public int insertDisclosuresBasic(DisclosuresBasicVO disclosuresBasicVO) {
        if (disclosuresBasicVO == null) {
            log.info("### DisclosuresBasicServiceImpl insertDisclosuresBasic disclosuresBasicVO is null");
            return 0;
        }
        DisclosuresBasicDto disclosuresBasicDto = new DisclosuresBasicDto();
        BeanUtils.copyProperties(disclosuresBasicVO, disclosuresBasicDto);

        disclosuresBasicDto.setId(SnowFlakeUtil.nextId());
        disclosuresBasicDto.setBasicNumber(disclosureNumberService.getDisclosureNumber(DisclosureConstant.QDP_SUFFIX));
        disclosuresBasicDto.setStatus(DisclosureBasicEnum.SUBMITTED.code);
        disclosuresBasicDto.setDelFlag(DisclosureConstant.ENABLE);
        disclosuresBasicDto.setCreateTime(new Date());
        disclosuresBasicDto.setUpdateTime(new Date());

        return disclosuresBasicManager.insertDisclosuresBasic(disclosuresBasicDto);
    }

    @Override
    public int updateDisclosuresBasic(DisclosuresBasicVO disclosuresBasicVO) {
        if (disclosuresBasicVO == null || StringUtils.isBlank(disclosuresBasicVO.getBasicId())) {
            log.info("### DisclosuresBasicServiceImpl updateDisclosuresBasic param is null");
            return 0;
        }
        DisclosuresBasicDto disclosuresBasicDto = new DisclosuresBasicDto();
        BeanUtils.copyProperties(disclosuresBasicVO, disclosuresBasicDto);
        disclosuresBasicDto.setId(Long.parseLong(disclosuresBasicVO.getBasicId()));
        disclosuresBasicDto.setUpdateTime(new Date());
        return disclosuresBasicManager.updateDisclosuresBasic(disclosuresBasicDto);
    }

    @Override
    public int updateDisclosuresBasicByExample(DisclosuresBasicVO disclosuresBasicVO) {
        if (disclosuresBasicVO == null || StringUtils.isBlank(disclosuresBasicVO.getBasicId())) {
            log.info("### DisclosuresBasicServiceImpl updateDisclosuresBasic param is null");
            return 0;
        }
        DisclosuresBasicDto disclosuresBasicDto = new DisclosuresBasicDto();
        BeanUtils.copyProperties(disclosuresBasicVO, disclosuresBasicDto);
        disclosuresBasicDto.setId(Long.parseLong(disclosuresBasicVO.getBasicId()));
        disclosuresBasicDto.setUpdateTime(new Date());
        return disclosuresBasicManager.updateDisclosuresBasicByExample(disclosuresBasicDto);
    }

    @Override
    public int deleteDisclosuresBasicByIds(List<String> ids, String updateBy) {
        return disclosuresBasicManager.deleteDisclosuresBasicByIds(ids, updateBy);
    }

    @Override
    public DisclosuresBasicVO selectDisclosuresBasicById(Long id) {
        if (id == null) {
            log.info("### DisclosuresBasicServiceImpl selectDisclosuresBasicById id is null");
            return null;
        }
        DisclosuresBasicDto disclosuresBasicDto = disclosuresBasicManager.selectDisclosuresBasicById(id);
        if (disclosuresBasicDto == null) {
            log.info("### DisclosuresBasicServiceImpl selectDisclosuresBasicById disclosuresBasicDto is null");
            return null;
        }
        DisclosuresBasicVO disclosuresBasicVO = new DisclosuresBasicVO();
        BeanUtils.copyProperties(disclosuresBasicDto, disclosuresBasicVO);
        disclosuresBasicVO.setBasicId(String.valueOf(id));
        disclosuresBasicVO.setId(null);
        return disclosuresBasicVO;
    }

    @Override
    public PageInfo<DisclosuresBasicVO> selectDisclosuresBasicList(DisclosuresBasicVO disclosuresBasicVO) {
        DisclosuresBasicDto disclosuresBasicDto = new DisclosuresBasicDto();
        BeanUtils.copyProperties(disclosuresBasicVO, disclosuresBasicDto);
        List<DisclosuresBasicDto> disclosuresBasicDtoList = disclosuresBasicManager.selectDisclosuresBasicList(disclosuresBasicDto);
        if (CollectionUtils.isEmpty(disclosuresBasicDtoList)) {
            log.info("### DisclosuresBasicServiceImpl selectDisclosuresBasicList disclosuresBasicDtoList is null");
            return null;
        }
        PageInfo<DisclosuresBasicDto> disclosuresBasicDtoPageInfo = new PageInfo<>(disclosuresBasicDtoList);
        long total = disclosuresBasicDtoPageInfo.getTotal();
        List<DisclosuresBasicVO> disclosuresBasicVOList = Lists.newArrayList();
        BeanUtils.copyListProperties(disclosuresBasicDtoList, disclosuresBasicVOList, DisclosuresBasicVO.class);
        //处理id
        handleDisclosuresBasicVOList(disclosuresBasicVOList);
        PageInfo<DisclosuresBasicVO> disclosuresBasicVOPageInfo = new PageInfo<>(disclosuresBasicVOList);
        disclosuresBasicVOPageInfo.setTotal(total);
        return disclosuresBasicVOPageInfo;
    }

    private void handleDisclosuresBasicVOList(List<DisclosuresBasicVO> disclosuresBasicVOList) {
        if (CollectionUtils.isEmpty(disclosuresBasicVOList)) {
            log.info("### DisclosuresBasicServiceImpl handleDisclosuresBasicVOList disclosuresBasicVOList isEmpty");
            return;
        }
        disclosuresBasicVOList.forEach(item -> {
            Long id = item.getId();
            item.setBasicId(String.valueOf(id));
            item.setId(null);
            item.setDueDateF(DateUtils.dateFormat(item.getDueDate(), DateUtils.QDP_DEFAULT_DATE_PATTERN));
            item.setCanChangeStatus(false);
            setCanChangeStatus(item, id);
        });
    }

    private void setCanChangeStatus(DisclosuresBasicVO item, Long id) {
        DisclosuresItemDto disclosuresItemDto = new DisclosuresItemDto();
        disclosuresItemDto.setDisclosuresId(String.valueOf(id));
        // 非 Data Collected 状态直接返回
        if (!StringUtils.equals(item.getStatus(), DisclosureBasicEnum.DATA_COLLECTED.code)) {
            return;
        }
        List<DisclosuresItemDto> disclosuresItemDtoList = disclosuresItemManager.selectDisclosuresItemList(disclosuresItemDto);
        if (CollectionUtils.isNotEmpty(disclosuresItemDtoList)) {
            Set<String> statusSet = disclosuresItemDtoList.stream().map(DisclosuresItemDto::getStatus).collect(Collectors.toSet());
            if (statusSet.contains(DisclosureItemEnum.CONFIRMED.code) && statusSet.size() == 1) {
                item.setCanChangeStatus(true);
            }
        }
    }

    @Override
    public int selectDisclosuresBasicCountByStatus(String status) {
        return disclosuresBasicManager.selectDisclosuresBasicCountByStatus(status);
    }

    @Override
    public List<DisclosuresBasicCount> selectDisclosuresBasicCount() {
        List<DisclosuresBasicCount> disclosuresBasicCountList = Lists.newArrayList();
        List<String> allStatus = DisclosureBasicEnum.getAllStatus();
        List<DisclosuresBasicCount> disclosuresBasicCounts = disclosuresBasicManager.selectDisclosuresBasicCount();
        Map<String, Integer> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(disclosuresBasicCounts)) {
            map = disclosuresBasicCounts.stream().collect(Collectors.toMap(DisclosuresBasicCount::getStatus, DisclosuresBasicCount::getCount, (k1, k2) -> k1));
        }
        for (String status : allStatus) {
            Integer count = MapUtils.getInteger(map, status, 0);
            DisclosuresBasicCount disclosuresBasicCount = new DisclosuresBasicCount();
            disclosuresBasicCount.setStatus(status);
            disclosuresBasicCount.setCount(count);
            disclosuresBasicCountList.add(disclosuresBasicCount);
        }
        return disclosuresBasicCountList;
    }

    @Override
    public List<DisclosuresBasicVO> selectDisclosuresBasic(DisclosuresBasicVO disclosuresBasicVO) {
        DisclosuresBasicDto disclosuresBasicDto = new DisclosuresBasicDto();
        BeanUtils.copyProperties(disclosuresBasicVO, disclosuresBasicDto);
        List<DisclosuresBasicDto> disclosuresBasicDtoList = disclosuresBasicManager.selectDisclosuresBasicList(disclosuresBasicDto);
        if (CollectionUtils.isEmpty(disclosuresBasicDtoList)) {
            log.info("### DisclosuresBasicServiceImpl selectDisclosuresBasicList disclosuresBasicDtoList is null");
            return null;
        }
        List<DisclosuresBasicVO> disclosuresBasicVOList = Lists.newArrayList();
        BeanUtils.copyListProperties(disclosuresBasicDtoList, disclosuresBasicVOList, DisclosuresBasicVO.class);
        //处理id
        handleDisclosuresBasicVOList(disclosuresBasicVOList);
        return disclosuresBasicVOList;
    }
}
