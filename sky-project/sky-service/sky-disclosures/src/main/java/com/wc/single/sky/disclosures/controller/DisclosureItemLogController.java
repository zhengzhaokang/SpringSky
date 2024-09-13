package com.wc.single.sky.disclosures.controller;

import com.github.pagehelper.PageInfo;
import com.wc.single.sky.common.core.domain.ResultDTO;
import com.wc.single.sky.common.utils.StringUtils;
import com.wc.single.sky.disclosures.service.DisclosureItemLogService;
import com.wc.single.sky.disclosures.vo.DisclosureItemLogVO;
import com.wc.single.sky.common.core.controller.BaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("disclosuresItemLog")
@RestController
@RequestMapping("disclosures/item/log")
@Slf4j
public class DisclosureItemLogController extends BaseController {

    @Autowired
    private DisclosureItemLogService disclosureItemLogService;

    @PostMapping("list")
    public ResultDTO<Object> list(@RequestBody DisclosureItemLogVO disclosureItemLogVO) {
        if (StringUtils.isBlank(disclosureItemLogVO.getBusiness())) {
            log.info("### DisclosureItemLogController list disclosureItemLogVO Business is null");
            return ResultDTO.success(getDataDefaultTable());
        }
        startPage();
        PageInfo<DisclosureItemLogVO> disclosureItemLogVOPageInfo = disclosureItemLogService.selectDisclosureItemLogPage(disclosureItemLogVO);
        if  (disclosureItemLogVOPageInfo == null) {
            log.info("### DisclosureItemLogController list disclosureItemLogVOPageInfo is null");
            return ResultDTO.success(getDataDefaultTable());
        }
        return ResultDTO.success(getDataTable(disclosureItemLogVOPageInfo));
    }
}
