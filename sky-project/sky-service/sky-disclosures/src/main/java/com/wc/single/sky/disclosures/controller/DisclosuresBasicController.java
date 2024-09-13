package com.wc.single.sky.disclosures.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wc.single.sky.common.constant.Constants;
import com.wc.single.sky.common.utils.DateUtils;
import com.wc.single.sky.common.utils.poi.NewExcelUtil;
import com.wc.single.sky.disclosures.common.DisclosureConstant;
import com.wc.single.sky.disclosures.common.enums.DisclosureBasicEnum;
import com.wc.single.sky.disclosures.component.ITextPDFService;
import com.wc.single.sky.disclosures.entity.DisclosuresBasicCount;
import com.wc.single.sky.disclosures.service.DisclosureApproveLogService;
import com.wc.single.sky.disclosures.service.DisclosuresBasicService;
import com.wc.single.sky.disclosures.vo.DisclosureApproveLogVO;
import com.wc.single.sky.disclosures.vo.DisclosuresBasicVO;
import com.wc.single.sky.common.core.controller.BaseController;
import com.wc.single.sky.common.core.domain.ResultDTO;
import com.wc.single.sky.common.enums.BusinessType;
import com.wc.single.sky.common.log.annotation.OperLog;
import com.wc.single.sky.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api("disclosuresBasic")
@RestController
@RequestMapping("disclosures/basic")
@Slf4j
public class DisclosuresBasicController extends BaseController {

    @Autowired
    private DisclosuresBasicService disclosuresBasicService;

    @Autowired
    private DisclosureApproveLogService disclosureApproveLogService;

    @Autowired
    private ITextPDFService iTextPDFService;

    @ApiOperation(value = "Query Disclosures Basic List")
    @PostMapping("list")
    public ResultDTO<Object> list(@RequestBody DisclosuresBasicVO disclosuresBasicVO) {
        startPage();
        PageInfo<DisclosuresBasicVO> disclosuresBasicVOPageInfo = disclosuresBasicService.selectDisclosuresBasicList(disclosuresBasicVO);
        if (disclosuresBasicVOPageInfo == null) {
            logger.info("### DisclosuresBasicController list disclosuresBasicVOPageInfo is null");
            return ResultDTO.success(getDataDefaultTable());
        }
        return ResultDTO.success(getDataTable(disclosuresBasicVOPageInfo));
    }

    @ApiOperation(value = "Query Disclosures Basic Export")
    @PostMapping("export")
    public void export(@RequestBody DisclosuresBasicVO disclosuresBasicVO) {
        PageHelper.startPage(Constants.DEFAULT_PAGE_NUM, Integer.MAX_VALUE);
        PageInfo<DisclosuresBasicVO> disclosuresBasicVOPageInfo = disclosuresBasicService.selectDisclosuresBasicList(disclosuresBasicVO);
        List<DisclosuresBasicVO> list = disclosuresBasicVOPageInfo.getList();
        NewExcelUtil<DisclosuresBasicVO> util = new NewExcelUtil<>(DisclosuresBasicVO.class);
        util.exportExcel(getResponse(),list, DisclosureConstant.DISCLOSURES_BASIC);
    }

    @ApiOperation(value = "Query Disclosures Basic Detail")
    @GetMapping("info/{basicId}")
    public ResultDTO<Object> info(@PathVariable("basicId") String basicId) {
        if (StringUtils.isBlank(basicId)) {
            return ResultDTO.fail("param is null");
        }
        DisclosuresBasicVO disclosuresBasicVO = disclosuresBasicService.selectDisclosuresBasicById(Long.parseLong(basicId));
        return ResultDTO.success(disclosuresBasicVO);
    }

    @ApiOperation(value = "Query Disclosures Basic Count")
    @GetMapping("count")
    public ResultDTO<Object> count() {
        List<DisclosuresBasicCount> disclosuresBasicCount = disclosuresBasicService.selectDisclosuresBasicCount();
        return ResultDTO.success(disclosuresBasicCount);
    }

