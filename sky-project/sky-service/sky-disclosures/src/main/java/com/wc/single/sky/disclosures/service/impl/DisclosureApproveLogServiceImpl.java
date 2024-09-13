package com.wc.single.sky.disclosures.service.impl;

import com.wc.single.sky.common.utils.SnowFlakeUtil;
import com.wc.single.sky.common.utils.bean.BeanUtils;
import com.wc.single.sky.disclosures.common.DisclosureConstant;
import com.wc.single.sky.disclosures.dto.DisclosureApproveLogDto;
import com.wc.single.sky.disclosures.mapper.DisclosureApproveLogMapper;
import com.wc.single.sky.disclosures.service.DisclosureApproveLogService;
import com.wc.single.sky.disclosures.vo.DisclosureApproveLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class DisclosureApproveLogServiceImpl implements DisclosureApproveLogService {

    @Autowired
    private DisclosureApproveLogMapper disclosureApproveLogMapper;

    @Autowired
    private SnowFlakeUtil snowFlakeUtil;

    @Override
    public int insertDisclosureApproveLog(DisclosureApproveLogVO disclosureApproveLogVO) {
        DisclosureApproveLogDto disclosureApproveLogDto = new DisclosureApproveLogDto();
        BeanUtils.copyProperties(disclosureApproveLogVO, disclosureApproveLogDto);
        disclosureApproveLogDto.setId(SnowFlakeUtil.nextId());
        disclosureApproveLogDto.setCreateTime(new Date());
        disclosureApproveLogDto.setUpdateTime(new Date());
        return disclosureApproveLogMapper.insertSelective(disclosureApproveLogDto);
    }

    @Override
    public int updateDisclosureApproveLog(DisclosureApproveLogVO disclosureApproveLogVO) {
        DisclosureApproveLogDto disclosureApproveLogDto = new DisclosureApproveLogDto();
        BeanUtils.copyProperties(disclosureApproveLogVO, disclosureApproveLogDto);
        disclosureApproveLogDto.setUpdateTime(new Date());

        Example example = new Example(DisclosureApproveLogDto.class);
        example.createCriteria().andEqualTo(DisclosureConstant.APPROVE_UUID, disclosureApproveLogVO.getApproveUuid());
        return disclosureApproveLogMapper.updateByExampleSelective(disclosureApproveLogDto, example);
    }

    @Override
    public DisclosureApproveLogVO getDisclosureApproveLog(String approveUuid) {
        Example example = new Example(DisclosureApproveLogDto.class);
        example.createCriteria().andEqualTo(DisclosureConstant.APPROVE_UUID, approveUuid);
        DisclosureApproveLogDto disclosureApproveLogDto = disclosureApproveLogMapper.selectOneByExample(example);
        DisclosureApproveLogVO disclosureApproveLogVO = new DisclosureApproveLogVO();
        BeanUtils.copyProperties(disclosureApproveLogDto, disclosureApproveLogVO);
        return disclosureApproveLogVO;
    }
}
