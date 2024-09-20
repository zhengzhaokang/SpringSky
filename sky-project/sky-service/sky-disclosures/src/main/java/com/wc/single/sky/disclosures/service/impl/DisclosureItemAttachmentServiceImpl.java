package com.wc.single.sky.disclosures.service.impl;

import com.alibaba.fastjson.JSON;
import com.wc.single.sky.common.utils.SnowFlakeUtil;
import com.wc.single.sky.disclosures.common.DisclosureConstant;
import com.wc.single.sky.disclosures.dto.DisclosureItemAttachmentDto;
import com.wc.single.sky.disclosures.dto.DisclosureS3AttachmentDto;
import com.wc.single.sky.disclosures.mapper.DisclosureItemAttachmentMapper;
import com.wc.single.sky.disclosures.mapper.DisclosureS3AttachmentMapper;
import com.wc.single.sky.disclosures.service.DisclosureItemAttachmentService;
import com.wc.single.sky.disclosures.vo.DisclosureItemAttachmentVO;
import com.wc.single.sky.common.utils.StringUtils;
import com.wc.single.sky.common.utils.bean.BeanUtils;
import com.wc.single.sky.disclosures.vo.DisclosureS3AttachmentVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DisclosureItemAttachmentServiceImpl implements DisclosureItemAttachmentService {

    @Autowired
    private DisclosureItemAttachmentMapper disclosureItemAttachmentMapper;

    @Autowired
    private DisclosureS3AttachmentMapper disclosureS3AttachmentMapper;

    @Autowired
    private SnowFlakeUtil snowFlakeUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveDisclosureItemAttachment(List<DisclosureItemAttachmentVO> disclosureItemAttachmentVOList, String business, String module) {
        if (CollectionUtils.isEmpty(disclosureItemAttachmentVOList) || StringUtils.isAnyBlank(business, module)) {
            log.info("### DisclosureItemAttachmentServiceImpl saveDisclosureItemAttachment disclosureItemAttachmentVOList isEmpty");
            return 0;
        }
        log.info("### DisclosureItemAttachmentServiceImpl saveDisclosureItemAttachment disclosureItemAttachmentVOList:{} business:{} module:{}", JSON.toJSONString(disclosureItemAttachmentVOList), business, module);
        Example example = new Example(DisclosureItemAttachmentDto.class);
        example.createCriteria().andEqualTo(DisclosureConstant.BUSINESS, business).andEqualTo(DisclosureConstant.MODULE, module);
        disclosureItemAttachmentMapper.deleteByExample(example);
        List<DisclosureItemAttachmentDto> disclosureItemAttachmentDtoList = Lists.newArrayList();
        BeanUtils.copyProperties(disclosureItemAttachmentVOList, disclosureItemAttachmentDtoList, DisclosureItemAttachmentDto.class);
        Date date = new Date();
        disclosureItemAttachmentDtoList.forEach(item -> {
            item.setCreateDate(date);
            item.setModule(module);
        });
        return disclosureItemAttachmentMapper.insertList(disclosureItemAttachmentDtoList);
    }

    @Override
    public List<DisclosureItemAttachmentVO> queryDisclosureItemAttachment(String business, String module) {
        if (StringUtils.isAnyBlank(business, module)) {
            log.info("### DisclosureItemAttachmentServiceImpl queryDisclosureItemAttachment param is null");
            return null;
        }
        Example example = new Example(DisclosureItemAttachmentDto.class);
        example.createCriteria().andEqualTo(DisclosureConstant.BUSINESS, business).andEqualTo(DisclosureConstant.MODULE, module);
        example.orderBy(DisclosureConstant.CREATE_DATE).desc();
        List<DisclosureItemAttachmentDto> disclosureItemAttachmentDtoList = disclosureItemAttachmentMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(disclosureItemAttachmentDtoList)) {
            List<DisclosureItemAttachmentVO> disclosureItemAttachmentVOList = Lists.newArrayList();
            BeanUtils.copyProperties(disclosureItemAttachmentDtoList, disclosureItemAttachmentVOList, DisclosureItemAttachmentVO.class);
            return disclosureItemAttachmentVOList;
        }
        log.info("### DisclosureItemAttachmentServiceImpl queryDisclosureItemAttachment disclosureItemAttachmentDtoList is null");
        return null;
    }

    @Override
    public int deleteDisclosureItemAttachment(String business, String module) {
        log.info("### DisclosureItemAttachmentServiceImpl deleteDisclosureItemAttachment business:{},module:{}", business, module);
        if (StringUtils.isAnyBlank(business, module)) {
            log.info("### DisclosureItemAttachmentServiceImpl deleteDisclosureItemAttachment param is null");
            return 0;
        }
        Example example = new Example(DisclosureItemAttachmentDto.class);
        example.createCriteria().andEqualTo(DisclosureConstant.BUSINESS, business).andEqualTo(DisclosureConstant.MODULE, module);
        return disclosureItemAttachmentMapper.deleteByExample(example);
    }

    @Override
    public int addDisclosureItemAttachment(List<DisclosureS3AttachmentVO> disclosureItemAttachmentVOList, String business) {
        if (CollectionUtils.isEmpty(disclosureItemAttachmentVOList)) {
            log.info("### DisclosureItemAttachmentServiceImpl addDisclosureItemAttachment disclosureItemAttachmentVOList is null");
            return 0;
        }

        removeDisclosureItemAttachment(business);

        List<DisclosureS3AttachmentDto> disclosureS3AttachmentDtoList = Lists.newArrayList();
        BeanUtils.copyListProperties(disclosureItemAttachmentVOList, disclosureS3AttachmentDtoList, DisclosureS3AttachmentDto.class);
        Date date = new Date();
        disclosureS3AttachmentDtoList.forEach(item -> {
            item.setId(SnowFlakeUtil.nextId());
            item.setUpdateDate(date);
            item.setTicketNumber(business);
            item.setDataId(business);
            item.setDelFlag(DisclosureConstant.ENABLE);
            disclosureS3AttachmentMapper.insertSelective(item);
        });
        return disclosureS3AttachmentDtoList.size();
    }

    @Override
    public int removeDisclosureItemAttachment(String business) {
        if (StringUtils.isBlank(business)) {
            log.info("### DisclosureItemAttachmentServiceImpl removeDisclosureItemAttachment param is null");
            return 0;
        }
        Example example = new Example(DisclosureS3AttachmentDto.class);
        example.createCriteria().andEqualTo(DisclosureConstant.TICKET_NUMBER, business);
        return disclosureS3AttachmentMapper.deleteByExample(example);
    }

    @Override
    public List<DisclosureS3AttachmentVO> getS3AttachmentByTicketNumber(String ticketNumber) {
        Example example = new Example(DisclosureS3AttachmentDto.class);
        example.createCriteria().andEqualTo(DisclosureConstant.TICKET_NUMBER, ticketNumber).andEqualTo(DisclosureConstant.DEL_FLAG, DisclosureConstant.ENABLE);
        List<DisclosureS3AttachmentDto> disclosureS3AttachmentDtoList = disclosureS3AttachmentMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(disclosureS3AttachmentDtoList)) {
            log.warn("### DisclosureItemAttachmentServiceImpl getS3AttachmentByTicketNumber ticketNumber:{}, disclosureS3AttachmentDtoList is null", ticketNumber);
            return null;
        }
        List<DisclosureS3AttachmentVO> disclosureS3AttachmentVOList = new ArrayList<>();
        for (int i = 0; i < disclosureS3AttachmentDtoList.size(); i++) {
            DisclosureS3AttachmentDto item = disclosureS3AttachmentDtoList.get(i);
            String fileKey = item.getFileKey();
            DisclosureS3AttachmentVO s3AttachmentVO = new DisclosureS3AttachmentVO();
            BeanUtils.copyProperties(item, s3AttachmentVO);
            s3AttachmentVO.setDownloadUrl("/disclosures/S3File/downloadFileUrl?fileKey=" + fileKey);
            s3AttachmentVO.setAttachmentRole(item.getRole());
            s3AttachmentVO.setAttachmentNumber(String.valueOf(i + 1));
            s3AttachmentVO.setAttachmentName(item.getFileName());
            disclosureS3AttachmentVOList.add(s3AttachmentVO);
        }
        log.info("### DisclosureItemAttachmentServiceImpl getS3AttachmentByTicketNumber disclosureS3AttachmentVOList:{}", JSON.toJSONString(disclosureS3AttachmentVOList));
        return disclosureS3AttachmentVOList;
    }
}