    @ApiOperation(value = "Add Disclosures Basic")
    @PostMapping("add")
    public ResultDTO<Object> add(@RequestBody DisclosuresBasicVO disclosuresBasicVO) {
        if (disclosuresBasicVO == null) {
            return ResultDTO.fail("disclosuresBasic param is null");
        }
        // 校验是否重复
        List<DisclosuresBasicVO> disclosuresBasicVOList = getDisclosuresBasicVOS(disclosuresBasicVO);
        if (CollectionUtils.isNotEmpty(disclosuresBasicVOList)) {
            return ResultDTO.fail("Same disclosure already existed, please check it");
        }
        int count = disclosuresBasicService.insertDisclosuresBasic(disclosuresBasicVO);
        return ResultDTO.success(count);
    }

    private List<DisclosuresBasicVO> getDisclosuresBasicVOS(DisclosuresBasicVO disclosuresBasicVO) {
        DisclosuresBasicVO param = new DisclosuresBasicVO();
        param.setBusinessGroup(disclosuresBasicVO.getBusinessGroup());
        param.setGeoCode(disclosuresBasicVO.getGeoCode());
        param.setFiscalYear(disclosuresBasicVO.getFiscalYear());
        param.setQuarter(disclosuresBasicVO.getQuarter());
        param.setDueDate(disclosuresBasicVO.getDueDate());
        return disclosuresBasicService.selectDisclosuresBasic(param);
    }

    @ApiOperation(value = "Update Disclosures Basic")
    @PostMapping("update")
    public ResultDTO<Object> update(@RequestBody DisclosuresBasicVO disclosuresBasicVO) {
        if (disclosuresBasicVO == null || StringUtils.isBlank(disclosuresBasicVO.getBasicId())) {
            return ResultDTO.fail("disclosuresBasic param is null");
        }
        disclosuresBasicVO.setDueDate(DateUtils.getDateZero(disclosuresBasicVO.getDueDate()));
        DisclosuresBasicVO oldDisclosuresBasicVO  = disclosuresBasicService.selectDisclosuresBasicById(Long.parseLong(disclosuresBasicVO.getBasicId()));
        if (StringUtils.equals(disclosuresBasicVO.getBusinessGroup(), oldDisclosuresBasicVO.getBusinessGroup()) &&
            StringUtils.equals(disclosuresBasicVO.getGeoCode(), oldDisclosuresBasicVO.getGeoCode()) &&
            StringUtils.equals(disclosuresBasicVO.getFiscalYear(), oldDisclosuresBasicVO.getFiscalYear()) &&
            StringUtils.equals(disclosuresBasicVO.getQuarter(), oldDisclosuresBasicVO.getQuarter()) &&
            StringUtils.equals(disclosuresBasicVO.getQdpFocal(), oldDisclosuresBasicVO.getQdpFocal()) &&
            disclosuresBasicVO.getDueDate().equals(oldDisclosuresBasicVO.getDueDate()) ) { //待验证
            return ResultDTO.fail("Not modified, please check it");
        }
        // 校验是否重复
        List<DisclosuresBasicVO> disclosuresBasicVOList = getDisclosuresBasicVOS(disclosuresBasicVO);
        if (CollectionUtils.isNotEmpty(disclosuresBasicVOList)) {
            if (disclosuresBasicVOList.size() == 1 && StringUtils.equals(disclosuresBasicVOList.get(0).getBasicId(),disclosuresBasicVO.getBasicId())) {
                logger.info("### DisclosuresBasicController disclosuresBasicVOList size is 1 and basicId is equal");
            } else {
                return ResultDTO.fail("Same disclosure already existed, please check it");
            }
        }
        disclosuresBasicVO.setBasicNumber(oldDisclosuresBasicVO.getBasicNumber());
        disclosuresBasicVO.setDelFlag(oldDisclosuresBasicVO.getDelFlag());
        disclosuresBasicVO.setCreateBy(oldDisclosuresBasicVO.getCreateBy());
        disclosuresBasicVO.setCreateTime(oldDisclosuresBasicVO.getCreateTime());
        disclosuresBasicVO.setStatus(oldDisclosuresBasicVO.getStatus());
        int count = disclosuresBasicService.updateDisclosuresBasicByExample(disclosuresBasicVO);
        return ResultDTO.success(count);
    }

