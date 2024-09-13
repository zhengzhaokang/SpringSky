package com.wc.single.sky.disclosures.service.impl;

import com.github.pagehelper.PageInfo;
import com.wc.single.sky.common.utils.SnowFlakeUtil;
import com.wc.single.sky.disclosures.common.DisclosureConstant;
import com.wc.single.sky.disclosures.dto.DisclosureItemCommentDto;
import com.wc.single.sky.disclosures.mapper.DisclosureItemCommentMapper;
import com.wc.single.sky.disclosures.service.DisclosureItemCommentService;
import com.wc.single.sky.disclosures.vo.DisclosureItemCommentVO;
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
public class DisclosureItemCommentServiceImpl implements DisclosureItemCommentService {

    @Autowired
    private DisclosureItemCommentMapper disclosureItemCommentMapper;

    @Autowired
    private SnowFlakeUtil snowFlakeUtil;

    @Override
    public int saveDisclosureItemComment(DisclosureItemCommentVO disclosureItemCommentVO) {
        if (disclosureItemCommentVO == null) {
            log.info("### DisclosureItemCommentServiceImpl saveDisclosureItemComment disclosureItemCommentVO is null");
            return 0;
        }
        DisclosureItemCommentDto disclosureItemCommentDto = new DisclosureItemCommentDto();
        BeanUtils.copyProperties(disclosureItemCommentVO, disclosureItemCommentDto);
        disclosureItemCommentDto.setId(SnowFlakeUtil.nextId());
        disclosureItemCommentDto.setModule(DisclosureConstant.MODULE_ITEM);
        disclosureItemCommentDto.setDelFlag(DisclosureConstant.ENABLE);
        disclosureItemCommentDto.setCreateTime(new Date());
        disclosureItemCommentDto.setUpdateTime(new Date());
        return disclosureItemCommentMapper.insertSelective(disclosureItemCommentDto);
    }

    @Override
    public int deleteDisclosureItemComment(String id, String updateBy) {
        if (StringUtils.isBlank(id)) {
            log.info("### DisclosureItemCommentServiceImpl deleteDisclosureItemComment id is null");
            return 0;
        }
        DisclosureItemCommentDto oldDisclosureItemCommentDto = disclosureItemCommentMapper.selectByPrimaryKey(Long.parseLong(id));
        if (oldDisclosureItemCommentDto == null) {
            log.info("### DisclosureItemCommentServiceImpl deleteDisclosureItemComment oldDisclosureItemCommentDto is null");
            return 0;
        }
        Date createTime = oldDisclosureItemCommentDto.getCreateTime();
        if (getDifferenceMinutes(new Date(), createTime) > DisclosureConstant.DISC_ITEM_COMMENT_DELETE_TIME) {
            log.info("### DisclosureItemCommentServiceImpl deleteDisclosureItemComment can not change");
            return DisclosureConstant.CAN_NOT_CHANGE;
        }

        DisclosureItemCommentDto disclosureItemCommentDto = new DisclosureItemCommentDto();
        disclosureItemCommentDto.setId(Long.parseLong(id));
        disclosureItemCommentDto.setUpdateBy(updateBy);
        disclosureItemCommentDto.setUpdateTime(new Date());
        disclosureItemCommentDto.setDelFlag(DisclosureConstant.DISABLE);
        return disclosureItemCommentMapper.updateByPrimaryKeySelective(disclosureItemCommentDto);
    }

    @Override
    public PageInfo<DisclosureItemCommentVO> getDisclosureItemCommentList(DisclosureItemCommentVO disclosureItemCommentVO) {
        List<DisclosureItemCommentDto> disclosureItemCommentDtoList = getDisclosureItemCommentDtoList(disclosureItemCommentVO);
        if (CollectionUtils.isNotEmpty(disclosureItemCommentDtoList)) {
            PageInfo<DisclosureItemCommentDto> disclosureItemCommentDtoPageInfo = new PageInfo<>(disclosureItemCommentDtoList);
            long total = disclosureItemCommentDtoPageInfo.getTotal();
            List<DisclosureItemCommentVO> disclosureItemCommentVOList = new ArrayList<>();
            BeanUtils.copyListProperties(disclosureItemCommentDtoList, disclosureItemCommentVOList, DisclosureItemCommentVO.class);
            handleDisclosureItemCommentVOList(disclosureItemCommentVOList);
            PageInfo<DisclosureItemCommentVO> disclosureItemCommentVOPageInfo = new PageInfo<>(disclosureItemCommentVOList);
            disclosureItemCommentVOPageInfo.setTotal(total);
            return disclosureItemCommentVOPageInfo;
        }
        log.info("### DisclosureItemCommentServiceImpl getDisclosureItemCommentList disclosureItemCommentDtoList is null");
        return null;
    }

    private List<DisclosureItemCommentDto> getDisclosureItemCommentDtoList(DisclosureItemCommentVO disclosureItemCommentVO) {
        if (disclosureItemCommentVO == null || StringUtils.isBlank(disclosureItemCommentVO.getBusiness())) {
            log.info("### DisclosureItemCommentServiceImpl disclosureItemCommentVO is null");
            return null;
        }
        DisclosureItemCommentDto disclosureItemCommentDto = new DisclosureItemCommentDto();
        BeanUtils.copyProperties(disclosureItemCommentVO, disclosureItemCommentDto);
        Example example = new Example(DisclosureItemCommentDto.class);
        example.createCriteria().andEqualTo(DisclosureConstant.DEL_FLAG, DisclosureConstant.ENABLE).andEqualTo(DisclosureConstant.BUSINESS, disclosureItemCommentDto.getBusiness());
        example.orderBy(DisclosureConstant.CREATE_TIME).desc();
        return disclosureItemCommentMapper.selectByExample(example);
    }

    @Override
    public List<DisclosureItemCommentVO> getDisclosureItemCommentVOList(DisclosureItemCommentVO disclosureItemCommentVO) {
        List<DisclosureItemCommentDto> disclosureItemCommentDtoList = getDisclosureItemCommentDtoList(disclosureItemCommentVO);
        if (CollectionUtils.isNotEmpty(disclosureItemCommentDtoList)) {
            List<DisclosureItemCommentVO> disclosureItemCommentVOList = new ArrayList<>();
            BeanUtils.copyListProperties(disclosureItemCommentDtoList, disclosureItemCommentVOList, DisclosureItemCommentVO.class);
            handleDisclosureItemCommentVOList(disclosureItemCommentVOList);
            return disclosureItemCommentVOList;
        }
        log.info("### DisclosureItemCommentServiceImpl getDisclosureItemCommentVOList disclosureItemCommentDtoList is null");
        return null;
    }

    private void handleDisclosureItemCommentVOList(List<DisclosureItemCommentVO> disclosureItemCommentVOList) {
        if (CollectionUtils.isEmpty(disclosureItemCommentVOList)) {
            log.info("### DisclosureItemCommentServiceImpl handleDisclosureItemCommentVOList disclosureItemCommentVOList isEmpty");
            return;
        }
        Date now = new Date();
        disclosureItemCommentVOList.forEach(item -> {
            Long id = item.getId();
            item.setCommentId(String.valueOf(id));
            item.setId(null);
            Date createTime = item.getCreateTime();
            long differenceMinutes = getDifferenceMinutes(now, createTime);
            item.setCanDel(differenceMinutes < DisclosureConstant.DISC_ITEM_COMMENT_DELETE_TIME);
        });
    }

    private long getDifferenceMinutes(Date now, Date createTime) {
        long difference = now.getTime() - createTime.getTime();
        return difference / (60 * 1000);
    }
}
