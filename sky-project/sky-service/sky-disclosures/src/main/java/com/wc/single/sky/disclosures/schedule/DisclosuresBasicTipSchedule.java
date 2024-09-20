package com.wc.single.sky.disclosures.schedule;

import com.alibaba.fastjson.JSON;
import com.wc.single.sky.common.utils.DateUtils;
import com.wc.single.sky.disclosures.common.DisclosureConstant;
import com.wc.single.sky.disclosures.common.MailConstant;
import com.wc.single.sky.disclosures.common.enums.DisclosureBasicEnum;
import com.wc.single.sky.disclosures.common.enums.DisclosureItemEnum;
import com.wc.single.sky.disclosures.dto.DisclosuresBasicDto;
import com.wc.single.sky.disclosures.dto.DisclosuresItemDto;
import com.wc.single.sky.disclosures.mapper.DisclosuresBasicMapper;
import com.wc.single.sky.disclosures.mapper.DisclosuresItemMapper;
import com.wc.single.sky.disclosures.mapper.SysRoleMapper;
import com.wc.single.sky.disclosures.util.SendMailUtil;
import com.wc.single.sky.disclosures.vo.user.SysUserRoleListVO;
import com.wc.single.sky.system.domain.dto.SysUserRoleListDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DisclosuresBasicTipSchedule {

    @Autowired
    private DisclosuresBasicMapper disclosuresBasicMapper;

    @Autowired
    private DisclosuresItemMapper disclosuresItemMapper;

    @Autowired
    private SendMailUtil mailUtil;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Scheduled(cron = "0 0 8 * * ?") // 每天8点执行一次
    public void execute() {
        List<DisclosuresBasicDto> disclosuresBasicDtoList = getDisclosuresBasicDtoList();
        if (CollectionUtils.isEmpty(disclosuresBasicDtoList)) {
            log.info("### DisclosuresBasicTipSchedule execute no need sendEmail");
            return;
        }
        List<String> collect = disclosuresBasicDtoList.stream().map(DisclosuresBasicDto::getQdpFocal).collect(Collectors.toList());
        log.info("### DisclosuresBasicTipSchedule need sendEmail qdpFocalList:{}", collect);
        for (DisclosuresBasicDto item : disclosuresBasicDtoList) {
            try {
                Example itemExample = new Example(DisclosuresItemDto.class);
                itemExample.createCriteria().andEqualTo(DisclosureConstant.DEL_FLAG, DisclosureConstant.ENABLE).
                        andEqualTo(DisclosureConstant.DISCLOSURES_ID, item.getId()).andEqualTo(DisclosureConstant.STATUS, DisclosureItemEnum.DATA_COLLECTED.code);
                List<DisclosuresItemDto> disclosuresItemDtoList = disclosuresItemMapper.selectByExample(itemExample);
                Set<String> emailSet = new HashSet<>();
                emailSet.add(item.getQdpFocal());
                if (CollectionUtils.isNotEmpty(disclosuresItemDtoList)) {
                    for (DisclosuresItemDto disclosuresItemDto : disclosuresItemDtoList) {
                        String owner = disclosuresItemDto.getOwner();
                        String createBy = disclosuresItemDto.getCreateBy();
                        emailSet.add(owner);
                        emailSet.add(createBy);
                    }
                }
                List<String> toList = Lists.newArrayList();
                for (String itCode : emailSet) {
                    String email = itCode + MailConstant.EMAIL_SUFFIX_LENOVO;
                    toList.add(email);
                }
                log.info("### DisclosuresBasicTipSchedule sendEmail toList:{}", JSON.toJSONString(toList));
                Map<String, Object> params = new HashMap<>();
                params.put(DisclosureConstant.DISCLOSURE_NUMBER, item.getBasicNumber());
                params.put(DisclosureConstant.DUE_DATE, DateUtils.dateFormat(item.getDueDate(), DateUtils.DATE_YYYY_MM_DD_EN));
                params.put(DisclosureConstant.REMAIN, DateUtils.dateBetween(new Date(), item.getDueDate()));
                SendMailUtil.MailContentBean mailContentBean = new SendMailUtil.MailContentBean(params);
                mailUtil.sendMailToQdpFocal(MailConstant.QDP_MANAGEMENT, MailConstant.QDP_MANAGEMENT, toList, null, null, mailContentBean);
            } catch (Exception e) {
                log.error("### DisclosuresBasicTipSchedule sendEmail error", e);
            }
        }
    }

    private List<DisclosuresBasicDto> getDisclosuresBasicDtoList() {
        Example example = new Example(DisclosuresBasicDto.class);
        example.createCriteria().andEqualTo(DisclosureConstant.STATUS, DisclosureBasicEnum.DATA_COLLECTED.code).
                andEqualTo(DisclosureConstant.DEL_FLAG, DisclosureConstant.ENABLE).andGreaterThan(DisclosureConstant.DUE_DATE, new Date());
        return disclosuresBasicMapper.selectByExample(example);
    }

    //@Scheduled(cron = "0 0/2 * 5,6,8,10,22 2,3,6,9,12 ?")
    @Scheduled(cron = "0 0 8 5,6,8,10 3,6,9,12 ?")// 根据下面季度末月份的第5/6/8/10 WD time 8AM
    public void quarterTipTask() {
        List<DisclosuresBasicDto> disclosuresBasicDtoList = getDisclosuresBasicDtoList();
        if (CollectionUtils.isEmpty(disclosuresBasicDtoList)) {
            log.info("### DisclosuresBasicTipSchedule quarterTipTask no need sendEmail");
            return;
        }
        List<String> qdpFocalList = disclosuresBasicDtoList.stream().map(DisclosuresBasicDto::getQdpFocal).collect(Collectors.toList());
        log.info("### DisclosuresBasicTipSchedule quarterTipTask need sendEmail qdpFocalList:{}", JSON.toJSONString(qdpFocalList));
        for (String qdpFocal : qdpFocalList) {
            try {
                List<String> toList = Lists.newArrayList();
                String email = qdpFocal + MailConstant.EMAIL_SUFFIX_LENOVO;
                toList.add(email);
                mailUtil.sendMailToQdpBasicByQuarter(MailConstant.QDP_MANAGEMENT, MailConstant.QDP_MANAGEMENT, toList, null, null, null);
            } catch (Exception e) {
                log.error("### DisclosuresBasicTipSchedule quarterTipTask sendEmail error", e);
            }
        }
    }

    // @Scheduled(cron = "0 0/2 * 15/1 2,3,6,9,12 ?")
    @Scheduled(cron = "0 0 8 15/1 3,6,9,12 ?")  //每个季度结束前15WD
    public void quarterFocalTipTask() {
        // 设置收件人Focal
        SysUserRoleListDTO sysUserRoleListDTO = new SysUserRoleListDTO();
        sysUserRoleListDTO.setRoleKey("TSY_FT_1002");
        List<SysUserRoleListVO> sysUserRoleListVOS = sysRoleMapper.userList(sysUserRoleListDTO);
        if (CollectionUtils.isEmpty(sysUserRoleListVOS)) {
            log.warn("### DisclosuresBasicTipSchedule quarterFocalTipTask sysUserRoleListVOS isEmpty");
            return;
        }
        List<String> itCodeList = sysUserRoleListVOS.stream().map(SysUserRoleListVO::getLoginName).collect(Collectors.toList());
        log.info("### DisclosuresBasicTipSchedule quarterFocalTipTask need sendEmail itCodeList:{}", JSON.toJSONString(itCodeList));
        for (String itCode : itCodeList) {
            try {
                List<String> toList = Lists.newArrayList();
                toList.add(itCode + MailConstant.EMAIL_SUFFIX_LENOVO);

                Map<String, Object> params = new HashMap<>();
                String period = getPeriod();
                params.put("period", period);
                SendMailUtil.MailContentBean mailContentBean = new SendMailUtil.MailContentBean(params);
                mailUtil.sendMailToFocalByQuarter(MailConstant.QDP_MANAGEMENT, MailConstant.QDP_MANAGEMENT, toList, null, null, mailContentBean);
            } catch (Exception e) {
                log.error("### DisclosuresBasicTipSchedule quarterFocalTipTask sendEmail error", e);
            }
        }

    }

    private static String getPeriod() {
        int currentQuarter = DateUtils.getCurrentQuarter(new Date());
        int currentYear = DateUtils.getCurrentYear(new Date());
        return "Q" + currentQuarter + " FY" + currentYear;
    }
}