    @ApiOperation(value = "Update Disclosures Basic Status")
    @PostMapping("status/update")
    public ResultDTO<Object> updateStatus(@RequestBody DisclosuresBasicVO disclosuresBasicVO) {
        if (disclosuresBasicVO == null || StringUtils.isAnyBlank(disclosuresBasicVO.getBasicId(), disclosuresBasicVO.getUpdateBy())) {
            return ResultDTO.fail("disclosuresBasic param is null");
        }
        DisclosuresBasicVO param = new DisclosuresBasicVO();
        param.setBasicId(disclosuresBasicVO.getBasicId());
        param.setStatus(disclosuresBasicVO.getStatus());
        param.setUpdateBy(disclosuresBasicVO.getUpdateBy());
        int count = disclosuresBasicService.updateDisclosuresBasic(param);

        // 如果新增状态为Reviewed, 则发送邮件，并记录 disclosure_approve_log
        if (StringUtils.equals(disclosuresBasicVO.getStatus(), DisclosureBasicEnum.REVIEWED.code)) {
            DisclosuresBasicVO curDisclosuresBasic = disclosuresBasicService.selectDisclosuresBasicById(Long.parseLong(disclosuresBasicVO.getBasicId()));
            DisclosureApproveLogVO disclosureApproveLogVO = new DisclosureApproveLogVO();
            disclosureApproveLogVO.setDisclosuresId(disclosuresBasicVO.getBasicId());
            disclosureApproveLogVO.setApproveUuid(UUID.randomUUID().toString());
            disclosureApproveLogVO.setDisclosureNumber(curDisclosuresBasic.getBasicNumber());
            disclosureApproveLogVO.setCompleted(DisclosureConstant.NO);
            // assignee字段待填充 todo
            disclosureApproveLogService.insertDisclosureApproveLog(disclosureApproveLogVO);
            // todo 发送邮件
            logger.info("### DisclosuresBasicController send email start");
            iTextPDFService.exportPdfAndSendEmail(disclosuresBasicVO, curDisclosuresBasic, disclosureApproveLogVO);
        }

        if (StringUtils.equals(disclosuresBasicVO.getStatus(), DisclosureBasicEnum.APPROVED.code)) {
            DisclosureApproveLogVO disclosureApproveLog = disclosureApproveLogService.getDisclosureApproveLog(disclosuresBasicVO.getApproveUuid());
            if (disclosureApproveLog == null) {
                logger.info("### DisclosuresBasicController disclosureApproveLog is null");
                return ResultDTO.fail("Disclosure Approve param is null");
            }
            if (StringUtils.equals(DisclosureConstant.YES, disclosureApproveLog.getCompleted())) {
                //如果已经审批过了
                logger.info("### DisclosuresBasicController disclosureApproveLog completed is yes");
                return ResultDTO.fail("Disclosure has already been approved");
            }
            // 如果未审批过
            logger.info("### DisclosuresBasicController approved disclosuresBasicVO is {}", JSON.toJSONString(disclosuresBasicVO));
            disclosureApproveLog.setApprover(disclosuresBasicVO.getApprover());
            disclosureApproveLog.setApproveTime(new Date());
            disclosureApproveLog.setCompleted(DisclosureConstant.YES);
            int approveLogCount = disclosureApproveLogService.updateDisclosureApproveLog(disclosureApproveLog);
            logger.info("### DisclosuresBasicController updateStatus approveLogCount is {}", approveLogCount);
        }

        return ResultDTO.success(String.valueOf(count));
    }

    @ApiOperation(value = "Del Disclosures Basic")
    @PostMapping("remove")
    @OperLog(title = "Del Disclosures Basic", businessType = BusinessType.DELETE)
    public ResultDTO<Object> remove(@RequestParam("ids") String ids, @RequestParam("update") String updateBy)
    {
        if (StringUtils.isBlank(ids)) {
            return ResultDTO.fail("disclosuresBasic param is null");
        }
        List<String> idList = Arrays.asList(ids.split(","));
        int count = disclosuresBasicService.deleteDisclosuresBasicByIds(idList, updateBy);
        return ResultDTO.success(count);
    }

}
